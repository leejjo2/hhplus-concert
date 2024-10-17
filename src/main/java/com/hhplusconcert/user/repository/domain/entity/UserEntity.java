package com.hhplusconcert.user.repository.domain.entity;

import com.hhplusconcert.user.repository.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USER")
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private int amount;

    public static User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        return new User(
                entity.getId(),
                entity.getUserId(),
                entity.getAmount()
        );
    }

    public static UserEntity fromDomain(User domain) {
        if (domain == null) {
            return null;
        }
        return new UserEntity(
                domain.getId(),
                domain.getUserId(),
                domain.getAmount()
        );
    }
}
