package Classes;

/**
 * Represents a Deluxe pizza, which includes specific toppings based on the pizza style.
 * @author Jimena Reyes
 * @author Jasmine Justin
 */
public class Deluxe extends Pizza {

    /**price for a small deluxe pizza*/
    private static final double SMALL_PRICE = 16.99;
    /**price for a medium deluxe pizza*/
    private static final double MEDIUM_PRICE = 18.99;
    /**price for a large deluxe pizza*/
    private static final double LARGE_PRICE = 20.99;
    /**boolean to check style of pizza*/
    private boolean isChicagoStyle;

    /**
     * Constructs a Deluxe pizza with the specified size and style.
     * @param size the size of the pizza
     * @param isChicago indicates whether the pizza is Chicago-style (true) or New York-style (false)
     */
    public Deluxe(Size size, boolean isChicago) {
        super(isChicago ? Crust.DEEP_DISH : Crust.BROOKLYN, size);
        isChicagoStyle = isChicago;

        //toppings for Deluxe pizza on table
        addTopping(Topping.SAUSAGE);
        addTopping(Topping.PEPPERONI);
        addTopping(Topping.GREEN_PEPPER);
        addTopping(Topping.ONION);
        addTopping(Topping.MUSHROOM);
    }

    /**returns type of style the current instance of deluxe pizza
     * @return string of style*/
    @Override
    public String getStyle() {
        if(isChicagoStyle) return "Chicago Style";
        return "New York Style";
    }

    /**
     * Returns the price of the Deluxe pizza based only on the size.
     * @return the price of the Deluxe pizza
     * @throws IllegalArgumentException if the size is invalid
     */
    @Override
    public double price() {
        switch (getSize()) {
            case SMALL:
                return SMALL_PRICE;
            case MEDIUM:
                return MEDIUM_PRICE;
            case LARGE:
                return LARGE_PRICE;
            default:
                throw new IllegalArgumentException("Invalid size");
        }
    }

    /**returns pizza in order including style, size, crust, and toppings if there are any
     * @return string form of order*/
    @Override
    public String toString() {
        String pizzaOrder;
        pizzaOrder = getStyle() + " - Deluxe Pizza [" + getSize() + ", " + getCrust() + " Crust] ";
        if(!getToppings().isEmpty()) return pizzaOrder + "Toppings: " + getToppings();
        return pizzaOrder;
    }
    /**String form of class/pizza type
     * @return the type of pizza*/
    @Override
    public String getPizzaType() {
        return "Deluxe";
    }
}

