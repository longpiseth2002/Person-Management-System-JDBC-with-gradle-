import com.github.javafaker.Faker;
import model.Person;
import repository.PersonRepository;
import service.PersonService;
import utils.TableUtils;
import view.MainView;

import java.util.*;
import java.util.concurrent.ThreadFactory;
//        Person person = new Person(1001,"james ","male","james@gmail.com","cambodia");

//        Person person = Person.builder()
//                .id(1001)
//                .fullName("Jenny ")
//                .gender("female")
//                .email("jenny@gmail.com")
//                .address("siem reap")
//                .build();

//        Person person = new Person()
//                .setFullName("James")
//                .setId(1001)
//                .setGender("male");
//
//        System.out.println(person.getFullName());
//        System.out.println(person);

//        PersonRepository.getAllPerson().forEach(
//                System.out::println
//        );
//        System.out.println("This is the second data : ");
//        PersonRepository.getAllPerson().forEach(
//                System.out::println
//        );

public class Main {
    private static PersonService personService =
            new PersonService(new PersonRepository());

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int option;
        do {
            option = MainView.renderMain(input);
            switch (option) {
                case 1: {
                    input.nextLine(); // clear buffer
                    System.out.println(
                            personService.createPerson(input) > 0 ?
                                    "Successfully Created a New Person"
                                    : ""
                    );

                }
                break;
                case 2: {
                    System.out.println(
                            personService
                                    .updatePerson(input) > 0 ?
                                    "Successfully Update Person Info"
                                    : ""
                    );
                }
                break;
                case 3: {
                    System.out.println(
                            personService
                                    .deletePersonByID(input) > 0 ?
                                    "Successfully Remove the Person"
                                    : "");
                    ;
                }
                break;
                case 4: {
                    int showOption;
                    List<String> showMenu = new ArrayList<>(List.of(
                            "Show Original Order",
                            "Show Descending Order (ID)",
                            "Show Descending Order (name) ",
                            "Exit"));
                    do {
                        TableUtils.renderMenu(showMenu, "Show Person Information");
                        System.out.print("Choose your option: ");
                        showOption = input.nextInt();


                        switch (showOption) {
                            case 1:

                                TableUtils.renderObjectToTable(personService.getAllPerson());
                                break;
                            case 2:
                                // descending id
                                TableUtils.renderObjectToTable(
                                        personService.getAllPersonDescendingByID()
                                );
                                break;
                            case 3:
                                // descending name
                                TableUtils.renderObjectToTable(
                                        personService.getAllPersonDescendingByName()
                                );
                                break;
                            default:
                                System.out.println("Invalid option ...!!!!");
                                break;
                        }
                    } while (showOption != showMenu.size());
                }
                break;
                case 5: {
                    int searchOption;
                    List<String> searchMenu = new ArrayList<>(Arrays.asList(
                            "Search By ID",
                            "Search By Gender",
                            "Search By Country",
                            "Exit"));
                    do {
                        TableUtils.renderMenu(searchMenu, "Search for Person");
                        System.out.print("Choose your option:");
                        searchOption = input.nextInt();
                        switch (searchOption) {
                            case 1:
                                int searchID = 0;
                                System.out.println("Enter Person ID to search:");
                                searchID = input.nextInt();
                                int finalSearchID = searchID;
                                try {
                                    Person optionalPerson =
                                            personService.getAllPerson()
                                                    .stream()
                                                    .filter(person -> person.getId() == finalSearchID)
                                                    .findFirst()
                                                    .orElseThrow(() -> new ArithmeticException("Whatever exception!! "));
                                    TableUtils.renderObjectToTable(
                                            Collections.singletonList(optionalPerson));
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                    System.out.println("There is no element with ID=" + searchID);
                                }

                                break;
                            case 2:
                                break;
                            case 3:
                                break;
                        }

                    } while (searchOption != searchMenu.size());

                }
                break;
                case 6:
                    System.out.println("Exit from the program!!! ");
                    break;
                default:
                    System.out.println("Invalid Option!!!!!! ");
                    break;
            }
        } while (option != 6);


    }
}
