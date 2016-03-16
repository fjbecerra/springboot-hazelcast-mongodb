package com.pakius;

import com.pakius.person.Application;
import com.pakius.person.model.Person;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.*;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.util.*;


@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
//Bad practice, POC purpose
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@WebAppConfiguration
@IntegrationTest
public class CRUDTest {

    private static final String HOST = "http://127.0.0.1:8080/";

    RestTemplate restTemplate = new RestTemplate();
    private static String personId;


    @Test
    public void test1CreatePerson() throws Exception {
        //Given
        Person person = new Person("Name Surname", "surname@test.com");
        //When
        HttpEntity<Person> entity = new HttpEntity<>(person);
        ResponseEntity<String> responseEntity = restTemplate.
                postForEntity(HOST , entity, String.class);
        //Then
        Assert.assertNotNull(responseEntity.getBody());
        personId = responseEntity.getBody();
        Assert.assertEquals(HttpStatus.OK,responseEntity.getStatusCode());

    }

    @Test
    public void test2LoadPerson()
    {
        //When
         ResponseEntity<Person> responseEntity = restTemplate.getForEntity(HOST + personId, Person.class);
         //Then
         Person actualPerson = responseEntity.getBody();
         Assert.assertEquals(personId, actualPerson.id);
         Assert.assertEquals("surname@test.com", actualPerson.email);
         Assert.assertEquals("Name Surname", actualPerson.name);

    }

    @Test
    public void test3UpdatePerson()
    {
        //Given
        ResponseEntity<Person> responseEntityGet = restTemplate.getForEntity(HOST + personId, Person.class);
        Person personFound = responseEntityGet.getBody();
        personFound.email = "newEmail@test.com";
        //When
        restTemplate.put(HOST + personId, personFound);
        //Then
        responseEntityGet = restTemplate.getForEntity(HOST + personId, Person.class);
        personFound = responseEntityGet.getBody();
        Assert.assertEquals(personId, personFound.id);
        Assert.assertEquals("newEmail@test.com", personFound.email);
        Assert.assertEquals("Name Surname", personFound.name);
    }

    @Test
    public void test4Predicate()
    {
        //Given
        String query = "email=newEmail@test.com";
        //When
        ResponseEntity<Set> responseEntity = restTemplate.getForEntity(HOST + "/predicate?query=" + query, Set.class);
        //Then
        Set set =  responseEntity.getBody();
        Iterator<LinkedHashMap<String, String>> it = set.iterator();
        LinkedHashMap linkedHashMap = it.next();
        Assert.assertEquals(personId, linkedHashMap.get("id"));
        Assert.assertEquals("newEmail@test.com", linkedHashMap.get("email"));
        Assert.assertEquals("Name Surname", linkedHashMap.get("name"));
    }

    @Test
    public void test5DeletePerson()
    {
        //When
        restTemplate.delete(HOST  + personId);
        //Then
        ResponseEntity<Person> responseEntity = restTemplate.getForEntity(HOST + personId, Person.class);
        Assert.assertNull(responseEntity.getBody());

    }






}
