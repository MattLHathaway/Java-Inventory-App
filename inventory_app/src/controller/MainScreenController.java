package controller;

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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/*
 * This is the MainScreenController
 * @author Matthew Hathaway
 */

public class MainScreenController implements Initializable {

    private ObservableList<Part> partInventory = FXCollections.observableArrayList();

    private ObservableList<Product> productInventory = FXCollections.observableArrayList();
    private ObservableList<Part> dummyAssociatedParts = FXCollections.observableArrayList();
    private ObservableList<Part> dummyAssociatedParts2 = FXCollections.observableArrayList();

    Inventory inv;

    public Button partDeleteButton;
    public TextField partQueryTF;
    public Button partSearchButton;
    public TextField productQueryTF;
    public Button productSearchButton;
    @FXML TableView<Part> partsTable;
    @FXML TableView<Product> productsTable;
    @FXML TableColumn partIDCol;
    @FXML TableColumn partNameCol;
    @FXML TableColumn partPriceCol;
    @FXML TableColumn partInvCol;
    @FXML TableColumn productIDCol;
    @FXML TableColumn productNameCol;
    @FXML TableColumn productPriceCol;
    @FXML TableColumn productInvCol;
    private static boolean firstTime = true;
    private Stage stage;
    private Scene scene;
    private Parent root;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addDummyData();
        createPartsTable();

        createProductsTable();
    }
    private void createPartsTable() {
        partsTable.setItems(Inventory.getAllParts());

        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }
    private void createProductsTable() {
        productsTable.setItems(Inventory.getAllProducts());

        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }
    public void addDummyData() {
        if(!firstTime) {
            return;
        }
        firstTime = false;

        Part dummyPart = new InHouse(1, "Saddle", 10.0, 5, 1, 10, 54);
        Part dummyPart2 = new Outsourced(2, "Handlebar", 15.0, 3, 1, 10, "ACME Inc");
        Part dummyPart3 = new Outsourced(3, "Pedals", 22.0, 7, 1, 50, "ACME Inc");
        Inventory.addPart(dummyPart);
        Inventory.addPart(dummyPart2);
        Inventory.addPart(dummyPart3);

        dummyAssociatedParts.add(dummyPart);
        dummyAssociatedParts2.add(dummyPart2);
        Product dummyProduct = new Product(dummyAssociatedParts, 4, "Bicycle", 3, 150.00, 1, 5);
        Product dummyProduct2 = new Product(null, 5, "Tricycle", 5, 100.00, 1, 10);
        Inventory.addProduct(dummyProduct);
        Inventory.addProduct(dummyProduct2);

        productInventory.add(dummyProduct);
    }
    public void addInHouseToInventory(InHouse tempPart) {
        InHouse partToAdd = tempPart;
        Inventory.addPart(partToAdd);
    }
    public void addOutsourcedToInventory(Outsourced tempPart) {
        Outsourced partToAdd = tempPart;
        Inventory.addPart(partToAdd);
    }
    public void onModifyPartClicked(ActionEvent event) throws IOException {

        Part SP = (Part) partsTable.getSelectionModel().getSelectedItem();
        if(SP == null) {
            AlertMessage.partError(11, null);
            return;
        }
        System.out.println("Modify Part clicked!");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyPartForm.fxml"));
        root = loader.load();

        ModifyPartFormController modifyPartFormController = loader.getController();
        modifyPartFormController.fillPartFields(SP);

        Inventory.deletePart(SP);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void addPartClicked(ActionEvent event) throws IOException {
        System.out.println("Add part clicked!");

        Parent root = FXMLLoader.load(getClass().getResource("/view/AddPartForm.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void addProductClicked(ActionEvent event) throws IOException {
        System.out.println("Add product clicked!");

        Parent root = FXMLLoader.load(getClass().getResource("/view/AddProductForm.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void onDeletePartClicked() {
        System.out.println("Delete Part clicked!");

        Part SP = (Part) partsTable.getSelectionModel().getSelectedItem();
        if(SP == null)
            return;

        //Creates "ARE YOU SURE?" Alert Box
        ButtonType delete = new ButtonType("Delete", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.WARNING,
                "Are you sure you want to Delete Part?",
                delete,
                cancel);
        alert.setTitle("Delete Warning");
        Optional<ButtonType> result = alert.showAndWait();
        //Deletes if delete is chosen
        if (result.orElse(cancel) == delete) {
            Inventory.deletePart(SP);
            System.out.println("Part was Deleted!");
        }
    }
    public void closeProgram(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }
    public void onDeleteProductClicked() {
        Product SP = (Product) productsTable.getSelectionModel().getSelectedItem();
        if(SP == null)
            return;

        if(SP.getAllAssociatedParts() != null) {
            AlertMessage.partError(12, null);
            return;
        } else {
            //Creates "ARE YOU SURE?" Alert Box
            ButtonType delete = new ButtonType("Delete", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Are you sure you want to Delete Product?",
                    delete,
                    cancel);
            alert.setTitle("Delete Warning");
            Optional<ButtonType> result = alert.showAndWait();
            //Deletes if delete is chosen
            if (result.orElse(cancel) == delete) {
                Inventory.deleteProduct(SP);
                System.out.println("Product was Deleted!");
            }
        }
    }

    //OUR SEARCH FUNCTIONS
    public void getResultsHandler(ActionEvent actionEvent) {
        String q = partQueryTF.getText();
        String z = partQueryTF.getText();

        ObservableList<Part> parts = searchByPartName(q);
        partsTable.setItems(parts);

        if (parts.size() == 0) {
            ObservableList<Part> partsID = searchByPartID(Integer.parseInt(z));
            partsTable.setItems(partsID);
        }
        partQueryTF.setText("");
    }

    public void getProductResultsHandler(ActionEvent actionEvent) {
        String q = productQueryTF.getText();
        String z = productQueryTF.getText();

        ObservableList<Product> products = searchByProductName(q);
        productsTable.setItems(products);

        if (products.size() == 0) {
            ObservableList<Product> productsID = searchByProductID(Integer.parseInt(z));
            productsTable.setItems(productsID);
        }
        productQueryTF.setText("");
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

    private ObservableList<Product> searchByProductName(String partialName) {
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();

        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for(Product product : allProducts) {
            if(product.getName().contains(partialName)){
                namedProducts.add(product);
            }
        }

        return namedProducts;
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

    private ObservableList<Product> searchByProductID(int partialID) {
        ObservableList<Product> ProductIDs = FXCollections.observableArrayList();

        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for(Product product : allProducts) {
            String parser = String.valueOf(Integer.valueOf(partialID));
            if(String.valueOf(product.getId()).contains(parser)){
                ProductIDs.add(product);
            }
        }

        return ProductIDs;
    }

}
