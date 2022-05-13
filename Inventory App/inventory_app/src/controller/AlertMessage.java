package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class AlertMessage {

    public static void partError(int code, TextField field) {
        fieldError(field);

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error adding product");
        alert.setHeaderText("Cannot add product");

        switch (code) {
            case 1: {
                alert.setContentText("Field is empty!");
                break;
            }
            case 2: {
                alert.setContentText("Part is already is associated with this product!");
                break;
            }
            case 3: {
                alert.setContentText("Price must be a double!");
                break;
            }
            case 4: {
                alert.setContentText("Name is invalid!");
                break;
            }
            case 5: {
                alert.setContentText("Value cannot be negative!");
                break;
            }
            case 6: {
                alert.setContentText("Product cost cannot be lower than it's parts!");
                break;
            }
            case 7: {
                alert.setContentText("Products must have at least one part");
                break;
            }
            case 8: {
                alert.setContentText("Inventory must be lower than Max and greater than Min");
                break;
            }
            case 9: {
                alert.setContentText("Inventory, Price, Minimum, Maximum, and Machine ID fields must be NUMERIC");
                break;
            }
            case 10: {
                alert.setContentText("Min is greater than max");
                break;
            }
            case 11: {
                alert.setContentText("You must click on a part to modify!");
                break;
            }
            case 12: {
                alert.setContentText("This Product has associated parts!");
                break;
            }
            case 13: {
                alert.setContentText("You must click a part to add or remove!");
                break;
            }
            case 14: {
                alert.setContentText("This is already an associated part!");
                break;
            }

            default: {
                alert.setContentText("Unknown error!");
                break;
            }
        }
        alert.showAndWait();
    }


    private static void fieldError(TextField field) {
        if (field != null) {
            field.setStyle("-fx-border-color: red");
        }
    }


}