package com.mn.springboot.person_service_api_mysql.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name="base_person")
@Data
public class Person {

    @Id
    private Long id;
    private String lastName;
    private String firstName;
    @Column(name = "dob", columnDefinition = "TIMESTAMP")
    private Instant dob; //maps to timestamp and internally stores UTC
    private String statusMessage;
    @CreationTimestamp
    @Column(name = "createdDateTime", nullable = false, updatable=false)
    private  LocalDateTime createdDateTime;
}
