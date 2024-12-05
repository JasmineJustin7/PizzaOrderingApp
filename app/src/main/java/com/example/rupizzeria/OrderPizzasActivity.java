
package com.example.rupizzeria;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import Classes.BBQChicken;
import Classes.BuildYourOwn;
import Classes.ChicagoPizza;
import Classes.Deluxe;
import Classes.Meatzza;
import Classes.NYPizza;
import Classes.Pizza;
import Classes.Size;
import Classes.Topping;

/**class associated with creating and customizing pizzas to add to an order
 * includes Chicago Style and New York Style pizzas, different types of pizzas such as
 * Deluxe, BBQ chicken, Meatzza and Build Your Own pizzas
 * offers small, medium and large sizes for each pizza*/
public class OrderPizzasActivity extends AppCompatActivity {

    /**deluxe pizza*/
    private final Pizza defaultDeluxe = new Deluxe(Size.SMALL, false);
    /**build your own pizza*/
    private Pizza defaultBuildYourOwn = new BuildYourOwn(Size.SMALL, false);
    /**bbq chicken pizza*/
    private final Pizza defaultBBQChicken = new BBQChicken(Size.SMALL, false);
    /**meatzza pizza*/
    private final Pizza defaultMeatzza = new Meatzza(Size.SMALL, false);
    /**URI of deluxe pizza image*/
    private String deluxeNY = "android.resource://com.example.rupizzeria/drawable/deluxe";
    /**URI of bbq chicken pizza image*/
    private String bbqchickenNY = "android.resource://com.example.rupizzeria/drawable/bbqchickenpizza";
    /**URI of meatzza pizza image*/
    private String meatzzaNY = "android.resource://com.example.rupizzeria/drawable/meatzza";
    /**URI of build your own pizza image*/
    private String byoNY = "android.resource://com.example.rupizzeria/drawable/newyorkstylepizza";
    /**URI of deluxe pizza image*/
    private String deluxeCS = "android.resource://com.example.rupizzeria/drawable/deepdishdeluxe";
    /**URI of bbq chicken pizza image*/
    private String bbqchickenCS = "android.resource://com.example.rupizzeria/drawable/deepdishbbq";
    /**URI of meatzza pizza image*/
    private String meatzzaCS = "android.resource://com.example.rupizzeria/drawable/deepdishmeatzza";
    /**URI of build your own pizza image*/
    private String byoCS = "android.resource://com.example.rupizzeria/drawable/chicagopizzaimage";
    /**string associated with deluxe pizza*/
    private final String deluxe = "Deluxe";
    /**string associated with bbq chicken pizza*/
    private final String bbqchicken = "BBQ Chicken";
    /**string associated with meatzza pizza*/
    private final String meatzza = "Meatzza";
    /**string associated with build your own pizza*/
    private final String byo = "Build Your Own";
    /**Declare an instance of ArrayList to hold the items to be display with the RecyclerView*/
    private ArrayList<Topping> toppings = new ArrayList<>();
    /**array list of toppings for current pizza selection*/
    private ArrayList<Topping> currentToppings = new ArrayList<>();

    private ArrayList<Topping> selectedToppings = new ArrayList<>();
    /**list of pizzas associated with an order, is shared amongst activities*/
//    private ArrayList<Pizza> pizzas;
    /**spinner that contains the different types of pizza provided by the pizzeria*/
    private Spinner pizzaTypeSpinner;
    /**recycler view that contains all possible toppings for a specific type of pizza*/
    private RecyclerView rv_toppingsView;
    /**singleton used by this activity*/
    private final OrderDetails orderDetails = OrderDetails.getInstance();

