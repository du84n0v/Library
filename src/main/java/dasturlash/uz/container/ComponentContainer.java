package dasturlash.uz.container;

import dasturlash.uz.controller.*;
import dasturlash.uz.dto.Profile;
import dasturlash.uz.repository.BookRepository;
import dasturlash.uz.repository.CategoryRepository;
import dasturlash.uz.repository.ProfileRepository;
import dasturlash.uz.repository.StudentBookRepository;
import dasturlash.uz.service.*;
import org.springframework.stereotype.Component;

import java.awt.print.Book;
import java.util.InputMismatchException;
import java.util.Scanner;

@Component
public class ComponentContainer {
    public static Scanner scannerText = new Scanner(System.in);
    public static Scanner scannerNumber = new Scanner(System.in);

    public static Profile currentProfile;
}
