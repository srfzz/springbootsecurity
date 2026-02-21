package com.posts.demo.entities;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.AnyDiscriminatorImplicitValues;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="sessions")
@Builder
public class SessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  String refreshToken;

    private LocalDate lastUsedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;
}
