package misoya.healing.domain.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import misoya.healing.domain.model.User;
import misoya.healing.domain.model.UserRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${server.servlet.session.cookie.name}")
    private String sessionCookieName;
    private final MemberDetailsService userDetailsService;
    private final RequestCache requestCache = new HttpSessionRequestCache();

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .securityMatcher("/**")
//                .sessionManagement(httpSecuritySessionManagementConfigurer -> mamage)
//                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
//                        .anyRequest().permitAll())// 메서드 권한 주기 위해 요청 사용
//                .csrf()
//                .disable();
//
//        return http.build();
//    }



    // 개발환경이라 일단 다 풀어놓았음
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // HTTP Basic 인증 비활성화
        http.httpBasic(AbstractHttpConfigurer::disable);
        //CSRF 보호 비활성화
        http.csrf(AbstractHttpConfigurer::disable);
        // 모든 URL security 적용함
        http.securityMatcher("/**");
        // 세션 관리 정책 설정
        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // 모든 요청에 대한 접근 허용 (상의 후 세팅하겠음)
        http.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());
        // UsernamePasswordAuthenticationFilter 앞에 필터(걸러주기) 추가
        http.addFilterBefore(new UsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        // 로그인 처리 -> 성공, 실패 시 각기 다른 핸들러 발생
        http.formLogin(formLogin -> formLogin.loginPage("/login")
                                            .loginProcessingUrl("/login")
                                            .failureHandler(this.userAuthenticationFailureHandler())
                                            .successHandler(this.userAuthenticationSuccessHandler()));

        http.rememberMe(rememberMe -> rememberMe
        .alwaysRemember(true)
        .rememberMeCookieName(sessionCookieName + "_REMEMBER_ME")
        // 일단 세션쿠키 만료날짜 한달로 크게 잡아놓음(개발환경)
        .tokenValiditySeconds(86500 * 30)
        .key(sessionCookieName + "_KEY")
        .userDetailsService(userDetailsService)
        );

        http.logout(logout -> logout
        .logoutUrl("/logout")
         // 로그아웃하고 리다이렉트 시킬 url 필요합니다
        .logoutSuccessUrl("/")
        );


        return http.build();
    }



    // 순서 바꾸기 금지! @order 때문에 처리가 꼬일 수 있음
    public AuthenticationFailureHandler userAuthenticationFailureHandler() {
        return (request, response, exception) -> {
            HttpSession session = request.getSession();

            session.setAttribute("errorMessage", exception.getMessage());
            response.sendRedirect("/login");
        };
    }

    public AuthenticationSuccessHandler userAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            // 로그인 오류 메시지 제거
            HttpSession session = request.getSession();
            session.removeAttribute("errorMessage");

            // 로그인 전 페이지로 이동
            SavedRequest savedRequest = requestCache.getRequest(request, response);
            String targetUrl = (savedRequest != null) ? savedRequest.getRedirectUrl() : "/";

            // 최근 로그인 일시
            User user = (User) authentication.getPrincipal();
            // 이거 회의해보고 칼럼 추가할지 말지 (baseEntity) 만들어놓기
            //user.setLastLoginAt(LocalDateTime.now());
            // userRepository.save(user);

            response.sendRedirect(targetUrl);
        };
    }


    /**
     * 회원 등급 계층
     * admin(상위등급)은 하위등급의 모든 권한을 가진다 ROLE_ADMIN > ROLE_STAFF > ROLE_USER
     * return RoleHierarchy
     * */
    @Bean
    public RoleHierarchy roleHierarchy(){
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();

        // 이건 따로 해도 상관없어용
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = UserRole.values().length -1; i >= 0; i--) {
            if(UserRole.values()[i] == UserRole.NONE || UserRole.values()[i-1] == UserRole.NONE) continue;
            if(UserRole.values()[i] == UserRole.ADMIN || UserRole.values()[i-1] == UserRole.ADMIN) continue;

            stringBuilder.append(UserRole.values()[i]).append(" > ").append(UserRole.values()[i - 1]).append(" \n ");
        }
        roleHierarchy.setHierarchy(stringBuilder.toString().trim());

        return roleHierarchy;

    }

    /**
     * WebSecurity (현재 설정 파일)에서 RoleHierarchy 사용하기 위한 설정
     * */
    @Bean
    public DefaultWebSecurityExpressionHandler customWebSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy());
        return expressionHandler;
    }
    /**
     * MethodSecurity 에서 RoleHierarchy 사용하기 위한 설정
     */
    @Bean
    public DefaultMethodSecurityExpressionHandler customMethodSecurityExpressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy());
        return expressionHandler;
    }

    /**
     * 추가 작업해야힘 : 로그인 시 쿠키(세션) 같이 내려주기 : 쿠키 안 까보기 :
     * */
    private class AuthenticationFilter extends OncePerRequestFilter {

        //OncePerRequestFilter 인터페이스 안에 있는 구현체
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                        FilterChain filterChain) throws ServletException, IOException {


            // SecurityContext 에 인증 정보 저장
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            filterChain.doFilter(request, response);

        }

    }

    // password 인코더 빈 등록 : 패스워드 암호화 하는 로직
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 일단 많이 쓴 BCrypt 넣기는 했음 SHA 쓸껀지 말껀지 정하기도 해야함
        return new BCryptPasswordEncoder();
    }


}
