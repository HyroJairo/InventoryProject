package Controllers;

import Model.InHouse;
import Model.Outsourced;
import Model.Inventory;
import Model.Part;
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
import java.util.ResourceBundle;

/**
 * This is the modifyPartController. This screen shows for you to modify parts
 */
public class modifyPartController implements Initializable {
    Inventory inv;
    Part part;

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
    @FXML
    private Label partMachineLabel;

    /**
     * This is the constructor for the modifyPartController
     * @param inv
     * @param part
     */
    public modifyPartController(Inventory inv, Part part) {
        this.inv = inv;
        this.part = part;
    }

    /**
     * Initializes the controller class
     * @param resources
     * @throws IOException
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(part instanceof InHouse) {
            InHouse partOne = (InHouse) part;
            modifyPartIn.setSelected(true);
            partMachineLabel.setText("Machine ID");
            modifyPartName.setText(partOne.getName());
            modifyPartID.setText(Integer.toString(partOne.getId()));
            modifyPartInv.setText(Integer.toString(partOne.getStock()));
            modifyPartPrice.setText(Double.toString(partOne.getPrice()));
            modifyPartMin.setText(Integer.toString(partOne.getMin()));
            modifyPartMax.setText(Integer.toString(partOne.getMax()));
            modifyPartMachine.setText(Integer.toString(partOne.getMachineId()));
        }
        if(part instanceof Outsourced) {
            Outsourced partTwo = (Outsourced) part;
            modifyPartIn.setSelected(true);
            partMachineLabel.setText("Company Name");
            modifyPartName.setText(partTwo.getName());
            modifyPartID.setText(Integer.toString(partTwo.getId()));
            modifyPartInv.setText(Integer.toString(partTwo.getStock()));
            modifyPartPrice.setText(Double.toString(partTwo.getPrice()));
            modifyPartMin.setText(Integer.toString(partTwo.getMin()));
            modifyPartMachine.setText(partTwo.getCompanyName());
        }
    }

    /**
     * This goes to the mainScreenController
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
     * This saves the modifying of the product whether its for InHouse or Outsourced
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        int id = Integer.parseInt(modifyPartID.getText().trim());
        String name = modifyPartName.getText().trim();
        Double price = Double.parseDouble(modifyPartPrice.getText().trim());
        Integer stock = Integer.parseInt(modifyPartInv.getText().trim());
        Integer min = Integer.parseInt(modifyPartMin.getText().trim());
        Integer max = Integer.parseInt(modifyPartMax.getText().trim());
        if(modifyPartIn.isSelected()) {
            Integer machID = Integer.parseInt(modifyPartMachine.getText().trim());
            Integer index = inv.getAllParts().indexOf(part);
            inv.updatePart(index, new InHouse(id, name, price, stock, min, max, machID));
        }

        else if (modifyPartOut.isSelected()) {
            String machID = modifyPartMachine.getText().trim();
            Integer index = inv.getAllParts().indexOf(part);
            inv.updatePart(index, new Outsourced(id, name, price, stock, min, max, machID));
        }
        mainScreen(event);
    }

    /**
     * This is the private method for going to the mainScreenController
     * @param event
     * @throws IOException
     */
    private void mainScreen(Event event) throws IOException {
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

}

