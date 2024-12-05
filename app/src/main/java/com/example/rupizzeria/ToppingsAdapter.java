package com.example.rupizzeria;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import Classes.Topping;

/**
 * This is an Adapter class to be used to instantiate an adapter for the RecyclerView.
 */
class ToppingsAdapter extends RecyclerView.Adapter<ToppingsAdapter.ItemsHolder>{
    /**need the context to inflate the layout*/
    private Context context;
    /**need the data binding to each row of RecyclerView*/
    private ArrayList<Topping> toppings;
    /**list of toppings in customizable pizza*/
    private ArrayList<Topping> currentToppings;
    /**radio group that stores the sizes of pizza*/
    private RadioGroup sizeRG;
    /**GUI object reference that stores price of current pizza selection*/
    private TextView price;
    /**radio group associated with pizza type*/
    private RadioGroup type;

    /**constructor of recycler view
     * @param price is the reference of GUI object from order pizzas activity
     * @param context is the reference to order pizzas activity
     * @param currentToppings is the current toppings selected by user
     * @param items are all possible toppings for pizzas on the menu
     * @param size is the radio group that stores all possible sizes
     * @param type is the radio group associated with pizza type*/
    public ToppingsAdapter(Context context, ArrayList<Topping> items,
                           ArrayList<Topping> currentToppings, TextView price, RadioGroup size,
                           RadioGroup type) {
        this.context = context;
        this.toppings = items;
        this.currentToppings = currentToppings;
        this.price = price;
        this.sizeRG = size;
        this.type = type;
    }

