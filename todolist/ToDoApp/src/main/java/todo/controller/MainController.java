package todo.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import todo.model.Task;
import todo.util.DBHelper;

import javafx.collections.*;
import javafx.fxml.FXMLLoader;
import javafx.stage.*;
import javafx.scene.*;

public class MainController {
    @FXML private TableView<Task> taskTable;
    @FXML private TableColumn<Task, String> titleColumn;
    @FXML private TableColumn<Task, String> descriptionColumn;
    @FXML private TableColumn<Task, String> dueDateColumn;
    @FXML private TableColumn<Task, String> categoryColumn;

    @FXML public void initialize() {
        titleColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().title));
        descriptionColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().description));
        dueDateColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().dueDate.toString()));
        categoryColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().category.name));
        refreshTable();
    }

    private void refreshTable() {
        try {
            taskTable.setItems(FXCollections.observableArrayList(DBHelper.getAllTasks()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML private void handleAddTask() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/add_task.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Новая задача");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        refreshTable();
    }
}
