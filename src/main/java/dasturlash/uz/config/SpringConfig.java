package dasturlash.uz.config;

import dasturlash.uz.controller.MainController;
import dasturlash.uz.service.BookService;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "dasturlash.uz")
public class SpringConfig {

    @Bean
    public SessionFactory sessionFactory(){
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate-config.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

        return meta.getSessionFactoryBuilder().build();
    }


}
