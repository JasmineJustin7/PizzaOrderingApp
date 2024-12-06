package com.example.rupizzeria;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.List;
import Classes.Order;
import Classes.Pizza;


/**
 * Singleton class to manage the shared order details.
 * This will be used to store and share the order data across different activities.
 * @author Jasmine Justin
 * @author Jimena Reyes
 * @author Lily Chang
 */
public final class OrderDetails {

    /**
     * Singleton instance of the {@link OrderDetails} class.
     */
    private static OrderDetails instance;

    /**
     * List of pizzas in the current order.
     */
    private ArrayList<Pizza> pizzas;

    /**
     * List of all placed orders.
     */
    private ArrayList<Order> orders;
    private int orderNumber = 1;

    /**
     * The application context used for accessing resources and shared preferences.
     */
    private Context context;

    /**
     * Private constructor to prevent instantiation from outside this class
     */
    private OrderDetails(Context context) {
        this.context = context;
        pizzas = new ArrayList<>();
        orders = new ArrayList<>();
    }

    /**
     * Get the singleton instance of OrderDetails.
     * @return the single instance of OrderDetails
     */
    public static synchronized OrderDetails getInstance(Context context) {
        if (instance == null) {
            instance = new OrderDetails(context);
        }
        return instance;
    }

    /**
     * Add an pizza to the current order
     * @param orderItem The order pizza to be added
     */
    public void addPizza(Pizza orderItem) {
        pizzas.add(orderItem);
    }

    /**
     * Adds a completed order to the order history.
     * @param selectedOrder the order to be added
     */
    public void addOrder(Order selectedOrder) {
        orders.add(selectedOrder);
    }

    /**
     * Removes a completed order from the order history.
     * @param selectedOrder the order to be removed
     */
    public void removeOrder(Order selectedOrder) {
        orders.remove(selectedOrder);
    }

    /**
     * Remove an item from the current order
     * @param pizza The order item to be removed
     */
    public void removeOrderItem(Pizza pizza) {
        pizzas.remove(pizza);
    }

    /**
     * Retrieves the current order number (always starts at 1).
     * @return the next order number
     */
    public int getCurrentOrderNumber(){
        return orderNumber;
    }

    /**
     * Retrieves the next available order number.
     * @return the next order number
     */
    public int getNextOrderNumber() {
        //return orderNumber;
        return orderNumber++;
    }

    /**
     * Get the list of items in the current order
     * @return A list of current order items
     */
    public ArrayList<Pizza> getPizzas() {
        return pizzas;
    }

    public void setPizzas(ArrayList<Pizza> selectedPizzas) {
        this.pizzas = selectedPizzas;
    }

    /**
     * Get the list of items in the current order
     * @return A list of current order items
     */
    public ArrayList<Order> getOrders() {
        return orders;
    }

    /**
     * Sets the list of completed orders.
     * @param orders the list of completed orders to set
     */
    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    /**
     * Places an order, saving it to shared preferences and clearing the current order.
     * @param orderNumber the order number
     * @param pizzas the list of pizzas in the order
     */
    public void placeOrder(int orderNumber, ArrayList<Pizza> pizzas) {
        saveOrderToSharedPreferences(orderNumber, pizzas);
        clearOrder();
    }

    /**
     * Save the order to SharedPreferences
     */
    public void saveOrderToSharedPreferences(int orderNumber, List<Pizza> pizzas) {
        StringBuilder orderDetails = new StringBuilder();
        orderDetails.append("Order Number: #").append(orderNumber).append("\n");
        for (Pizza item : pizzas) {
            orderDetails.append("Pizza: ")
                    .append(item.getStyle()).append(", ")
                    .append(item.getPizzaType()).append(", ")
                    .append("Crust: ").append(item.getCrust()).append(", ")
                    .append("Size: ").append(item.getSize()).append("\n");
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences("OrderHistory", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Order_" + orderNumber, orderDetails.toString());
        editor.apply();
    }

    /**
     * Clear all items in the current order
     */
    public void clearOrder() {
        orderNumber++;
        pizzas.clear();
    }
}
