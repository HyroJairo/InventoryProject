package Controllers;

import Model.InHouse;
import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * This is the addProductController class
 */
public class addProductController implements Initializable {
    Inventory inv;

    //Below these fields are the ones you have to fill out
    @FXML
    private TextField addProductID;
    @FXML
    private TextField addProductName;
    @FXML
    private TextField addProductInv;
    @FXML
    private TextField addProductPrice;
    @FXML
    private TextField addProductMax;
    @FXML
    private TextField addProductMin;

    //This is for the parts table
    @FXML
    private TextField partSearchBox;
    @FXML
    private Button addPartAdd;
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



    //This is for the associate table
    @FXML
    private TableView<Part> associateTable;
    @FXML
    private TableColumn<Part, Integer> partIDColumn1 = new TableColumn<>();
    @FXML
    private TableColumn<Part, String> partNameColumn1 = new TableColumn<>();
    @FXML
    private TableColumn<Part, Integer> partInventoryColumn1 = new TableColumn<>();
    @FXML
    private TableColumn<Part, Double> partPriceColumn1 = new TableColumn<>();

    @FXML
    private Button addPartRemove;
    @FXML
    private Button addProductSave;
    @FXML
    private Button addProductCancel;

    private ObservableList<Part> partInventory = FXCollections.observableArrayList();
    private ObservableList<Part> part1Inventory = FXCollections.observableArrayList();

    /**
     * This is the constructor for the addProductController class
     * @param inv
     */
    public addProductController(Inventory inv) {
        this.inv = inv;
    }

    /**
     * Initializes the addProductController class
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //This populates the parts table
        //This uploads the info into the parts table
        partInventory.setAll(inv.getAllParts());
        partsTable.setItems(partInventory);
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        generateProductID();
    }

    /**
     * This randomly generates the id for the product
     */
    void generateProductID() {
        Random num = new Random();
        Integer idNum = num.nextInt(1000);
        if(inv.lookUpProduct(idNum) == null) {
            addProductID.setText(idNum.toString());
        }
    }

    /**
     * This adds the parts data into the associates table
     * @param event
     */
    @FXML
    void onActionAdd(ActionEvent event) {
        Part part = partsTable.getSelectionModel().getSelectedItem();
        boolean notSame = true;
        if(part != null) {
            int id = part.getId();
            for(int i = 0; i < part1Inventory.size(); i++) {
                if(part1Inventory.get(i).getId() == id) {
                    notSame = false;
                }
            }
            if(notSame) {
                part1Inventory.add(part);
            }
        }
        associateTable.setItems(part1Inventory);
        partIDColumn1.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn1.setCellValueFactory(new PropertyValueFactory<>("price"));
    }


    /**
     * This removes the part from the associate table
     * @param event
     */
    @FXML
    void onActionRemove(ActionEvent event) {
        Part removePart = associateTable.getSelectionModel().getSelectedItem();
        if(removePart != null) {
            if(infoBoxConfirm()) {
                part1Inventory.remove(removePart);
                associateTable.refresh();
            }

        }
    }

    /**
     * This saves the data for the associated parts
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        boolean clean = true;
        TextField[] fields = {addProductID, addProductName, addProductPrice,
                addProductInv, addProductMin, addProductMax};
        for(TextField field: fields) {
            if(!checkValue(field)) {
                clean = false;
            }
        }
        if(!clean) return;

        int id = Integer.parseInt(addProductID.getText().trim());
        String name = addProductName.getText().trim();
        Double price = Double.parseDouble(addProductPrice.getText().trim());
        Integer stock = Integer.parseInt(addProductInv.getText().trim());
        Integer min = Integer.parseInt(addProductMin.getText().trim());
        Integer max = Integer.parseInt(addProductMax.getText().trim());
        //this checks if the stock is not greater than max
        if(stock <= max) {
            //this checks if the stock is not less than min
            if(stock >= min) {
                //this checks if max is greater or equal to min and if the associate table is not empty
                if(max >= min) {
                    //this checks if part1Inventory is not empty
                    if(part1Inventory.size() != 0) {
                        Product product = new Product(id, name, price, stock, min, max);
                        double cost = 0;
                        for(int i = 0; i < part1Inventory.size(); i++) {
                            Part item = part1Inventory.get(i);
                            cost += item.getPrice();
                            product.addAssociatedPart(part1Inventory.get(i));
                        }
                       //this checks if price is greater or equal to cost
                        if(price >= cost) {
                            inv.addProduct(product);
                            mainScreen(event);
                        } else {
                            infoBoxError("price cannot be less than the price of the parts", "error");
                        }
                    }  else {
                        infoBoxError("You must add a part", "error");
                    }
                } else {
                    infoBoxError("max cannot be less than min", "error");
                }
            } else {
                infoBoxError("stock cannot be less than min", "error");
            }
        } else {
            infoBoxError("stock cannot be greater than max", "error");
        }
    }

    /**
     * This searches for the parts section
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
     * This clears the search box and resets the parts table
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
    }

    /**
     * This goes back to the main screen
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        if(infoBoxConfirm()) {
            mainScreen(event);
        }
    }

    /**
     * This is the private method of going to the mainScreenController
     * @param event
     * @throws IOException
     */
    private void mainScreen(Event event) throws IOException {
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

    /**
     * This is an infobox for errors
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

    /**
     * This checks the user input values
     */
    public boolean checkValue(TextField text) {

        if (text.getText().trim().isEmpty() || (text.getText().trim() == null)) {
            infoBoxError("value cannot be empty", "error");
            return false;
        } else if (text == addProductInv && !text.getText().trim().matches("[0-9]+")) {
            infoBoxError("inventory must be of numerical format", "error");
            return false;
        } else if (text == addProductPrice && !text.getText().trim().matches("^[0-9]+[.][0-9]{2}$")) {

            infoBoxError("price must be of price format ex(1.11)", "error");
            return false;
        } else if (text == addProductPrice && Double.parseDouble(text.getText().trim()) < 0) {
            infoBoxError("price cannot be less than 0", "error");
            return false;
        } else if ((text == addProductMax ||
                text == addProductMin) && !text.getText().trim().matches("[0-9]+")) {

            infoBoxError(text.getText() + " must be of numerical format", "error");
            return false;
        } else {
            return true;
        }
    }
}

