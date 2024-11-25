package com.example.rupizzeria;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton cStyleIcon = findViewById(R.id.CStyleIcon);
        ImageButton currentOrderIcon = findViewById(R.id.currentOrderIcon);
        ImageButton ordersIcon = findViewById(R.id.ordersIcon);

        //order pizza
        cStyleIcon.setOnClickListener(v -> {
            //Toast.makeText(MainActivity.this, "Order Pizza clicked", Toast.LENGTH_SHORT).show(); - test to see if button was being clicked
            Intent intent = new Intent(MainActivity.this, OrderPizzasActivity.class);
            startActivity(intent);
        });

        //current order
        currentOrderIcon.setOnClickListener(v -> {
            //Toast.makeText(MainActivity.this, "Current Order clicked", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, CurrentOrdersActivity.class);
            startActivity(intent);
        });

        //view orders
        ordersIcon.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ViewOrdersActivity.class);
            startActivity(intent);
        });
    }
}
