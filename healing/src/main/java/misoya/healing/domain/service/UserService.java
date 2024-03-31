package misoya.healing.domain.service;

import misoya.healing.domain.model.User;

import java.util.List;

public interface UserService {
    public User saveUser(User user);

    public List<User> getAllUser();
}
