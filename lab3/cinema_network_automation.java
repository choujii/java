import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

class Seat {
    private final int row;
    private final int number;
    private boolean isBooked;

    public Seat(int row, int number) {
        this.row = row;
        this.number = number;
        this.isBooked = false;
    }

    public int getRow() { return row; }
    public int getNumber() { return number; }
    public boolean isBooked() { return isBooked; }
    public void book() { isBooked = true; }

    @Override
    public String toString() {
        return isBooked ? "[X]" : "[O]";
    }
}

class Hall {
    private final String name;
    private final List<Integer> seatsConfig;

    public Hall(String name, List<Integer> seatsConfig) {
        this.name = name;
        this.seatsConfig = seatsConfig;
    }

    public String getName() { return name; }
    public List<Integer> getSeatsConfig() { return seatsConfig; }
}

class Movie {
    private final String title;
    private final int durationMinutes;

    public Movie(String title, int durationMinutes) {
        this.title = title;
        this.durationMinutes = durationMinutes;
    }

    public String getTitle() { return title; }
    public int getDurationMinutes() { return durationMinutes; }
}

class Session {
    private final Cinema cinema;
    private final Hall hall;
    private final Movie movie;
    private final LocalDateTime startTime;
    private final int durationMinutes;
    private final Seat[][] seats;

    public Session(Cinema cinema, Hall hall, Movie movie, LocalDateTime startTime, int durationMinutes) {
        this.cinema = cinema;
        this.hall = hall;
        this.movie = movie;
        this.startTime = startTime;
        this.durationMinutes = durationMinutes;
        this.seats = new Seat[hall.getSeatsConfig().size()][];
        initializeSeats();
    }

    private void initializeSeats() {
        List<Integer> config = hall.getSeatsConfig();
        for (int i = 0; i < config.size(); i++) {
            seats[i] = new Seat[config.get(i)];
            for (int j = 0; j < config.get(i); j++) {
                seats[i][j] = new Seat(i+1, j+1);
            }
        }
    }

    public Cinema getCinema() { return cinema; }
    public Hall getHall() { return hall; }
    public Movie getMovie() { return movie; }
    public LocalDateTime getStartTime() { return startTime; }
    public int getDurationMinutes() { return durationMinutes; }

    public boolean hasAvailableSeats() {
        for (Seat[] row : seats) {
            for (Seat seat : row) {
                if (!seat.isBooked()) return true;
            }
        }
        return false;
    }

    public void printSeats() {
        for (Seat[] row : seats) {
            for (Seat seat : row) {
                System.out.print(seat + " ");
            }
            System.out.println();
        }
    }

    public boolean bookSeat(int row, int seatNumber) {
        if (row < 1 || row > seats.length || seatNumber < 1 || seatNumber > seats[row-1].length) 
            return false;
        Seat seat = seats[row-1][seatNumber-1];
        if (seat.isBooked()) return false;
        seat.book();
        return true;
    }
}

class Cinema {
    private final String name;
    private final List<Hall> halls = new ArrayList<>();

    public Cinema(String name) { this.name = name; }
    public String getName() { return name; }
    public void addHall(Hall hall) { halls.add(hall); }
    public List<Hall> getHalls() { return halls; }
}

class User {
    private final String username;
    private final String password;
    private final boolean isAdmin;

    public User(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public String getUsername() { return username; }
    public boolean isAdmin() { return isAdmin; }
    public boolean authenticate(String password) { return this.password.equals(password); }
}

public class CinemaSystem {
    private static final List<User> users = new ArrayList<>();
    private static final List<Cinema> cinemas = new ArrayList<>();
    private static final List<Session> sessions = new ArrayList<>();
    private static User currentUser;

    public static void main(String[] args) {
        initializeData();
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n1. Войти\n2. Регистрация\n3. Выход");
            String choice = scanner.nextLine();
            
            if (choice.equals("1")) {
                loginUser(scanner);
                if (currentUser != null) {
                    if (currentUser.isAdmin()) adminMenu(scanner);
                    else userMenu(scanner);
                }
            } else if (choice.equals("2")) {
                registerUser(scanner);
            } else if (choice.equals("3")) {
                break;
            }
        }
    }

