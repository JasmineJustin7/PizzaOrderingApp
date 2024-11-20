package Classes;

/**one of two concrete classes that implements the interface pizza factory, represents type of pizza a customer can choose
 *  @author Jimena Reyes
 *  @author Jasmine Justin*/
public class NYPizza implements PizzaFactory {

    /**
     * Creates a New York-style Deluxe pizza with the specified size.
     * @param size the size of the pizza
     * @return a Deluxe pizza with New York-style crust and specified size
     */
    @Override
    public Pizza createDeluxe(Size size) {
        return new Deluxe(size, false);
    }

    /**
     * Creates a New York-style BBQ Chicken pizza with the specified size.
     * @param size the size of the pizza
     * @return a BBQ Chicken pizza with New York-style crust and specified size
     */
    @Override
    public Pizza createBBQChicken(Size size) {
        return new BBQChicken(size, false);
    }

    /**
     * Creates a New York-style Meatzza pizza with the specified size.
     * @param size the size of the pizza
     * @return a Meatzza pizza with New York-style crust and specified size
     */
    @Override
    public Pizza createMeatzza(Size size) {
        return new Meatzza(size, false);
    }

    /**
     * Creates a New York-style Build Your Own pizza with the specified size.
     * @param size the size of the pizza
     * @return a Build Your Own pizza with New York-style crust and specified size
     */
    @Override
    public Pizza createBuildYourOwn(Size size) {
        return new BuildYourOwn(size, false);
    }

}
