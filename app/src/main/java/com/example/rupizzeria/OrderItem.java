package com.example.rupizzeria;

import java.util.List;

public class OrderItem {

    private String pizzaStyle;
    private String pizzaType;
    private String crustType;
    private String size;
    private List<String> toppings;
    private double price;

    public OrderItem(String pizzaStyle, String pizzaType, String crustType, String size, double price, List<String> toppings) {
        this.pizzaStyle = pizzaStyle;
        this.pizzaType = pizzaType;
        this.crustType = crustType;
        this.size = size;
        this.toppings = toppings;
        this.price = price;
    }

    public String getPizzaStyle() {
        return pizzaStyle;
    }

    public String getPizzaType() {
        return pizzaType;
    }

    public String getCrustType() {
        return crustType;
    }

    public String getSize() {
        return size;
    }

    public List<String> getToppings() {
        return toppings;
    }

    public double getPrice() {
        return price;
    }
}

