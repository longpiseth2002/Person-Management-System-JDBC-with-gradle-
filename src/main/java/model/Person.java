package model;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Scanner;

//@Getter
//@Setter
//@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
//@Builder
@Accessors(chain = true)
public class Person {
    private int id;
    private String fullName;
    private String gender;
    private String email;
    private String address;


    public Person addPerson(Scanner input){
        System.out.println("Enter fullname: ");
        fullName = input.nextLine();
        System.out.println("Enter gender : ");
        gender = input.nextLine();
        System.out.println("Enter Address : ");
        address = input.nextLine();
        System.out.println("Enter email address : ");
        email = input.nextLine();

        return this;
    }

}
