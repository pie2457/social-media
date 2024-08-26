package wanted.media.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 엔티티입니다."),
    // 클라이언트의 입력 값에 대한 일반적인 오류 (@PathVariable, @RequestParam가 잘못되었을 때)
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "클라이언트의 입력 값을 확인해주세요.");

    private final HttpStatus status;
    private final String message;
}
