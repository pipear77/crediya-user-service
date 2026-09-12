package co.com.pragma.r2dbc;

import co.com.pragma.model.usuario.Usuario;
import co.com.pragma.model.usuario.gateways.UsuarioRepository;
import co.com.pragma.r2dbc.entity.UsuarioEntity;
import co.com.pragma.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class UsuarioRepositoryAdapter extends ReactiveAdapterOperations<Usuario, UsuarioEntity, String, UsuarioReactiveRepository> implements UsuarioRepository {

    private final TransactionalOperator transactionalOperator;

    public UsuarioRepositoryAdapter(UsuarioReactiveRepository repository, ObjectMapper mapper, TransactionalOperator transactionalOperator) {
        // Configuramos el mapeador genérico que nos da el Scaffold
        super(repository, mapper, d -> mapper.map(d, Usuario.class));
        this.transactionalOperator = transactionalOperator;
    }

    @Override
    public Mono<Usuario> save(Usuario usuario) {
        // Convertimos el modelo de dominio a entidad, lo guardamos y aplicamos la transacción
        return this.saveData(this.toData(usuario))
                .map(this::toEntity)
                .as(transactionalOperator::transactional);
    }

    @Override
    public Mono<Boolean> existsByCorreo(String correo) {
        return repository.existsByCorreo(correo);
    }

    @Override
    public Mono<Boolean> existsByDocumentNumber(String documentNumber) {
        return repository.existsByNumeroDocumento(documentNumber);
    }

    @Override
    public Mono<Usuario> findByCorreo(String correo) {
        return repository.findByCorreo(correo).map(this::toEntity);
    }

    @Override
    public Mono<Usuario> findByNumeroDocumento(String documentNumber) {
        return repository.findByNumeroDocumento(documentNumber).map(this::toEntity);
    }

    @Override
    public Flux<Usuario> findAllUsuarios() {
        return this.findAll(); // Método heredado de ReactiveAdapterOperations
    }
}