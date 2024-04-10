package misoya.healing.domain.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {

    private final RedisRepository repository;

    /**
     * @info : Redis - save (User)
     * @name : addUser
     * @version : 1.0.0
     * @Description :
     */
    @Transactional
    public RedisUser addUser(RedisUser user) {
        // save
        RedisUser save = repository.save(user);

        // find
        Optional<RedisUser> result = repository.findById(save.getId());

        // Handling
        // 해당 data 존재시 return.
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new RuntimeException("Database has no Data");
        }
    }//save

    /**
     * @info : Redis - get
     * @name : name
     * @version : 1.0.0
     * @Description :
     */

    @Transactional(readOnly = true)
    public RedisUser getUserById(String reqId) {
        Optional<RedisUser> result = repository.findById(reqId);

        // Handling
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new RuntimeException("Database has no Data");
        }
    }
}