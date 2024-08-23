package wanted.media.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wanted.media.user.config.TokenProvider;
import wanted.media.user.domain.User;
import wanted.media.user.dto.UserLoginRequestDto;
import wanted.media.user.dto.UserLoginResponseDto;
import wanted.media.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    public UserLoginResponseDto loginUser(UserLoginRequestDto requestDto) {
        User user = userRepository.findByAccount(requestDto.getAccount())
                .orElseThrow(() -> new IllegalArgumentException("account나 password를 다시 확인해주세요."));
        if (!requestDto.getPassword().equals(user.getPassword())) // password 암호화 저장시 변경하기
            throw new IllegalArgumentException("account나 password를 다시 확인해주세요.");
        return new UserLoginResponseDto(user.getUserId(), tokenProvider.makeToken(requestDto.getAccount()));
    }
}
