package com.mn.springboot.person_service_api_mysql.utiliities;

import com.mn.springboot.person_service_api_mysql.entity.Person;

public abstract class PersonValidator {

    //Creating static methods can cause concurrency issues unless synchronized which is an
    //overhead. Might be better to create small objects instead. This is just to show
    //synchronization.
    public static synchronized boolean validatePerson(Person newPerson) {
        return (newPerson == null || newPerson.getLastName() == null
                || newPerson.getLastName().isEmpty());
    }
}

