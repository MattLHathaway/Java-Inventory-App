package controller;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import java.util.UUID;
import java.util.Random;

public class AddPartFormController {

    public TextField partMaxTextField;
    public TextField partMachineIDTextField;
    public TextField partMinTextField;
    public RadioButton inHouseButton;
    public Label switchLabel;
    public RadioButton outsourcedButton;
    public ToggleGroup AddPart;
    @FXML TextField partIDTextField;
    @FXML TextField partNameTextField;
    @FXML TextField partInvTextField;
    @FXML TextField partPriceTextField;
    @FXML TextField partToggleTextField;

    public void addPartClicked(ActionEvent event) throws IOException {

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


        // Creating out Object, Filling it, and Adding it to Inventory
        if (inHouseButton.isSelected()) {
            InHouse tempInHouse = new InHouse(
                    Inventory.idGenerator(),
                    partNameTextField.getText(),
                    Double.parseDouble(partPriceTextField.getText()),
                    Integer.parseInt(partInvTextField.getText()),
                    Integer.parseInt(partMinTextField.getText()),
                    Integer.parseInt(partMaxTextField.getText()),
                    Integer.parseInt(partToggleTextField.getText())
                );

            Inventory.addPart(tempInHouse);
        } else {
            Outsourced tempOutsourced = new Outsourced(
                    Inventory.idGenerator(),
                    partNameTextField.getText(),
                    Double.parseDouble(partPriceTextField.getText()),
                    Integer.parseInt(partInvTextField.getText()),
                    Integer.parseInt(partMinTextField.getText()),
                    Integer.parseInt(partMaxTextField.getText()),
                    partToggleTextField.getText()
            );

            Inventory.addPart(tempOutsourced);
        }


        //Switching back to Main Screen
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        System.out.println("Part Added!");
    }
    public void cancelButtonClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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



