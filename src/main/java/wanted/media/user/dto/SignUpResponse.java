package wanted.media.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SignUpResponse {
    private String message;
    private UserCreateDto userCreateDto; // 사용자 정보 DTO
    private String authCode; // 사용자 인증코드
}
