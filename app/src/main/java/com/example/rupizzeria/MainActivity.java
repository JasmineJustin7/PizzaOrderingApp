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

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private ArrayAdapter<String> adapter;

    private String[] activities = {"Order Pizzas", "View Current Order", "View all Orders"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, activities);
        listView = findViewById(R.id.main_listView);
        listView.setOnItemClickListener(this); //register the listener for an OnItemClick event.
        listView.setAdapter(adapter);
    }

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
