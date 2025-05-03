package todo.model;

import java.time.LocalDate;

public class Task {
    public int id;
    public String title;
    public String description;
    public LocalDate dueDate;
    public Category category;
}
