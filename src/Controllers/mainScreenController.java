package Controllers;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import Model.*;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;


public class mainScreenController implements Initializable {
    Stage stage;
    Parent scene;
    Inventory inv;

    @FXML
    private TableView<Part> partsTable = new TableView<>();
    @FXML
    private TableColumn<Part, Integer> idColumn = new TableColumn<>();
    private TableColumn<Part, String> name;
    private TableColumn<Part, Integer> stock;
    private TableColumn<Part, Double> price;


    private ObservableList<Part> partInventory = FXCollections.observableArrayList();
    private ObservableList<Product> productInventory = FXCollections.observableArrayList();
    private ObservableList<Part> partsInventorySearch = FXCollections.observableArrayList();
    private ObservableList<Product> productInventorySearch = FXCollections.observableArrayList();



    public mainScreenController(Inventory inv) {
        this.inv = inv;
    }

    /**
     * Initializes the controller class
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        partInventory.setAll(inv.getAllParts());
        partsTable.setItems(partInventory);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));


    }




    @FXML
    void onActionAddParts(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/add_part.fxml"));
        addPartController controller = new addPartController(inv);

        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void onActionAddProducts(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/add_product.fxml"));
        addProductController controller = new addProductController(inv);

        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void onActionDeleteParts(ActionEvent event) {

    }

    @FXML
    void onActionDeleteProducts(ActionEvent event) {

    }

    @FXML
    void onActionExit(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void onActionModifyParts(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/modify_part.fxml"));
        modifyPartController controller = new modifyPartController(inv);

        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void onActionModifyProducts(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/modify_product.fxml"));
        modifyProductController controller = new modifyProductController(inv);

        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void onActionPartsSearch(ActionEvent event) {

    }

    @FXML
    void onActionProductsSearch(ActionEvent event) {

    }



    private <T> TableColumn<T, Double> formatPrice() {
        TableColumn<T, Double> costCol = new TableColumn("Price");
        costCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
        // Format as currency
        costCol.setCellFactory((TableColumn<T, Double> column) -> {
            return new TableCell<T, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    if (!empty) {
                        setText("$" + String.format("%10.2f", item));
                    }
                    else{
                        setText("");
                    }
                }
            };
        });
        return costCol;
    }
}






