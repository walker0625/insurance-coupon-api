package com.backend.coupon.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.coupon.entity.Person;

public interface PersonRepository extends JpaRepository<Person, UUID> {
}
