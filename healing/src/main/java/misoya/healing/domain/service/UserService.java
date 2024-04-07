package misoya.healing.domain.service;

import misoya.healing.domain.dto.UserJoinSaveDto;
import misoya.healing.domain.model.User;

import java.util.List;

public interface UserService {
    public User saveUser(User user);

    void saveUser2(UserJoinSaveDto dto);

    public List<User> getAllUser();

}
