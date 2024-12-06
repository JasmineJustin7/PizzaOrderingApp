package com.example.rupizzeria;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Classes.Order;
import Classes.Pizza;

/**class that contains all current order's pizzas
 * @author Jimena Reyes
 * @author Jasmine Justin
 * */
public class CurrentOrdersActivity extends AppCompatActivity {
    /**adapter to store pizzas*/
    private OrderItemsAdapter adapter;
    /**list of pizzas*/
    private ArrayList<Pizza> pizzas;

    private ArrayList<Order> orders;

    /**text views associated with charges of pizzas in order*/
    private TextView totalCurrentText, salesTaxCurrentText, subtotalCurrentText, orderNumberText;

    /**singleton instance*/
    private final OrderDetails orderDetails = OrderDetails.getInstance(this);


    /**initializes state of current order activity
     * @param savedInstanceState is the state of the activity*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_orders);

        pizzas = orderDetails.getPizzas();

        //debug
        Log.d("CurrentOrdersActivity", "Order items: " + pizzas.toString());

        //set up RecyclerView with the OrderItemsAdapter
        RecyclerView orderItemRecyclerView = findViewById(R.id.orderItemRecyclerView);
        orderItemRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        adapter = new OrderItemsAdapter(pizzas);
        orderItemRecyclerView.setAdapter(adapter);

        //initialize TextViews for the summary
        totalCurrentText = findViewById(R.id.totalCurrentText);
        salesTaxCurrentText = findViewById(R.id.salesTaxCurrentText);
        subtotalCurrentText = findViewById(R.id.subtotalCurrentText);
        orderNumberText = findViewById(R.id.orderNumberText);

        //display order number
        int orderNumber = orderDetails.getNextOrderNumber();
        orderNumberText.setText(String.valueOf(orderNumber));
        updateOrderSummary();


        //button to remove selected items
        findViewById(R.id.removeButton).setOnClickListener(v -> {
            if (orderDetails.getPizzas().isEmpty()) {
                Toast.makeText(CurrentOrdersActivity.this, "No items in order.", Toast.LENGTH_SHORT).show();
            } else {

                //remove items from the adapter
                ArrayList<Pizza> selectedPizzas = adapter.getSelectedItems();

                if (!selectedPizzas.isEmpty()) {
                    // remove items from the list
                    adapter.removeSelectedItems();

                    // remove from the Singleton
                    for (Pizza pizza : selectedPizzas) {
                        orderDetails.removeOrderItem(pizza);
                    }

                    // if items successfully removed
                    Toast.makeText(CurrentOrdersActivity.this, "Item(s) have been removed from your order.", Toast.LENGTH_SHORT).show();
                    updateOrderSummary(); // Update the order summary after removal
                } else {
                    // if user tries to remove without selecting items
                    Toast.makeText(CurrentOrdersActivity.this, "No items selected for removal.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Button to place the order
        findViewById(R.id.placeOrderButton).setOnClickListener(new View.OnClickListener() {
            //@SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                //check to see if button was clicked
                Log.d("PlaceOrder", "Button clicked");

                //check if null and debug

                if (orderDetails == null) {
                    Log.e("PlaceOrder", "orderDetails is null!");
                    return;
                }

                if (pizzas == null) {
                    Log.e("PlaceOrder", "orderItems is null!");
                    return;
                }

                if (adapter == null) {
                    Log.e("PlaceOrder", "adapter is null!");
                    return;
                }

                if (orderNumberText == null) {
                    Log.e("PlaceOrder", "orderNumberText is null!");
                    return;
                }

                //get order items that weren't removed - Jasmine
                ArrayList<Pizza> remainingItems = orderDetails.getPizzas();
                Order newOrder = new Order();
                newOrder.setPizzas(remainingItems);
                Toast.makeText(CurrentOrdersActivity.this, "newOrder now has current list of pizzas.", Toast.LENGTH_SHORT).show();
                orderDetails.addOrder(newOrder);
                //adapter.clearRecycler();
                //orderDetails.placeOrder(newOrder.getNumber(), pizzas);
                Toast.makeText(CurrentOrdersActivity.this, "Order added to list of orders.", Toast.LENGTH_SHORT).show();
                System.out.println("These are the details of all pizzas in each order: " + orderDetails.getOrders());

            }
        });
    }

    /**updates order summary*/
    @SuppressLint("DefaultLocale")
    private void updateOrderSummary() {
        double total = 0.0;

        //calculate subtotal
        for (Pizza item : pizzas) {
            total += item.price();
        }

        //calculate sales tax and total
        double salesTax = total * 0.06625;
        double subtotal = total + salesTax;

        //update
        subtotalCurrentText.setText(String.format("$%.2f", subtotal));
        salesTaxCurrentText.setText(String.format("$%.2f", salesTax));
        totalCurrentText.setText(String.format("$%.2f", total));
    }
}