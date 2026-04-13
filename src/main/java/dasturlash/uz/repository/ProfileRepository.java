package dasturlash.uz.repository;

import dasturlash.uz.dto.Profile;
import dasturlash.uz.enums.ProfileRole;
import dasturlash.uz.enums.ProfileStatus;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;


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
                System.out.println(e.getMessage());
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
        query = query.trim().toLowerCase();

        try(Session session = factory.openSession()){
            Query<Profile> answer = session.createQuery("FROM Profile", Profile.class);
            List<Profile> cur = answer.getResultList();
            List<Profile> result = new LinkedList<>();

            for(Profile p :cur){
                if(rolee.contains(p.getRole()) && (
                        p.getName().toLowerCase().contains(query) ||
                        p.getSurname().toLowerCase().contains(query) ||
                        p.getLogin().contains(query) ||
                        p.getPhone().contains(query))){
                    result.add(p);
                }
            }

            return result;
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

        try(Session session = factory.openSession()){
            Transaction tt = session.beginTransaction();
            try{
                Query query = session.createQuery(
                        "UPDATE Profile SET status =:status WHERE id =:id"
                );
                query.setParameter("id", id);
                query.setParameter("status", status);
                int result = query.executeUpdate();
                tt.commit();
                return result;
            }
            catch (Exception e){
                tt.rollback();
                e.getMessage();
                return 0;
            }
        }

    }

    public Profile get(Integer id) {
        try(Session session = factory.openSession()) {
            Query<Profile> query = session.createQuery("FROM Profile where id =: id", Profile.class);
            query.setParameter("id", id);
            List<Profile> result = query.getResultList();
            return (result.isEmpty() ? null : result.getFirst());
        }
    }
}
