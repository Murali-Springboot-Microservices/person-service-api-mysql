package com.mn.springboot.person_service_api_mysql.controller;

import com.mn.springboot.person_service_api_mysql.entity.Person;
import com.mn.springboot.person_service_api_mysql.service.PersonService;
import com.mn.springboot.person_service_api_mysql.utiliities.Constants;
import org.hibernate.mapping.Any;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonController.class)
public class PersonControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PersonService personService;

    @Autowired
    private ObjectMapper objectMapper; // Helper to deal with JSON

    @Test
    public void getAllPersons_ReturnsListOfPersons() throws Exception {
        // Arrange
        Person person1 = new Person(1L, "lastname1");
        Person person2 = new Person(2L, "lastname2");
        List<Person> mockPersons = Arrays.asList(person1, person2);

        // Define the behavior of the mocked service
        when(personService.getAllPersons()).thenReturn(mockPersons);

        // Act & Assert
        mockMvc.perform(get("/api/v1/persons")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2))) // Checks the size of the returned JSON array
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].lastName", is("lastname1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].lastName", is("lastname2")));
    }

    @Test
    public void getAllPersons_ReturnsEmptyListOfPersonsAlways() throws Exception {
        // Arrange

        List<Person> mockPersons = null; //new ArrayList<>();

        // Define the behavior of the mocked service
        when(personService.getAllPersons()).thenReturn(mockPersons);

        // Act & Assert
        mockMvc.perform(get("/api/v1/persons")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0))); // Checks the size of the returned JSON array

    }

    @Test
    public void givenPersonId_whenGetEmployeeById_thenReturnJson() throws Exception {
        // Given
        Long personId = 1L;
        Person person = new Person(1L, "lastname1");

        given(personService.getPersonById(personId)).willReturn(Optional.of(person));

        // When & Then
        mockMvc.perform(get("/api/v1/persons/{id}", personId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(personId.intValue())))
                .andExpect(jsonPath("$.lastName", is(person.getLastName())));
    }

    @Test
    public void givenInvalidPersonId_whenGetPersonById_thenReturnJson() throws Exception {
        // Given
        Long personId = 1L;
        Person person = new Person(0, null, Constants.ID_NOT_FOUND_DB);

        given(personService.getPersonById(personId)).willReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/v1/persons/{id}", personId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
               .andExpect(jsonPath("$.statusMessage", is(person.getStatusMessage())));
    }

    @Test
    void createPerson_ReturnsCreatedStatusAndPerson() throws Exception {
        // Arrange
        Person inputPerson = new Person("Alice", "Wonderland");
        Person createdPerson = new Person(1L, "Alice", "Wonderland");

        // Define the behavior of the mocked service
        when(personService.createPerson(any(Person.class))).thenReturn(createdPerson);

        // Act & Assert
        mockMvc.perform(post("/api/v1/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputPerson))) // Convert object to JSON bytes
                .andExpect(status().isOk()) //
                .andExpect(jsonPath("$.id").value(1L)) // Verify the ID in the response body
                .andExpect(jsonPath("$.lastName").value("Alice")); // Verify the name in the response body
    }

    @Test
    void verifyInvalidPerson_ReturnsStatusAndPerson() throws Exception {
        // Arrange
        Person inputPerson = new Person();


        // Define the behavior of the mocked service
        //when(personService.createPerson(any(Person.class))).thenReturn(createdPerson);

        // Act & Assert
        mockMvc.perform(post("/api/v1/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputPerson))) // Convert object to JSON bytes
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusMessage").value("Unable to process request. Person lastname is required")); // Verify the ID in the response body

    }
}
