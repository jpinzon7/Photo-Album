package photos;

import static photos.Utils.DATA_FILE;
import static photos.Utils.saveUsers;
import static photos.Utils.USERS;
import static photos.Utils.CURRENT_USER;
import static photos.Utils.CURRENT_ALBUMS;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * This class controls the user scene.
 *
 * @author Maxim Vyshnevsky
 */
public class UserSceneController {
    @FXML
    private Label userLabel; // The label at the top of the page
    @FXML
    private TilePane albumPane; // The TilePane to display the albums
    @FXML
    private Label albumName; // The label for the album name
    @FXML
    private TextField newAlbumName; // The text field for the new album name
    @FXML
    private ScrollPane scrollPane; // The scroll pane for the TilePane

    // Runs after the user logs in
    // If the user has already logged in and just going back to this page, do not
    // run
    public void initialize(String username) {
        userLabel.setText("Welcome, " + username + "!"); // Top of the page greeting

        // If the file exists and is not empty, read the list of users from it and store
        // it in the users list
        if (DATA_FILE.exists() && DATA_FILE.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
                @SuppressWarnings("unchecked")
                List<User> readUsers = (List<User>) ois.readObject();
                USERS = readUsers;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        // If the user is not in the list of users, add them
        if (USERS.stream().noneMatch(user -> user.getUsername().equals(username))) {
            System.out.println("Adding new user...");
            USERS.add(new User(username));
            // Write the user to the data file
            saveUsers();
        }

        // Print the list of users names
        System.out.println("Stored users: {");
        for (User user : USERS) {
            System.out.println(user.getUsername());
        }
        System.out.println("}");

        // Update the current user
        CURRENT_USER = USERS.stream().filter(u -> u.getUsername().equals(username)).findFirst().get();

        // Print the current user's name
        System.out.println("CurrentUser: " + CURRENT_USER.getUsername());

        // Update the current user's albums
        CURRENT_ALBUMS = CURRENT_USER.getAlbums();

        // Print the current user's albums
        System.out.println("User has " + CURRENT_ALBUMS.size() + " albums");
        System.out.println("Albums: {");
        for (Album album : CURRENT_ALBUMS) {
            System.out.println(album.getName());
            // System.out.println(album.getEarlyDate());
            // System.out.println(album.getLateDate());
        }
        System.out.println("}");

        // Load the albums into the TilePane
        for (Album album : CURRENT_ALBUMS) {
            tileMaker(album);
        }

    }

    // If user clicks on create album button
    public void createAlbum() {
        // Create a new album with the name from the text field
        Album album = new Album(newAlbumName.getText());
        // Add the album to the user's list of albums
        CURRENT_USER.addAlbum(album);

        // Add the album to the TilePane
        tileMaker(album);

        // Update the data file
        saveUsers();
    }

    // Create a tile for the album
    // Tiles are used to display the albums in the TilePane
    public void tileMaker(Album album) {
        System.out.println("Loading album: " + album.getName());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AlbumTile.fxml"));
            Node albumTile = loader.load();
            AlbumTileController albumTileController = loader.getController();
            albumTileController.initialize(album, albumTile, scrollPane);
            albumPane.getChildren().add(albumTile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // If user clicks on logout button
    // Go back to the login scene
    public void logout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScene.fxml"));
            Parent root = loader.load();
            Scene loginScene = new Scene(root);

            Stage stage = (Stage) userLabel.getScene().getWindow();
            stage.setScene(loginScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // If user clicks on exit button, exit the program
    public void exitProgram() {
        System.exit(0);
    }
}
