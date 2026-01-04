package com.mn.springboot.person_service_api_mysql.utiliities;

import com.mn.springboot.person_service_api_mysql.entity.Person;

public abstract class PersonValidator {

    public static boolean validatePerson(Person newPerson) {
        return (newPerson == null || newPerson.getLastName().isEmpty());
    }
}

