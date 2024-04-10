package misoya.healing.redis;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;


/************
 * @info : Redis - Service Test - Template
 * @name : UserSerivceTest
 * @version : 1.0.0
 * @Description :
 ************/
@SpringBootTest

// Redis 테스트 https://obwo.tistory.com/133 실행 후 데이터 조회

class UserServiceTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 저장 테스트
    @Test
    void testStrings() {
        //given
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String key = "first";

        //when
        valueOperations.set(key, "helloWorld!");

        //then
        String value = valueOperations.get(key);
        Assertions.assertThat(value).isEqualTo("helloWorld!");
    }

}