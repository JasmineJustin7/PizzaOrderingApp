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

import Classes.Pizza;

public class CurrentOrdersActivity extends AppCompatActivity {
    private OrderItemsAdapter adapter;
    private ArrayList<Pizza> pizzas;

    private TextView totalCurrentText, salesTaxCurrentText, subtotalCurrentText, orderNumberText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_orders);

        OrderDetails orderDetails = OrderDetails.getInstance(this);

        //OrderDetails orderDetails = new OrderDetails(CurrentOrdersActivity.this);

        //add tests
        //orderDetails.addSampleItems();

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
            //remove selected items from the adapter
            //List<OrderItem> selectedItems = adapter.getSelectedItems();
            //check if order has no pizzas
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
            @SuppressLint("NotifyDataSetChanged")
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

                //get order items that weren't removed
                ArrayList<Pizza> remainingItems = orderDetails.getPizzas();

                //check if all items were removed
                //if (remainingItems.isEmpty()) {//removed

                //debug
                if (remainingItems == null) {
                    Log.e("PlaceOrder", "remainingItems is null!");
                    Toast.makeText(CurrentOrdersActivity.this, "No items in order to place.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if the list is empty
                if (remainingItems.isEmpty()) {
                    Log.d("PlaceOrder", "No items in the order");
                    Toast.makeText(CurrentOrdersActivity.this, "No items in order to place.", Toast.LENGTH_SHORT).show();
                    return;
                }


                //new line add after checking debug statements 6:27
                //if (remainingItems == null || remainingItems.isEmpty()) {
                //Toast.makeText(CurrentOrdersActivity.this, "No items in order to place.", Toast.LENGTH_SHORT).show();
                //eturn;
                //}

                //int orderNumber = orderDetails.getNextOrderNumber(); //remove here 6:11
                Log.d("PlaceOrder", "Next Order Number: " + orderNumber);

                // Save the order in SharedPreferences
                try {
                    orderDetails.saveOrderToSharedPreferences(orderNumber, pizzas);
                } catch (Exception e) {
                    Log.e("PlaceOrder", "Error saving order to SharedPreferences", e);
                    return;
                }

                // Place the order
                try {
                    orderDetails.placeOrder(orderNumber, pizzas);//changed from remianingItems to pizza
                } catch (Exception e) {
                    Log.e("PlaceOrder", "Error placing order", e);
                    return;
                }


                // Save the order in SharedPreferences
                //orderDetails.saveOrderToSharedPreferences(orderNumber, remainingItems);//add after testing 6:27

                //orderDetails.placeOrder(orderNumber, remainingItems); add later after testing 6:27
                //Toast.makeText(CurrentOrdersActivity.this, "Order placed successfully!", Toast.LENGTH_SHORT).show();

                //second message to check is correct order number is placed
                Toast.makeText(CurrentOrdersActivity.this, "Order #" + orderNumber + " placed successfully!", Toast.LENGTH_SHORT).show();

                try {
                    orderDetails.clearOrder();
                    pizzas.clear();  // Clear the order items list
                    adapter.notifyDataSetChanged();  // Update the adapter

                    // Update order summary
                    updateOrderSummary();

                    // Update the order number TextView
                    orderNumberText.setText(String.valueOf(orderNumber));
                    //orderNumber++;

                } catch (Exception e) {
                    Log.e("PlaceOrder", "Error while clearing order or updating UI", e);
                }

                //save and place the order added 2:20
                //orderDetails.placeOrder(orderNumber, remainingItems);

                //orderDetails.clearOrder(); //add after 6:27
                //update RecyclerView
                //orderItems.clear();//add after 6:27
                //adapter.notifyDataSetChanged();//add after 6L27
                //updateOrderSummary();//add after 6:27

                //orderNumberText.setText(String.valueOf(orderNumber));//add after 6:27
            }
        });
    }

    //tests to see if recycler view populates correctly
    /*@SuppressLint("NotifyDataSetChanged")
    private void testAddItemsToOrder() {
        OrderItem sampleItem1 = new OrderItem("NY Style", "BuildYourOwn", "Pan", "Small", 14.99,new ArrayList<>(List.of("Pepperonni", "Onion")));
        OrderItem sampleItem2 = new OrderItem("Chicago Style", "BuildYourOwn", "Deep Dish", "Medium", 17.99, new ArrayList<>(List.of("Cheddar", "Olives")));
        OrderItem sampleItem3 = new OrderItem("NY Style", "BuildYourOwn", "Pan", "Large", 20.99 , new ArrayList<>(List.of("Cheese", "Peppers", "Olives")));

        //add to the singleton
        OrderDetails orderDetails = OrderDetails.getInstance();
        orderDetails.addOrderItem(sampleItem1);
        orderDetails.addOrderItem(sampleItem2);
        orderDetails.addOrderItem(sampleItem3);

        adapter.notifyDataSetChanged();
        Log.d("OrderDetails", "Order Items: " + orderDetails.getOrderItems().toString());
    }*/
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