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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import Model.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.scene.control.TableColumn;

/**
 * This is the mainScreenController. This is the main screen which pops up when you run the program
 */
public class mainScreenController implements Initializable {
    Stage stage;
    Parent scene;
    Inventory inv;

    //Under these private fields are for the parts section
    @FXML
    private TextField partSearchBox;
    @FXML
    private TableView<Part> partsTable = new TableView<>();
    @FXML
    private TableColumn<Part, Integer> partIDColumn = new TableColumn<>();
    @FXML
    private TableColumn<Part, String> partNameColumn = new TableColumn<>();
    @FXML
    private TableColumn<Part, Integer> partInventoryColumn = new TableColumn<>();
    @FXML
    private TableColumn<Part, Double> partPriceColumn = new TableColumn<>();
    private ObservableList<Part> partInventory = FXCollections.observableArrayList();

    //Under these private fields are for the products section
    @FXML
    private TextField productSearchBox;
    @FXML
    private TableView<Product> productsTable = new TableView<>();
    @FXML
    private TableColumn<Product, Integer> productIDColumn = new TableColumn<>();
    @FXML
    private TableColumn<Product, String> productNameColumn = new TableColumn<>();
    @FXML
    private TableColumn<Product, Integer> productInventoryColumn = new TableColumn<>();
    @FXML
    private TableColumn<Product, Double> productPriceColumn = new TableColumn<>();
    private ObservableList<Product> productInventory = FXCollections.observableArrayList();

    public mainScreenController(Inventory inv) {
        this.inv = inv;
    }

    /**
     * Initializes the controller class
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //This uploads the info into the parts table
        partInventory.setAll(inv.getAllParts());
        partsTable.setItems(partInventory);
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        //This uploads the info into the products table
        productInventory.setAll(inv.getAllProducts());
        productsTable.setItems(productInventory);
        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * This helps searches for the parts section
     *
     * @param event
     */
    @FXML
    void onActionPartsSearch(ActionEvent event) {
        if (!partSearchBox.getText().trim().isEmpty()) {
            partsTable.setItems(inv.lookUpPart(partSearchBox.getText().trim()));
            partsTable.refresh();
        }
    }

    /**
     * This goes to the add parts screen
     *
     * @param event
     * @throws IOException
     */
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

    /**
     * This goes to the modify parts screen
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionModifyParts(ActionEvent event) throws IOException {
        Part item = partsTable.getSelectionModel().getSelectedItem();
        if (item != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/modify_part.fxml"));
            Part part = partsTable.getSelectionModel().getSelectedItem();
            modifyPartController controller = new modifyPartController(inv, part);

            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
    }

    /**
     * This is the delete action for parts
     *
     * @param event
     */
    @FXML
    void onActionDeleteParts(ActionEvent event) {
        Part removePart = partsTable.getSelectionModel().getSelectedItem();
        if (removePart != null) {
            if(infoBoxConfirm()) {
                inv.deletePart(removePart);
                partInventory.remove(removePart);
            }
        } else {
            infoBoxError("You must select a part", "error");
        }

    }

    /**
     * This helps searches for the products section
     *
     * @param event
     */
    @FXML
    void onActionProductsSearch(ActionEvent event) {
        if (!productSearchBox.getText().trim().isEmpty()) {
            productsTable.setItems(inv.lookUpProduct(productSearchBox.getText().trim()));
            productsTable.refresh();
        }
    }

    /**
     * This goes to the add products screen
     *
     * @param event
     * @throws IOException
     */
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

    /**
     * This goes to the modify products screen
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionModifyProducts(ActionEvent event) throws IOException {
        Product item = productsTable.getSelectionModel().getSelectedItem();
        if (item != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/modify_product.fxml"));
            modifyProductController controller = new modifyProductController(inv, item);
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
    }

    /**
     * This is the delete action for products
     *
     * @param event
     */
    @FXML
    void onActionDeleteProducts(ActionEvent event) {
        Product removeProduct = productsTable.getSelectionModel().getSelectedItem();
        if (removeProduct != null) {
            if(removeProduct.getAllAssociatedParts().size() > 0) {

               Alert alert = new Alert(Alert.AlertType.WARNING);
               alert.setHeaderText("Warning");
               alert.setContentText("Product still has parts associated with it");
               alert.showAndWait();
               if(infoBoxConfirm()) {
                   inv.deleteProduct(removeProduct);
                   productInventory.remove(removeProduct);
               }
            }
        } else {
            infoBoxError("You must select a product", "error");
        }

    }

    /**
     * This is the exit action for the whole screen
     *
     * @param event
     */
    @FXML
    void onActionExit(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    /**
     * This clears the text in the search box
     *
     * @param event
     */
    @FXML
    void clearText(MouseEvent event) {
        Object source = event.getSource();
        TextField field = (TextField) source;
        field.setText("");
        if (partSearchBox == field) {
            if (partInventory.size() != 0) {
                partsTable.setItems(partInventory);
            }
        }
        if (productSearchBox == field) {
            if (productInventory.size() != 0) {
                productsTable.setItems(productInventory);
            }
        }
    }

    /**
     * This is an infobox for errors
     *
     * @param infoMessage
     * @param headerText
     */
    public void infoBoxError(String infoMessage, String headerText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(headerText);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }

    /**
     * This is an infobox for confirmations
     */
    public boolean infoBoxConfirm() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

}
