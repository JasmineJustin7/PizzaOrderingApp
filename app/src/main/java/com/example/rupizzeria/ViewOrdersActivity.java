package com.example.rupizzeria;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_orders);

        // Initialize RecyclerView
        orderRecyclerView = findViewById(R.id.orderRecyclerView);
        subtotalAmountVOTextView = findViewById(R.id.subtotalAmountVOTextView);
        cancelOrderButton = findViewById(R.id.cancelOrderButton);

        // Initialize the list of pizzas (or orders)
        pizzasList = new ArrayList<>();

        //call test
        //testOrders();

        // check list size
        Log.d("ViewOrdersActivity", "Pizzas List Size: " + pizzasList.size());

        finalOrderItemsAdapter = new FinalOrderItemsAdapter(pizzasList);
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderRecyclerView.setAdapter(finalOrderItemsAdapter);

        updateSubtotal();

        cancelOrderButton.setOnClickListener(view -> cancelOrder());
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
        // clear the pizzas list and update the RecyclerView
        pizzasList.clear();
        finalOrderItemsAdapter.notifyDataSetChanged();
        updateSubtotal();

        // reset the subtotal to $0.00
        subtotalAmountVOTextView.setText("$0.00");
    }

    //test code while i figure out current order
    //private void testOrders() {
        //List<String> toppings1 = List.of("Pepperoni", "Cheese", "Onions");
        //OrderItem orderItem1 = new OrderItem("NY Style", "Regular", "Thin", "Small", 14.99, toppings1);

        //List<String> toppings2 = List.of("Cheese", "Olives", "Mushrooms");
        //OrderItem orderItem2 = new OrderItem("Chicago Style", "Build Your Own", "Deep Dish", "Medium", 17.99, toppings2);

    //List<String> toppings3 = List.of("Cheese", "Peppers", "Olives");
        //OrderItem orderItem3 = new OrderItem("NY Style", "Build Your Own", "Pan", "Large", 19.99, toppings3);

        //OrderDetails orderDetails = OrderDetails.getInstance();
        //orderDetails.addPizza(orderItem1);
        //orderDetails.addPizza(orderItem2);
        //orderDetails.addPizza(orderItem3);

    }