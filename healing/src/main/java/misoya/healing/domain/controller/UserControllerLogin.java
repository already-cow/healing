package misoya.healing.domain.controller;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import misoya.healing.domain.model.User;
import misoya.healing.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserControllerLogin {

    // 서비스 안타는 이유는 시큐리티 -> 로그인 정의해놓았기 때문
    @GetMapping("/login")
    public String login(HttpSession session) {

        // 로그인 시 세션 같이 내려준다
        String errorMessage = (String) session.getAttribute("errorMessage");

        // 로그인 실패 오류 메세지
        if(errorMessage != null) {
            return errorMessage;
        }

        return "로그인 성공";
    }

    // 로그아웃 -> 리액트에 버튼 링크 처리/logout

}
