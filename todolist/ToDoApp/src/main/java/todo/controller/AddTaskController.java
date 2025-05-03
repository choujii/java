package todo.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import todo.model.*;
import todo.util.DBHelper;

import java.util.List;

public class AddTaskController {
    @FXML private TextField titleField;
    @FXML private TextField descriptionField;
    @FXML private DatePicker dueDatePicker;
    @FXML private ComboBox<Category> categoryBox;

    @FXML public void initialize() {
        try {
            List<Category> categories = DBHelper.getCategories();
            categoryBox.getItems().addAll(categories);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML private void handleSave() {
        Task task = new Task();
        task.title = titleField.getText();
        task.description = descriptionField.getText();
        task.dueDate = dueDatePicker.getValue();
        task.category = categoryBox.getValue();
        try {
            DBHelper.insertTask(task);
            ((Stage) titleField.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
