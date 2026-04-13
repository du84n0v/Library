package dasturlash.uz.dto;

import dasturlash.uz.enums.StudentBookStatus;
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
@Table(name = "student_book")
public class StudentBook implements Comparable<StudentBook> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @Column
//    private Integer studentId;
//
//    @Column
//    private Integer bookId;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Profile student;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "deadline_date")
    private LocalDate deadlineDate;

    @Column(name = "returned_date")
    private LocalDateTime returnedDate;

    @Enumerated(EnumType.STRING)
    private StudentBookStatus status;

    @Column(name = "taken_count")
    private Integer takenCount = 0;

    public String toWrite() {
        return id + "#" + student.getId() + "#" + book.getId() + "#" + createdDate + "#" + deadlineDate + "#" + status + "#" + returnedDate;
    }

    //    public Integer getStudentId() {
//        return studentId;
//    }
//
//    public void setStudentId(Integer studentId) {
//        this.studentId = studentId;
//    }
//
//    public Integer getBookId() {
//        return bookId;
//    }
//
//    public void setBookId(Integer bookId) {
//        this.bookId = bookId;
//    }

//    public void setCreatedDate(LocalDateTime createdDate) {
//        this.createdDate = createdDate;
//    }
//
//    public void setReturnedDate(LocalDateTime returnedDate) {
//        this.returnedDate = returnedDate;
//    }
//
//    public void setStatus(StudentBookStatus status) {
//        this.status = status;
//    }
//
//    public void setBook(Book book) {
//        this.book = book;
//    }
//
//    public void setDeadlineDate(LocalDate deadlineDate) {
//        this.deadlineDate = deadlineDate;
//    }
//
//    public void setStudent(Profile student) {
//        this.student = student;
//    }
//
//    public void setTakenCount(Integer takenCount) {
//        this.takenCount = takenCount;
//    }

    @Override
    public int compareTo(StudentBook o) {
        return this.createdDate.compareTo(o.createdDate);
    }
}
