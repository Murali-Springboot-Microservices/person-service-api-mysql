package com.mn.springboot.person_service_api_mysql.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name="base_person")
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String lastName;
    private String firstName;
    @Column(name = "dob", columnDefinition = "TIMESTAMP")
    private Instant dob; //maps to timestamp and internally stores UTC
    private String statusMessage;
    @CreationTimestamp
    @Column(name = "createdDateTime", nullable = false, updatable=false)
    private  LocalDateTime createdDateTime;

    public Person(long id, String lastName) {
        this.id=id;
        this.lastName=lastName;
    }

    public Person(String lastName, String firstName) {
        this.lastName=lastName;
        this.firstName=firstName;
    }

    public Person(Long id, String lastName, String firstName) {
        this.id=id;
        this.lastName=lastName;
        this.firstName=firstName;
    }

    public Person(long id, String lastName, String statusMessage) {
        this.id=id;
        this.lastName=lastName;
        this.statusMessage=statusMessage;
    }

    public Person(){}
}
