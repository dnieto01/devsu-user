package com.ds.devsuuser.infraestructure.config;

import com.ds.devsuuser.infraestructure.exceptions.ApiErrorResponse;
import com.ds.devsuuser.infraestructure.exceptions.ApiException;
import com.ds.devsuuser.infraestructure.exceptions.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.lang.reflect.Method;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ControllerExceptionHandlerTest {

    private final ControllerExceptionHandler handler = new ControllerExceptionHandler();

    @Test
    void handleApiExceptionReturnsExpectedBody() {
        ApiException ex = new ApiException(ErrorCode.CLIENT_NOT_FOUND);

        ResponseEntity<ApiErrorResponse> response = handler.handleApiException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ex.getCode(), response.getBody().getCode());
        assertEquals(ex.getDescription(), response.getBody().getDescription());
    }

    @Test
    void noHandlerFoundExceptionReturnsRouteNotFound() {
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getRequestURI()).thenReturn("/missing");
        NoHandlerFoundException ex = new NoHandlerFoundException("GET", "/missing", null);

        ResponseEntity<ApiErrorResponse> response = handler.noHandlerFoundException(req, ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("route_not_found", response.getBody().getCode());
    }

    @Test
    void noResourceFoundExceptionReturnsRouteNotFound() {
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getRequestURI()).thenReturn("/asset");
        NoResourceFoundException ex = new NoResourceFoundException(HttpMethod.GET, "/asset", "");

        ResponseEntity<ApiErrorResponse> response = handler.noResourceFoundException(req, ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("route_not_found", response.getBody().getCode());
    }

    @Test
    void handleValidationExceptionsReturnsFieldErrors() throws NoSuchMethodException {
        Dummy dummy = new Dummy();
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(dummy, "dummy");
        bindingResult.addError(new FieldError("dummy", "name", "name is required"));

        Method method = Dummy.class.getDeclaredMethod("setName", String.class);
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(
                new org.springframework.core.MethodParameter(method, 0), bindingResult);

        ResponseEntity<Map<String, String>> response = handler.handleValidationExceptions(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("name is required", response.getBody().get("name"));
    }

    @Test
    void handleUnknownExceptionWithApiCauseDelegatesToApiHandling() {
        ApiException cause = new ApiException(ErrorCode.CLIENT_NOT_FOUND);
        Exception ex = new Exception("wrapper", cause);

        ResponseEntity<ApiErrorResponse> response = handler.handleUnknownException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(cause.getCode(), response.getBody().getCode());
    }

    @Test
    void handleUnknownExceptionReturnsInternalErrorForGenericExceptions() {
        ResponseEntity<ApiErrorResponse> response = handler.handleUnknownException(new RuntimeException("boom"));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("internal_error", response.getBody().getCode());
        assertInstanceOf(ApiErrorResponse.class, response.getBody());
    }

    private static class Dummy {
        @SuppressWarnings("unused")
        public void setName(String name) {
        }
    }
}
