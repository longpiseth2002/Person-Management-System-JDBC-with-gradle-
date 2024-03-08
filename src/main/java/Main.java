
import com.github.javafaker.Faker;
import model.Person;
import repository.PersonRepository;
import service.PersonService;
import utils.TableUtils;
import view.MainView;

import java.sql.SQLException;
import java.util.*;

public class Main {

    private static PersonService personService = new PersonService(new PersonRepository());
    private static PersonRepository personRepository = new PersonRepository();

    public static void main(String[] args) throws SQLException {
        Scanner input = new Scanner(System.in);

        // Perform user authentication
        if (authenticateUser(input)) {
            int option;
            do {
                option = MainView.renderMain(input);
                switch (option) {
                    case 1: {
                        input.nextLine();
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
                                    System.out.print("Enter Person ID to search:");
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
                                    input.nextLine();
                                    System.out.print("Enter gender to search: ");
                                    String genderToSearch = input.nextLine();
                                    List<Person> personsByGender = personService.getPersonsByGender(genderToSearch);
                                    if (!personsByGender.isEmpty()) {
                                        TableUtils.renderObjectToTable(personsByGender);
                                    } else {
                                        System.out.println("No persons found with the specified gender.");
                                    }
                                    break;

                                case 3:
                                    input.nextLine(); // Consume newline character
                                    System.out.print("Enter country to search: ");
                                    String countryToSearch = input.nextLine();
                                    List<Person> personsByCountry = personService.getPersonsByName(countryToSearch);
                                    if (!personsByCountry.isEmpty()) {
                                        TableUtils.renderObjectToTable(personsByCountry);
                                    } else {
                                        System.out.println("No persons found from the specified country.");
                                    }
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
        } else {
            System.out.println("Invalid username or password. Exiting...");
        }
    }

    private static boolean authenticateUser(Scanner input) {
        System.out.print("Enter username: ");
        String username = input.nextLine();
        System.out.print("Enter password: ");
        String password = input.nextLine();
        return personRepository.authenticateUser(username, password);
    }
}
