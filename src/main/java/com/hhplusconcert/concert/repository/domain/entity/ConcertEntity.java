package com.hhplusconcert.concert.repository.domain.entity;

import com.hhplusconcert.concert.repository.domain.Concert;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CONCERT")
@Entity
public class ConcertEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    public static Concert toDomain(ConcertEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Concert(
                entity.getId(),
                entity.getTitle()
        );
    }

    public static ConcertEntity fromDomain(Concert domain) {
        if (domain == null) {
            return null;
        }
        return new ConcertEntity(
                domain.getId(), domain.getTitle()
        );
    }
}
