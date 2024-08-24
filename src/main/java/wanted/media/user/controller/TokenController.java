package wanted.media.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wanted.media.user.dto.TokenRequestDto;
import wanted.media.user.dto.TokenResponseDto;
import wanted.media.user.service.TokenService;

@RestController
@RequestMapping("/api/token")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/reissue")
    public ResponseEntity<TokenResponseDto> reIssueToken(@RequestBody TokenRequestDto requestDto) {
        TokenResponseDto responseDto = tokenService.reIssueToken(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
