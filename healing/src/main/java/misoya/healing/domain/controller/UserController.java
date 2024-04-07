package misoya.healing.domain.controller;
import lombok.extern.slf4j.Slf4j;
import misoya.healing.domain.common.ApiResponse;
import misoya.healing.domain.dto.UserJoinSaveDto;
import misoya.healing.domain.model.User;
import misoya.healing.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * @ApiApiOperation = 24.04.05 시큐리티 설정 상 userRole 추가
     * 시큐리티 컨피그 -> RoleHierarchyROLE_ADMIN > ROLE_STAFF > ROLE_USER
     * */
//    @PostMapping("/add")
//    public String add(@RequestBody User user) {
//        userService.saveUser(user);
//        return "New user save";
//    }

    @PostMapping("/add")
    @ResponseBody
    public ApiResponse<?> memberJoin(@RequestBody UserJoinSaveDto dto){

        try{
            userService.saveUser2(dto);
            return ApiResponse.ok("New user save");
        } catch (Exception e) {
            return new ApiResponse<>(500, e.getMessage());
        }

    }

    @GetMapping("/getAll")
    public List<User> getAllUser() {
        return userService.getAllUser();
    }
}
