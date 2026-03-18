package com.ds.devsuuser.infraestructure.exceptions;

import lombok.Getter;

@Getter
public enum ErrorCode {
    ROUTE_NOT_FOUND(0, "Route not found", 404),
    RESOURCE_ALREADY_LOCKED(2, "Resource already locked", 400),
    ERROR_LOCKING_RESOURCE(3, "Error locking resource", 423),



    CLIENT_NOT_FOUND(100, "Client not found", 404),
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
