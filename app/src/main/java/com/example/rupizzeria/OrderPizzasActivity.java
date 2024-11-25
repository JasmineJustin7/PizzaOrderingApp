
package com.example.rupizzeria;


import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
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
    private String deluxeNY = "android.resource://com.your.package/drawable/deluxe";
    /**URI of bbq chicken pizza image*/
    private String bbqchickenNY = "android.resource://com.your.package/drawable/bbqchickenpizza";
    /**URI of meatzza pizza image*/
    private String meatzzaNY = "android.resource://com.your.package/drawable/meatzza";
    /**URI of build your own pizza image*/
    private String byoNY = "android.resource://com.your.package/drawable/newyorkstylepizza";
    /**URI of deluxe pizza image*/
    private String deluxeCS = "android.resource://com.your.package/drawable/deepdishdeluxe";
    /**URI of bbq chicken pizza image*/
    private String bbqchickenCS = "android.resource://com.your.package/drawable/deepdishbbq";
    /**URI of meatzza pizza image*/
    private String meatzzaCS = "android.resource://com.your.package/drawable/deepdishmeatzza";
    /**URI of build your own pizza image*/
    private String byoCS = "android.resource://com.your.package/drawable/chicagopizzaimage";


    /**Declare an instance of ArrayList to hold the items to be display with the RecyclerView*/
    private ArrayList<Topping> toppings = new ArrayList<>();
    /* All the images associated with the menu items are stored in the res/drawable folder
     *  Each image are accessed with the resource ID, which is an integer.
     *  We need an array of integers to hold the resource IDs. Make sure the index of a given
     *  ID is consistent with the index of the associated menu item in the ArrayList.
     *  An image resource could also be an URI.
     */
    private Spinner pizzaTypeSpinner;

    private RecyclerView rv_toppingsView;

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
        RadioButton sizeSmall = findViewById(R.id.cs_small_size);
        RadioGroup styleRadioGroup = findViewById(R.id.rg_pizzaStyle);
        RadioButton chicagoStyle = findViewById(R.id.choose_CStyle_RB);
        RadioButton nyStyle = findViewById(R.id.choose_NYStyle_RB);
        styleRadioGroup.check(R.id.choose_CStyle_RB); //automatically select Chicago-Style pizza
        sizeRadioGroup.check(R.id.cs_small_size); //automatically select small size when activity is created

        pizzaTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ImageView pizzaImage = findViewById(R.id.pizza_image);
                TextView crustType = findViewById(R.id.display_crust_text);
                TextView price = findViewById(R.id.display_price);
                rv_toppingsView = findViewById(R.id.recycler_toppings);
                //String selectedItem = pizzaTypeSpinner.getSelectedItem().toString(); try this out instead of below line of code
                String selectedItem = parent.getItemAtPosition(position).toString();
                Size size;

                if(sizeSmall.isSelected()){
                    size = Size.SMALL;
                }else if(sizeMedium.isSelected()){
                    size = Size.MEDIUM;
                }else{
                    size = Size.LARGE;
                }
                switch (selectedItem) {
                    case "Deluxe":
                        if(nyStyle.isChecked()){
                            loadPizza("Brooklyn", defaultDeluxe, new NYPizza().createDeluxe(size), deluxeNY);
                        }else{ //Chicago style is chosen
                            loadPizza("Deep Dish", defaultDeluxe, new ChicagoPizza().createDeluxe(size), deluxeCS);

                        }
                        break;
                    case "BBQ Chicken":
                        if(nyStyle.isChecked()){
                            loadPizza("Thin", defaultBBQChicken, new NYPizza().createBBQChicken(size), bbqchickenNY);

                        }else{//Chicago style is chosen
                            loadPizza("Pan", defaultBBQChicken, new ChicagoPizza().createBBQChicken(size), bbqchickenCS);

                        }
                        break;
                    case "Meatzza":
                        if(nyStyle.isChecked()){
                            loadPizza("Hand-tossed", defaultMeatzza, new NYPizza().createMeatzza(size), meatzzaNY);

                        }else{//Chicago style is chosen
                            loadPizza("Stuffed", defaultMeatzza, new ChicagoPizza().createMeatzza(size), meatzzaCS);

                        }
                        break;
                    case "Build Your Own":
                        if(nyStyle.isChecked()){
                            //rv_toppingsView.
                            //lv_availableToppingsNY.setDisable(true); //
                            crustType.setText(R.string.hand_tossed);
                            crustType.setClickable(false);
                            //toppings.addAll(defaultPizza.getToppings());
                            //lv_availableToppingsNY.setItems(availableToppingsNY);
                            Pizza BYOPrototype = new NYPizza().createBuildYourOwn(size);
                            DecimalFormat df = new DecimalFormat("#.00");
                            String formattedPrice = df.format(BYOPrototype.price());
                            price.setText(formattedPrice);
                            Uri imageUri = Uri.parse(byoNY);
                            pizzaImage.setImageURI(imageUri);
                        }else{//Chicago style is chosen
                            //lv_availableToppingsCSP.setDisable(false);
                            //bt_addToppingsCSP.setDisable(false);
                            crustType.setText(R.string.pan);
                            crustType.setClickable(false);
                            //lv_availableToppingsCSP.setItems(availableToppingsBYO);
                            Pizza BYOPrototype = new ChicagoPizza().createBuildYourOwn(size);
                            DecimalFormat df = new DecimalFormat("#.00");
                            String formattedPrice = df.format(BYOPrototype.price());
                            price.setText(formattedPrice);
                            Uri imageUri = Uri.parse(byoCS);
                            pizzaImage.setImageURI(imageUri);
                        }
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        Button addPizza = findViewById(R.id.add_to_order_button);
        pizzaTypeSpinner = findViewById(R.id.pizza_type_spinner);
        String selectedItem = pizzaTypeSpinner.getSelectedItem().toString();
        addPizza.setOnClickListener(new View.OnClickListener() {
            Size size;
            RadioButton sizeMedium = findViewById(R.id.cs_medium_size);
            RadioButton sizeLarge = findViewById(R.id.cs_large_size);
            RadioButton sizeSmall = findViewById(R.id.cs_small_size);
            @Override
            public void onClick(View v) {
                if(sizeSmall.isSelected()){
                    size = Size.SMALL;
                }else if(sizeMedium.isSelected()){
                    size = Size.MEDIUM;
                }else{
                    size = Size.LARGE;
                }

                if(selectedItem.equalsIgnoreCase("deluxe")){
                    Pizza deluxe = new ChicagoPizza().createDeluxe(size);
                    //mainController.pizzas.add(deluxe);

                }else if(selectedItem.equalsIgnoreCase("meatzza")){
                    Pizza meatzza = new ChicagoPizza().createMeatzza(size);
                    //mainController.pizzas.add(meatzza);

                }else if(selectedItem.equalsIgnoreCase("BBQ chicken")){
                    Pizza bbq = new ChicagoPizza().createBBQChicken(size);
                    //mainController.pizzas.add(bbq);
                }else{ //build your own
                    Pizza buildYourOwn = new ChicagoPizza().createBuildYourOwn(size);
                    /*for (Topping topping : selectedToppingsBYO) {
                        buildYourOwn.addTopping(topping);
                    }*/
                    //mainController.pizzas.add(buildYourOwn);
                }

                Toast.makeText(OrderPizzasActivity.this, "Pizza added to order!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**loads the info for each pizza given type
     * @param Crust is the crust
     * @param imagePizza is the image of the type of pizza
     * @param size is the size of the pizza
     * @param defaultType is the default pizza option
     * this resets the recycler view according to the toppings for each pizza*/
    private void loadPizza(String Crust, Pizza defaultType, Pizza size, String imagePizza) {
        ImageView pizzaImage = findViewById(R.id.pizza_image);
        TextView crustType = findViewById(R.id.display_crust_text);
        TextView price = findViewById(R.id.display_price);
        rv_toppingsView = findViewById(R.id.recycler_toppings);
        rv_toppingsView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                if(defaultType instanceof BuildYourOwn){
                    return false;
                }
                return true;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        //lv_availableToppingsCSP.setDisable(true); //disable list view of toppings so disable recycler view
        crustType.setText(Crust); //set text for crust
        crustType.setClickable(false); //disable editing for crust
        //toppings.addAll(defaultType.getToppings()); //i don't think this is needed, disable toppings that aren't in pizza type
        //lv_selectedToppingsCSP.setItems(toppings); //make sure toppings in chosen pizza are shown
        DecimalFormat df = new DecimalFormat("#.00");
        String formattedPrice = df.format(size.price());
        price.setText(formattedPrice); //display price in price textview

        Uri imageUri = Uri.parse(imagePizza);// Set an image using a URI
        pizzaImage.setImageURI(imageUri);


    }

    /**create pizza using info provided by user
     * @param view is the view object that fired the event*/
    public void setupAddToOrderListener(View view) {
        Button addPizza = findViewById(R.id.add_to_order_button);
        pizzaTypeSpinner = findViewById(R.id.pizza_type_spinner);
        String selectedItem = pizzaTypeSpinner.getSelectedItem().toString();
        addPizza.setOnClickListener(new View.OnClickListener() {
            Size size;
            RadioButton sizeMedium = findViewById(R.id.cs_medium_size);
            RadioButton sizeLarge = findViewById(R.id.cs_large_size);
            RadioButton sizeSmall = findViewById(R.id.cs_small_size);
            @Override
            public void onClick(View v) {
                if(sizeSmall.isSelected()){
                    size = Size.SMALL;
                }else if(sizeMedium.isSelected()){
                    size = Size.MEDIUM;
                }else{
                    size = Size.LARGE;
                }

                if(selectedItem.equalsIgnoreCase("deluxe")){
                    Pizza deluxe = new ChicagoPizza().createDeluxe(size);
                    //mainController.pizzas.add(deluxe);

                }else if(selectedItem.equalsIgnoreCase("meatzza")){
                    Pizza meatzza = new ChicagoPizza().createMeatzza(size);
                    //mainController.pizzas.add(meatzza);

                }else if(selectedItem.equalsIgnoreCase("BBQ chicken")){
                    Pizza bbq = new ChicagoPizza().createBBQChicken(size);
                    //mainController.pizzas.add(bbq);
                }else{ //build your own
                    Pizza buildYourOwn = new ChicagoPizza().createBuildYourOwn(size);
                    /*for (Topping topping : selectedToppingsBYO) {
                        buildYourOwn.addTopping(topping);
                    }*/
                    //mainController.pizzas.add(buildYourOwn);
                }

                Toast.makeText(OrderPizzasActivity.this, "Pizza added to order!", Toast.LENGTH_SHORT).show();
            }
        });
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

    /*private void viewSelectedToppingsNY(ActionEvent actionEvent) {
        toppings.clear(); //when window is first initialized
        bt_addToppingsNY.setDisable(true); //disable recycler View
        bt_removeToppingsNY.setDisable(true); //disable recycler View

        Size size;
        if(rb_smallNY.isSelected()){
            size = Size.SMALL;
        }else if(rb_mediumNY.isSelected()){
            size = Size.MEDIUM;
        }else{
            size = Size.LARGE;
        }

        if(cb_pizzaTypeNY.getValue().equals("Deluxe")){
            loadPizza("Brooklyn", defaultDeluxe, new NYPizza().createDeluxe(size), deluxe);

        }else if(cb_pizzaTypeNY.getValue().equals("Meatzza")){
            loadPizza("Hand-tossed", defaultMeatzza, new NYPizza().createMeatzza(size), meatzza);

        }else if(cb_pizzaTypeNY.getValue().equals("BBQ Chicken")){
            loadPizza("Thin", defaultBBQChicken, new NYPizza().createBBQChicken(size), bbqchicken);

        }else if(cb_pizzaTypeNY.getValue().equals("Build Your Own")){
            lv_availableToppingsNY.setDisable(false);
            bt_addToppingsNY.setDisable(false);
            tf_crustTypeNY.setText("Hand-tossed");
            tf_crustTypeNY.setDisable(true);
            lv_availableToppingsNY.setItems(availableToppingsNY);
            Pizza BYOPrototype = new NYPizza().createBuildYourOwn(size);
            DecimalFormat df = new DecimalFormat("#.00");
            String formattedPrice = df.format(BYOPrototype.price());
            tf_pizzaPriceNY.setText(formattedPrice);
            iv_NYPizza.setImage(NYBYO);
        }
    }*/

}
