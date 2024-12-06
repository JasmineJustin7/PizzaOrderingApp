package com.example.rupizzeria;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import Classes.Pizza;
import Classes.Topping;

/**
 * Adapter for managing and displaying a list of pizzas in a RecyclerView.
 * @author Jimena Reyes
 * @author Jasmine Justin
 */
public class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsAdapter.OrderItemHolder> {

    /** List of all pizzas in the current order. */
    private ArrayList<Pizza> pizzas;

    /** List of selected pizzas. */
    private ArrayList<Pizza> selectedPizzas;

    /**
     * Constructor to initialize the adapter with a list of pizzas.
     * @param orderItems the list of pizzas to display
     */
    public OrderItemsAdapter(ArrayList<Pizza> orderItems) {
        this.pizzas = orderItems;
        this.selectedPizzas = new ArrayList<>();
    }

    /**
     * Inflates the layout for a single pizza item and creates a ViewHolder.
     * @param parent   the parent ViewGroup into which the new view will be added
     * @param viewType the type of view (not used here as all items are the same)
     * @return a new instance of {@link OrderItemHolder} holding the inflated view
     */
    @NonNull
    @Override
    public OrderItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new OrderItemHolder(view);
    }

    /**
     * Binds data from a pizza item to the views in the ViewHolder.
     * @param holder   the ViewHolder to update
     * @param position the position of the pizza item in the list
     */
    @Override
    public void onBindViewHolder(@NonNull OrderItemHolder holder, int position) {
        Pizza currentItem = pizzas.get(position);
        ArrayList<Topping> listToppings = currentItem.getToppings();
        holder.pizzaStyleTextView.setText(currentItem.getStyle());
        holder.pizzaTypeTextView.setText(currentItem.getPizzaType());
        holder.pizzaCrustTextView.setText(currentItem.getCrust().toString());
        holder.pizzaSizeTextView.setText(currentItem.getSize().toString());;
        holder.pizzaToppingsTextView.setText(currentItem.getToppings().toString());//will fix if problems arise
        holder.pizzaToppingsTextView.setText(listToppings.isEmpty() ? "No toppings" : listToppings.toString());
        holder.selectItemCheckBox.setChecked(selectedPizzas.contains(currentItem));
        holder.selectItemCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (!selectedPizzas.contains(currentItem)) {
                    selectedPizzas.add(currentItem);
                }
            } else {
                selectedPizzas.remove(currentItem);
            }
        });
    }

    /**
     * Returns the total number of pizza items in the adapter.
     * @return the size of the pizzas list
     */
    @Override
    public int getItemCount() {
        return pizzas.size();
    }

    /**
     * Clears all pizzas from the RecyclerView and refreshes the view.
     */
    public void clearRecycler(){
        pizzas.clear();
        notifyDataSetChanged();
    }

    /**
     * Gets the list of selected pizzas.
     * @return the list of selected pizzas
     */
    public ArrayList<Pizza> getSelectedItems() {
        return selectedPizzas;
    }

    /**
     * ViewHolder class for pizza items in the RecyclerView.
     */
    public static class OrderItemHolder extends RecyclerView.ViewHolder {

        TextView pizzaStyleTextView;
        TextView pizzaTypeTextView;
        TextView pizzaCrustTextView;
        TextView pizzaSizeTextView;
        TextView pizzaToppingsTextView;
        CheckBox selectItemCheckBox;

        /**
         * Initializes the ViewHolder with references to the item views.
         * @param itemView the layout view for a single pizza item
         */
        public OrderItemHolder(@NonNull View itemView) {
            super(itemView);
            pizzaStyleTextView = itemView.findViewById(R.id.pizzaStyleTextViewCurrent);
            pizzaTypeTextView = itemView.findViewById(R.id.pizzaTypeTextViewCurrent);
            pizzaCrustTextView = itemView.findViewById(R.id.crustTypeTextViewCurrent);
            pizzaSizeTextView = itemView.findViewById(R.id.sizeTextViewCurrent);
            pizzaToppingsTextView = itemView.findViewById(R.id.toppingsTextViewCurrent);
            selectItemCheckBox = itemView.findViewById(R.id.selectItemCheckBox);
        }
    }

    /**
     * Updates the pizza list and refreshes the RecyclerView.
     * @param updatedItems the updated list of pizzas
     */
    @SuppressLint("NotifyDataSetChanged")
    public void updateOrderItems(ArrayList<Pizza> updatedItems) {
        this.pizzas = updatedItems;
        notifyDataSetChanged();
    }

    /**
     * Removes selected pizzas from the list and refreshes the RecyclerView.
     */
    @SuppressLint("NotifyDataSetChanged")
    public void removeSelectedItems() {
        pizzas.removeAll(selectedPizzas);
        selectedPizzas.clear();
        notifyDataSetChanged();
    }
}