    private static void initializeData() {
        users.add(new User("admin", "admin", true));
        users.add(new User("user", "user", false));
    }

    private static void loginUser(Scanner scanner) {
        System.out.print("Логин: ");
        String username = scanner.nextLine();
        System.out.print("Пароль: ");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.authenticate(password)) {
                currentUser = user;
                System.out.println("Добро пожаловать, " + username + "!");
                return;
            }
        }
        System.out.println("Неверные учетные данные!");
    }

    private static void registerUser(Scanner scanner) {
        System.out.print("Введите логин: ");
        String username = scanner.nextLine();
        if (users.stream().anyMatch(u -> u.getUsername().equals(username))) {
            System.out.println("Логин уже занят!");
            return;
        }
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();
        users.add(new User(username, password, false));
        System.out.println("Регистрация успешна!");
    }

    private static void adminMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nАдмин меню:");
            System.out.println("1. Добавить кинотеатр");
            System.out.println("2. Добавить зал");
            System.out.println("3. Создать сеанс");
            System.out.println("4. Просмотр сеансов");
            System.out.println("5. Выход");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1": addCinema(scanner); break;
                case "2": addHall(scanner); break;
                case "3": createSession(scanner); break;
                case "4": viewSessions(); break;
                case "5": return;
                default: System.out.println("Неверный выбор!");
            }
        }
    }

    private static void addCinema(Scanner scanner) {
        System.out.print("Название кинотеатра: ");
        String name = scanner.nextLine();
        cinemas.add(new Cinema(name));
        System.out.println("Кинотеатр добавлен!");
    }

    private static void addHall(Scanner scanner) {
        if (cinemas.isEmpty()) {
            System.out.println("Сначала добавьте кинотеатр!");
            return;
        }

        System.out.println("Выберите кинотеатр:");
        for (int i = 0; i < cinemas.size(); i++) {
            System.out.println((i+1) + ". " + cinemas.get(i).getName());
        }
        int cinemaIndex = readInt(scanner, "Номер кинотеатра: ") - 1;
        if (cinemaIndex < 0 || cinemaIndex >= cinemas.size()) {
            System.out.println("Неверный номер!");
            return;
        }
        
        System.out.print("Название зала: ");
        String name = scanner.nextLine();
        System.out.print("Конфигурация мест (через запятую): ");
        List<Integer> config = Arrays.stream(scanner.nextLine().split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        
        cinemas.get(cinemaIndex).addHall(new Hall(name, config));
        System.out.println("Зал добавлен!");
    }

    private static boolean isHallAvailable(Hall hall, LocalDateTime newStart, int newDuration) {
        LocalDateTime newEnd = newStart.plusMinutes(newDuration);
        for (Session session : sessions) {
            if (session.getHall().equals(hall)) {
                LocalDateTime existingStart = session.getStartTime();
                LocalDateTime existingEnd = existingStart.plusMinutes(session.getDurationMinutes());
                if (newStart.isBefore(existingEnd) && newEnd.isAfter(existingStart)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void createSession(Scanner scanner) {
        if (cinemas.isEmpty()) {
            System.out.println("Нет кинотеатров!");
            return;
        }

        System.out.println("Выберите кинотеатр:");
        for (int i = 0; i < cinemas.size(); i++) {
            System.out.println((i+1) + ". " + cinemas.get(i).getName());
        }
        int cinemaIndex = readInt(scanner, "Номер кинотеатра: ") - 1;
        Cinema cinema = cinemas.get(cinemaIndex);

        System.out.println("Выберите зал:");
        List<Hall> halls = cinema.getHalls();
        for (int i = 0; i < halls.size(); i++) {
            System.out.println((i+1) + ". " + halls.get(i).getName());
        }
        int hallIndex = readInt(scanner, "Номер зала: ") - 1;
        Hall hall = halls.get(hallIndex);

        System.out.print("Название фильма: ");
        String title = scanner.nextLine();
        int duration = readInt(scanner, "Длительность (мин): ");
        Movie movie = new Movie(title, duration);

        LocalDateTime time = null;
        while (time == null) {
            System.out.print("Дата и время (гггг-мм-дд чч:мм): ");
            try {
                time = LocalDateTime.parse(scanner.nextLine(), 
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            } catch (DateTimeParseException e) {
                System.out.println("Неверный формат даты!");
            }
        }

        if (!isHallAvailable(hall, time, duration)) {
            System.out.println("Зал занят в это время!");
            return;
        }

        sessions.add(new Session(cinema, hall, movie, time, duration));
        System.out.println("Сеанс создан!");
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка! Введите число.");
            }
        }
    }

    private static void viewSessions() {
        for (Session session : sessions) {
            System.out.println("\nКинотеатр: " + session.getCinema().getName());
            System.out.println("Зал: " + session.getHall().getName());
            System.out.println("Фильм: " + session.getMovie().getTitle());
            System.out.println("Время: " + session.getStartTime().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            System.out.println("Длительность: " + session.getDurationMinutes() + " мин");
        }
    }

    private static void userMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nПользовательское меню:");
            System.out.println("1. Поиск сеанса");
            System.out.println("2. Просмотр мест");
            System.out.println("3. Купить билет");
            System.out.println("4. Выход");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1": findSession(scanner); break;
                case "2": showSeats(scanner); break;
                case "3": buyTicket(scanner); break;
                case "4": return;
                default: System.out.println("Неверный выбор!");
            }
        }
    }

    private static void findSession(Scanner scanner) {
        System.out.print("Введите название фильма: ");
        String title = scanner.nextLine().toLowerCase();
        LocalDateTime now = LocalDateTime.now();
        Session nearest = null;

        for (Session session : sessions) {
            if (session.getMovie().getTitle().toLowerCase().contains(title) &&
                session.getStartTime().isAfter(now) &&
                session.hasAvailableSeats()) {
                
                if (nearest == null || session.getStartTime().isBefore(nearest.getStartTime())) {
                    nearest = session;
                }
            }
        }

        if (nearest != null) {
            System.out.println("\nБлижайший сеанс:");
            System.out.println("Кинотеатр: " + nearest.getCinema().getName());
            System.out.println("Зал: " + nearest.getHall().getName());
            System.out.println("Время: " + nearest.getStartTime().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            System.out.println("Свободные места: " + (nearest.hasAvailableSeats() ? "Да" : "Нет"));
        } else {
            System.out.println("Сеансы не найдены!");
        }
    }

    private static void showSeats(Scanner scanner) {
        if (sessions.isEmpty()) {
            System.out.println("Нет доступных сеансов!");
            return;
        }
        System.out.println("Доступные сеансы:");
        for (int i = 0; i < sessions.size(); i++) {
            Session s = sessions.get(i);
            System.out.println((i+1) + ". " + s.getMovie().getTitle() + " - " + 
                s.getCinema().getName() + " (" + s.getStartTime().format(
                    DateTimeFormatter.ofPattern("HH:mm")) + ")");
        }
        int index = readInt(scanner, "Выберите сеанс: ") - 1;
        if (index < 0 || index >= sessions.size()) {
            System.out.println("Неверный номер!");
            return;
        }
        sessions.get(index).printSeats();
    }

    private static void buyTicket(Scanner scanner) {
        if (sessions.isEmpty()) {
            System.out.println("Нет доступных сеансов!");
            return;
        }
        System.out.println("Доступные сеансы:");
        for (int i = 0; i < sessions.size(); i++) {
            Session s = sessions.get(i);
            System.out.println((i+1) + ". " + s.getMovie().getTitle() + " - " + 
                s.getCinema().getName() + " (" + s.getStartTime().format(
                    DateTimeFormatter.ofPattern("HH:mm")) + ")");
        }
        int sessionIndex = readInt(scanner, "Выберите сеанс: ") - 1;
        if (sessionIndex < 0 || sessionIndex >= sessions.size()) {
            System.out.println("Неверный номер!");
            return;
        }
        Session session = sessions.get(sessionIndex);
        session.printSeats();

        int row = readInt(scanner, "Ряд: ");
        int seat = readInt(scanner, "Место: ");

        if (session.bookSeat(row, seat)) {
            System.out.println("Билет куплен!");
        } else {
            System.out.println("Ошибка бронирования!");
        }
    }
}