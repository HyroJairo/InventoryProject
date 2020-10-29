package Controllers;
import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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


    public addPartController(Inventory inv) {
        this.inv = inv;
    }

    /**
     * Initializes the class
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addPartIn.setSelected(true);
        generatePartID();
        System.out.println("hi");
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
     * This goes the mainScreenController
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        mainScreen(event);
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
        int id = Integer.parseInt(addPartID.getText().trim());
        String name = addPartName.getText().trim();
        Double price = Double.parseDouble(addPartPrice.getText().trim());
        Integer stock = Integer.parseInt(addPartInv.getText().trim());
        Integer min = Integer.parseInt(addPartMin.getText().trim());
        Integer max = Integer.parseInt(addPartMax.getText().trim());
        if(addPartIn.isSelected()) {
            Integer machID = Integer.parseInt(addPartMachine.getText().trim());
            inv.addPart(new InHouse(id, name, price, stock, min, max, machID));
        }

        else if (addPartOut.isSelected()) {
            String machID = addPartMachine.getText().trim();
            inv.addPart(new Outsourced(id, name, price, stock, min, max, machID));
        }
        mainScreen(event);
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
}


