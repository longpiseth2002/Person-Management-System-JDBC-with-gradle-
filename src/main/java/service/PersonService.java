package service;

import model.Person;
import repository.PersonRepository;

import java.sql.SQLException;
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
                )
                .toList();
    }

    public List<Person> getAllPersonDescendingByName() {
        return personRepository.getAllPerson()
                .stream()
                .sorted(
                        Comparator.comparing(Person::getFullName).reversed()
                )
                .toList();
    }

    public int createPerson(Scanner input) {
        return personRepository.addNewPerson(new Person().addPerson(input));
    }



    public int deletePersonByID(Scanner input){
        System.out.print("Enter the Person ID : ");
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
        System.out.print("Enter the Person ID : ");
        int id = input.nextInt();
        try {
            // validation , condition
            var originalPerson = personRepository.getAllPerson()
                    .stream().filter(person -> person.getId() == id)
                    .findFirst().orElseThrow();


            input.nextLine();
            originalPerson.addPerson(input);
            return personRepository.updatePerson(originalPerson);
        } catch (NoSuchElementException ex) {
            System.out.println("There is no element with id = "+id);
            return 0;
        }

    }
    public List<Person> getPersonsByGender(String genderToSearch) throws SQLException {
        return personRepository.getPersonsByGender(genderToSearch);
    }

    public List<Person> getPersonsByName(String nameToSearch) throws SQLException {
        return personRepository.getPersonsByName(nameToSearch);
    }
}
