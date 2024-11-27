package Classes;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class representing a pizza with a specific crust, size, and toppings.
 * @author Jimena Reyes
 * @author Jasmine Justin
 */
public abstract class Pizza {
    /**holds a list of toppings associated with each pizza*/
    //private List<Topping> toppings;
    private ArrayList<Topping> toppings;
    /** information about the crust of the pizza*/
    private Crust crust;
    /**size of the pizza*/
    private Size size;

    /** Constructor for a pizza with a specific crust and size
     * @param crust the type of crust for the pizza
     * @param size the size of the pizza
     */
    public Pizza(Crust crust, Size size) {
        this.crust = crust;
        this.size = size;
        this.toppings = new ArrayList<>();
    }

    /**
     * Adds a topping to the pizza, max 7.
     * @param topping the topping to add
     */
    public void addTopping(Topping topping) {
        if (toppings.size() < 7) {
            toppings.add(topping);
        }
    }

    /**
     * Gets the list of toppings for the pizza.
     * @return the list of toppings
     */
    public ArrayList<Topping> getToppings() {
        return toppings;
    }

    /**
     * Gets the crust type of the pizza.
     * @return the crust of the pizza
     */
    public Crust getCrust() {
        return crust;
    }

    /**
     * Gets the size of the pizza.
     * @return the size of the pizza
     */
    public Size getSize() {
        return size;
    }

    /**string of style of pizza depending on the type of pizza
     * @return string of pizza style*/
    public abstract String getStyle();

    /**
     * Abstract method to calculate the price of the pizza.
     * @return the price of the pizza
     */
    public abstract double price();

    @Override
    public String toString() {
        return getSize() + " " + getCrust() + " Pizza with toppings: " + toppings.toString();
    }
}
