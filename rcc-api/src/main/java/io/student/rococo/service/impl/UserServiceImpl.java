package io.student.rococo.service.impl;

import io.student.rococo.data.entity.UserEntity;
import io.student.rococo.data.repository.UserRepository;
import io.student.rococo.exception.ResourceNotFoundException;
import io.student.rococo.model.UserJson;
import io.student.rococo.service.api.UserService;
import io.student.rococo.util.StringAsBytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserJson createUser(String username) {
        return UserJson.fromEntity(userRepository.findByUsername(username)
                .orElseGet(() -> {
                    UserEntity userEntity = new UserEntity();
                    userEntity.setUsername(username);
                    return userRepository.save(userEntity);
                })
        );
    }

    @Override
    @Transactional
    public UserJson updateUser(UserJson user) {
        UserEntity userEntity = getUser(user.username());
        userEntity.setFirstname(user.firstname());
        userEntity.setLastname(user.lastname());
        userEntity.setAvatar(new StringAsBytes(user.avatar()).bytes());
        return UserJson.fromEntity(userRepository.save(userEntity));
    }

    @Override
    @Transactional(readOnly = true)
    public UserJson getCurrentUser(String username) {
        return UserJson.fromEntity(
                getUser(username)
        );
    }

    @Transactional(readOnly = true)
    public UserJson findUserById(String id) {
        return UserJson.fromEntity(userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден по id: " + id)));
    }

    private UserEntity getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("Пользователь не найден по username: " + username));
    }
}
