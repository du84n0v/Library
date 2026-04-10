package dasturlash.uz.config;

import dasturlash.uz.controller.MainController;
import dasturlash.uz.service.BookService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "dasturlash.uz")
public class SpringConfig {

    @Bean
    public BookService mmCc(){
        return new BookService();
    }
}
