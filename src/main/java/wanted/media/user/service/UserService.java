package wanted.media.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import wanted.media.user.domain.Code;
import wanted.media.user.domain.Grade;
import wanted.media.user.domain.User;
import wanted.media.user.dto.SignUpRequest;
import wanted.media.user.dto.SignUpResponse;
import wanted.media.user.dto.UserCreateDto;
import wanted.media.user.repository.CodeRepository;
import wanted.media.user.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CodeRepository codeRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserValidator userValidator;
    private final GenerateCode generateCode;


    //회원가입
    public SignUpResponse signUp(SignUpRequest request) {
        // 1. 사용자 입력내용 검증
        userValidator.validateRequest(request);
        // 2. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        // 3. 인증코드 생성
        String verificationCode = generateCode.codeGenerate();
        // 4. User 객체 생성
        User user = User.builder()
                .account(request.getAccount())
                .email(request.getEmail())
                .password(encodedPassword)
                .grade(Grade.NORMAL_USER)
                .build();
        // 5. 사용자 db 저장
        userRepository.save(user);
        // 6. Code 객체 생성
        Code code = Code.builder()
                .user(user)
                .authCode(verificationCode)
                .createdTime(LocalDateTime.now())
                .build();
        // 7. 인증코드 db 저장
        codeRepository.save(code);
        // 8. UserCreateDto 생성
        UserCreateDto userCreateDto = new UserCreateDto(user.getAccount(), user.getEmail());
        // 9. SignUpResponse 생성
        SignUpResponse signUpResponse = new SignUpResponse("회원가입이 성공적으로 완료됐습니다.", userCreateDto, verificationCode);

        return signUpResponse;
    }

}
