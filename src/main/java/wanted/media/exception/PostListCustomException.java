package wanted.media.exception;

import lombok.Getter;

@Getter
public class PostListCustomException extends RuntimeException {
    private final ErrorCode errorCode;

    public PostListCustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
