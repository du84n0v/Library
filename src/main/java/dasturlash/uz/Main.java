package dasturlash.uz;

import dasturlash.uz.config.SpringConfig;
import dasturlash.uz.controller.MainController;
import dasturlash.uz.enums.ProfileRole;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) {
        // login: adminjon
        // pswd:  12345
//        MainController mainController = new MainController();
//        mainController.start();

//        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate-config.xml").build();
//        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
//
//        SessionFactory factory = meta.getSessionFactoryBuilder().build();
//
//
//        factory.close();

        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

        MainController mainController1 = (MainController) context.getBean("mainController");
        mainController1.start();
    }

}