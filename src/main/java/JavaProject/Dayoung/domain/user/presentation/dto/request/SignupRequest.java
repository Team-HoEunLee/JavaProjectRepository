package JavaProject.Dayoung.domain.user.presentation.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import reactor.util.annotation.Nullable;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignupRequest {

    @NotNull(message = "아이디을 입력하세요")
    private String accountId;

    @NotNull(message = "비밀번호를 입력하세요")
    private String password;

    @NotNull(message = "닉네임을 입력하세요")
    private String name;

    @Nullable
    private String introduction;

    @Nullable
    private List<Long> areaId;
}
