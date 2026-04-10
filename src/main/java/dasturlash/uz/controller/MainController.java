package dasturlash.uz.controller;

import dasturlash.uz.container.ComponentContainer;
import dasturlash.uz.dto.Profile;
import dasturlash.uz.service.AuthService;
import dasturlash.uz.service.BookService;
import dasturlash.uz.service.CategoryService;
import dasturlash.uz.service.InitService;
import dasturlash.uz.util.ScannerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;


@Controller
public class MainController {
    @Autowired
//    @Qualifier("mmCC")
    private BookService bookService;
    @Autowired
    private AuthService authService;
    @Autowired
    private CategoryService categoryService;

    // lesson finished
    public void start() {
        InitService initService = new InitService();

        initService.initCreateFile();
        initService.initAdmin();
        initService.initTestStudent();

        boolean loop = true;
        while (loop) {
            showMenu();
            int action = ScannerUtil.getAction();
            switch (action) {
                case 1:
                    bookService.all();
                    break;
                case 2:
                    search();
                    break;
                case 3:
                    byCategory();
                    break;
                case 4:
                    login();
                    break;
                case 5:
                    registration();
                    break;
                case 0:
                    loop = false;
                    break;
                default:
                    System.out.println("Mazgi bu hatoku.");
            }

        }

    }

    public void showMenu() {
        System.out.println("*** Main Menu ***");
        System.out.println("1. BookList");
        System.out.println("2. Search");
        System.out.println("3. By category");
        System.out.println("4. Login");
        System.out.println("5. Registration");
        System.out.println("0. Exit");
    }

    public void login() {
        System.out.print("Enter login: ");
        String login = ComponentContainer.scannerText.next();

        System.out.print("scannerText password: ");
        String password = ComponentContainer.scannerText.next();
        //
        authService.login(login, password);
    }

    public void registration() {
        System.out.print("Enter name: ");
        String name = ComponentContainer.scannerText.next();

        System.out.print("Enter surname: ");
        String surname = ComponentContainer.scannerText.next();

        System.out.print("Enter phone: ");
        String phone = ComponentContainer.scannerText.next();

        System.out.print("Enter login: ");
        String login = ComponentContainer.scannerText.next();

        System.out.print("Enter password: ");
        String password = ComponentContainer.scannerText.next();

        Profile profile = new Profile();
        profile.setName(name.trim());
        profile.setSurname(surname.trim());
        profile.setPhone(phone.trim());

        profile.setLogin(login.trim()); // valish
        profile.setPassword(password.trim()); // 222

        authService.registration(profile);
    }

    public void search() {
        System.out.print("Enter query:");
        String query = ComponentContainer.scannerText.next();
        bookService.search(query);
    }

    public void byCategory() {
        categoryService.list();
        System.out.print("Enter category id:");
        Integer categoryId = ComponentContainer.scannerNumber.nextInt();
        bookService.byCategoryId(categoryId);
    }

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
}
