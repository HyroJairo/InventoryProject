package Controllers;

import Model.Inventory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.stage.Stage;

import java.io.IOException;

public class addProductController {

    Inventory inv;

    @FXML
    private TextField addProductID;

    @FXML
    private TextField addProductName;

    @FXML
    private TextField addProductInv;

    @FXML
    private TextField addProductPrice;

    @FXML
    private TextField addPartMax;

    @FXML
    private TextField addProductMin;

    @FXML
    private Button addProductAdd;

    @FXML
    private Button addProductCancel;

    @FXML
    private TextField addProductSearch;

    @FXML
    private TableView<?> partsTable;

    @FXML
    private TableColumn<?, ?> partIDColumn;

    @FXML
    private TableColumn<?, ?> partNameColumn;

    @FXML
    private TableColumn<?, ?> partInventoryColumn;

    @FXML
    private TableColumn<?, ?> partPriceColumn;

    @FXML
    private TableView<?> partsTable1;

    @FXML
    private TableColumn<?, ?> partIDColumn1;

    @FXML
    private TableColumn<?, ?> partNameColumn1;

    @FXML
    private TableColumn<?, ?> partInventoryColumn1;

    @FXML
    private TableColumn<?, ?> partPriceColumn1;

    @FXML
    private Button addProductSave;

    @FXML
    private Button addProductRemove;

    public addProductController(Inventory inv) {
        this.inv = inv;
    }

    @FXML
    void onActionAdd(ActionEvent event) {

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
    void onActionRemove(ActionEvent event) {

    }

    @FXML
    void onActionSave(ActionEvent event) {

    }

}

