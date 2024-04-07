package misoya.healing.domain.security;

import lombok.RequiredArgsConstructor;
import misoya.healing.domain.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

// security 안의 UserDetailsService 상속받아 구현하였음
public class MemberDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // 아이디 검증로직 -> 컨트롤러 태우면 여기 타게 구현해두기
    // String username -> userid(로그인아이디)로 사용자 정보 가저오기
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return  userRepository
                .findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 아이디 입니다."));
    }


    // 사용자 index 로 사용자 정보 가저오기
    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
        return (UserDetails) userRepository.
                findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("회원 정보를 찾을 수 없습니다."));

    }

}
