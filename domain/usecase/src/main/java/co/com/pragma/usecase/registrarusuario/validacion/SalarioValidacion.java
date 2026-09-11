package co.com.pragma.usecase.registrarusuario.validacion;

import co.com.pragma.model.usuario.Usuario;
import co.com.pragma.usecase.exceptions.BusinessErrorMessage;
import co.com.pragma.usecase.exceptions.BusinessException;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;

public class SalarioValidacion implements UsuarioValidationStrategy {
    private static final BigDecimal MAX_SALARIO = new BigDecimal("15000000");

    @Override
    public Mono<Void> validar(Usuario usuario) {
        if (usuario.getSalarioBase() == null ||
                usuario.getSalarioBase().compareTo(BigDecimal.ZERO) < 0 ||
                usuario.getSalarioBase().compareTo(MAX_SALARIO) > 0) {
            return Mono.error(new BusinessException(BusinessErrorMessage.SALARIO_INVALIDO));
        }
        return Mono.empty();
    }
}