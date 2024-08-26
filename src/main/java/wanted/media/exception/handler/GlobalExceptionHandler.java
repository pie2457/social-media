package wanted.media.exception.handler;

import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import wanted.media.exception.CustomException;
import wanted.media.exception.ErrorCode;
import wanted.media.exception.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException e) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(400, e.getMessage()));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        ErrorResponse response = new ErrorResponse(errorCode);
        return new ResponseEntity<>(response, errorCode.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse response = new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, ErrorCode.INTERNAL_SERVER_ERROR.getStatus());
    }
}
