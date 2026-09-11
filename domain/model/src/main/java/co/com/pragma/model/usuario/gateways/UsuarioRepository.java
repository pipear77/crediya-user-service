package co.com.pragma.model.usuario.gateways;

import co.com.pragma.model.usuario.Usuario;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UsuarioRepository {
    Mono<Usuario> save(Usuario usuario);
    Mono<Boolean> existsByCorreo(String correo);
    Mono<Boolean> existsByDocumentNumber(String documentNumber);
    Mono<Usuario> findByCorreo(String correo);
    Mono<Usuario> findByNumeroDocumento(String documentNumber);
    Flux<Usuario> findAllUsuarios();
}