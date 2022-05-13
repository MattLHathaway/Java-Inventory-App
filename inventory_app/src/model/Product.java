package model;

import javafx.collections.ObservableList;

public class Product {
    ObservableList<Part> associatedParts;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    //CONSTRUCTOR
    public Product(ObservableList<Part> associatedParts, Integer id, String name, Integer stock, Double price, Integer min, Integer max) {
        this.associatedParts = associatedParts;
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.min = min;
        this.max = max;
    }
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }
    //add the associated part to list of associated parts
    public void addAssociatedPart(Part newAssociatedPart) {
        this.associatedParts.add(newAssociatedPart);
    }
    //remove the associated part to list of associated parts
    public void deleteAssociatedPart(Part selectedAssociatedPart) {
        this.associatedParts.remove(selectedAssociatedPart);
    }
    //returns list of associated parts
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }


}
