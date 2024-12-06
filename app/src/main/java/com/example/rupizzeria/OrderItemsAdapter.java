package com.example.rupizzeria;

import android.annotation.SuppressLint;
import android.util.Log;
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

public class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsAdapter.OrderItemHolder> {

    //private List<OrderItem> orderItems;
    //private final List<OrderItem> selectedItems;

    private ArrayList<Pizza> pizzas;
    private ArrayList<Pizza> selectedPizzas;

    public OrderItemsAdapter(ArrayList<Pizza> orderItems) {
        //this.orderItems = orderItems;
        //this.selectedItems = new ArrayList<>();
        this.pizzas = orderItems;
        this.selectedPizzas = new ArrayList<>();
    }

    @NonNull
    @Override
    public OrderItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new OrderItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemHolder holder, int position) {
        Pizza currentItem = pizzas.get(position);
        ArrayList<Topping> listToppings = currentItem.getToppings();

        //debug
        Log.d("OrderItemsAdapter", "Binding item: " + currentItem.toString());

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
                    selectedPizzas.add(currentItem);  // Add to selected list
                }
            } else {
                selectedPizzas.remove(currentItem);  // Remove from selected list
            }
        });
    }

    @Override
    public int getItemCount() {
        return pizzas.size();
    }

    public void clearRecycler(){
        pizzas.clear();
        notifyDataSetChanged();
    }

    public ArrayList<Pizza> getSelectedItems() {
        return selectedPizzas;
    }
    public static class OrderItemHolder extends RecyclerView.ViewHolder {

        TextView pizzaStyleTextView;
        TextView pizzaTypeTextView;
        TextView pizzaCrustTextView;
        TextView pizzaSizeTextView;
        TextView pizzaToppingsTextView;
        CheckBox selectItemCheckBox;

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

    // Method to update the orderItems list and refresh RecyclerView
    @SuppressLint("NotifyDataSetChanged")
    public void updateOrderItems(ArrayList<Pizza> updatedItems) {
        this.pizzas = updatedItems; // Update the list with new items
        notifyDataSetChanged(); // Notify adapter to refresh the RecyclerView
    }

    @SuppressLint("NotifyDataSetChanged")
    public void removeSelectedItems() {
        //remove selected items from the list and notify adapter
        pizzas.removeAll(selectedPizzas);
        selectedPizzas.clear();
        notifyDataSetChanged();
    }
}
