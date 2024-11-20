package Classes;

/**enum class holding all possible toppings available at the pizzeria
 @author Jasmine Justin
 @author Jimena Reyes*/
public enum Topping {
    /**sausage topping*/
    SAUSAGE,
    /**pepperoni topping*/
    PEPPERONI,
    /**green pepper topping*/
    GREEN_PEPPER,
    /**onion topping*/
    ONION,
    /**mushroom topping*/
    MUSHROOM,
    /**bbq chicken topping*/
    BBQ_CHICKEN,
    /**provolone topping*/
    PROVOLONE,
    /**cheddar topping*/
    CHEDDAR,
    /**beef topping*/
    BEEF,
    /**ham topping*/
    HAM,
    /**pineapple topping*/
    PINEAPPLE,
    /**olive topping*/
    OLIVE,
    /**anchovy topping*/
    ANCHOVY;

    /**returns the string for of the topping
     * @return string form enum*/
    @Override
    public String toString() {
        return this.name();
    }
}
