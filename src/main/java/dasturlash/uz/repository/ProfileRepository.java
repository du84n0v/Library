package dasturlash.uz.repository;

import dasturlash.uz.dto.Book;
import dasturlash.uz.dto.Category;
import dasturlash.uz.dto.Profile;
import dasturlash.uz.enums.ProfileRole;
import dasturlash.uz.enums.ProfileStatus;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
public class ProfileRepository {

    @Autowired
    private SessionFactory factory;

    public Profile getByLogin(String login) {

        try (Session session = factory.openSession()) {
            Query<Profile> query = session.createQuery("From Profile where login =:login", Profile.class);
            query.setParameter("login", login);
            List<Profile> list = query.getResultList();

            if (list.isEmpty()) return null;
            return list.getFirst();
        }
    }

    public int create(Profile profile) {

        try (Session session = factory.openSession()) {
            Transaction t = session.beginTransaction();
            try {
                session.save(profile);
                t.commit();
                return 1;

            } catch (Exception e) {
                t.rollback();
                e.getMessage();
                return 0;
            }
        }
    }

    public List<Profile> getAll(ProfileRole... roles) {  // ProfileRole[] roles   ADMIN,STAFF    STUDENT
        List<ProfileRole> rolee = List.of(roles);

        try(Session session = factory.openSession()){
            Query<Profile> query = session.createQuery("from Profile where role in (:roles)", Profile.class);
            query.setParameter("roles", rolee);
             return query.getResultList();
        }

    }


    public List<Profile> search(String query, ProfileRole... roles) {
        List<ProfileRole> rolee = List.of(roles);
        try {
            Stream<String> stream = Files.lines(Paths.get("profile.txt"));
            return stream.filter(line -> {
                String[] str = line.split("#");
                return rolee.contains(ProfileRole.valueOf(str[7]))
                        && str[1].toLowerCase().contains(query.toLowerCase())
                        || str[2].toLowerCase().contains(query.toLowerCase())
                        || str[3].toLowerCase().contains(query.toLowerCase())
                        || str[5].toLowerCase().contains(query.toLowerCase());
            }).map(this::toDTO).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Profile getById(Integer id) {

        try(Session session = factory.openSession()){
            Query<Profile> query = session.createQuery("from Profile where id =: id", Profile.class);
            query.setParameter("id", id);
            List<Profile> result = query.getResultList();
            return (result.isEmpty() ? null : result.getFirst());
        }

    }

    public int updateStatus(Integer id, ProfileStatus status) {

        /*
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();

        Student entity = session.get(Student.class, id);
        entity.setName(student.getName());
        entity.setSurname(student.getSurname());
        entity.setAge(student.getAge());

        session.save(entity);
        t.commit();

        session.close();
        factory.close();
         */

        try(Session session = factory.openSession()){
            Transaction tt = session.beginTransaction();
            try{
                Query query = session.createQuery(
                        "UPDATE Profile SET status =:status WHERE id =:id"
                );
                query.setParameter("id", id);
                query.setParameter("status", status);

                int upd = query.executeUpdate();
                tt.commit();
                return upd;
            }
            catch (Exception e){
                tt.rollback();
                e.getMessage();
                return 0;
            }
        }

//        List<Profile> list = new ArrayList<>();
//        try {
//            Stream<String> stream = Files.lines(Paths.get("profile.txt"));
//            list = stream.map(line -> {
//                String[] str = line.split("#");
//                Profile profile = toDTO(line);
//                profile.setPassword(str[4]);
//                return profile;
//            }).toList();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//
//        for (Profile profile : list) {
//            if (profile.getId().equals(id)) {
//                profile.setStatus(status);
//                rewrite(list);
//                return 1;
//            }
//        }
//        return 0;

    }

    private void rewrite(List<Profile> list) {
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new FileWriter("profile.txt"));
            for (Profile profile : list) {
                printWriter.println(profile.toWrite());
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

    private Profile toDTO(String line) {
        String[] str = line.split("#");
        Profile profile = new Profile();
        profile.setId(Integer.valueOf(str[0]));
        profile.setName(str[1]);
        profile.setSurname(str[2]);
        profile.setLogin(str[3]);
        profile.setPhone(str[5]);
        profile.setStatus(ProfileStatus.valueOf(str[6]));
        profile.setRole(ProfileRole.valueOf(str[7]));
        profile.setCreatedDate(LocalDateTime.parse(str[8]));
        return profile;
    }

    public Profile get(Integer id) {
        try {
            Stream<String> stream = Files.lines(Paths.get("profile.txt"));
            return stream.filter(line -> {
                String[] str = line.split("#");
                return Integer.valueOf(str[0]).equals(id);
            }).map(line -> {
                String[] str = line.split("#");
                Profile profile = new Profile();
                profile.setId(Integer.valueOf(str[0]));
                profile.setName(str[1]);
                profile.setSurname(str[2]);
                profile.setPhone(str[5]);
                return profile;
            }).findAny().orElse(null);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
