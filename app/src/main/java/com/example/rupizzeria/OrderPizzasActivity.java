
package com.example.rupizzeria;


import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Classes.Topping;


public class OrderPizzasActivity extends AppCompatActivity {

    //Declare an instance of ArrayList to hold the items to be display with the RecyclerView
    private ArrayList<Topping> toppings = new ArrayList<>();
    /* All the images associated with the menu items are stored in the res/drawable folder
     *  Each image are accessed with the resource ID, which is an integer.
     *  We need an array of integers to hold the resource IDs. Make sure the index of a given
     *  ID is consistent with the index of the associated menu item in the ArrayList.
     *  An image resource could also be an URI.
     */
    private Spinner pizzaTypeSpinner;

    private int [] toppingImages = {R.drawable.anchovy, R.drawable.bbq_chicken, R.drawable.beef,
            R.drawable.cheddar, R.drawable.green_pepper, R.drawable.ham, R.drawable.mushroom,
            R.drawable.olive, R.drawable.pepperoni, R.drawable.pineapple, R.drawable.provolone,
    R.drawable.red_onion, R.drawable.sausage};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_pizza);
        RecyclerView recyclerView = findViewById(R.id.recycler_toppings);
        setupMenuItems(); //add the list of items to the ArrayList
        ToppingsAdapter adapter = new ToppingsAdapter(this, toppings); //create the adapter
        recyclerView.setAdapter(adapter); //bind the list of items to the RecyclerView
        //use the LinearLayout for the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        pizzaTypeSpinner = findViewById(R.id.pizza_type_spinner);
        String[] pizzaTypes = {"Build Your Own", "Deluxe", "BBQ Chicken", "Meatzza"};
        ArrayAdapter<String> pizzaTypeAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, pizzaTypes);
        pizzaTypeSpinner.setAdapter(pizzaTypeAdapter);

        RadioGroup sizeRadioGroup = findViewById(R.id.rg_size);
        RadioButton sizeMedium = findViewById(R.id.cs_medium_size);
        RadioButton sizeLarge = findViewById(R.id.cs_large_size);
        RadioGroup styleRadioGroup = findViewById(R.id.rg_pizzaStyle);
        RadioButton sizeSmall = findViewById(R.id.cs_small_size);
        RadioButton chicagoStyle = findViewById(R.id.choose_CStyle_RB);
        RadioButton nyStyle = findViewById(R.id.choose_NYStyle_RB);
        styleRadioGroup.check(R.id.choose_CStyle_RB); //automatically select Chicago-Style pizza
        sizeRadioGroup.check(R.id.cs_small_size); //automatically select small size when activity is created

    }


    /**
     * Helper method to set up the data (the Model of the MVC).
     */
    private void setupMenuItems() {
        /*
         * Item names are defined in a String array under res/string.xml.
         * Your item names might come from other places, such as an external file, or the database
         * from the backend.
         */
        //String [] toppingNames;

        /* Add the items to the ArrayList.
         * Note that I use hardcoded prices for demo purpose. This should be replace by other
         * data sources.
         */
        toppings.add(Topping.ANCHOVY);
        toppings.add(Topping.BBQ_CHICKEN);
        toppings.add(Topping.BEEF);
        toppings.add(Topping.CHEDDAR);
        toppings.add(Topping.GREEN_PEPPER);
        toppings.add(Topping.HAM);
        toppings.add(Topping.MUSHROOM);
        toppings.add(Topping.OLIVE);
        toppings.add(Topping.PEPPERONI);
        toppings.add(Topping.PINEAPPLE);
        toppings.add(Topping.PROVOLONE);
        toppings.add(Topping.ONION);
        toppings.add(Topping.SAUSAGE);

        /*for (int i = 0; i < toppingNames.length; i++) {
            //toppings.add(itemNames[i], itemImages[i]);
        }*/
    }

}
