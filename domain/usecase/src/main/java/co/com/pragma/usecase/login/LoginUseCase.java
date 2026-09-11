package co.com.pragma.usecase.login;

import co.com.pragma.model.rol.gateways.RolRepository;
import co.com.pragma.model.usuario.gateways.JwtProviderRepository;
import co.com.pragma.model.usuario.gateways.PasswordEncoderRepository;
import co.com.pragma.model.usuario.gateways.UsuarioRepository;
import co.com.pragma.usecase.exceptions.BusinessErrorMessage;
import co.com.pragma.usecase.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.Map;

@RequiredArgsConstructor
public class LoginUseCase {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoderRepository passwordEncoder;
    private final JwtProviderRepository jwtProvider;
    private final RolRepository rolRepository;

    public Mono<String> login(String correo, String contrasena) {
        return usuarioRepository.findByCorreo(correo)
                .switchIfEmpty(Mono.error(new BusinessException(BusinessErrorMessage.CREDENCIALES_INVALIDAS)))
                .flatMap(usuario -> {
                    // Validar contraseña
                    if (!passwordEncoder.matches(contrasena, usuario.getContrasena())) {
                        return Mono.error(new BusinessException(BusinessErrorMessage.CREDENCIALES_INVALIDAS));
                    }

                    // Buscar el rol y generar el token SECRETO (solo con el rol)
                    return rolRepository.findById(usuario.getIdRol())
                            .map(rol -> {
                                // 🔒 SEGURIDAD: Solo inyectamos el Rol para autorizar endpoints.
                                // El ID del usuario viaja en el "Subject" del token. NUNCA datos sensibles.
                                Map<String, Object> claims = Map.of("rol", rol.getName());

                                return jwtProvider.generateToken(usuario.getId(), claims);
                            });
                });
    }
}