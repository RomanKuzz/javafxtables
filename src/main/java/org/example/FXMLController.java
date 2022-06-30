package org.example;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

import com.sun.javafx.scene.control.IntegerField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.model.Comment;
import org.example.model.Post;
import org.example.model.User;

public class FXMLController implements Initializable {
    @FXML
    private TabPane tabs;

    @FXML
    private TableView<Post> postsTable;
    @FXML
    private TableColumn<Integer, Void> postAuthorIdColumn;
    @FXML
    private IntegerField postId;
    @FXML
    private TextField postText;
    @FXML
    private IntegerField postAuthorId;

    @FXML
    private TableView<User> usersTable;
    @FXML
    private IntegerField userId;
    @FXML
    private TextField userName;

    @FXML
    private TableView<Comment> commentsTable;
    @FXML
    private TableColumn<Integer, Void> commentsPostIdColumn;
    @FXML
    private TableColumn<Integer, Void> commentsAuthorIdColumn;
    @FXML
    private IntegerField process;
    @FXML
    private IntegerField commentPostId;
    @FXML
    private IntegerField commentAuthorId;
    @FXML
    private TextField commentText;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        postsTable.getItems().add(new Post(1, "hello world", 1));
//        usersTable.getItems().add(new User(1, "Chloe"));
//        usersTable.getItems().add(new User(2, "Jackson"));
//        commentsTable.getItems().add(new Comment(1, 1, 2, "comment"));

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(ClassLoader.getSystemResource("datasource.xlsx").openStream());

            Iterator<Row> posts = workbook.getSheet("posts").rowIterator();
            posts.next(); // skip header
            posts.forEachRemaining(postRow -> {
                Integer postId = (int) Math.round(postRow.getCell(0).getNumericCellValue());
                String text = postRow.getCell(1).getStringCellValue();
                Integer authorId = (int) Math.round(postRow.getCell(2).getNumericCellValue());
                postsTable.getItems().add(new Post(postId, text, authorId));
            });

            Iterator<Row> users = workbook.getSheet("users").rowIterator();
            users.next(); // skip header
            users.forEachRemaining(userRow -> {
                Integer userId = (int) Math.round(userRow.getCell(0).getNumericCellValue());
                String name = userRow.getCell(1).getStringCellValue();
                usersTable.getItems().add(new User(userId, name));
            });

            Iterator<Row> comments = workbook.getSheet("comments").rowIterator();
            comments.next(); // skip header
            comments.forEachRemaining(commentRow -> {
                Integer process = (int) Math.round(commentRow.getCell(0).getNumericCellValue());
                Integer postId = (int) Math.round(commentRow.getCell(1).getNumericCellValue());
                Integer authorId = (int) Math.round(commentRow.getCell(2).getNumericCellValue());
                String text = commentRow.getCell(3).getStringCellValue();
                commentsTable.getItems().add(new Comment(process, postId, authorId, text));
            });
        } catch (IOException e) {
            System.exit(2);
        }
    }

    @FXML
    protected void switchTables(MouseEvent event) {
        String target = event.getTarget().toString();
        if (target.contains("postAuthorIdColumn")) {
            Integer selectedUserId = postsTable.getSelectionModel().getSelectedItem().getAuthorId();
            User selectedUser = usersTable.getItems().stream()
                    .filter(user -> user.getUserId().equals(selectedUserId))
                    .findFirst()
                    .orElseThrow();
            usersTable.getSelectionModel().select(selectedUser);
            tabs.getSelectionModel().select(1);
        } else if (target.contains("commentsAuthorIdColumn")) {
            Integer selectedUserId = commentsTable.getSelectionModel().getSelectedItem().getAuthorId();
            User selectedUser = usersTable.getItems().stream()
                    .filter(user -> user.getUserId().equals(selectedUserId))
                    .findFirst()
                    .orElseThrow();
            usersTable.getSelectionModel().select(selectedUser);
            tabs.getSelectionModel().select(1);
        } else if (target.contains("commentsPostIdColumn")) {
            Integer selectedPostId = commentsTable.getSelectionModel().getSelectedItem().getPostId();
            Post selectedPost = postsTable.getItems().stream()
                    .filter(post -> post.getPostId().equals(selectedPostId))
                    .findFirst()
                    .orElseThrow();
            postsTable.getSelectionModel().select(selectedPost);
            tabs.getSelectionModel().select(0);
        }
    }

    @FXML
    protected void addPost(ActionEvent event) throws IOException {
        postsTable.getItems().add(new Post(postId.getValue(),
                postText.getText(),
                postAuthorId.getValue()
        ));

        XSSFWorkbook workbook = new XSSFWorkbook(ClassLoader.getSystemResource("datasource.xlsx").openStream());

        XSSFSheet posts = workbook.getSheet("posts");

        XSSFRow row = posts.createRow(posts.getLastRowNum() + 1);
        row.createCell(0).setCellValue(postId.getValue());
        row.createCell(1).setCellValue(postText.getText());
        row.createCell(2).setCellValue(postAuthorId.getValue());

        FileOutputStream outputStream = new FileOutputStream(ClassLoader.getSystemResource("datasource.xlsx").getFile());
        workbook.write(outputStream);
        workbook.close();

        postId.setValue(0);
        postText.setText("");
        postAuthorId.setValue(0);
    }

    @FXML
    protected void addUser(ActionEvent event) throws IOException {
        usersTable.getItems().add(new User(userId.getValue(), userName.getText()));

        XSSFWorkbook workbook = new XSSFWorkbook(ClassLoader.getSystemResource("datasource.xlsx").openStream());

        XSSFSheet posts = workbook.getSheet("users");

        XSSFRow row = posts.createRow(posts.getLastRowNum() + 1);
        row.createCell(0).setCellValue(userId.getValue());
        row.createCell(1).setCellValue(userName.getText());

        FileOutputStream outputStream = new FileOutputStream(ClassLoader.getSystemResource("datasource.xlsx").getFile());
        workbook.write(outputStream);
        workbook.close();

        userId.setValue(0);
        userName.setText("");
    }

    @FXML
    protected void addComment(ActionEvent event) throws IOException {
        commentsTable.getItems().add(new Comment(process.getValue(),
                commentPostId.getValue(),
                commentAuthorId.getValue(),
                commentText.getText()
        ));

        XSSFWorkbook workbook = new XSSFWorkbook(ClassLoader.getSystemResource("datasource.xlsx").openStream());

        XSSFSheet posts = workbook.getSheet("comments");

        XSSFRow row = posts.createRow(posts.getLastRowNum() + 1);
        row.createCell(0).setCellValue(process.getValue());
        row.createCell(1).setCellValue(commentPostId.getValue());
        row.createCell(2).setCellValue(commentAuthorId.getValue());
        row.createCell(3).setCellValue(commentText.getText());

        FileOutputStream outputStream = new FileOutputStream(ClassLoader.getSystemResource("datasource.xlsx").getFile());
        workbook.write(outputStream);
        workbook.close();

        process.setValue(0);
        commentPostId.setValue(0);
        commentAuthorId.setValue(0);
        commentText.setText("");
    }
}