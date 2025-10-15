package Enitity;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String address;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    private String passwordHash;
    private String profileHeadline;

    @OneToOne(mappedBy = "applicant", cascade = CascadeType.ALL)
    private Profile profile;

    public enum UserType
    {
        APPLICANT, ADMIN
    }
}
