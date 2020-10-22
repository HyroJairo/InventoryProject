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

public class modifyPartController {
    Inventory inv;

    @FXML
    private RadioButton modifyPartIn;

    @FXML
    private RadioButton modifyPartOut;

    @FXML
    private TextField modifyPartID;

    @FXML
    private TextField modifyPartName;

    @FXML
    private TextField modifyPartInv;

    @FXML
    private TextField modifyPartPrice;

    @FXML
    private TextField modifyPartMax;

    @FXML
    private TextField modifyPartMachine;

    @FXML
    private TextField modifyPartMin;

    @FXML
    private Button modifyPartSave;

    @FXML
    private Button modifyPartCancel;

    public modifyPartController(Inventory inv) {
        this.inv = inv;
    }

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/main_screen.fxml"));
        mainScreenController controller = new mainScreenController(inv);

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

