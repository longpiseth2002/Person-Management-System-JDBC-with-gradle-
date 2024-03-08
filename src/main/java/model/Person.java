package model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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

}