    /**
     * This method will inflate the row layout for the items in the RecyclerView
     * @param parent is the parent within constraint layout
     * @param viewType is the type of view used
     * @return items holder with parameter view
     */
    @NonNull
    @Override
    public ItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context); //inflate the row layout for the items
        View view = inflater.inflate(R.layout.pizza_toppings_view, parent, false);
        return new ItemsHolder(view);
    }

    /**
     * Assign data values for each row according to their "position" (index) when the item becomes
     * visible on the screen.
     * @param holder the instance of ItemsHolder
     * @param position the index of the item in the list of items
     */
    @Override
    public void onBindViewHolder(@NonNull ItemsHolder holder, int position) {
        //assign values for each row
        holder.tv_name.setText(toppings.get(position).toString());
        holder.im_topping.setImageResource(toppings.get(position).getImage());

        // Handle the "Add" button click to add item to addedItems list
        holder.btn_add.setOnClickListener(v -> {

            if(currentToppings.size() >= 7){
                Toast.makeText(this.context, "Cannot exceed more than 7 toppings",
                        Toast.LENGTH_LONG).show();
                return;
            }
            if(currentToppings.contains(Topping.valueOf(toppings.get(position).toString()))){
                Toast.makeText(this.context, "Topping has already been added to pizza",
                        Toast.LENGTH_LONG).show();
                return;
            }
            if (!currentToppings.contains(Topping.valueOf(toppings.get(position).toString()))) {
                currentToppings.add(Topping.valueOf(toppings.get(position).toString()));
                Toast.makeText(this.context,
                        toppings.get(position).toString() + " added.", Toast.LENGTH_LONG).show();
                notifyItemChanged(position);// Notify the adapter that the data has changed
            }
            updatePrice();
        });

        holder.btn_remove.setOnClickListener(v -> {
            if(currentToppings.isEmpty()){
                Toast.makeText(this.context, "No toppings on pizza currently. Please add toppings before removing",
                        Toast.LENGTH_LONG).show();
                return;
            }
            if (currentToppings.contains(Topping.valueOf(toppings.get(position).toString()))) {
                currentToppings.remove(Topping.valueOf(toppings.get(position).toString()));
                Toast.makeText(this.context,
                        toppings.get(position).toString() + " removed.", Toast.LENGTH_LONG).show();
                // Notify the adapter that the data has changed
                notifyItemChanged(position);
            }
            updatePrice();
        });
    }

    /**updates the price of a build your own pizza whenever toppings are added or removed*/
    private void updatePrice() {
        //check what size is being chosen
        int selectedRadioButton = sizeRG.getCheckedRadioButtonId();
        RadioButton checkedSize = sizeRG.findViewById(selectedRadioButton);
        double basePrice = 0;
        if(checkedSize.getText().toString().equalsIgnoreCase("small")){
            basePrice = 8.99;
        }else if(checkedSize.getText().toString().equalsIgnoreCase("medium")){
            basePrice = 10.99;
        }else{
            basePrice = 12.99;
        }
        basePrice = basePrice + (currentToppings.size() * 1.69);
        DecimalFormat df = new DecimalFormat("#.00");
        String formattedPrice = df.format(basePrice);
        price.setText(formattedPrice);
    }

    /**
     * Get the number of items in the ArrayList.
     * @return the number of items in the list.
     */
    @Override
    public int getItemCount() {
        return toppings.size(); //number of MenuItem in the array list.
    }

    /**Get the views from the row layout file, similar to the onCreate() method.*/
    public static class ItemsHolder extends RecyclerView.ViewHolder {
        /**text associated with name of topping*/
        private TextView tv_name;
        /**text view associated with price of pizza*/
        private TextView tv_price;
        /**image of respective topping*/
        private ImageView im_topping;
        /**button to add topping to order*/
        private Button btn_add;
        /**button to remove topping from order*/
        private Button btn_remove;
        /***/
        private ConstraintLayout parentLayout; //this is the row layout
        private Spinner pizzaType;
        private ArrayList<Topping> currentToppings;

        public ItemsHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_topping);
            tv_price = itemView.findViewById(R.id.tv_price);
            im_topping = itemView.findViewById(R.id.im_topping);
            btn_add = itemView.findViewById(R.id.btn_add);
            btn_remove = itemView.findViewById(R.id.btn_remove);
            parentLayout = itemView.findViewById(R.id.rowLayout);

            parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), OrderPizzasActivity.class);
                    intent.putExtra("ITEM", tv_name.getText());
                    itemView.getContext().startActivity(intent);
                }
            });
        }

        /**
         * Set the onClickListener for the button on each row.
         * Clicking on the button will create an AlertDialog with the options of YES/NO.
         * @param toppingView is the recycler view of toppings
         */
        /*private void setAddButtonOnClick(@NonNull View toppingView) {
            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //pizzaType = itemView.findViewById(R.id.pizza_type_spinner);
                    AlertDialog.Builder alert = new AlertDialog.Builder(toppingView.getContext());
                    alert.setTitle("Add to order");
                    alert.setMessage(tv_name.getText().toString());
                    //handle the "YES" click
                    alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            pizzaType = itemView.findViewById(R.id.pizza_type_spinner);
                            Toast.makeText(toppingView.getContext(),
                                    tv_name.getText().toString() + " added.", Toast.LENGTH_LONG).show();
                        }
                        //handle the "NO" click
                    }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(toppingView.getContext(),
                                    tv_name.getText().toString() + " not added.", Toast.LENGTH_LONG).show();
                        }
                    });
                    AlertDialog dialog = alert.create();
                    dialog.show();
                }
            });
        }*/

        private void setRemoveButtonOnClick(@NonNull View toppingView) {
            btn_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(toppingView.getContext());
                    alert.setTitle("Remove from order?");
                    alert.setMessage(tv_name.getText().toString());
                    //handle the "YES" click
                    alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(toppingView.getContext(),
                                    tv_name.getText().toString() + " added.", Toast.LENGTH_LONG).show();
                        }
                        //handle the "NO" click
                    }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(toppingView.getContext(),
                                    tv_name.getText().toString() + " not added.", Toast.LENGTH_LONG).show();
                        }
                    });
                    AlertDialog dialog = alert.create();
                    dialog.show();
                }
            });
        }
    }
}