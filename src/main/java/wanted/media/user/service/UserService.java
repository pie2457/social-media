package wanted.media.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wanted.media.user.config.TokenProvider;
import wanted.media.user.domain.Token;
import wanted.media.user.domain.User;
import wanted.media.user.dto.UserLoginRequestDto;
import wanted.media.user.dto.UserLoginResponseDto;
import wanted.media.user.repository.TokenRepository;
import wanted.media.user.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final TokenProvider tokenProvider;

    public UserLoginResponseDto login(UserLoginRequestDto requestDto) {
        User user = userRepository.findByAccount(requestDto.getAccount())
                .orElseThrow(() -> new IllegalArgumentException("account나 password를 다시 확인해주세요."));
        if (!requestDto.getPassword().equals(user.getPassword())) // password 암호화 저장시 변경하기
            throw new IllegalArgumentException("account나 password를 다시 확인해주세요.");

        Optional<Token> refreshToken = tokenRepository.findByUserId(user.getUserId()); // 리프레시 토큰 있는지 확인
        String newRefreshToken = tokenProvider.makeToken(requestDto.getAccount(), "refresh"); // 새 리프레시 토큰
        if (refreshToken.isPresent()) { // 리프레시 토큰 있을 경우
            refreshToken.get().updateToken(newRefreshToken); // 새 토큰으로 업데이트
        } else { // 리프레시 토큰 없을 경우
            tokenRepository.save(new Token(newRefreshToken, user)); // 새 토큰 저장
        }

        return new UserLoginResponseDto(user.getUserId(), tokenProvider.makeToken(requestDto.getAccount(), "access"));
    }
}
