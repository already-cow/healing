package misoya.healing.web.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    Long id;
    String name;
    int age;
    String gender;
}
