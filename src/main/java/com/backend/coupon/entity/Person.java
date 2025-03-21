package com.backend.coupon.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@DynamicUpdate
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "person")
public class Person {

    @Builder.Default
    @Id
    @Column(name = "person_id")
    UUID personId = UUID.randomUUID();

    @Column(name = "name")
    String name;

    @Column(name = "email")
    String email;

    @Column(name = "phone")
    String phone;

    @Builder.Default
    @Column(name = "created_at")
    LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    @Column(name = "updated_at")
    LocalDateTime updatedAt = LocalDateTime.now();

}