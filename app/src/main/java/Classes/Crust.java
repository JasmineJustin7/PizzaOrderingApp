package Classes;

/**enum class holds all possible types of crust offered by the pizzeria
 * @author Jimena Reyes
 * @author Jasmine Justin*/
public enum Crust {
    /**deep dish crust*/
    DEEP_DISH,
    /**pan crust*/
    PAN,
    /**stuffed crust*/
    STUFFED,
    /**brooklyn crust*/
    BROOKLYN,
    /**thin crust*/
    THIN,
    /**hand-tossed crust*/
    HAND_TOSSED;

    /**returns string form of crust enum
     * @return string form*/
    @Override
    public String toString() {
        return this.name();
    }
}
