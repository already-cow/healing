package misoya.healing.domain.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;


@Entity
@AllArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가
    private Long id;
    private String userid;
    private String password;
    private String username;
    // 04.05 security 추가하면서..
    @Convert(converter = UserRole.UserRoleConverter.class)
    private UserRole userRole;

    public User() {
    }

    /**
     * UserDetails 의 인터페이스로 사용
     * 오버라이딩 된 메소드들이 그 역할을 함
     * */

    // 등급에 따라서 로그인하기 ->
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return Collections.unmodifiableList(AuthorityUtils.createAuthorityList(this.userRole.getRole()));
    }

    //
    @Override
    public String getUsername() {
        return this.userid;
    }

    // 만료된 회원인가 ? 일단 다 true 로 열어둠
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 잠긴회원(일시정지)인가 ?  이것도 열어둠
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 이건 안쓸꺼임 : 토큰방
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 이것도 안쓸꺼임 true
    @Override
    public boolean isEnabled() {
        return true;
    }


}
