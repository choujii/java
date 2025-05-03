package todo.util;

import todo.db.Database;
import todo.model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class DBHelper {
    public static List<Task> getAllTasks() throws Exception {
        List<Task> list = new ArrayList<>();
        Connection conn = Database.connect();
        String sql = "SELECT t.*, c.name as categoryName FROM tasks t LEFT JOIN categories c ON t.category_id = c.id";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            Task t = new Task();
            t.id = rs.getInt("id");
            t.title = rs.getString("title");
            t.description = rs.getString("description");
            t.dueDate = LocalDate.parse(rs.getString("due_date"));
            Category cat = new Category();
            cat.id = rs.getInt("category_id");
            cat.name = rs.getString("categoryName");
            t.category = cat;
            list.add(t);
        }
        conn.close();
        return list;
    }

    public static List<Category> getCategories() throws Exception {
        List<Category> list = new ArrayList<>();
        Connection conn = Database.connect();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM categories");
        while (rs.next()) {
            Category c = new Category();
            c.id = rs.getInt("id");
            c.name = rs.getString("name");
            list.add(c);
        }
        conn.close();
        return list;
    }

    public static void insertTask(Task task) throws Exception {
        Connection conn = Database.connect();
        String sql = "INSERT INTO tasks (title, description, due_date, category_id) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, task.title);
        stmt.setString(2, task.description);
        stmt.setString(3, task.dueDate.toString());
        stmt.setInt(4, task.category.id);
        stmt.executeUpdate();
        conn.close();
    }
}
