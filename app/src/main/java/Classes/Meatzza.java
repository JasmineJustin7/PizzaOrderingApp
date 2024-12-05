package Classes;

/**
 * Represents a Meatzza pizza, which includes specific toppings based on the pizza style.
 * @author Jimena Reyes
 * @author Jasmine Justin
 */
public class Meatzza extends Pizza {
    /**price for a small meatzza pizza*/
    private static final double SMALL_PRICE = 17.99;
    /**price for a medium meatzza pizza*/
    private static final double MEDIUM_PRICE = 19.99;
    /**price for a large meatzza pizza*/
    private static final double LARGE_PRICE = 21.99;
    /**boolean to check style of pizza*/
    private boolean isChicagoStyle;


    /**
     * Constructs a Meatzza pizza with the specified size and style
     * @param size the size of the pizza
     * @param isChicago indicates whether the pizza is Chicago-style (true) or New York-style (false)
     */
    public Meatzza(Size size, boolean isChicago) {
        super(isChicago ? Crust.STUFFED: Crust.HAND_TOSSED, size);
        isChicagoStyle = isChicago;
        //from table
        addTopping(Topping.SAUSAGE);
        addTopping(Topping.PEPPERONI);
        addTopping(Topping.BEEF);
        addTopping(Topping.HAM);
    }

    /**returns type of style the current instance of meatzza pizza
     * @return string of style*/
    @Override
    public String getStyle() {
        if(isChicagoStyle) return "Chicago Style";
        return "New York Style";
    }


    /**
     * Returns the price of the Meatzza pizza based only on the size.
     * @return the price of the Meatzza pizza
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
        pizzaOrder = getStyle() + " - Meatzza Pizza [" + getSize() + ", " + getCrust() + " Crust] ";
        if(!getToppings().isEmpty()) return pizzaOrder + "Toppings: " + getToppings();
        return pizzaOrder;
    }
    /**String form of class/pizza type
     * @return the type of pizza*/
    @Override
    public String getPizzaType() {
        return "Meatzza";
    }
}
