package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    public static Integer IDCounter = 5;
    public static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    public static ObservableList<Part> allParts = FXCollections.observableArrayList();
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public int partListSize() {
        return allParts.size();
    }

    public int productListSize() {
        return allProducts.size();
    }
    public static void addPart(Part partToAdd) {
        if (partToAdd != null) {
            allParts.add(partToAdd);
        } else {
            System.out.println("Part to Add is NULL");
        }
    }
    public static int idGenerator() {
        IDCounter = IDCounter + 1;
        return IDCounter;
    }
    public static void addProduct(Product productToAdd) {
        if (productToAdd != null) {
            allProducts.add(productToAdd);
        }
    }
    public static boolean deletePart(Part partToDelete) {
        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getId() == partToDelete.getId()) {
                allParts.remove(i);
                return true;
            }
        }
        return false;
    }

    public static boolean deleteProduct(Product productToDelete) {
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getId() == productToDelete.getId()) {
                allProducts.remove(i);
                return true;
            }
        }
        return false;
    }

    public ObservableList<Part> lookupPartByName(String partName) {
        if(!allParts.isEmpty()) {
            ObservableList searchPartsList = FXCollections.observableArrayList();
            for (Part part : getAllParts()) {
                if (part.getName().contains(partName)) {
                    searchPartsList.add(part);
                }
            }
            return searchPartsList;
        }
        return null;
    }

    public ObservableList<Product> lookupProductByName(String productName) {
        if(!allProducts.isEmpty()) {
            ObservableList searchProductsList = FXCollections.observableArrayList();
            for (Product product : getAllProducts()) {
                if (product.getName().contains(productName)) {
                    searchProductsList.add(product);
                }
            }
            return searchProductsList;
        }
        return null;
    }

    public Part lookupPartByID(int partID) {
        if (!allParts.isEmpty()) {
            for (int i = 0; i < allParts.size(); i++) {
                if (allParts.get(i).getId() == partID) {
                    return allParts.get(i);
                }
            }
        }
        return null;
    }

    public Product lookupProductByID(int productID) {
        if (!allProducts.isEmpty()) {
            for (int i = 0; i < allProducts.size(); i++) {
                if (allProducts.get(i).getId() == productID) {
                    return allProducts.get(i);
                }
            }
        }
        return null;
    }

    public void updatePart(Part partToUpdate) {
        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getId() == partToUpdate.getId()) {
                allParts.set(i, partToUpdate);
                break;
            }
        }
        return;
    }

    public void updateProduct(Product productToUpdate) {
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getId() == productToUpdate.getId()) {
                allProducts.set(i, productToUpdate);
                break;
            }
        }
        return;
    }

}
