package Classes;

/**
 * The PizzaFactory interface provides methods to create different types of pizzas.
 * @author Jimena Reyes
 * @author Jasmine Justin
 */
public interface PizzaFactory {
    /**recipe of deluxe pizza
     * @param size is taken from user input*/
    Pizza createDeluxe(Size size);
    /**recipe of bbq chicken pizza
     * @param size is taken from user input*/
    Pizza createBBQChicken(Size size);
    /**recipe of meatzza pizza
     * @param size is taken from user input*/
    Pizza createMeatzza(Size size);
    /**recipe of build your own pizza
     * @param size is taken from user input*/
    Pizza createBuildYourOwn(Size size);
}
