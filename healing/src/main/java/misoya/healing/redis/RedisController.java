import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import misoya.healing.redis.RedisService;
import misoya.healing.redis.RedisUser;
import org.springframework.web.bind.annotation.*;

/************
 * @info : Redis User Controller
 * @name : UserController
 * @version : 1.0.0
 * @Description :
 ************/
@RestController
@RequiredArgsConstructor
@Slf4j
public class RedisController {

    private final RedisService redisService;

    /**
     * @info : Redis에 User 정보를 저장한다.
     * @name : addUser
     * @version : 1.0.0
     */
    @PostMapping("/redis/v1/post")
    public RedisUser addUser(@RequestBody RedisUser user) {
        log.info("Controller Request: {}", user);

        RedisUser result = redisService.addUser(user);

        log.info("Controller result: {}", result);

        return result;
    }// save


    /**
     * @info : Redis에 ID 값으로 유저 정보를 가져온다.
     * @name : getUser
     * @version : 1.0.0
     * @Description :
     */
    @GetMapping("/redis/v1/getUser")
    public RedisUser getUser(@RequestParam String reqId) {
        RedisUser userById = redisService.getUserById(reqId);
        return userById;
    }
}
