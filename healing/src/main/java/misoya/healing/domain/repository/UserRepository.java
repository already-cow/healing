package misoya.healing.domain.repository;

import misoya.healing.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends
        JpaRepository<User, Long>,
        QuerydslPredicateExecutor<User> {

    @Query("SELECT m FROM User m WHERE m.userid = :userid")
    Optional<UserDetails> findByUserId(@Param("userid") String username);
}
