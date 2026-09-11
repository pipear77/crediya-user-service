package co.com.pragma.usecase.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BusinessException extends RuntimeException {
    private final BusinessErrorMessage businessErrorMessage;

    @Override
    public String getMessage() {
        return businessErrorMessage.getMensaje();
    }
}