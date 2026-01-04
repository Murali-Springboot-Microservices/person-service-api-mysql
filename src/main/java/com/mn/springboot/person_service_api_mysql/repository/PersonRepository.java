package com.mn.springboot.person_service_api_mysql.repository;

import com.mn.springboot.person_service_api_mysql.entity.Person;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<@NonNull Person, @NonNull Long> {
}
