package dasturlash.uz.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private LocalDateTime createdDate;

    @Column
    private boolean visible;

    public String toWrite(){
        return id+"#"+name+"#"+createdDate+"#"+visible;
    }


}
