package dasturlash.uz;

import dasturlash.uz.config.SpringConfig;
import dasturlash.uz.controller.MainController;
import dasturlash.uz.enums.ProfileRole;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
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

        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");

        MainController mainController1 = (MainController) context.getBean("mainController");
        mainController1.start();
    }

}