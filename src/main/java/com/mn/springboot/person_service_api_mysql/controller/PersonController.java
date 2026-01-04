package com.mn.springboot.person_service_api_mysql.controller;

import com.mn.springboot.person_service_api_mysql.entity.Person;
import com.mn.springboot.person_service_api_mysql.service.PersonService;
import com.mn.springboot.person_service_api_mysql.utiliities.PersonValidator;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/persons")
public class PersonController {

    @Autowired
    PersonService personService;

    @GetMapping
    private ResponseEntity<@NonNull List<Person>> getAllPersons(){

        List<Person> personList = personService.getAllPersons();

        if (personList==null)
            return ResponseEntity.ok(Collections.emptyList());

        return ResponseEntity.ok(personList);
    }

    @GetMapping ("/{id}")
    private ResponseEntity<Optional<Person>> getPersonById(@PathVariable Long id){
        Optional<Person> person = personService.getPersonById(id);

        if (person.isEmpty()) {
            person.map(p -> {p.setStatusMessage("Person Id not found");
            return ResponseEntity.ok(p);});
        }

        return ResponseEntity.ok(person);
    }

    @PostMapping
    private ResponseEntity<@NonNull Person> createPerson(@RequestBody Person newPerson){


        //caller can send a null, good to check. Validators typically check individual fields.
        //implement business logic, do not rely on the caller and send custom messages.
        //next example, we will implement @valid in the model object.
        if (PersonValidator.validatePerson(newPerson)) {
            Person errPerson = new Person();
            
            errPerson.setStatusMessage("Unable to process request. Person lastname is required");
            return ResponseEntity.ok(errPerson);
        }

        Person person = personService.savePerson(newPerson);
        return ResponseEntity.ok(person);
    }

    @PutMapping
    private ResponseEntity<Person> updatePerson(@PathVariable Long id,
                                                         @RequestBody Person updatePerson){


        return personService.getPersonById(id)
                .map(person -> {
                    person.setId(id);
                    person.setFirstName(updatePerson.getFirstName());
                    person.setLastName(updatePerson.getLastName());

                    Person latestPerson = personService.savePerson(person);
                    return ResponseEntity.ok(latestPerson);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    private ResponseEntity<@NonNull String> deletePerson(@PathVariable Long id){
        String status = personService.deletePerson(id);
        return ResponseEntity.ok(status);
    }
}