    /**create and initializes the gui objects and implements
     * listeners and handlers
     * @param savedInstanceState is the state needed to run this activity*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            setContentView(R.layout.order_pizza);
            RecyclerView recyclerView = findViewById(R.id.recycler_toppings);
            setupMenuItems(); //add the list of items to the ArrayList
            RadioGroup sizeRadioGroup = findViewById(R.id.rg_size);
            RadioGroup styleRadioGroup = findViewById(R.id.rg_pizzaStyle);
            TextView price = findViewById(R.id.display_price);
            ToppingsAdapter adapter = new ToppingsAdapter(this, toppings, currentToppings,
                    price,sizeRadioGroup, styleRadioGroup); //create the adapter
            recyclerView.setAdapter(adapter); //bind the list of items to the RecyclerView
            recyclerView.setLayoutManager(new LinearLayoutManager(this));//use the LinearLayout for the RecyclerView
            pizzaTypeSpinner = findViewById(R.id.pizza_type_spinner);
            String[] pizzaTypes = {"Build Your Own", "Deluxe", "BBQ Chicken", "Meatzza"};
            ArrayAdapter<String> pizzaTypeAdapter = new ArrayAdapter<>(this,
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, pizzaTypes);
            pizzaTypeSpinner.setAdapter(pizzaTypeAdapter);

            RadioButton sizeMedium = findViewById(R.id.cs_medium_size);
            RadioButton sizeLarge = findViewById(R.id.cs_large_size);
            RadioButton sizeSmall = findViewById(R.id.cs_small_size);
            RadioButton chicagoStyle = findViewById(R.id.choose_CStyle_RB);
            RadioButton nyStyle = findViewById(R.id.choose_NYStyle_RB);
            styleRadioGroup.check(R.id.choose_CStyle_RB); //automatically select Chicago-Style pizza
            sizeRadioGroup.check(R.id.cs_small_size); //automatically select small size when activity is created

            onClickPizzaStyle(styleRadioGroup, nyStyle);
            onClickPizzaSize(sizeRadioGroup, nyStyle, price);
            onClickPizzaType(sizeSmall, sizeMedium, recyclerView, nyStyle);

            Button addPizza = findViewById(R.id.add_to_order_button);
            pizzaTypeSpinner = findViewById(R.id.pizza_type_spinner);
            onClickAddPizza(addPizza, nyStyle);
        }catch(NullPointerException e){
            Log.e("View Object Error", "GUI Object is null");
        }


    }

    private void onClickPizzaStyle(RadioGroup styleRadioGroup, RadioButton nyStyle) {
        styleRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton sizeMedium1 = findViewById(R.id.cs_medium_size);
            RadioButton sizeLarge1 = findViewById(R.id.cs_large_size);
            RadioButton sizeSmall1 = findViewById(R.id.cs_small_size);
            RadioButton selectedRadioButton = findViewById(checkedId);
            String selectedItem = pizzaTypeSpinner.getSelectedItem().toString();
            Size size;

            if(sizeLarge1.isChecked()){
                size = Size.LARGE;
            }else if(sizeMedium1.isChecked()){
                size = Size.MEDIUM;
            }else{
                size = Size.SMALL;
            }
            try{
                loadPizzaImages(nyStyle, selectedItem, size);
            }catch(Resources.NotFoundException e){
                alertResourceError();
            }
        });
    }

    private void loadPizzaImages(RadioButton nyStyle, String selectedItem, Size size) {
        if (nyStyle.isChecked()) {
            switch (selectedItem)
            {   case deluxe:
                    Pizza nyDeluxe = new NYPizza().createDeluxe(size);
                    loadPizza("Brooklyn", defaultDeluxe, nyDeluxe, deluxeNY);
                    break;
                case bbqchicken:
                    Pizza nyBBQ = new NYPizza().createBBQChicken(size);
                    loadPizza("Thin", defaultBBQChicken, nyBBQ, bbqchickenNY);
                    break;
                case meatzza:
                    Pizza nyMeatzza = new NYPizza().createMeatzza(size);
                    loadPizza("Hand-tossed", defaultMeatzza, nyMeatzza, meatzzaNY);
                    break;
                case byo:
                    Pizza BYOPrototype = new NYPizza().createBuildYourOwn(size);
                    loadPizza("Hand-tossed", defaultBuildYourOwn, BYOPrototype, byoNY);
                    break;
            }
        }else{//chicago style
            switch (selectedItem)
            {   case deluxe:
                    Pizza csDeluxe = new ChicagoPizza().createDeluxe(size);
                    loadPizza("Deep Dish", defaultDeluxe, csDeluxe, deluxeCS);
                    break;
                case bbqchicken:
                    Pizza csBBQ = new ChicagoPizza().createBBQChicken(size);
                    loadPizza("Pan", defaultBBQChicken, csBBQ, bbqchickenCS);
                    break;
                case meatzza:
                    Pizza csMeatzza = new ChicagoPizza().createMeatzza(size);
                    loadPizza("Stuffed", defaultMeatzza, csMeatzza, meatzzaCS);
                    break;
                case byo:
                    //if(!currentToppings.isEmpty()) currentToppings.clear();
                    Pizza BYOPrototype = new ChicagoPizza().createBuildYourOwn(size);
                    loadPizza("Pan", defaultBuildYourOwn, BYOPrototype, byoCS);
                    break;
            }
        }
    }

    private void onClickPizzaSize(RadioGroup sizeRadioGroup, RadioButton nyStyle, TextView price) {
        sizeRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedRadioButton = findViewById(checkedId);
            String selectedItem = pizzaTypeSpinner.getSelectedItem().toString();
            Size size;

            if(selectedRadioButton.getText().toString().equalsIgnoreCase("small")){
                size = Size.SMALL;
            }else if(selectedRadioButton.getText().toString().equalsIgnoreCase("medium")){
                size = Size.MEDIUM;
            }else{
                size = Size.LARGE;
            }
            if (nyStyle.isChecked()) {
                switch (selectedItem) {
                    case deluxe:
                        Pizza nyDeluxe = new NYPizza().createDeluxe(size);
                        updatePrice(nyDeluxe, price, currentToppings);
                        break;
                    case bbqchicken:
                        Pizza nyBBQ = new NYPizza().createBBQChicken(size);
                        updatePrice(nyBBQ, price, currentToppings);
                        break;
                    case meatzza:
                        Pizza nyMeatzza = new NYPizza().createMeatzza(size);
                        updatePrice(nyMeatzza, price, currentToppings);
                        break;
                    case byo:
                        Pizza BYOPrototype = new NYPizza().createBuildYourOwn(size);
                        updatePrice(BYOPrototype, price, currentToppings);
                        break;
                }
            }else{//chicago style
                switch (selectedItem)
                {
                    case deluxe:
                        Pizza csDeluxe = new ChicagoPizza().createDeluxe(size);
                        updatePrice(csDeluxe, price, currentToppings);
                        break;
                    case bbqchicken:
                        Pizza csBBQ = new ChicagoPizza().createBBQChicken(size);
                        updatePrice(csBBQ, price, currentToppings);
                        break;
                    case meatzza:
                        Pizza csMeatzza = new ChicagoPizza().createMeatzza(size);
                        updatePrice(csMeatzza, price, currentToppings);
                        break;
                    case byo:
                        Pizza BYOPrototype = new ChicagoPizza().createBuildYourOwn(size);
                        updatePrice(BYOPrototype, price, currentToppings);
                        break;
                }
            }
        });
    }

    private void onClickPizzaType(RadioButton sizeSmall, RadioButton sizeMedium, RecyclerView recyclerView, RadioButton nyStyle) {
        pizzaTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ImageView pizzaImage = findViewById(R.id.pizza_image);
                TextView crustType = findViewById(R.id.display_crust_text);
                TextView price = findViewById(R.id.display_price);
                String selectedItem = pizzaTypeSpinner.getSelectedItem().toString();
                Size size;

                if(sizeSmall.isChecked()){
                    size = Size.SMALL;
                }else if(sizeMedium.isChecked()){
                    size = Size.MEDIUM;
                }else{
                    size = Size.LARGE;
                }
                try{
                    switch (selectedItem) {
                        case deluxe:
                            recyclerView.setVisibility(View.GONE); // Hide RecyclerView
                            if(nyStyle.isChecked()){
                                Pizza nyDeluxe = new NYPizza().createDeluxe(size);
                                loadPizza("Brooklyn", defaultDeluxe, nyDeluxe, deluxeNY);
                                currentToppings = nyDeluxe.getToppings();
                                updatePrice(nyDeluxe, price, currentToppings);
                            }else{ //Chicago style is chosen
                                Pizza csDeluxe = new ChicagoPizza().createDeluxe(size);
                                loadPizza("Deep Dish", defaultDeluxe, csDeluxe, deluxeCS);
                                currentToppings = csDeluxe.getToppings();
                                updatePrice(csDeluxe, price, currentToppings);
                            }
                            break;
                        case bbqchicken:
                            recyclerView.setVisibility(View.GONE); // Hide RecyclerView
                            if(nyStyle.isChecked()){
                                Pizza nyBBQ = new NYPizza().createBBQChicken(size);
                                loadPizza("Thin", defaultBBQChicken, nyBBQ, bbqchickenNY);
                                currentToppings = nyBBQ.getToppings();
                                updatePrice(nyBBQ, price, currentToppings);
                            }else{//Chicago style is chosen
                                Pizza csBBQ = new ChicagoPizza().createBBQChicken(size);
                                loadPizza("Pan", defaultBBQChicken, csBBQ, bbqchickenCS);
                                currentToppings = csBBQ.getToppings();
                                updatePrice(csBBQ, price, currentToppings);
                            }
                            break;
                        case meatzza:
                            recyclerView.setVisibility(View.GONE); // Hide RecyclerView
                            if(nyStyle.isChecked()){
                                Pizza nyMeatzza = new NYPizza().createMeatzza(size);
                                loadPizza("Hand-tossed", defaultMeatzza, nyMeatzza, meatzzaNY);
                                currentToppings = nyMeatzza.getToppings();
                                updatePrice(nyMeatzza, price, currentToppings);

                            }else{//Chicago style is chosen
                                Pizza csMeatzza = new ChicagoPizza().createMeatzza(size);
                                loadPizza("Stuffed", defaultMeatzza, csMeatzza, meatzzaCS);
                                currentToppings = csMeatzza.getToppings();
                                updatePrice(csMeatzza, price, currentToppings);
                            }
                            break;
                        case byo:
                            recyclerView.setVisibility(View.VISIBLE); // Hide RecyclerView
                            if(!currentToppings.isEmpty()) currentToppings.clear();//clear current selection of toppings
                            if(nyStyle.isChecked()){
                                crustType.setText(R.string.hand_tossed);
                                crustType.setClickable(false);
                                Pizza BYOPrototype = new NYPizza().createBuildYourOwn(size);
                                updatePrice(BYOPrototype, price, currentToppings);
                                Uri imageUri = Uri.parse(byoNY);
                                pizzaImage.setImageURI(imageUri);
                            }else{//Chicago style is chosen
                                crustType.setText(R.string.pan);
                                crustType.setClickable(false);
                                Pizza BYOPrototype = new ChicagoPizza().createBuildYourOwn(size);
                                updatePrice(BYOPrototype, price, currentToppings);
                                Uri imageUri = Uri.parse(byoCS);
                                pizzaImage.setImageURI(imageUri);
                            }
                            break;
                    }
                }catch(Resources.NotFoundException e){
                    alertResourceError();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { //left empty because it is not needed
            }
        });
    }

    private void onClickAddPizza(Button addPizza, RadioButton nyStyle) {
        addPizza.setOnClickListener(new View.OnClickListener() {
            //final Boolean NYChecked = nyStyle.isChecked();
            Size size;
            RadioButton sizeMedium = findViewById(R.id.cs_medium_size);
            RadioButton sizeLarge = findViewById(R.id.cs_large_size);
            RadioButton sizeSmall = findViewById(R.id.cs_small_size);
            @Override
            public void onClick(View v) {
                final String selectedItem = pizzaTypeSpinner.getSelectedItem().toString();
                if(sizeSmall.isChecked()){
                    size = Size.SMALL;
                }else if(sizeMedium.isChecked()){
                    size = Size.MEDIUM;
                }else{
                    size = Size.LARGE;
                }

                switch (selectedItem) {
                    case deluxe:
                        if(nyStyle.isChecked()){
                            Pizza deluxeNY = new NYPizza().createDeluxe(size);
                            orderDetails.addPizza(deluxeNY);
                        }else{ //Chicago style is chosen
                            Pizza deluxeCS = new ChicagoPizza().createDeluxe(size);
                            orderDetails.addPizza(deluxeCS);
                        }
                        break;
                    case bbqchicken:
                        if(nyStyle.isChecked()){
                            Pizza bbqNY = new NYPizza().createBBQChicken(size);
                            orderDetails.addPizza(bbqNY);
                        }else{//Chicago style is chosen
                            Pizza bbqCS = new ChicagoPizza().createBBQChicken(size);
                            orderDetails.addPizza(bbqCS);
                        }
                        break;
                    case meatzza:
                        if(nyStyle.isChecked()){
                            Pizza meatzzaNY = new NYPizza().createMeatzza(size);
                            orderDetails.addPizza(meatzzaNY);

                        }else{//Chicago style is chosen
                            Pizza meatzzaCS = new ChicagoPizza().createMeatzza(size);
                            orderDetails.addPizza(meatzzaCS);
                        }
                        break;
                    case byo:
                        //if(!currentToppings.isEmpty()) currentToppings.clear();//clear current selection of toppings
                        if(nyStyle.isChecked()){
                            Pizza buildYourOwnNY = new NYPizza().createBuildYourOwn(size);
                            buildYourOwnNY.setToppings(currentToppings);
                            orderDetails.addPizza(buildYourOwnNY);
                        }else{
                            Pizza buildYourOwnCS = new ChicagoPizza().createBuildYourOwn(size);
                            buildYourOwnCS.setToppings(currentToppings);
                            orderDetails.addPizza(buildYourOwnCS);

                        }
                        break;
                }
                //currentToppings.clear(); //test to see if this erases toppings in current activity
                Toast.makeText(OrderPizzasActivity.this, "Pizza added to order!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**update price according to changes in the activity
     * @param price is the GUI object associated with price of pizza
     * @param prototype is the type of pizza*/
    private static void updatePrice(Pizza prototype, TextView price, ArrayList<Topping> byoToppings) {
        DecimalFormat df = new DecimalFormat("#.00");
        String formattedPrice = df.format(prototype.price());
        price.setText(formattedPrice);
        if(prototype instanceof BuildYourOwn){
            Double basePrice = prototype.price() + (byoToppings.size() * 1.69);
            DecimalFormat dfByo = new DecimalFormat("#.00");
            String formattedPriceByo = dfByo.format(basePrice);
            price.setText(formattedPriceByo);
        }
    }

