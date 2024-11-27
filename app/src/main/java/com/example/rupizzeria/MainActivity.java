package com.example.rupizzeria;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

/**main view of pizzeria app that gives users the option to order pizzas, view their current order, or view all orders*/
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    /**list view containing all options provided to users*/
    private ListView listView;
    /**adapter to hold all list view items*/
    private ArrayAdapter<String> adapter;
    /**string associated with opening order pizza activity*/
    private final String orderPizzas = "Order Pizzas";
    /**string associated with viewing current order activity*/
    private final String viewCurrent = "View Current Order";
    /**string associated with viewing all orders made*/
    private final String viewOrders = "View all Orders";
    /**list of all strings associated with activities users can access in the app*/
    private final String[] activities = {orderPizzas, viewCurrent, viewOrders};

    /**initializes the main activity view
     * @param savedInstanceState used to create view of main activity*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, activities);
        listView = findViewById(R.id.main_listView);
        listView.setOnItemClickListener(this); //register the listener for an OnItemClick event.
        listView.setAdapter(adapter);
    }

    /**listener for clicking each item in list view
     * @param view is main view
     * @param id is the id
     * @param parent adapter view of list view
     * @param position is the position */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Open different activities based on the item clicked
        Intent intent;
        switch (position) {
            case 0:
                intent = new Intent(MainActivity.this, OrderPizzasActivity.class);
                break;
            case 1:
                intent = new Intent(MainActivity.this, CurrentOrdersActivity.class);
                break;
            case 2:
                intent = new Intent(MainActivity.this, ViewOrdersActivity.class);
                break;
            default:
                intent = new Intent(MainActivity.this, MainActivity.class);
                break;
        }
        startActivity(intent);
    }
}
