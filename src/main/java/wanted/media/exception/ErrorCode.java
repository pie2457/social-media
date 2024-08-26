package wanted.media.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 엔티티입니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "클라이언트의 입력 값을 확인해주세요."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "권한이 없습니다.");

    private final HttpStatus status;
    private final String message;
}
