package com.mn.springboot.person_service_api_mysql.service;

import com.mn.springboot.person_service_api_mysql.repository.PersonRepository;
import com.mn.springboot.person_service_api_mysql.utiliities.Constants;
import com.mn.springboot.person_service_api_mysql.entity.Person;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    public Person createPerson(Person person){
        return personRepository.save(person);
    }

    public Person savePerson(Long id, Person person){

        return getPersonById(id)
                .map(p -> {
                    p.setId(id);
                    p.setFirstName(person.getFirstName());
                    p.setLastName(person.getLastName());
                    return personRepository.save(p);
                })
                .orElse(new Person());

    }

    public List<Person> getAllPersons(){
        return personRepository.findAll();
    }

    public Optional<Person> getPersonById(Long id){
        return personRepository.findById(id);
    }

    public String deletePerson(Long id){

        personRepository.deleteById(id);
        return Constants.DB_SUCCESS;
    }

}
