package misoya.healing.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import misoya.healing.domain.model.UserRole;

@Data
public class UserJoinSaveDto {

    private Long id;
    @NotEmpty(message = "아이디를 입력해 주세요.")
    @NotNull(message = "아이디를 입력해 주세요.")
    private String userid;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$")
    @NotBlank(message = "비밀번호를 입력해 주세요.")
    private String password;
    private String userName;
    private UserRole userRole;
}
