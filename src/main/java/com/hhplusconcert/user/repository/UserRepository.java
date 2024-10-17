package com.hhplusconcert.user.repository;

import com.hhplusconcert.user.repository.domain.User;
import com.hhplusconcert.user.repository.domain.entity.UserEntity;
import com.hhplusconcert.user.repository.orm.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final UserJpaRepository userJpaRepository;

    // 여러 사용자 정보를 저장하는 메소드
    public void saveAll(List<User> users) {
        userJpaRepository.saveAll(users.stream().map(UserEntity::fromDomain).collect(Collectors.toList()));
    }

    // ID로 사용자를 찾는 메소드
    public User findById(Long id) {
        return userJpaRepository.findById(id)
                .map(UserEntity::toDomain)
                .orElseThrow(() -> new RuntimeException("ID가 " + id + "인 사용자를 찾을 수 없습니다."));
    }

    // 사용자를 저장하는 메소드
    public User save(User user) {
        return UserEntity.toDomain(userJpaRepository.save(UserEntity.fromDomain(user)));
    }
}
