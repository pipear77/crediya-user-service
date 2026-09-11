package co.com.pragma.usecase.registrarusuario.validacion;

import co.com.pragma.model.usuario.Usuario;
import reactor.core.publisher.Mono;

public interface UsuarioValidationStrategy {
    Mono<Void> validar(Usuario usuario);
}