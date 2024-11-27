
package com.example.rupizzeria;

import android.annotation.SuppressLint;
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

/**class asociated with creating and customizing pizzas to add to an order
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
    /**list of pizzas associated with an order*/
    private ArrayList<Pizza> pizzas;
    /**spinner that contains the different types of pizza provided by the pizzeria*/
    private Spinner pizzaTypeSpinner;
    /**recycler view that contains all possible toppings for a specific type of pizza*/
    private RecyclerView rv_toppingsView;

    /*private int [] toppingImages = {R.drawable.anchovy, R.drawable.bbq_chicken, R.drawable.beef,
            R.drawable.cheddar, R.drawable.green_pepper, R.drawable.ham, R.drawable.mushroom,
            R.drawable.olive, R.drawable.pepperoni, R.drawable.pineapple, R.drawable.provolone,
    R.drawable.red_onion, R.drawable.sausage};*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OrderDetails orderDetails = OrderDetails.getInstance();
        pizzas = new ArrayList<>();
        setContentView(R.layout.order_pizza);
        RecyclerView recyclerView = findViewById(R.id.recycler_toppings);
        setupMenuItems(byo); //add the list of items to the ArrayList
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

        sizeSmall.setOnClickListener(new View.OnClickListener() {
            String selectedItem = pizzaTypeSpinner.getSelectedItem().toString(); //try this out instead of below line of code

            @Override
            public void onClick(View v) {
                // Check if radioButton1 is clicked
                if (sizeSmall.isChecked()) {
                    //Toast.makeText(OrderPizzasActivity.this, "RadioButton 1 clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sizeMedium.setOnClickListener(new View.OnClickListener() {
            String selectedItem = pizzaTypeSpinner.getSelectedItem().toString(); //try this out instead of below line of code

            @Override
            public void onClick(View v) {
                // Check if radioButton2 is clicked
                if (sizeMedium.isChecked()) {
                    //Toast.makeText(OrderPizzasActivity.this, "RadioButton 2 clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sizeLarge.setOnClickListener(new View.OnClickListener() {
            String selectedItem = pizzaTypeSpinner.getSelectedItem().toString(); //try this out instead of below line of code

            @Override
            public void onClick(View v) {
                // Check if radioButton2 is clicked
                if (sizeLarge.isChecked()) {
                    //Toast.makeText(OrderPizzasActivity.this, "RadioButton 2 clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });

        pizzaTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ImageView pizzaImage = findViewById(R.id.pizza_image);
                TextView crustType = findViewById(R.id.display_crust_text);
                TextView price = findViewById(R.id.display_price);
                rv_toppingsView = findViewById(R.id.recycler_toppings);
                String selectedItem = pizzaTypeSpinner.getSelectedItem().toString(); //try this out instead of below line of code
                //String selectedItem = parent.getItemAtPosition(position).toString();
                Size size;

                if(sizeSmall.isChecked()){
                    size = Size.SMALL;
                }else if(sizeMedium.isChecked()){
                    size = Size.MEDIUM;
                }else{
                    size = Size.LARGE;
                }
                switch (selectedItem) {
                    case deluxe:
                        setupMenuItems(deluxe);
                        if(nyStyle.isChecked()){
                            Pizza nyDeluxe = new NYPizza().createDeluxe(size);
                            loadPizza("Brooklyn", defaultDeluxe, nyDeluxe, deluxeNY);
                            updatePrice(nyDeluxe, price);
                        }else{ //Chicago style is chosen
                            Pizza csDeluxe = new ChicagoPizza().createDeluxe(size);
                            loadPizza("Deep Dish", defaultDeluxe, csDeluxe, deluxeCS);
                            updatePrice(csDeluxe, price);
                        }
                        break;
                    case bbqchicken:
                        setupMenuItems(bbqchicken);
                        if(nyStyle.isChecked()){
                            Pizza nyBBQ = new NYPizza().createBBQChicken(size);
                            loadPizza("Thin", defaultBBQChicken, nyBBQ, bbqchickenNY);
                            updatePrice(nyBBQ, price);
                        }else{//Chicago style is chosen
                            Pizza csBBQ = new ChicagoPizza().createBBQChicken(size);
                            loadPizza("Pan", defaultBBQChicken, csBBQ, bbqchickenCS);
                            updatePrice(csBBQ, price);
                        }
                        break;
                    case meatzza:
                        setupMenuItems(meatzza);
                        if(nyStyle.isChecked()){
                            Pizza nyMeatzza = new NYPizza().createMeatzza(size);
                            loadPizza("Hand-tossed", defaultMeatzza, nyMeatzza, meatzzaNY);
                            updatePrice(nyMeatzza, price);

                        }else{//Chicago style is chosen
                            Pizza csMeatzza = new ChicagoPizza().createMeatzza(size);
                            loadPizza("Stuffed", defaultMeatzza, csMeatzza, meatzzaCS);
                            updatePrice(csMeatzza, price);
                        }
                        break;
                    case byo:
                        setupMenuItems(byo);
                        if(nyStyle.isChecked()){
                            //rv_toppingsView.
                            //lv_availableToppingsNY.setDisable(true); //
                            crustType.setText(R.string.hand_tossed);
                            crustType.setClickable(false);
                            //toppings.addAll(defaultPizza.getToppings());
                            //lv_availableToppingsNY.setItems(availableToppingsNY);
                            Pizza BYOPrototype = new NYPizza().createBuildYourOwn(size);
                            updatePrice(BYOPrototype, price);
                            Uri imageUri = Uri.parse(byoNY);
                            pizzaImage.setImageURI(imageUri);
                        }else{//Chicago style is chosen
                            //lv_availableToppingsCSP.setDisable(false);
                            //bt_addToppingsCSP.setDisable(false);
                            crustType.setText(R.string.pan);
                            crustType.setClickable(false);
                            //lv_availableToppingsCSP.setItems(availableToppingsBYO);
                            Pizza BYOPrototype = new ChicagoPizza().createBuildYourOwn(size);
                            updatePrice(BYOPrototype, price);
                            Uri imageUri = Uri.parse(byoCS);
                            pizzaImage.setImageURI(imageUri);
                        }
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { //left empty because it is not needed
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
                if(sizeSmall.isChecked()){
                    size = Size.SMALL;
                }else if(sizeMedium.isChecked()){
                    size = Size.MEDIUM;
                }else{
                    size = Size.LARGE;
                }

                if(selectedItem.equalsIgnoreCase(deluxe)){
                    Pizza deluxe = new ChicagoPizza().createDeluxe(size);
                    //orderDetails.addOrderItem(new OrderItem());
                    pizzas.add(deluxe);

                }else if(selectedItem.equalsIgnoreCase(meatzza)){
                    Pizza meatzza = new ChicagoPizza().createMeatzza(size);
                    pizzas.add(meatzza);

                }else if(selectedItem.equalsIgnoreCase(bbqchicken)){
                    Pizza bbq = new ChicagoPizza().createBBQChicken(size);
                    pizzas.add(bbq);
                }else{ //build your own
                    Pizza buildYourOwn = new ChicagoPizza().createBuildYourOwn(size);
                    /*for (Topping topping : selectedToppingsBYO) {
                        buildYourOwn.addTopping(topping);
                    }*/
                    //mainController.pizzas.add(buildYourOwn);
                }
                //orderDetails.
                Toast.makeText(OrderPizzasActivity.this, "Pizza added to order!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static void updatePrice(Pizza BYOPrototype, TextView price) {
        DecimalFormat df = new DecimalFormat("#.00");
        String formattedPrice = df.format(BYOPrototype.price());
        price.setText(formattedPrice);
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
        updatePrice(size, price);

        Uri imageUri = Uri.parse(imagePizza);// Set an image using a URI
        pizzaImage.setImageURI(imageUri);


    }

    /**allows addition of toppings when customizing build your own pizza type
     * removes toppings from available toppings list and adds to selected toppings list
     * ties to add button in recycler view*/
    /*private void addAvailableToppingCSP(){
        if(selectedToppingsBYO.size() == 7){//error
            showAlert("Invalid amount of toppings", "You cannot have more than 7 toppings at a given time.");
            return;
        }

        Size size;
        if(rb_smallCSP.isSelected()){
            size = Size.SMALL;
        }else if(rb_mediumCSP.isSelected()){
            size = Size.MEDIUM;
        }else{
            size = Size.LARGE;
        }

        Pizza buildYourOwnPrototype = new ChicagoPizza().createBuildYourOwn(size); //create a new byo pizza everytime a topping is added
        if(lv_availableToppingsCSP.getSelectionModel().getSelectedItem() == null) return;
        Topping selectedTopping = lv_availableToppingsCSP.getSelectionModel().getSelectedItem();
        availableToppingsBYO.remove(selectedTopping); //remove selected from available list view
        selectedToppingsBYO.add(selectedTopping);//add selected available toppings from list view to selected list view
        lv_selectedToppingsCSP.setItems(selectedToppingsBYO);
        lv_availableToppingsCSP.setItems(availableToppingsBYO);
        if(!selectedToppingsBYO.isEmpty()) bt_removeToppingsCSP.setDisable(false);

        for (Topping topping : selectedToppingsBYO) {
            buildYourOwnPrototype.addTopping(topping);
        }

        DecimalFormat df = new DecimalFormat("#.00");
        String formattedPrice = df.format(buildYourOwnPrototype.price());
        tf_pizzaPriceCSP.setText(formattedPrice);
    }*/

    /**allows deletion of toppings when customizing build your own pizza type
     * removes toppings from selected toppings list and adds to available toppings list
     * tied to remove buttons on toppings in recycler view*/
    /*private void removeSelectedToppingCSP(){
        if(selectedToppingsBYO.isEmpty()){
            showAlert("Invalid amount of toppings.", "There are no toppings yet. Please select a topping or add to order.");
            return;
        }
        Size size;
        if(rb_smallCSP.isSelected()){
            size = Size.SMALL;
        }else if(rb_mediumCSP.isSelected()){
            size = Size.MEDIUM;
        }else{
            size = Size.LARGE;
        }

        Pizza buildYourOwnPrototype = new ChicagoPizza().createBuildYourOwn(size); //create a new byo pizza everytime a topping is added

        if(lv_selectedToppingsCSP.getSelectionModel().getSelectedItem() == null) return;
        Topping selectedTopping = lv_selectedToppingsCSP.getSelectionModel().getSelectedItem();
        bt_removeToppingsCSP.setDisable(false);
        selectedToppingsBYO.remove(selectedTopping);
        availableToppingsBYO.add(selectedTopping);
        lv_availableToppingsCSP.setItems(availableToppingsBYO);

        for (Topping topping : selectedToppingsBYO) {
            buildYourOwnPrototype.addTopping(topping);
        }
        //dynamically update price
        DecimalFormat df = new DecimalFormat("#.00");
        String formattedPrice = df.format(buildYourOwnPrototype.price());
        tf_pizzaPriceCSP.setText(formattedPrice);
    }*/



    /**
     * Helper method to set up the data (the Model of the MVC).
     * @param pizzaType is the type of pizza chosen
     */
    @SuppressLint("NotifyDataSetChanged")
    private void setupMenuItems(String pizzaType) {
        switch (pizzaType) {
            case deluxe:
                toppings = defaultDeluxe.getToppings();
                //notifyDataSetChanged();
            case bbqchicken:
                toppings = defaultBBQChicken.getToppings();
                //notifyDataSetChanged();
            case meatzza:
                toppings = defaultMeatzza.getToppings();
                //notifyDataSetChanged();
            default:
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
                //notifyDataSetChanged();

        }
    }

}
