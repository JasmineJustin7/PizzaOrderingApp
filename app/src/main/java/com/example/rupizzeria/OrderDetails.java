package com.example.rupizzeria;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;


import Classes.Pizza;


/**
 * Singleton class to manage the shared order details.
 * This will be used to store and share the order data across different activities.
 */
public final class OrderDetails {
    private static OrderDetails instance;
    private ArrayList<Pizza> pizzas;
    private int orderNumber = 1;


    private Context context;
    /**
     * Private constructor to prevent instantiation from outside this class
     */
    private OrderDetails(Context context) {
        this.context = this.context;
        pizzas = new ArrayList<>();
        orderNumber = 1;
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
     * Add an item to the current order
     * @param orderItem The order item (pizza) to be added
     */
    public void addPizza(Pizza orderItem) {
        pizzas.add(orderItem);
    }


    /**
     * Remove an item from the current order
     * @param pizza The order item to be removed
     */
    public void removeOrderItem(Pizza pizza) {
        pizzas.remove(pizza);
    }




    //method to get the next order number
    public int getNextOrderNumber() {
        //orderNumber++;
        return orderNumber;
    }


    /**
     * Get the list of items in the current order
     * @return A list of current order items
     */
    public ArrayList<Pizza> getPizzas() {
        //debug
        Log.d("OrderDetails", "Order items size: " + pizzas.size());
        for (Pizza pizza : pizzas) {
            Log.d("OrderDetails", pizza.toString());
        }
        //debug ends here
        return pizzas;
    }


    //test code
   /*public void addSampleItems() {
       OrderItem sampleItem1 = new OrderItem("NY Style", "BuildYourOwn", "Pan", "Small", 14.99,new ArrayList<>(List.of("Pepperoni", "Onion")));
       OrderItem sampleItem2 = new OrderItem("Chicago Style", "BuildYourOwn", "Deep Dish", "Medium", 17.99, new ArrayList<>(List.of("Cheddar", "Olives")));
       OrderItem sampleItem3 = new OrderItem("NY Style", "BuildYourOwn", "Pan", "Large", 20.99 , new ArrayList<>(List.of("Cheese", "Peppers", "Olives")));
       currentOrderItems.add(sampleItem1);
       currentOrderItems.add(sampleItem2);
       currentOrderItems.add(sampleItem3);


       // Log the items to verify they're added
       Log.d("OrderDetails", "Sample items added: " + currentOrderItems.size());
   }*/


    // New method to build order details
    //private String buildOrderDetails(int orderNumber, List<OrderItem> items) {
    //StringBuilder orderDetails = new StringBuilder();
    //orderDetails.append("Order Number: #").append(orderNumber).append("\n");


    // Iterate through the items and append details to the string
    //for (OrderItem item : items) {
    //orderDetails.append("Pizza: ")
    //.append(item.getPizzaStyle()).append(", ")
    // .append(item.getPizzaType()).append(", ")
    //.append("Crust: ").append(item.getCrustType()).append(", ")
    //.append("Size: ").append(item.getSize()).append(", ")
    //.append("Price: $").append(item.getPrice()).append("\n");
    // }


    //return orderDetails.toString();  // Return the final order details string
    // }


    //method to place the order
    public void placeOrder(int orderNumber, ArrayList<Pizza> pizzas) {
        saveOrderToSharedPreferences(orderNumber, pizzas);


        //debug
        Log.d("PlaceOrder", "Placing order #" + orderNumber);
        clearOrder();


    }


    /**
     * Save the order to SharedPreferences
     */
    public void saveOrderToSharedPreferences(int orderNumber, List<Pizza> pizzas) {
        //test
        if (context == null) {
            Log.e("OrderDetails", "Context is null!");
            return;  // Avoid proceeding if context is null
        }




        StringBuilder orderDetails = new StringBuilder();
        orderDetails.append("Order Number: #").append(orderNumber).append("\n");


        for (Pizza item : pizzas) {
            orderDetails.append("Pizza: ")
                    .append(item.getStyle()).append(", ")
                    .append(item.getPizzaType()).append(", ")
                    .append("Crust: ").append(item.getCrust()).append(", ")
                    .append("Size: ").append(item.getSize()).append("\n");
        }


        //get SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences("OrderHistory", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        //save the order under the key "Order_" + orderNumber
        editor.putString("Order_" + orderNumber, orderDetails.toString());
        editor.apply();
    }


    /**
     * Clear all items in the current order
     */
    public void clearOrder () {
        orderNumber++;
        pizzas.clear();
    }
}
