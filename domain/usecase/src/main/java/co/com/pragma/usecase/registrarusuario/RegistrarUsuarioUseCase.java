package co.com.pragma.usecase.registrarusuario;

import co.com.pragma.model.usuario.Usuario;
import co.com.pragma.model.usuario.gateways.PasswordEncoderRepository;
import co.com.pragma.model.usuario.gateways.UsuarioRepository;
import co.com.pragma.usecase.exceptions.BusinessErrorMessage;
import co.com.pragma.usecase.exceptions.BusinessException;
import co.com.pragma.usecase.registrarusuario.validacion.UsuarioValidationStrategy;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class RegistrarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoderRepository passwordEncoder;
    private final List<UsuarioValidationStrategy> validaciones; // Patrón Strategy Inyectado

    public Mono<Usuario> registrar(Usuario usuario) {
        // 1. Ejecutar Validaciones (Pipeline)
        return Mono.defer(() -> ejecutarValidaciones(usuario))
                // 2. Concurrencia: Buscar si existe correo y documento AL MISMO TIEMPO
                .then(Mono.zip(
                        usuarioRepository.existsByCorreo(usuario.getCorreo()),
                        usuarioRepository.existsByDocumentNumber(usuario.getNumeroDocumento())
                ))
                .flatMap(resultados -> {
                    boolean correoExiste = resultados.getT1();
                    boolean documentoExiste = resultados.getT2();

                    if (correoExiste) return Mono.error(new BusinessException(BusinessErrorMessage.CORREO_DUPLICADO));
                    if (documentoExiste) return Mono.error(new BusinessException(BusinessErrorMessage.DOCUMENTO_DUPLICADO));

                    // 3. Patrón Builder: Encriptar contraseña y guardar
                    Usuario usuarioAguardar = usuario.toBuilder()
                            .contrasena(passwordEncoder.encode(usuario.getContrasena()))
                            .build();

                    return usuarioRepository.save(usuarioAguardar);
                });
    }

    private Mono<Void> ejecutarValidaciones(Usuario usuario) {
        return Flux.fromIterable(validaciones)
                .concatMap(estrategia -> estrategia.validar(usuario))
                .then();
    }
}