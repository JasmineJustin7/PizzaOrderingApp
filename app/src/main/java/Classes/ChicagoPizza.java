package Classes;

/**
 * This class creates specific pizza types for Chicago Style Pizza
 * @author Jimena Reyes
 * @author Jasmine Justin
 */
public class ChicagoPizza implements PizzaFactory {

    /**
     * Creates a Chicago-style Deluxe pizza with the specified size.
     * @param size the size of the pizza
     * @return a Chicago-style Deluxe pizza
     */
    @Override
    public Pizza createDeluxe(Size size) {
        return new Deluxe(size, true);  // Chicago-style Deluxe pizza (size can be chosen elsewhere)
    }

    /**
     * Creates a Chicago-style BBQ Chicken pizza with the specified size.
     * @param size the size of the pizza
     * @return a Chicago-style BBQ Chicken pizza
     */
    @Override
    public Pizza createBBQChicken(Size size) {
        return new BBQChicken(size, true);
    }

    /**
     * Creates a Chicago-style Meatzza pizza with the specified size.
     * @param size the size of the pizza
     * @return a Chicago-style Meatzza pizza
     */
    @Override
    public Pizza createMeatzza(Size size) {
        return new Meatzza(size, true);
    }

    /**
     * Creates a Chicago-style Build Your Own pizza with the specified size.
     * @param size the size of the pizza
     * @return a Chicago-style Build Your Own pizza
     */
    @Override
    public Pizza createBuildYourOwn(Size size) {
        return new BuildYourOwn(size, true);
    }

}
