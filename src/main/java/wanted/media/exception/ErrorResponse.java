package wanted.media.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {

    private final int statusCode;
    private final String message;

    public ErrorResponse(ErrorCode errorCode) {
        this.statusCode = errorCode.getStatus().value();
        this.message = errorCode.getMessage();
    }
}
