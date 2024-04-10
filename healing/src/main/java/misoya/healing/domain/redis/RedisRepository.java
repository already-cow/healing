package misoya.healing.domain.redis;

import org.springframework.data.repository.CrudRepository;

public interface RedisRepository extends CrudRepository<RedisUser,String > {
}
