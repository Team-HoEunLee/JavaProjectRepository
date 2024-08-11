package JavaProject.Dayoung.domain.user.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignupRequest {

    @NotNull(message = "닉네임을 입력하세요")
    private String accountId;

    @NotNull(message = "이메일을 입력하세요")
    private String email;

    @NotNull(message = "이메일을 입력하세요")
    private String password;
}
