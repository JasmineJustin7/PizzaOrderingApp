package com.example.rupizzeria;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
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
        RecyclerView orderItemRecyclerView = findViewById(R.id.orderItemRecyclerView);
        orderItemRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        adapter = new OrderItemsAdapter(pizzas);
        orderItemRecyclerView.setAdapter(adapter);
        totalCurrentText = findViewById(R.id.totalCurrentText);
        salesTaxCurrentText = findViewById(R.id.salesTaxCurrentText);
        subtotalCurrentText = findViewById(R.id.subtotalCurrentText);
        orderNumberText = findViewById(R.id.orderNumberText);
        int currentOrderNumber = orderDetails.getCurrentOrderNumber();
        orderNumberText.setText(String.valueOf(currentOrderNumber));
        updateOrderSummary();

        findViewById(R.id.removeButton).setOnClickListener(v -> {
            if (orderDetails.getPizzas().isEmpty()) {
                Toast.makeText(CurrentOrdersActivity.this, "No items in order.", Toast.LENGTH_SHORT).show();
            } else {
                ArrayList<Pizza> selectedPizzas = adapter.getSelectedItems();
                if (!selectedPizzas.isEmpty()) {
                    adapter.removeSelectedItems();
                    for (Pizza pizza : selectedPizzas) {
                        orderDetails.removeOrderItem(pizza);
                    }
                    Toast.makeText(CurrentOrdersActivity.this, "Item(s) have been removed from your order.", Toast.LENGTH_SHORT).show();
                    updateOrderSummary();
                } else {
                    Toast.makeText(CurrentOrdersActivity.this, "No items selected for removal.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        findViewById(R.id.placeOrderButton).setOnClickListener(v -> {
            ArrayList<Pizza> remainingItems = new ArrayList<>(orderDetails.getPizzas());
            Order newOrder = new Order(remainingItems);
            newOrder.setPizzas(remainingItems);
            orderDetails.addOrder(newOrder);
            int orderNumber = orderDetails.getNextOrderNumber();//added
            orderNumberText.setText(String.valueOf(orderNumber));//added
            adapter.clearRecycler();
            Toast.makeText(CurrentOrdersActivity.this, "Order added to list of orders.", Toast.LENGTH_SHORT).show();
        });
    }

    /**updates order summary*/
    @SuppressLint("DefaultLocale")
    private void updateOrderSummary() {
        double total = 0.0;
        for (Pizza item : pizzas) {
            total += item.price();
        }
        double salesTax = total * 0.06625;
        double subtotal = total + salesTax;
        subtotalCurrentText.setText(String.format("$%.2f", subtotal));
        salesTaxCurrentText.setText(String.format("$%.2f", salesTax));
        totalCurrentText.setText(String.format("$%.2f", total));
    }
}