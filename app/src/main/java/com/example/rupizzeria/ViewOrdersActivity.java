package com.example.rupizzeria;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import Classes.Order;

/**
 * This class class displays a list of orders in a RecyclerView,
 * allows users to view the subtotal, and provides functionality to cancel selected orders.
 * @author Jimena Reyes
 * @author Jasmine Justin
 */
public class ViewOrdersActivity extends AppCompatActivity {

    /**Adapter for RecyclerView*/
    private FinalOrderItemsAdapter finalOrderItemsAdapter;

    /**List of current orders*/
    private ArrayList<Order> ordersList;

    /**Displays subtotal*/
    private TextView subtotalAmountVOTextView;

    /**Cancels selected orders*/
    private Button cancelOrderButton;

    /**reference to singleton*/
    private final OrderDetails orderDetails = OrderDetails.getInstance(this);

    /**
     * Initialize order list, UI components
     * Set up RecyclerView with adapter and layout manager
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_orders);
        ordersList = orderDetails.getOrders();
        subtotalAmountVOTextView = findViewById(R.id.subtotalAmountVOTextView);
        cancelOrderButton = findViewById(R.id.cancelOrderButton);
        subtotalAmountVOTextView = findViewById(R.id.subtotalAmountVOTextView);
        RecyclerView orderRecyclerView = findViewById(R.id.orderRecyclerView);
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        finalOrderItemsAdapter = new FinalOrderItemsAdapter(ordersList, subtotalAmountVOTextView);
        orderRecyclerView.setAdapter(finalOrderItemsAdapter);
        updateSubtotal();
        cancelOrder();
        updateSubtotal();
    }

    /**
     * Sets up the cancel button to remove selected orders.
     */
    private void cancelOrder() {
        cancelOrderButton.setOnClickListener(v -> {
            if (orderDetails.getOrders().isEmpty()) {
                Toast.makeText(ViewOrdersActivity.this, "No items in order.", Toast.LENGTH_SHORT).show();
            } else {
                ArrayList<Order> selectedOrders = finalOrderItemsAdapter.getSelectedOrders();
                if (!selectedOrders.isEmpty()) {
                    finalOrderItemsAdapter.removeSelectedItems();
                    for (Order order : selectedOrders) {
                        orderDetails.removeOrder(order);
                    }
                    Toast.makeText(ViewOrdersActivity.this, "Item(s) have been removed from your order.", Toast.LENGTH_SHORT).show();
                    updateOrderSummary(); // Update the order summary after removal
                } else {
                    Toast.makeText(ViewOrdersActivity.this, "No items selected for removal.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Updates and displays the subtotal amount.
     */
    @SuppressLint("DefaultLocale")
    private void updateSubtotal() {
        double subtotal = 0.0;
        for(Order order : ordersList){
            subtotal = order.getTotal();
        }
        // display the subtotal in the TextView
        subtotalAmountVOTextView.setText(String.format("$%.2f", subtotal));
    }

    /**
     * Resets the subtotal display after clearing orders.
     */
    @SuppressLint("SetTextI18n")
    private void updateOrderSummary() {
        subtotalAmountVOTextView.setText("");
    }
}
