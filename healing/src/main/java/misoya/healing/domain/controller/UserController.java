package misoya.healing.domain.controller;
import lombok.extern.slf4j.Slf4j;
import misoya.healing.domain.model.User;
import misoya.healing.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/user")
public class UserController{

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public String add(@RequestBody User user) {
        userService.saveUser(user);
        return "New user save";
    }



}