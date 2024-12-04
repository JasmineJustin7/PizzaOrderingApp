package com.example.rupizzeria;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.util.Log;
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
        try{
            listView = findViewById(R.id.main_listView);
        }catch(NullPointerException e){
            Log.e("View Object Error", "List View is null");
        }

        listView.setOnItemClickListener(this); //register the listener for an OnItemClick event.
        listView.setAdapter(adapter);
    }

    private void displayActivityException() {
        // Create the AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        // Set the title, message, and buttons
        builder.setTitle("Activity Failed")
                .setMessage("Failure to open Activity.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Code to run when the "OK" button is pressed
                    }
                })
                .setCancelable(false); // Disable outside touch to dismiss the dialog

        // Show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
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
                try{
                    intent = new Intent(MainActivity.this, OrderPizzasActivity.class);
                    startActivity(intent);
                    break;
                }catch(ActivityNotFoundException e){
                    displayActivityException();
                }

            case 1:
                try{
                    intent = new Intent(MainActivity.this, CurrentOrdersActivity.class);
                    startActivity(intent);
                    break;
                }catch(ActivityNotFoundException e){
                    displayActivityException();
                }

            case 2:
                try{
                    intent = new Intent(MainActivity.this, ViewOrdersActivity.class);
                    startActivity(intent);
                    break;
                }catch(ActivityNotFoundException e){
                    displayActivityException();
                }

            default:
                try{
                    intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
                }catch(ActivityNotFoundException e){
                    displayActivityException();
                }

        }
        //startActivity(intent);
    }


}
