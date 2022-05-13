package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddProductFormController implements Initializable {

    //Instance variables used to create new Product objects

    @FXML TableView<Part> partsTable;
    @FXML TableView<Product> productsTable;

    @FXML TableView<Part> assocPartsTable;
    @FXML
    TableColumn partIDCol;
    @FXML TableColumn partNameCol;
    @FXML TableColumn partPriceCol;
    @FXML TableColumn partInvCol;
    @FXML
    TableColumn productIDCol;
    @FXML TableColumn productNameCol;
    @FXML TableColumn productPriceCol;
    @FXML TableColumn productInvCol;
    @FXML
    TableColumn partIDColA;
    @FXML TableColumn partNameColA;
    @FXML TableColumn partPriceColA;
    @FXML TableColumn partInvColA;
    @FXML private TextField productIDTextField;
    @FXML private TextField productNameTextField;
    @FXML private TextField productInvTextField;
    @FXML private TextField productPriceTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createPartsTable();
    }
    public void switchToFirstScreen(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void saveProductButtonPushed() {

    }

    private void createPartsTable() {
        partsTable.setItems(Inventory.getAllParts());

        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

    private void createAssocPartsTable() {
        assocPartsTable.setItems(Inventory.getAllParts());

        partIDColA.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColA.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceColA.setCellValueFactory(new PropertyValueFactory<>("price"));
        partInvColA.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }



}
