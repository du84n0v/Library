package dasturlash.uz.repository;

import dasturlash.uz.dto.Book;
import dasturlash.uz.dto.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

@Repository
public class CategoryRepository {

    @Autowired
    SessionFactory factory;

    public Category getByName(String name) {
        try(Session session = factory.openSession()){
            Query<Category> query = session.createQuery("FROM Category WHERE name =: name", Category.class);
            query.setParameter("name", name);
            List<Category> categories = query.getResultList();
            return (categories.isEmpty() ? null : categories.getFirst());
        }
    }

    public int save(Category category) {
        try(Session session = factory.openSession()){
            Transaction tt = session.beginTransaction();
            try {
                session.save(category);
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

    public List<Category> getAll() { //
        try(Session session = factory.openSession()){
            Query<Category> query = session.createQuery("FROM Category ", Category.class);
            return query.getResultList();
        }
    }

    public int deleteById(Integer id) {
        try(Session session = factory.openSession()){
            Transaction tt = session.beginTransaction();
            try {
                Query query = session.createQuery("UPDATE Category SET visible = false WHERE id =: id");
                query.setParameter("id", id);
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

    public Category getById(Integer id) {
        try(Session session = factory.openSession()){
            Query<Category> query = session.createQuery("FROM Category WHERE id =:id", Category.class);
            query.setParameter("id", id);
            List<Category> result = query.getResultList();
            return (result.isEmpty() ? null : result.getFirst());
        }
    }
}
