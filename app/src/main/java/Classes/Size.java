package Classes;

/**enum class that returns the available sizes offered by the pizzeria
 * @author Jimena Reyes
 * @author Jasmine Justin*/
public enum Size {
    /**small size of pizza*/
    SMALL,
    /**medium size of pizza*/
    MEDIUM,
    /**large size of pizza*/
    LARGE;

    /**returns the string form of the enum size values
     * @return string form of enum*/
    @Override
    public String toString() {
        return this.name();
    }
}

