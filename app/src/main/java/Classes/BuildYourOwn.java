package Classes;//package Classes;

import java.util.ArrayList;

/**
 * Represents Build Your Own pizza with a customizable set of toppings.
 *  @author Jimena Reyes
 * @author Jasmine Justin
 */
public class BuildYourOwn extends Pizza {
    /**price for a small build your own pizza*/
    private static final double SMALL_PRICE = 8.99;
    /**price for a medium build your own pizza*/
    private static final double MEDIUM_PRICE = 10.99;
    /**price for a large build your own pizza*/
    private static final double LARGE_PRICE = 12.99;
    /**boolean to check style of pizza*/
    private static boolean isChicagoStyle;

    /**
     * Creates a Build Your Own pizza with the checked size and crust type.
     * @param size the size of the pizza
     * @param isChicago determines the crust type (true for Chicago-style, false for New York-style)
     */
    public BuildYourOwn(Size size, boolean isChicago) {
        super(isChicago ? Crust.PAN : Crust.HAND_TOSSED, size);
        isChicagoStyle = isChicago;
    }

    /**returns type of style the current instance of build your own pizza
     * @return string of style*/
    @Override
    public String getStyle() {
        if(isChicagoStyle) return "Chicago Style";
        return "New York Style";    }

    /**
     * Returns the price of the Build Your Own pizza based on size and toppings.
     * @return the price of the pizza including additional toppings
     * @throws IllegalArgumentException if the size is invalid
     */
    @Override
    public double price() {
        double basePrice = 0;
        switch (getSize()) {
            case SMALL:
                basePrice = SMALL_PRICE;
                break;
            case MEDIUM:
                basePrice = MEDIUM_PRICE;
                break;
            case LARGE:
                basePrice = LARGE_PRICE;
                break;
            default:
                throw new IllegalArgumentException("Invalid size");
        }
        return basePrice + (getToppings().size() * 1.69);
    }

    /**returns pizza in order including style, size, crust, and toppings if there are any
     * @return string form of order*/
    @Override
    public String toString() {
        String pizzaOrder;
        pizzaOrder = getStyle() + " - Build Your Own Pizza [" + getSize() + ", " + getCrust() + " Crust] ";
        if(!getToppings().isEmpty()) return pizzaOrder + "Toppings: " + getToppings();
        return pizzaOrder;
    }
}
