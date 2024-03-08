package service;

import model.Person;
import repository.PersonRepository;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getAllPerson() {
        return personRepository.getAllPerson();
    }

    public List<Person> getAllPersonDescendingByID() {
        return personRepository.getAllPerson()
                .stream()
                .sorted(
                        Comparator.comparingInt(Person::getId).reversed()
                        //  (a,b)-> b.getId() - a.getId()
                )
                .toList();
    }

    public List<Person> getAllPersonDescendingByName() {
        return personRepository.getAllPerson()
                .stream()
                .sorted(
                        Comparator.comparing(Person::getFullName).reversed()
                        //  (a,b)-> b.getId() - a.getId()
                )
                .toList();
    }

    public int createPerson(Scanner input) {
        return personRepository.addNewPerson(new Person().addPerson(input));
    }



    public int deletePersonByID(Scanner input){
        System.out.println("Enter the Person ID : ");
        int id  = input.nextInt();
        try{
            personRepository.getAllPerson()
                    .stream().filter(
                     person -> person.getId() == id
                    ).findFirst()
                    .orElseThrow();
            return personRepository.deletePersonByID(id);
        }catch (NoSuchElementException ex ){
            System.out.println("There is no element  with id = "+id);
            return 0;
        }

    }

    public int updatePerson(Scanner input) {
        System.out.println("Enter the Person ID : ");
        int id = input.nextInt();
        try {
            // validation , condition
            var originalPerson = personRepository.getAllPerson()
                    .stream().filter(person -> person.getId() == id)
                    .findFirst().orElseThrow(); // throw  no such element if the person doesn't exist

            // ask the user to input the new updated value for the person
            // clear buffer
            input.nextLine();
            originalPerson.addPerson(input);
            return personRepository.updatePerson(originalPerson);
        } catch (NoSuchElementException ex) {
            System.out.println("There is no element with id = "+id);
            return 0;
        }
    }
}
