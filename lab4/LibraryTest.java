import java.util.*;
import java.util.stream.Collectors;

class Book {
    private String title;
    private String author;
    private int year;

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getYear() { return year; }

    @Override
    public String toString() {
        return "Book{title='" + title + "', author='" + author + "', year=" + year + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return year == book.year && 
               Objects.equals(title, book.title) && 
               Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, year);
    }
}

class Library {
    private List<Book> books = new ArrayList<>();
    private Set<String> authors = new HashSet<>();
    private Map<String, Integer> authorCounts = new HashMap<>();

    public void addBook(Book book) {
        books.add(book);
        authors.add(book.getAuthor());
        authorCounts.put(book.getAuthor(), 
                        authorCounts.getOrDefault(book.getAuthor(), 0) + 1);
    }

    public void removeBook(Book book) {
        if (books.remove(book)) {
            String author = book.getAuthor();
            boolean authorExists = books.stream()
                                      .anyMatch(b -> b.getAuthor().equals(author));
            if (!authorExists) authors.remove(author);
            
            int count = authorCounts.getOrDefault(author, 0);
            if (count > 0) {
                authorCounts.put(author, count - 1);
                if (count - 1 == 0) authorCounts.remove(author);
            }
        }
    }

    public List<Book> findBooksByAuthor(String author) {
        return books.stream()
                .filter(book -> book.getAuthor().equals(author))
                .collect(Collectors.toList());
    }

    public List<Book> findBooksByYear(int year) {
        return books.stream()
                .filter(book -> book.getYear() == year)
                .collect(Collectors.toList());
    }

    public void printAllBooks() {
        books.forEach(System.out::println);
    }

    public void printUniqueAuthors() {
        authors.forEach(System.out::println);
    }

    public void printAuthorStatistics() {
        authorCounts.forEach((author, count) -> 
                System.out.println(author + ": " + count + " книг(и)"));
    }
}

public class LibraryTest {
    public static void main(String[] args) {
        Library library = new Library();

        Book book1 = new Book("Граф Монте-Кристо", "Александр Дюма", 1844);
        Book book2 = new Book("Думай и богатей", "Наполеон Хилл", 1937);
        Book book3 = new Book("Гарри Поттер и Кубок огня", "Джоан Роулинг", 2000);
        Book book4 = new Book("Граф Монте-Кристо", "Александр Дюма", 1844); // Дубликат

        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        library.addBook(book4);

        System.out.println("=== Все книги в библиотеке ===");
        library.printAllBooks();

        System.out.println("\n=== Уникальные авторы ===");
        library.printUniqueAuthors();

        System.out.println("\n=== Статистика по авторам ===");
        library.printAuthorStatistics();

        System.out.println("\n=== Книги Александра Дюма ===");
        library.findBooksByAuthor("Александр Дюма").forEach(System.out::println);

        System.out.println("\n=== Книги 1937 года ===");
        library.findBooksByYear(1937).forEach(System.out::println);

        library.removeBook(book1);
        System.out.println("\n=== После удаления одной копии 'Графа Монте-Кристо' ===");
        System.out.println("Оставшиеся книги:");
        library.printAllBooks();
        System.out.println("\nОбновленная статистика:");
        library.printAuthorStatistics();
    }
}