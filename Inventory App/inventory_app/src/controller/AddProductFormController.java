package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddProductFormController implements Initializable {

    //Instance variables used to create new Product objects

    @FXML TableView<Part> partsTable;
    @FXML TableView<Part> partsTableB;
    @FXML TableView<Product> productsTable;
    @FXML TableView<Part> assocPartsTable;

    public TextField partBQueryTF;
    public Button partBSearchButton;
    public TextField idTextFieldProduct;
    public TextField nameTextFieldProduct;
    public TextField invTextFieldProduct;
    public TextField priceTextFieldProduct;
    public TextField maxTextFieldProduct;
    public TextField minTextFieldProduct;
    private ObservableList<Part> assocPartList = FXCollections.observableArrayList();
    @FXML
    TableColumn partIDColB;
    @FXML TableColumn partNameColB;
    @FXML TableColumn partPriceColB;
    @FXML TableColumn partInvColB;
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
        createAssocPartsTable();
    }
    public void switchToFirstScreen(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void addToAssocClicked() {
        //MAKE SURE WE SELECTED A PART
        Part SP = (Part) partsTableB.getSelectionModel().getSelectedItem();
        if(SP == null) {
            AlertMessage.partError(13, null);
            return;
        }
        //CHECK FOR DUPLICATES
        int id = SP.getId();
        for(int i = 0; i < assocPartList.size(); i++) {
            if (assocPartList.get(i).getId() == id) {
                AlertMessage.partError(14, null);
                return;
            }
        }
        //ADD PART TO OUR LIST
        assocPartList.add(SP);
        assocPartsTable.setItems(assocPartList);
    }

    public void removeAssocClicked() {
        //MAKE SURE WE SELECTED A PART
        Part SP = (Part) assocPartsTable.getSelectionModel().getSelectedItem();
        if(SP == null) {
            AlertMessage.partError(13, null);
            return;
        }
        //REMOVE PART FROM OUR LIST
        assocPartList.remove(SP);
        assocPartsTable.setItems(assocPartList);

    }

    public void saveProductButtonPushed(ActionEvent event) throws IOException {
        //Checks for EMPTY text entry
        if (nameTextFieldProduct.getText().length() == 0 ||
                invTextFieldProduct.getText().length() == 0 ||
                priceTextFieldProduct.getText().length() == 0 ||
                minTextFieldProduct.getText().length() == 0 ||
                maxTextFieldProduct.getText().length() == 0
        ) {
            AlertMessage.partError(1, null);
            return;
        }
        //Checks that Min, Max, and Inv are Integers
        if (!isNumeric(minTextFieldProduct.getText()) ||
                !isNumeric(maxTextFieldProduct.getText()) ||
                !isNumeric(invTextFieldProduct.getText())) {
            AlertMessage.partError(9, null);
            return;
        }
        //Checks that Price is a Double
        if (!isDouble(priceTextFieldProduct.getText())) {
            AlertMessage.partError(3, null);
            return;
        }
        //Checks that Min is smaller than Max
        if (Integer.parseInt(minTextFieldProduct.getText()) > Integer.parseInt(maxTextFieldProduct.getText()))  {
            AlertMessage.partError(10, null);
            return;
        }
        //Checks that Min is smaller than Max
        if (Integer.parseInt(minTextFieldProduct.getText()) > Integer.parseInt(maxTextFieldProduct.getText()))  {
            AlertMessage.partError(10, null);
            return;
        }
        //Checks that Min is not negative
        if (Integer.parseInt(minTextFieldProduct.getText()) < 0 ||
                Integer.parseInt(maxTextFieldProduct.getText()) < 0 ||
                Integer.parseInt(invTextFieldProduct.getText()) < 0 ||
                Double.parseDouble(priceTextFieldProduct.getText()) < 0) {
            AlertMessage.partError(5, null);
            return;
        }
        //Checks that Inventory is within Min/Max constraints
        if (Integer.parseInt(invTextFieldProduct.getText()) > Integer.parseInt(maxTextFieldProduct.getText()) ||
                Integer.parseInt(invTextFieldProduct.getText()) < Integer.parseInt(minTextFieldProduct.getText())) {
            AlertMessage.partError(8, null);
            return;
        }

        //Add Product to our Inventory
        Product newProduct = new Product(Inventory.idGenerator(),
                nameTextFieldProduct.getText(),
                Integer.parseInt(invTextFieldProduct.getText()),
                Double.parseDouble(priceTextFieldProduct.getText()),
                Integer.parseInt(minTextFieldProduct.getText()),
                Integer.parseInt(maxTextFieldProduct.getText()));

        for (int i = 0; i < assocPartList.size(); i++) {
            newProduct.addAssociatedPart(assocPartList.get(i));
        }
        Inventory.addProduct(newProduct);

        //Switching back to home screen!
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        System.out.println("Product Added!");
    }

    private void createPartsTable() {
        partsTableB.setItems(Inventory.getAllParts());

        partIDColB.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColB.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceColB.setCellValueFactory(new PropertyValueFactory<>("price"));
        partInvColB.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

    private void createAssocPartsTable() {
        assocPartsTable.setItems(assocPartList);

        partIDColA.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColA.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceColA.setCellValueFactory(new PropertyValueFactory<>("price"));
        partInvColA.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

    //SEARCH FUNCTIONS
    public void getResultsHandler(ActionEvent actionEvent) {
        String q = partBQueryTF.getText();
        String z = partBQueryTF.getText();

        ObservableList<Part> parts = searchByPartName(q);
        partsTableB.setItems(parts);

        if (parts.size() == 0) {
            ObservableList<Part> partsID = searchByPartID(Integer.parseInt(z));
            partsTableB.setItems(partsID);
        }
        partBQueryTF.setText("");
    }
    private ObservableList<Part> searchByPartName(String partialName) {
        ObservableList<Part> namedParts = FXCollections.observableArrayList();

        ObservableList<Part> allParts = Inventory.getAllParts();

        for(Part part : allParts) {
            if(part.getName().contains(partialName)){
                namedParts.add(part);
            }
        }

        return namedParts;
    }
    private ObservableList<Part> searchByPartID(int partialID) {
        ObservableList<Part> PartIDs = FXCollections.observableArrayList();

        ObservableList<Part> allParts = Inventory.getAllParts();

        for(Part part : allParts) {
            String parser = String.valueOf(Integer.valueOf(partialID));
            if(String.valueOf(part.getId()).contains(parser)){
                PartIDs.add(part);
            }
        }

        return PartIDs;
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
