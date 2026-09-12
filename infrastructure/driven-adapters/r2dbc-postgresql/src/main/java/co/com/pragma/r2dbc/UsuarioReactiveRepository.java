package co.com.pragma.r2dbc;

import co.com.pragma.r2dbc.entity.UsuarioEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UsuarioReactiveRepository extends ReactiveCrudRepository<UsuarioEntity, String>, ReactiveQueryByExampleExecutor<UsuarioEntity> {
    Mono<Boolean> existsByCorreo(String correo);
    Mono<Boolean> existsByNumeroDocumento(String numeroDocumento);
    Mono<UsuarioEntity> findByCorreo(String correo);
    Mono<UsuarioEntity> findByNumeroDocumento(String numeroDocumento);
}