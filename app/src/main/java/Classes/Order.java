package Classes;

import java.text.NumberFormat;
import java.util.ArrayList;


/**
 * Represents a customer order with a list of pizzas.
 * @author Jimena Reyes
 * @author Jasmine Justin
 */
public class Order {
    /**order number of given instance*/
    private int Number;
    /**holds list of pizzas associated with given order*/
    private ArrayList<Pizza> pizzas;
    /**increments order number by 1 for every new order created*/
    private static int nextOrderNumber = 1;



    /** Constructor to create a new order
     * @param pizzas the list of pizzas included in the order
     */
    public Order(ArrayList<Pizza> pizzas) {
        this.Number = nextOrderNumber++;
        this.pizzas = pizzas;
    }

    /**setter to associate a list of pizzas with an order
     * @param pizzas is a list of pizzas*/
    public void setPizzas(ArrayList<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    /**get a specific pizza from order
     * @return Pizza
     * @param i is the index location of pizza in list*/
    public Pizza getPizza(int i){
        return pizzas.get(i);
    }

    /**associate a unique number with this order
     * @param number will be new order number*/
    public void setNumber(int number){
        this.Number = number;
    }

    /** Get the order number
     * @return the order number
     */
    public int getNumber() {
        return Number;
    }

    /** Get the pizzas in the order
     * @return the list of pizzas
     */
    public ArrayList<Pizza> getPizzas() {
        return pizzas;
    }

    /**
     * Calculates the total price for the order with sales tax.
     * @return the total price of the order with tax
     */
    public double getTotal() {
        double total = 0;
        for (Pizza pizza : pizzas) {
            total += pizza.price();
        }
        return total * 1.06625;  // Add sales tax (6.625%)
    }

    /**
     * Returns a string representation of the order details.
     * @return a formatted string with the order details
     */
    @Override
    public String toString() {
        String orderDetails = "Order #" + Number + "\n";
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();  //format prices as currency

        // Check if there are pizzas in the order
        if (pizzas.isEmpty()) {
            return "Order #" + Number + "\nNo pizzas in this order.";
        }

        // Loop through each pizza and add details to order string
        for (Pizza pizza : pizzas) {
            orderDetails += pizza.getClass().getSimpleName() + ": " + currencyFormat.format(pizza.price()) + "\n";
        }

        // Add the total price w/ tax
        orderDetails += "Total: " + currencyFormat.format(getTotal());
        return orderDetails;
    }
}
