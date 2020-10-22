package Controllers;
import Model.Inventory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class addPartController {
    Inventory inv;
    @FXML
    private RadioButton addPartIn;

    @FXML
    private RadioButton addPartOut;

    @FXML
    private TextField addPartID;

    @FXML
    private TextField addPartName;

    @FXML
    private TextField addPartInv;

    @FXML
    private TextField addPartPrice;

    @FXML
    private TextField addPartMax;

    @FXML
    private TextField addPartMachine;

    @FXML
    private TextField addPartMin;

    @FXML
    private Button addPartSave;

    @FXML
    private Button addPartCancel;

    public addPartController(Inventory inv) {
        this.inv = inv;
    }

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/main_screen.fxml"));

        Controllers.mainScreenController controller = new Controllers.mainScreenController(inv);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    @FXML
    void onActionIn(ActionEvent event) {

    }

    @FXML
    void onActionOut(ActionEvent event) {

    }

    @FXML
    void onActionSave(ActionEvent event) {

    }

}

