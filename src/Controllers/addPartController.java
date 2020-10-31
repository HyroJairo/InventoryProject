package Controllers;
import Model.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * This is the addPartController. This screen shows for you to add parts
 */
public class addPartController implements Initializable {
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
    @FXML
    private Label partMachineLabel;


    /**
     * This is the constructor for the addPartController class
     * @param inv
     */
    public addPartController(Inventory inv) {
        this.inv = inv;
    }

    /**
     * Initializes the addProductController class
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addPartIn.setSelected(true);
        generatePartID();
    }

    /**
     * This generates the id for the part
     */
    void generatePartID() {
        Random num = new Random();
        Integer idNum = num.nextInt(1000);
        if(inv.lookUpPart(idNum) == null) {
            addPartID.setText(idNum.toString());
        }
    }



    /**
     * This switches to the InHouse section where there is the Machine ID
     * @param event
     */
    @FXML
    void onActionIn(ActionEvent event) {
        partMachineLabel.setText("Machine ID");

    }

    /**
     * This switches to the Outsource section where there is the Company Name
     * @param event
     */
    @FXML
    void onActionOut(ActionEvent event) {
        partMachineLabel.setText("Company Name");

    }

    /**
     * This saves the adding of the product whether its for InHouse or Outsourced
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        boolean clean = true;
        TextField[] fields = {addPartID, addPartName, addPartPrice,
                addPartInv, addPartMin, addPartMax};
        for(TextField field: fields) {
            if(!checkValue(field)) {
                clean = false;
            }
        }
        if(!clean) return;

        int id = Integer.parseInt(addPartID.getText().trim());
        String name = addPartName.getText().trim();
        Double price = Double.parseDouble(addPartPrice.getText().trim());
        Integer stock = Integer.parseInt(addPartInv.getText().trim());
        Integer min = Integer.parseInt(addPartMin.getText().trim());
        Integer max = Integer.parseInt(addPartMax.getText().trim());
        //this checks if the stock is not greater than max
        if(stock <= max) {
            //this checks if the stock is not less than min
            if(stock >= min) {
                //this checks if max is greater or equal to min and if the associate table is not empty
                if(max >= min) {
                    //This is a divider
                    if(addPartIn.isSelected()) {
                        Integer machID = Integer.parseInt(addPartMachine.getText().trim());
                        inv.addPart(new InHouse(id, name, price, stock, min, max, machID));
                    }

                    else if (addPartOut.isSelected()) {
                        String machID = addPartMachine.getText().trim();
                        inv.addPart(new Outsourced(id, name, price, stock, min, max, machID));
                    }
                    mainScreen(event);

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
     * This goes the mainScreenController
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

        if(text.getText().trim().isEmpty() || (text.getText().trim() == null)) {
            infoBoxError("value cannot be empty", "error");
            return false;
        } else if(text == addPartInv && !text.getText().trim().matches("[0-9]+")) {
            infoBoxError("inventory must be of numerical format", "error");

            return false;
        } else if(text == addPartPrice && !text.getText().trim().matches("^[0-9]+[.][0-9]{2}$")) {

            infoBoxError("price must be of price format ex(1.11)", "error");
            return false;
        } else if(text == addPartPrice && Double.parseDouble(text.getText().trim()) < 0) {
            infoBoxError("value cannot be less than 0", "error");
            return false;
        } else if((text == addPartMax ||
                text == addPartMin ||
                text == addPartMachine) && !text.getText().trim().matches("[0-9]+")) {

            infoBoxError(text.getText() + " must be of numerical format", "error");
            return false;
        } else {
            return true;
        }
    }


}


