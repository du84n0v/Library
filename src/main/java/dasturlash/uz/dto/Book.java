package dasturlash.uz.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor

@Entity
public class Book implements Comparable<Book>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String title;

    @Column
    private String author;

    @Column(name = "published_date")
    private LocalDate publishDate;

    @Column
    private Integer availableDay;

    @Column
    private Boolean visible;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "taken_count")
    private Integer takenCount;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Override
    public int compareTo(Book o) {
        return this.takenCount-o.takenCount;
    }
}
