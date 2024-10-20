package com.hhplusconcert.user.repository.orm;

import com.hhplusconcert.user.repository.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findById(Long id);
}
