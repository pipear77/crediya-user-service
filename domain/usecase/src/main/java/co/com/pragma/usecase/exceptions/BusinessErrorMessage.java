package co.com.pragma.usecase.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BusinessErrorMessage {
    CREDENCIALES_INVALIDAS("AUTH_001", "Correo o contraseña incorrectos"),
    CORREO_DUPLICADO("USER_001", "El correo electrónico ya está registrado"),
    DOCUMENTO_DUPLICADO("USER_002", "El número de documento ya está registrado"),
    SALARIO_INVALIDO("USER_003", "El salario base está fuera del rango permitido"),
    DATOS_INCOMPLETOS("USER_004", "Faltan datos obligatorios para el registro");

    private final String codigo;
    private final String mensaje;
}