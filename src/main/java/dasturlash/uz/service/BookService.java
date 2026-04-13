package dasturlash.uz.service;

//import dasturlash.uz.container.ComponentContainer;
import dasturlash.uz.dto.Book;
import dasturlash.uz.dto.Category;
import dasturlash.uz.repository.BookRepository;
import dasturlash.uz.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Collections.sort;

@Service
public class BookService {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BookRepository bookRepository;

    public void add(Book book) {
//        categoryService.list();
        // check
        if (!isValid(book)) {
            return;
        }
        // category
        Category category = categoryRepository.getById(book.getCategory().getId());
        if (category == null) {
            System.out.println("Category not found.");
            return;
        }
        book.setCreatedDate(LocalDateTime.now());
        book.setVisible(true);
        int effectedRow = bookRepository.save(book);
        if (effectedRow == 1) {
            System.out.println("Book saved.");
        } else {
            System.out.println("Book not saved.");
        }
    }

    public boolean isValid(Book book) {
        if (book.getTitle() == null || book.getTitle().isBlank() || book.getTitle().length() < 3) {
            System.out.println("Title required.");
            return false;
        }

        if (book.getAuthor() == null || book.getAuthor().isBlank() || book.getAuthor().length() < 3) {
            System.out.println("Author required.");
            return false;
        }

        if (book.getAvailableDay() == null || book.getAvailableDay() <= 0) {
            System.out.println("Available day required.");
            return false;
        }
        return true;
    }

    public void all() {
        List<Book> bookList = bookRepository.getAll();
        if(bookList.isEmpty()){
            return;
        }
        System.out.printf("-------------------------------------------------------------------%n");
        System.out.printf("                               Book list                        %n");
        System.out.printf("-------------------------------------------------------------------%n");
        System.out.printf("| %-4s | %-30s | %-20s | %-20s |%n", "Id", "Title", "Author", "Category name");
        System.out.printf("-------------------------------------------------------------------%n");
        bookList.forEach(book -> {
            System.out.printf("| %-4d | %-30s | %-20s | %-20s |%n", book.getId(), book.getTitle(), book.getAuthor(), book.getCategory().getName());
        });
        System.out.printf("-------------------------------------------------------------------%n");
    }

    public void search(String query) {
        List<Book> bookList = bookRepository.search(query);
        bookList.forEach(book -> {
            System.out.println(book.getId() + ", " + book.getTitle() + ", " + book.getAuthor() + ", " + book.getCategory().getName());
        });
    }

    public void delete(Integer bookId) {
        int effectedRows = bookRepository.delete(bookId);
        if (effectedRows == 1) {
            System.out.println("Book deleted");
        } else {
            System.out.println("Book not deleted");
        }
    }

    public void byCategoryId(Integer categoryId) {
        List<Book> bookList = bookRepository.getAllByCategoryId(categoryId);
        bookList.forEach(book -> {
            System.out.println(book.getId() + ", " + book.getTitle() + ", " + book.getAuthor() + ", " + book.getCategory().getName());
        });
    }

    public void bestBooks(){
        List<Book> topBook = bookRepository.getBestBooks();
        if(topBook.isEmpty()){
            System.out.println("There are no book has been taken yet");
            return ;
        }
        sort(topBook);
        for(int i = 1; i < Math.max(topBook.size(), 11); ++ i){
            Book book = topBook.get(i);
            String str = String.format("%d, %d %s, %s, %s, %s", i+1, book.getId(), book.getTitle(),
                    book.getAuthor(), book.getCategory().getName(), book.getTakenCount());
            System.out.println(str);
        }
    }
}
