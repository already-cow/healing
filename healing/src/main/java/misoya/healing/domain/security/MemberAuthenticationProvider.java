package misoya.healing.domain.security;

import lombok.RequiredArgsConstructor;
import misoya.healing.domain.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberAuthenticationProvider implements AuthenticationProvider {


    private final MemberDetailsService memberDetailsService;
    private final PasswordEncoder passwordEncoder;


    // Authentication 안에 implements
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        // 아이디 없으면 UsernameNotFoundException 발생(서비스 안으로 타고 들어가 보세요)
        User user = (User) memberDetailsService.loadUserByUsername(username);

        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");

        // user Repository 로 가 리턴
        return new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
    }

    // AuthenticationProvider 인터페이스의
    // Authentication 타입을 해당 인증 프로바이더가 처리할 수 있는지 결정하는 데 사용
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
