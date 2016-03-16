package com.pakius.person.controller;

import java.util.Set;


import com.pakius.person.config.OperationsMap;
import com.pakius.person.model.Person;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hazelcast.query.SqlPredicate;


@RestController
public class PersonController
{
    private static final Logger log = LoggerFactory.getLogger(PersonController.class);

    private final OperationsMap operationsMap;

    @Autowired
    public PersonController( OperationsMap operationsMap)
    {
        this.operationsMap = operationsMap;
    }

    @RequestMapping(method = RequestMethod.POST , consumes = "application/json")
    @ResponseBody
    public ResponseEntity<String> createPerson(@RequestBody Person person)
    {
       log.info("create person " + person.toString());
       person.id = String.valueOf(System.nanoTime());
       operationsMap.getMap().put(person.id, person);
       return new ResponseEntity<>(person.id, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Person> loadPerson(@PathVariable("id") String id)
    {
      log.info("load person with id = " + id);
      Person person = operationsMap.getMap().get(id);

      return  new ResponseEntity<>(person, HttpStatus.OK);
    }


    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable("id") String id)
    {
        log.info("delete person with id = " + id);
       Person person = operationsMap.getMap().get(id);
        if (person == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        operationsMap.getMap().remove(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(value = "{id}", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseBody
    public ResponseEntity<Void> updateUser(@PathVariable("id") String id,@RequestBody Person person)
    {
        log.info("update person ", person.toString());


        Person found =operationsMap.getMap().get(id);
        if(found == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        operationsMap.getMap().put(person.id, person);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(value = "predicate", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Set<Person>> predicate(@RequestParam("query") final String sqlQuery)
    {
        log.info("Predicate with query =  ", sqlQuery);

        Set<Person> persons = (Set<Person>) operationsMap.getMap().values(new SqlPredicate(sqlQuery));

        if (persons == null || persons.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        else
             return new ResponseEntity<>(persons, HttpStatus.OK);

     }



}
