package com.example.rupizzeria;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsAdapter.OrderItemHolder> {

    private List<OrderItem> orderItems;
    private final List<OrderItem> selectedItems;

    public OrderItemsAdapter(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
        this.selectedItems = new ArrayList<>();
    }

    @NonNull
    @Override
    public OrderItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new OrderItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemHolder holder, int position) {
        OrderItem currentItem = orderItems.get(position);

        //debug
        Log.d("OrderItemsAdapter", "Binding item: " + currentItem.toString());

        holder.pizzaStyleTextView.setText(currentItem.getPizzaStyle());
        holder.pizzaTypeTextView.setText(currentItem.getPizzaType());
        holder.pizzaCrustTextView.setText(currentItem.getCrustType());
        holder.pizzaSizeTextView.setText(currentItem.getSize());
        holder.pizzaToppingsTextView.setText(String.join(", ", currentItem.getToppings()));

        holder.selectItemCheckBox.setChecked(selectedItems.contains(currentItem));

        holder.selectItemCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedItems.add(currentItem);
            } else {
                selectedItems.remove(currentItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public List<OrderItem> getSelectedItems() {
        return selectedItems;
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
            pizzaStyleTextView = itemView.findViewById(R.id.pizzaStyleTextView);
            pizzaTypeTextView = itemView.findViewById(R.id.pizzaTypeTextView);
            pizzaCrustTextView = itemView.findViewById(R.id.crustTypeTextView);
            pizzaSizeTextView = itemView.findViewById(R.id.sizeTextView);
            pizzaToppingsTextView = itemView.findViewById(R.id.toppingsTextView);
            selectItemCheckBox = itemView.findViewById(R.id.selectItemCheckBox);
        }
    }

    // Method to update the orderItems list and refresh RecyclerView
    @SuppressLint("NotifyDataSetChanged")
    public void updateOrderItems(List<OrderItem> updatedItems) {
        this.orderItems = updatedItems; // Update the list with new items
        notifyDataSetChanged(); // Notify adapter to refresh the RecyclerView
    }

    @SuppressLint("NotifyDataSetChanged")
    public void removeSelectedItems() {
        //remove selected items from the list and notify adapter
        orderItems.removeAll(selectedItems);
        selectedItems.clear();
        notifyDataSetChanged();
    }
}
