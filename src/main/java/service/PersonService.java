package service;


import model.Person;
import resonsitory.PersonRepository;

import java.util.Comparator;
import java.util.List;

public class PersonService {
    private  final PersonRepository personRepository;
    public PersonService(PersonRepository personRepository){
        this.personRepository = personRepository;
    }

    public List<Person> getAllPerson(){
        return personRepository.getAllPerson();
    }
    public List<Person> getAllPersonDescendingByID(){
        return   personRepository.getAllPerson()
                .stream()
                .sorted(
                        Comparator.comparingInt(Person::getId).reversed()
                        //  (a,b)-> b.getId() - a.getId()
                )
                .toList();
    } public List<Person> getAllPersonDescendingByName(){
        return   personRepository.getAllPerson()
                .stream()
                .sorted(
                        Comparator.comparing(Person::getFullName).reversed()
                        //  (a,b)-> b.getId() - a.getId()
                )
                .toList();
    }
}

