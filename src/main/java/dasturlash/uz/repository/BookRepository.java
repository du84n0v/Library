package dasturlash.uz.repository;

import dasturlash.uz.dto.Book;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

@Repository
public class BookRepository {

    @Autowired
    SessionFactory factory;


    public int save(Book book) {
        try(Session session = factory.openSession()){
            Transaction tt = session.beginTransaction();
            try{
                session.save(book);
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

    public List<Book> getAll() {
        try(Session session = factory.openSession()){
            Query<Book> query = session.createQuery("FROM Book WHERE visible = true", Book.class);
            return query.getResultList();
        }
    }

    public List<Book> search(String text) {
        text = text.trim().toLowerCase();

        try (Session session = factory.openSession()) {
            Query<Book> query = session.createQuery(
                    "FROM Book WHERE visible = true AND " +
                            "(lower(title) LIKE :text OR lower(author) LIKE :text)",
                    Book.class
            );
            query.setParameter("text", "%" + text + "%");
            return query.getResultList();
        }
    }

    public int delete(Integer bookId) {

        try(Session session = factory.openSession()){
            Transaction tt = session.beginTransaction();

            try{
                Query query = session.createQuery("UPDATE Book SET visible = false where id =: id");
                query.setParameter("id", bookId);

                int result = query.executeUpdate();
                tt.commit();
                return result;
            }
            catch (Exception e){
                tt.rollback();
                System.out.println(e.getMessage());
                return 0;
            }
        }
    }

    public Book getById(Integer id) {
        try(Session session = factory.openSession()){
            Query<Book> query = session.createQuery("FROM Book WHERE id =: id", Book.class);
            query.setParameter("id", id);
            List<Book> books = query.getResultList();
            return (books.isEmpty() ? null : books.getFirst());
        }
    }

    public List<Book> getAllByCategoryId(Integer categoryId) {
        try(Session session = factory.openSession()){
            Query<Book> query = session.createQuery("FROM Book WHERE category.id =: category_id", Book.class);
            query.setParameter("category_id", categoryId);
            return query.getResultList();
        }
    }
    public Book get(Integer id) {
        try(Session session = factory.openSession()){
            Query<Book> query = session.createQuery("FROM Book WHERE id =: id", Book.class);
            query.setParameter("id", id);
            return query.getResultList().getFirst();
        }
    }

    public List<Book> getBestBooks() {
        try(Session session = factory.openSession()){
            Query<Book> query = session.createQuery(
                    "FROM Book WHERE takenCount > 0",
                    Book.class);
            return query.getResultList();
        }
    }
}
