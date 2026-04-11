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

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public String toWrite() {
        return id + "#" + title + "#" + author + "#" + publishDate + "#" + availableDay + "#" + createdDate +"#"+ visible;
    }

    //    public Integer getCategoryId() {
//        return categoryId;
//    }
//
//    public void setCategoryId(Integer categoryId) {
//        this.categoryId = categoryId;
//    }


    @Override
    public int compareTo(Book o) {
        return this.id-o.id;
    }
}
