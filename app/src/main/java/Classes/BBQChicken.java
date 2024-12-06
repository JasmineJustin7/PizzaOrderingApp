package Classes;

/**
 * Represents a BBQ Chicken pizza with specific pricing and toppings.
 * @author Jimena Reyes
 * @author Jasmine Justin
 */
public class BBQChicken extends Pizza {
    /**price for a small bbq chicken pizza*/
    private static final double SMALL_PRICE = 14.99;
    /**price for a medium bbq chicken pizza*/
    private static final double MEDIUM_PRICE = 16.99;
    /**price for a large bbq chicken pizza*/
    private static final double LARGE_PRICE = 19.99;
    /**boolean to check style of pizza*/
    private boolean isChicagoStyle;

    /**
     * Creates a BBQ Chicken pizza with the checked size and crust type.
     *
     * @param size      the size of the pizza (SMALL, MEDIUM, LARGE)
     * @param isChicago determines the crust type (true for Chicago, false for New York)
     */
    public BBQChicken(Size size, boolean isChicago) {
        super(isChicago ? Crust.PAN : Crust.THIN, size);
        isChicagoStyle = isChicago;

        // toppings for BBQ Chicken according to table
        addTopping(Topping.BBQ_CHICKEN);
        addTopping(Topping.GREEN_PEPPER);
        addTopping(Topping.PROVOLONE);
        addTopping(Topping.CHEDDAR);
    }

    /**returns type of style the current instance of bbq chicken pizza
     * @return string of style*/
    @Override
    public String getStyle() {
        if(isChicagoStyle) return "Chicago Style";
        return "New York Style";
    }

    /**
     * Returns the price of the pizza based on its size only.
     *
     * @return the price of the pizza
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
        pizzaOrder = getStyle() + " - BBQ Chicken Pizza [" + getSize() + ", " + getCrust() + " Crust] ";
        if(!getToppings().isEmpty()) return pizzaOrder + "Toppings: " + getToppings();
        return pizzaOrder;
    }

    /**String form of class/pizza type
     * @return the type of pizza*/
    @Override
    public String getPizzaType() {
        return "BBQ Chicken";
    }
}