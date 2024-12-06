package com.example.rupizzeria;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Classes.Order;
import Classes.Pizza;

public class ViewOrdersActivity extends AppCompatActivity {
    private FinalOrderItemsAdapter finalOrderItemsAdapter;

    private ArrayList<Order> ordersList;
    private TextView subtotalAmountVOTextView;
    private Button cancelOrderButton;
    private Spinner orderNumSpinner;

    private final OrderDetails orderDetails = OrderDetails.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_orders);

        ordersList = orderDetails.getOrders();

        //debug
        Log.d("ViewOrdersActivity", "Order items: " + ordersList.toString());

        //populate spinner with order numbers
        //loadOrderNumbersIntoSpinner();

        subtotalAmountVOTextView = findViewById(R.id.subtotalAmountVOTextView);
        cancelOrderButton = findViewById(R.id.cancelOrderButton);
        orderNumSpinner = findViewById(R.id.orderNumSpinner); //set spinner view
        subtotalAmountVOTextView = findViewById(R.id.subtotalAmountVOTextView);

        //set up RecyclerView with the FinalOrderItemsAdapter
        RecyclerView orderRecyclerView = findViewById(R.id.orderRecyclerView);
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        finalOrderItemsAdapter = new FinalOrderItemsAdapter(ordersList, subtotalAmountVOTextView);
        orderRecyclerView.setAdapter(finalOrderItemsAdapter);
        updateSubtotal();

        // Set up the cancel entire order button
        cancelOrder();
    }

    private void cancelOrder() {
        cancelOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderDetails.getOrders().isEmpty()) {
                    Toast.makeText(ViewOrdersActivity.this, "No items in order.", Toast.LENGTH_SHORT).show();
                } else {

                    //remove items from the adapter
                    ArrayList<Order> selectedOrders = finalOrderItemsAdapter.getSelectedOrders();

                    if (!selectedOrders.isEmpty()) {
                        // remove items from the list
                        finalOrderItemsAdapter.removeSelectedItems();

                        // remove from the Singleton
                        for (Order order : selectedOrders) {
                            orderDetails.removeOrder(order);
                        }

                        // if items successfully removed
                        Toast.makeText(ViewOrdersActivity.this, "Item(s) have been removed from your order.", Toast.LENGTH_SHORT).show();
                        updateOrderSummary(); // Update the order summary after removal
                    } else {
                        // if user tries to remove without selecting items
                        Toast.makeText(ViewOrdersActivity.this, "No items selected for removal.", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }


    private void loadOrderNumbersIntoSpinner() {
        SharedPreferences sharedPreferences = getSharedPreferences("OrderHistory", MODE_PRIVATE);
        List<String> orderNumbers = new ArrayList<>();

        for (String key : sharedPreferences.getAll().keySet()) {
            if (key.matches("\\d+")) {
                orderNumbers.add(key);
                orderNumbers.add(key.replace("Order_", ""));//added 12:19}


                Log.d("ViewOrdersActivity", "Order Numbers: " + orderNumbers);


                // If no orders exist, show a message
                if (orderNumbers.isEmpty()) {
                    Toast.makeText(this, "No orders placed yet", Toast.LENGTH_SHORT).show();
                }
                // Set up the spinner adapter with the order numbers
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, orderNumbers);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                orderNumSpinner.setAdapter(adapter);
            }
            orderNumSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    String selectedOrderNumber = (String) parentView.getItemAtPosition(position);
                    loadOrderItems(selectedOrderNumber);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                }

            });
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private void loadOrderItems(String orderNumber) {
        SharedPreferences sharedPreferences = getSharedPreferences("OrderHistory", MODE_PRIVATE);
        ArrayList<Order> orders = OrderDetails.getInstance(this).getOrders();
        ordersList.addAll(orders);
        finalOrderItemsAdapter.notifyDataSetChanged();
        updateSubtotal();
        Log.d("ViewOrdersActivity", "No pizzas found for Order #" + orderNumber);
    }


    // Method to calculate and update the subtotal
    @SuppressLint("DefaultLocale")
    private void updateSubtotal() {
        double subtotal = 0.0;
        /*for (Pizza pizza : pizzasList) {
            subtotal += pizza.price();
        }*/

        for(Order order : ordersList){
            subtotal = order.getTotal();
        }
        // display the subtotal in the TextView
        subtotalAmountVOTextView.setText(String.format("$%.2f", subtotal));
    }

    private void updateOrderSummary() { //clear textview
        subtotalAmountVOTextView.setText("");
    }
}
