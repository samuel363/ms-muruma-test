package com.muruma.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INTERNAL_ERROR(100, "Error interno del servidor"),
    CLIENT_BAD_REQUEST(101, "Error en la peticion"),
    CLIENT_NOT_FOUND(102, "Usuario no encontrado"),
    INVALID_PASSWORD(103, "La clave no cumple con los requerimientos"),
    INVALID_CREDENTIALS(104, "usuario o clave invalida"),
    EMAIL_ALREADY_EXISTS(105, "El correo ya fue registrado"),
    ENCRYPT_PROCESS_FAILED(106, "Error de encriptando"),
    DECRYPT_PROCESS_FAILED(107, "Error de desencriptando"),
    INVALID_ID(108, "Parametro ID es invalido, requiere UUID"),
    UNAUTHORIZED_USER(109, "Usuario no autorizado");

    private final int value;
    private final String reason;
}
