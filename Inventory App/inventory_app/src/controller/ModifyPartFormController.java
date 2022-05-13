package controller;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;
import controller.MainScreenController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ModifyPartFormController implements Initializable {
    Inventory inv;

    Outsourced tempOutsourced = new Outsourced(9, "99", 99.9, 9, 9, 99, "99");
    InHouse tempInHouse = new InHouse(9, "99", 99.9, 9, 9, 99, 99);

    boolean isLocal = true;

    public TextField partMaxTextField;
    public TextField partMachineIDTextField;
    public TextField partMinTextField;
    public RadioButton inHouseButton;
    public Label switchLabel;
    public RadioButton outsourcedButton;
    public ToggleGroup AddPart;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    TextField partIDTextField;
    @FXML TextField partNameTextField;
    @FXML TextField partInvTextField;
    @FXML TextField partPriceTextField;
    @FXML TextField partToggleTextField;


    public ModifyPartFormController() {
//        this.inv = inv;
//        this.part = part;
    }

    @Override public void initialize(URL url, ResourceBundle rb) {

    }
    public void inHousePressed(ActionEvent event) {
        switchLabel.setText("Machine ID");
        partToggleTextField.setText("");
        inHouseButton.setSelected(true);
    }
    public void outSourcedPressed(ActionEvent event) {
        switchLabel.setText("Company Name");
        partToggleTextField.setText("");
        outsourcedButton.setSelected(true);
    }
    public void saveButtonPressed(ActionEvent event) throws IOException {
        System.out.println("Save part button pressed!");

        //Checks for EMPTY text entry
        if (partNameTextField.getText().length() == 0 ||
                partInvTextField.getText().length() == 0 ||
                partPriceTextField.getText().length() == 0 ||
                partMinTextField.getText().length() == 0 ||
                partMaxTextField.getText().length() == 0 ||
                partToggleTextField.getText().length() == 0
        ) {
            AlertMessage.partError(1, null);
            return;
        }
        //Checks that Min, Max, and Inv are Numeric
        if (!isNumeric(partMinTextField.getText()) ||
                !isNumeric(partMaxTextField.getText()) ||
                !isNumeric(partPriceTextField.getText()) ||
                !isNumeric(partInvTextField.getText())) {
            AlertMessage.partError(9, null);
            return;
        }
        //Checks that price is a double
        if (!isDouble(partPriceTextField.getText())) {
            AlertMessage.partError(3, null);
            return;
        }
        //Checks that Min is smaller than Max
        if (Integer.parseInt(partMinTextField.getText()) > Integer.parseInt(partMaxTextField.getText()))  {
            AlertMessage.partError(10, null);
            return;
        }
        //Checks that Min, Max, and Inv are Numeric
        if (!isNumeric(partMinTextField.getText()) ||
                !isNumeric(partMaxTextField.getText()) ||
                !isNumeric(partPriceTextField.getText()) ||
                !isNumeric(partInvTextField.getText())) {
            AlertMessage.partError(9, null);
            return;
        }
        //Checks that price is a double
        if (!isDouble(partPriceTextField.getText())) {
            AlertMessage.partError(3, null);
            return;
        }
        //Checks that Min is smaller than Max
        if (Integer.parseInt(partMinTextField.getText()) > Integer.parseInt(partMaxTextField.getText()))  {
            AlertMessage.partError(10, null);
            return;
        }
        //Checks that Min is not negative
        if (Integer.parseInt(partMinTextField.getText()) < 0 ||
                Integer.parseInt(partMaxTextField.getText()) < 0 ||
                Integer.parseInt(partInvTextField.getText()) < 0 ||
                Double.parseDouble(partPriceTextField.getText()) < 0) {
            AlertMessage.partError(5, null);
            return;
        }
        //Checks that Inventory is within Min/Max constraints
        if (Integer.parseInt(partInvTextField.getText()) > Integer.parseInt(partMaxTextField.getText()) ||
                Integer.parseInt(partInvTextField.getText()) < Integer.parseInt(partMinTextField.getText())) {
            AlertMessage.partError(8, null);
            return;
        }
        //Checks that Machine ID is an Integer
        if (inHouseButton.isSelected()) {
            if(!isNumeric(partToggleTextField.getText())) {
                AlertMessage.partError(9, null);
                return;
            }
        }



        //Assigning Values to our Temporary Object
        if (switchLabel.getText() == "Company Name") {

            modifyPart(tempOutsourced);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainScreen.fxml"));
            root = loader.load();

            MainScreenController mainScreenController = loader.getController();
            mainScreenController.addOutsourcedToInventory(tempOutsourced);
            mainScreenController.partsTable.setItems(Inventory.getAllParts());

        } else {

            modifyPart(tempInHouse);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainScreen.fxml"));
            root = loader.load();

            MainScreenController mainScreenController = loader.getController();
            mainScreenController.addInHouseToInventory(tempInHouse);
            mainScreenController.partsTable.setItems(Inventory.getAllParts());
        }

        //SWITCHING TO MAIN SCREEN
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        System.out.println("Part modified!");
    }
    public void cancelButtonPressed(ActionEvent event) throws IOException {
        System.out.println("Cancel Button Pressed!");

        if (isLocal) {
            Inventory.addPart(tempInHouse);
        } else {
            Inventory.addPart(tempOutsourced);
        }

        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void fillPartFields(Part selectedPart) {

        if (selectedPart instanceof InHouse) {
            InHouse part1 = (InHouse) selectedPart;
            inHouseButton.setSelected(true);
            switchLabel.setText("Machine ID");
            this.partNameTextField.setText(part1.getName());
            this.partIDTextField.setText(Integer.toString(part1.getId()));
            this.partInvTextField.setText(Integer.toString(part1.getStock()));
            this.partPriceTextField.setText(Double.toString(part1.getPrice()));
            this.partMinTextField.setText(Integer.toString(part1.getMin()));
            this.partMaxTextField.setText(Integer.toString(part1.getMax()));
            this.partToggleTextField.setText(Integer.toString(part1.getMachineID()));

            modifyPart(tempInHouse);
        }
        if (selectedPart instanceof Outsourced) {
            Outsourced part2 = (Outsourced) selectedPart;
            outsourcedButton.setSelected(true);
            switchLabel.setText("Company Name");
            isLocal = false;
            this.partNameTextField.setText(part2.getName());
            this.partIDTextField.setText(Integer.toString(part2.getId()));
            this.partInvTextField.setText(Integer.toString(part2.getStock()));
            this.partPriceTextField.setText(Double.toString(part2.getPrice()));
            this.partMinTextField.setText(Integer.toString(part2.getMin()));
            this.partMaxTextField.setText(Integer.toString(part2.getMax()));
            this.partToggleTextField.setText(part2.getCompanyName());

            modifyPart(tempOutsourced);
        }
    }
    public void modifyPart(Part selectedPart) {
        if (selectedPart instanceof InHouse) {
            selectedPart.setId(Integer.parseInt(partIDTextField.getText()));
            selectedPart.setName(partNameTextField.getText());
            selectedPart.setStock(Integer.parseInt(partInvTextField.getText()));
            selectedPart.setPrice(Double.parseDouble(partPriceTextField.getText()));
            selectedPart.setMin(Integer.parseInt(partMinTextField.getText()));
            selectedPart.setMax(Integer.parseInt(partMaxTextField.getText()));
            ((InHouse) selectedPart).setMachineID(Integer.parseInt(partToggleTextField.getText()));
        }
        else {
            selectedPart.setId(Integer.parseInt(partIDTextField.getText()));
            selectedPart.setName(partNameTextField.getText());
            selectedPart.setStock(Integer.parseInt(partInvTextField.getText()));
            selectedPart.setPrice(Double.parseDouble(partPriceTextField.getText()));
            selectedPart.setMin(Integer.parseInt(partMinTextField.getText()));
            selectedPart.setMax(Integer.parseInt(partMaxTextField.getText()));
            ((Outsourced) selectedPart).setCompanyName(partToggleTextField.getText());
        }
    }
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    public static boolean isDouble(String strNum) {
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}
