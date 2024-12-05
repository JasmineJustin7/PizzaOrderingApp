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

import Classes.Pizza;

public class ViewOrdersActivity extends AppCompatActivity {
    private RecyclerView orderRecyclerView;
    private FinalOrderItemsAdapter finalOrderItemsAdapter;
    private ArrayList<Pizza> pizzasList;
    private TextView subtotalAmountVOTextView;
    private Button cancelOrderButton;

    private Spinner orderNumSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_orders);


        //OrderDetails orderDetails = OrderDetails.getInstance(ViewOrdersActivity.this);
        // Initialize RecyclerView
        orderRecyclerView = findViewById(R.id.orderRecyclerView);
        subtotalAmountVOTextView = findViewById(R.id.subtotalAmountVOTextView);
        Button cancelOrderButton = findViewById(R.id.cancelOrderButton);
        orderNumSpinner = findViewById(R.id.orderNumSpinner);
        subtotalAmountVOTextView = findViewById(R.id.subtotalAmountVOTextView);


        // Initialize the list of pizzas (or orders)
        pizzasList = new ArrayList<>();// add after testing 3:13


        //added code 3:13
        //pizzasList = OrderDetails.getInstance().getPizzas();


        // check list size
        Log.d("ViewOrdersActivity", "Pizzas List Size: " + pizzasList.size());
        updateSubtotal();


        finalOrderItemsAdapter = new FinalOrderItemsAdapter(pizzasList);
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderRecyclerView.setAdapter(finalOrderItemsAdapter);
        //populate spinner with order numbers
        loadOrderNumbersIntoSpinner();


        // If pizzas exist, add them to the RecyclerView
        //if (!pizzasList.isEmpty()) {
        //pizzasList.clear();//Clear any existing pizzas
        //pizzasList.addAll(pizzasList);
        //finalOrderItemsAdapter.notifyDataSetChanged();
        //} else {
        //Log.d("ViewOrdersActivity", "No pizzas found in the current order.");
        //}


        // Set up the cancel entire order button
        cancelOrderButton.setOnClickListener(v -> cancelOrder());
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

        // Access the pizzas directly from the singleton
        ArrayList<Pizza> pizzas = OrderDetails.getInstance(this).getPizzas();


        //if (pizzas != null && !pizzas.isEmpty()) {
        // Clear the current list and add new pizzas
        pizzasList.clear();
        pizzasList.addAll(pizzas);

        finalOrderItemsAdapter.notifyDataSetChanged();

        updateSubtotal();
        Log.d("ViewOrdersActivity", "No pizzas found for Order #" + orderNumber);
    }


    // Method to calculate and update the subtotal
    @SuppressLint("DefaultLocale")
    private void updateSubtotal() {
        double subtotal = 0.0;
        for (Pizza pizza : pizzasList) {
            subtotal += pizza.price();
        }
        // display the subtotal in the TextView
        subtotalAmountVOTextView.setText(String.format("$%.2f", subtotal));
    }

    // Method to handle canceling the order
    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    private void cancelOrder() {
        String selectedOrderNumber = (String) orderNumSpinner.getSelectedItem();
        if (selectedOrderNumber != null && !selectedOrderNumber.isEmpty()) {
            pizzasList.clear();
            finalOrderItemsAdapter.notifyDataSetChanged();
            Toast.makeText(ViewOrdersActivity.this, "Order(s) canceled.", Toast.LENGTH_SHORT).show();
            updateSubtotal();
            // reset the subtotal to $0.00
            subtotalAmountVOTextView.setText("$0.00");
            // Also remove the canceled order from SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("OrderHistory", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            // Remove the order from SharedPreferences
            editor.remove("Order_" + selectedOrderNumber);
            editor.apply();

            Toast.makeText(ViewOrdersActivity.this, "Order canceled.", Toast.LENGTH_SHORT).show();
            loadOrderNumbersIntoSpinner(); // Reload order numbers after cancellation
        } else {
            Toast.makeText(this, "No order selected to cancel.", Toast.LENGTH_SHORT).show();
        }
    }
}
