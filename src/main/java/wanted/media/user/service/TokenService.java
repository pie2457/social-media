package wanted.media.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import wanted.media.exception.NotFoundException;
import wanted.media.user.config.TokenProvider;
import wanted.media.user.domain.Token;
import wanted.media.user.domain.User;
import wanted.media.user.domain.UserDetail;
import wanted.media.user.dto.TokenRequestDto;
import wanted.media.user.dto.TokenResponseDto;
import wanted.media.user.repository.TokenRepository;
import wanted.media.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenProvider tokenProvider;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    // 액세스 토큰 재발행
    public TokenResponseDto reIssueToken(TokenRequestDto requestDto) {
        if (!tokenProvider.validToken(requestDto.getRefreshToken())) { // 리프레시 토큰 만료 기간 지났을 경우
            throw new IllegalArgumentException("다시 로그인해주세요.");
        }

        User user = findUserByToken(requestDto);

        Token storedToken = tokenRepository.findByUserId(user.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 토큰입니다."));

        if (!storedToken.getRefreshToken().equals(requestDto.getRefreshToken())) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        String accessToken = tokenProvider.makeToken(user.getAccount(), "access");
        String refreshToken = tokenProvider.makeToken(user.getAccount(), "refresh");

        storedToken.updateToken(refreshToken);
        tokenRepository.save(storedToken);

        return new TokenResponseDto(accessToken, refreshToken);
    }

    public User findUserByToken(TokenRequestDto requestDto) {
        Authentication authentication = tokenProvider.getAuthentication(requestDto.getAccessToken());
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        String account = userDetail.getUsername();
        return userRepository.findByAccount(account)
                .orElseThrow(() -> new NotFoundException("회원을 찾을 수 없습니다."));
    }

}
