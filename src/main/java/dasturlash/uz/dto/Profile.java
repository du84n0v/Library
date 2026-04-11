package dasturlash.uz.dto;

import dasturlash.uz.enums.ProfileRole;
import dasturlash.uz.enums.ProfileStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor

@Entity
public class Profile implements Comparable<Profile> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String login;

    @Column
    private String password;

    @Column
    private String phone;

    @Enumerated(EnumType.STRING)
    private ProfileStatus status;

    @Enumerated(EnumType.STRING)
    private ProfileRole role;

    @Column
    private LocalDateTime createdDate;

    public String toWrite() {
        return id + "#" + name + "#" + surname + "#" + login + "#" + password + "#" + phone + "#" + status + "#" + role + "#" + createdDate;
    }

    public String getDetailAsString() {
        return id + " " + name + " " + surname + " " + login + " " + phone + " " + status + " " + role + " " + createdDate;
    }

    public String getStudentDetailAsString() {
        return id + " " + name + " " + surname + " " + login + " " + phone + " " + status + " " + createdDate;
    }

    @Override
    public int compareTo(Profile o) {
        return this.createdDate.compareTo(o.createdDate);

    }
}
