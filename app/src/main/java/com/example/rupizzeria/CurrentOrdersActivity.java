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

public class CurrentOrdersActivity extends AppCompatActivity {
    private OrderItemsAdapter adapter;
    private List<OrderItem> orderItems;

    private TextView totalCurrentText, salesTaxCurrentText, subtotalCurrentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_orders);

        OrderDetails orderDetails = OrderDetails.getInstance();

        //add tests
        orderDetails.addSampleItems();

        orderItems = orderDetails.getOrderItems();

        //debug
        Log.d("CurrentOrdersActivity", "Order items: " + orderItems.toString());

        //set up RecyclerView with the OrderItemsAdapter
        RecyclerView orderItemRecyclerView = findViewById(R.id.orderItemRecyclerView);
        orderItemRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new OrderItemsAdapter(orderItems);
        orderItemRecyclerView.setAdapter(adapter);

        //initialize TextViews for the summary
        totalCurrentText = findViewById(R.id.totalCurrentText);
        salesTaxCurrentText = findViewById(R.id.salesTaxCurrentText);
        subtotalCurrentText = findViewById(R.id.subtotalCurrentText);


        //button to remove selected items
        findViewById(R.id.removeButton).setOnClickListener(v -> {
            //remove selected items from the adapter
            List<OrderItem> selectedItems = adapter.getSelectedItems();

            if (!selectedItems.isEmpty()) {
                //remove items from the list
                adapter.removeSelectedItems();

                //remove from the Singleton data manager
                for (OrderItem item : selectedItems) {
                    orderDetails.removeOrderItem(item);
                }
                // if items successfully removed
                Toast.makeText(CurrentOrdersActivity.this, "Items have been removed from your order.", Toast.LENGTH_SHORT).show();
                updateOrderSummary();
            } else {
                // if user tries to remove without selecting items
                Toast.makeText(CurrentOrdersActivity.this, "No items selected for removal.", Toast.LENGTH_SHORT).show();
            }
        });

        // Button to place the order
        findViewById(R.id.placeOrderButton).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                //get order items that weren't removed
                List<OrderItem> remainingItems = orderDetails.getOrderItems();

                //check if all items were removed
                if (remainingItems.isEmpty()) {
                    Toast.makeText(CurrentOrdersActivity.this, "No items in order.", Toast.LENGTH_SHORT).show();
                    return;
                }

                int orderNumber = orderDetails.getNextOrderNumber();

                // Save the order in SharedPreferences
                orderDetails.saveOrderToSharedPreferences(orderNumber, remainingItems);

                orderDetails.placeOrder(orderNumber, remainingItems);
                //Toast.makeText(CurrentOrdersActivity.this, "Order placed successfully!", Toast.LENGTH_SHORT).show();

                //second message to check is correct order number is placed
                Toast.makeText(CurrentOrdersActivity.this, "Order #" + orderNumber + " placed successfully!", Toast.LENGTH_SHORT).show();

                orderDetails.clearOrder();
                //update RecyclerView
                orderItems.clear();
                adapter.notifyDataSetChanged();
                updateOrderSummary();
            }
        });
    }

    //tests to see if recycler view populates correctly
    @SuppressLint("NotifyDataSetChanged")
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
    }
    @SuppressLint("DefaultLocale")
    private void updateOrderSummary() {
        double subtotal = 0.0;

        //calculate subtotal
        for (OrderItem item : orderItems) {
            subtotal += item.getPrice();
        }

        //calculate sales tax and total
        double salesTax = subtotal * 0.06625;
        double total = subtotal + salesTax;

        //update
        subtotalCurrentText.setText(String.format("$%.2f", subtotal));
        salesTaxCurrentText.setText(String.format("$%.2f", salesTax));
        totalCurrentText.setText(String.format("$%.2f", total));
    }
}