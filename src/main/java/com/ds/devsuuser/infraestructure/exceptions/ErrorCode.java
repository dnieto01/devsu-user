package com.ds.devsuuser.infraestructure.exceptions;

import lombok.Getter;

@Getter
public enum ErrorCode {
    ROUTE_NOT_FOUND(0, "Route not found", 404),
    CLIENT_NOT_FOUND(1001, "Client not found", 404),
    TEMP(1000, "Temp", 404),
    ;

    private String code;
    private String message;
    private int statusCode;

    ErrorCode(int code, String message, Integer statusCode) {
        this.code = String.format("%s", code);
        this.message = message;
        this.statusCode = statusCode;
    }

    ErrorCode(int code, String message) {
        this(code, message, 500);
    }

}
