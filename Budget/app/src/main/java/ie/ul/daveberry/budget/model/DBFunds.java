package ie.ul.daveberry.budget.model;

public class DBFunds {
    private double amount;
    private Integer id;

    public DBFunds(double amount) {
        this.amount = amount;
    }

    public DBFunds(Integer id, double amount) {
        this.amount = amount;
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }


    public Integer getId() {
        return id;
    }



}
