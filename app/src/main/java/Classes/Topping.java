package Classes;

import android.media.Image;

import androidx.annotation.NonNull;

import com.example.rupizzeria.R;

import android.net.Uri;

/**enum class holding all possible toppings available at the pizzeria
 @author Jasmine Justin
 @author Jimena Reyes*/
public enum Topping {
    /**anchovy topping*/
    ANCHOVY(R.drawable.anchovy){
    },
    /**bbq chicken topping*/
    BBQ_CHICKEN(R.drawable.bbq_chicken),
    /**beef topping*/
    BEEF(R.drawable.beef),
    /**cheddar topping*/
    CHEDDAR(R.drawable.cheddar),
    /**green pepper topping*/
    GREEN_PEPPER(R.drawable.green_pepper),
    /**ham topping*/
    HAM(R.drawable.ham),
    /**mushroom topping*/
    MUSHROOM(R.drawable.mushroom),
    /**olive topping*/
    OLIVE(R.drawable.olive),
    /**onion topping*/
    ONION(R.drawable.red_onion),
    /**pepperoni topping*/
    PEPPERONI(R.drawable.pepperoni),
    /**provolone topping*/
    PROVOLONE(R.drawable.provolone),
    /**pineapple topping*/
    PINEAPPLE(R.drawable.pineapple),
    /**sausage topping*/
    SAUSAGE(R.drawable.sausage);

    // Instance field to hold the image file path (or a URL or resource ID)
    //private int imagePath;

    private int itemUri;

    // Constructor for the enum constants
    Topping(int image) {
        this.itemUri = image;
    }

    // Getter for the image path
    /*public int getImagePath() {
        return imagePath;
    }*/

    // Getter for Uri
    public int getImage() {
        return itemUri;
    }

    /**returns the string for of the topping
     * @return string form enum*/
    @NonNull
    @Override
    public String toString() {
        return this.name();
    }
}
