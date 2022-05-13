package model;

public class Outsourced extends Part {

    private String companyName;

    public Outsourced(int partID, String name, double price, int numInStock, int min, int max, String company) {
        super(partID, name, price, numInStock, min, max);

        setId(partID);
        setName(name);
        setPrice(price);
        setStock(numInStock);
        setMin(min);
        setMax(max);
        setCompanyName(company);
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String name) {
        this.companyName = name;
    }

}