    /**loads the info for each pizza given type
     * @param Crust is the crust
     * @param imagePizza is the image of the type of pizza
     * @param size is the size of the pizza
     * @param defaultType is the default pizza option
     * this resets the recycler view according to the toppings for each pizza*/
    private void loadPizza(String Crust, Pizza defaultType, Pizza size, String imagePizza) {
        try{
            ImageView pizzaImage = findViewById(R.id.pizza_image);
            TextView crustType = findViewById(R.id.display_crust_text);
            TextView price = findViewById(R.id.display_price);
            rv_toppingsView = findViewById(R.id.recycler_toppings);
            crustType.setText(Crust); //set text for crust
            crustType.setClickable(false); //disable editing for crust
            updatePrice(size, price, currentToppings);
            Uri imageUri = Uri.parse(imagePizza);// Set an image using a URI
            pizzaImage.setImageURI(imageUri);
        }catch(Resources.NotFoundException e){
            alertResourceError();
        }

    }

    /**alert message to display if issues with resource recovery is encountered*/
    private void alertResourceError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(OrderPizzasActivity.this);
        builder.setTitle("Resource Error")
                .setMessage("Failure to load Resource.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Code to run when the "OK" button is pressed
                    }
                })
                .setCancelable(false); // Disable outside touch to dismiss the dialog

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**Helper method to set up the data of the recycler view.*/
    private void setupMenuItems() {
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
    }

}