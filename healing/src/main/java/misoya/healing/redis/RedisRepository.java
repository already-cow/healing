package misoya.healing.redis;

import org.springframework.data.repository.CrudRepository;

public interface RedisRepository extends CrudRepository<RedisUser,String > {
}
