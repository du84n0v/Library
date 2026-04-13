package dasturlash.uz.repository;

import dasturlash.uz.dto.StudentBook;
import dasturlash.uz.enums.StudentBookStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class StudentBookRepository {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    SessionFactory factory;


    public int save(StudentBook studentBook) {
        try(Session session = factory.openSession()){
            Transaction tt = session.beginTransaction();
            try{
                session.save(studentBook);
                tt.commit();
                return 1;
            }
            catch (Exception e){
                tt.rollback();
                System.out.println(e.getMessage());
                return 0;
            }
        }
    }

    public List<StudentBook> studentBookOnHand(Integer sId, StudentBookStatus... status) {
        List<StudentBookStatus> list = List.of(status);
        try(Session session = factory.openSession()){
            Query<StudentBook> query = session.createQuery(
                    "FROM StudentBook WHERE student.id =: s_id AND status in (:status)",
                    StudentBook.class);
            query.setParameter("s_id", sId);
            query.setParameter("status", list);
            return query.getResultList();
        }
    }

    public StudentBook getStudentBook(Integer sId, Integer bId) {
        try(Session session = factory.openSession()){
            Query<StudentBook> query = session.createQuery(
                    "FROM StudentBook WHERE student.id =: sid AND book.id =: bid",
                    StudentBook.class);
            query.setParameter("sid", sId);
            query.setParameter("bid",bId);

            List<StudentBook> result = query.getResultList();
            return (result.isEmpty() ? null : result.getFirst());
        }
    }

    public int returnBook(Integer sbId) {

        try(Session session = factory.openSession()){
            Transaction tt = session.beginTransaction();
            try{
                Query query = session.createQuery(
                        "UPDATE StudentBook SET status =: status, returnedDate =: now WHERE id =: id");
                query.setParameter("id", sbId);
                query.setParameter("status", StudentBookStatus.RETURNED);
                query.setParameter("now", LocalDateTime.now());
                int upd = query.executeUpdate();
                tt.commit();
                return upd;
            }
            catch (Exception e){
                tt.rollback();
                System.out.println(e.getMessage());
                return 0;
            }
        }
    }

    public int returnBook(Integer sId, Integer bId) {
        try(Session session = factory.openSession()){
            Transaction tt = session.beginTransaction();
            try{
                Query query = session.createQuery(
                        "UPDATE StudentBook SET status =: new_status, returnedDate =: now WHERE student.id =: sid AND book.id =: bid AND status =: old_status");
                query.setParameter("new_status", StudentBookStatus.RETURNED);
                query.setParameter("now", LocalDateTime.now());
                query.setParameter("sid", sId);
                query.setParameter("bid", bId);
                query.setParameter("old_status", StudentBookStatus.TAKEN);

                int upd = query.executeUpdate();
                tt.commit();
                return upd;

            }
            catch (Exception e){
                tt.rollback();
                System.out.println(e.getMessage());
                return 0;
            }
        }
    }


    public List<StudentBook> booksOnHand() {
        try(Session session = factory.openSession()){
            Query<StudentBook> query = session.createQuery(
                    "FROM StudentBook WHERE status =: status", StudentBook.class);
            query.setParameter("status", StudentBookStatus.TAKEN);
            return query.getResultList();
        }
    }


    public List<StudentBook> bookHistory(Integer bId) {
        try(Session session = factory.openSession()){
            Query<StudentBook> query = session.createQuery(
                    "FROM StudentBook WHERE book.id =: bid", StudentBook.class);
            query.setParameter("bid", bId);
            return query.getResultList();
        }
    }

    public List<StudentBook> bestBooks() {
//        try(Session session = factory.openSession()){
//            Query<StudentBook> query = session.createQuery(
//                    "SELECT ", StudentBook.class)
//        }
        return new LinkedList<>();
    }

    private void rewrite(List<StudentBook> list) {
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new FileWriter("student_book.txt"));
            for (StudentBook studentBook : list) {
                printWriter.println(studentBook.toWrite());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (printWriter != null) {
                printWriter.flush();
                printWriter.close();
            }
        }
    }

    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void setProfileRepository(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }
}
