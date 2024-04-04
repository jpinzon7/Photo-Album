package photos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;

public class UserSceneController {
    @FXML
    private Label userLabel;
    @FXML
    private TilePane albumPane;
    @FXML
    private Label albumName;
    @FXML
    private TextField newAlbumName;

    private User currentUser;

    public void initialize(String username) {
        userLabel.setText("Welcome, " + username + "!");

        File file = new File("data/users.dat");
        List<User> users = new ArrayList<>();
        if (file.exists() && file.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                @SuppressWarnings("unchecked")
                List<User> readUsers = (List<User>) ois.readObject();
                users = readUsers;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (users.stream().noneMatch(user -> user.getUsername().equals(username))) {
            System.out.println("Adding new user");
            // Add the new user to the list and write it back to the file
            users.add(new User(username));
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(users);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Load the user's albums
        currentUser = users.stream().filter(u -> u.getUsername().equals(username)).findFirst().get();
        List<Album> albums = currentUser.getAlbums();
        System.out.println("User has " + albums.size() + " albums");

        // Load the albums into the TilePane
        for (Album album : albums) {
            String albumName = album.getName();
            System.out.println("Loading album: " + albumName);
            albumPane.getChildren().add(new Label(albumName));
        }
    }

    // If user clicks on create album button
    public void createAlbum() {
        // Create a new album
        Album album = new Album(newAlbumName.getText());

        // Add the album to the user's list of albums
        currentUser.addAlbum(album);

        // Add the album to the TilePane
        albumPane.getChildren().add(new Label(album.getName()));
    }
}
