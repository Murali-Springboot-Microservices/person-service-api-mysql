package com.mn.springboot.person_service_api_mysql.controller;

import com.mn.springboot.person_service_api_mysql.entity.Person;
import com.mn.springboot.person_service_api_mysql.service.PersonService;
import com.mn.springboot.person_service_api_mysql.utiliities.Constants;
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
            Optional<Person> p = Optional.of(new Person());
            p.get().setStatusMessage(Constants.ID_NOT_FOUND_DB);
            return ResponseEntity.ok(p);
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

        //Good to have logiclike lanme + phone# as unique to avoid dups to have idempotency.
        // this will make lname + phone# as unique key in DB.
        Person person = personService.createPerson(newPerson);
        return ResponseEntity.ok(person);
    }

    //Even though JPA save handles both update & create, good to check this explicitly and
    //force caller to send the Id. Passing null in the body will open up other issues.
    //This will also give us control over idempotency.
    @PutMapping ("/{id}")
    private ResponseEntity<Person> updatePerson(@PathVariable Long id,
                                                         @RequestBody Person updatePerson){

        Person updatedPerson =  personService.savePerson(id, updatePerson);
        return ResponseEntity.ok(updatedPerson);
    }

    @DeleteMapping
    private ResponseEntity<@NonNull String> deletePerson(@PathVariable Long id){
        String status = personService.deletePerson(id);
        return ResponseEntity.ok(status);
    }
}
