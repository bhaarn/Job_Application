package com.bits.job.portal.controller;

import com.bits.job.portal.model.Person;
import com.bits.job.portal.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    // Add a person to the database
    @PostMapping("/add")
    public Person addPerson(@RequestBody Person person) {
        return personRepository.save(person);
    }

    // Get person by name
    @GetMapping("/{name}")
    public Person getPerson(@PathVariable String name) {
        return personRepository.findByName(name);
    }

    // Get all persons
    @GetMapping("/all")
    public Iterable<Person> getAllPersons() {
        return personRepository.findAll();
    }
}

