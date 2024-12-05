package com.example.rupizzeria;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Classes.Pizza;
import Classes.Topping;

public class FinalOrderItemsAdapter extends RecyclerView.Adapter<FinalOrderItemsAdapter.OrderItemHolder> {

    private ArrayList<Pizza> pizzas; // List of pizzas to display
    private ArrayList<Pizza> selectedPizzas = new ArrayList<>();

    public FinalOrderItemsAdapter(ArrayList<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    @NonNull
    @Override
    public OrderItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.finalorder_item, parent, false);
        return new OrderItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemHolder holder, int position) {
        Pizza currentItem = pizzas.get(position);
        ArrayList<Topping> listToppings = currentItem.getToppings();

        // Debug log to ensure items are bound correctly
        Log.d("FinalOrderItemsAdapter", "Binding item: " + currentItem);

        // Setting text for each item
        holder.pizzaStyleTextView.setText(currentItem.getStyle());
        holder.pizzaTypeTextView.setText(currentItem.getPizzaType());
        holder.pizzaCrustTextView.setText(currentItem.getCrust().toString());
        holder.pizzaSizeTextView.setText(currentItem.getSize().toString());
        holder.pizzaToppingsTextView.setText(listToppings.isEmpty() ? "No toppings" : listToppings.toString());

        // Handle item selection for cancellation
        holder.itemView.setOnClickListener(v -> {
            if (selectedPizzas.contains(currentItem)) {
                selectedPizzas.remove(currentItem);
            } else {
                selectedPizzas.add(currentItem);
            }
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return pizzas.size();
    }

    // Method to get selected pizzas
    public ArrayList<Pizza> getSelectedPizzas() {
        return selectedPizzas;
    }

    public static class OrderItemHolder extends RecyclerView.ViewHolder {

        TextView pizzaStyleTextView;
        TextView pizzaTypeTextView;
        TextView pizzaCrustTextView;
        TextView pizzaSizeTextView;
        TextView pizzaToppingsTextView;

        public OrderItemHolder(@NonNull View itemView) {
            super(itemView);
            pizzaStyleTextView = itemView.findViewById(R.id.pizzaStyleTextView);
            pizzaTypeTextView = itemView.findViewById(R.id.pizzaTypeTextView);
            pizzaCrustTextView = itemView.findViewById(R.id.crustTypeTextView);
            pizzaSizeTextView = itemView.findViewById(R.id.sizeTextView);
            pizzaToppingsTextView = itemView.findViewById(R.id.toppingsTextView);
        }
    }

    // Method to update the list of pizzas
    @SuppressLint("NotifyDataSetChanged")
    public void updateOrderItems(ArrayList<Pizza> updatedItems) {
        this.pizzas = updatedItems;
        notifyDataSetChanged();
    }
}