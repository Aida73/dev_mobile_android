package sn.ept.devmobile.finalprojectandroid.services;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import sn.ept.devmobile.finalprojectandroid.models.Cart;
import sn.ept.devmobile.finalprojectandroid.models.Product;

public class CartServices {

    public static List<Cart> loadData(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", context.MODE_PRIVATE);

        Gson gson = new Gson();

        String json = sharedPreferences.getString("carts", null);

        Type type = new TypeToken<List<Cart>>() {}.getType();

        List<Cart> carts = gson.fromJson(json, type);

        if (carts == null) {
            carts = new ArrayList<>();
        }

        return carts;
    }

    public static void saveData(Context context, Product product) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", context.MODE_PRIVATE);

        List<Cart> carts = loadData(context);

        if (carts.stream().map(cart -> cart.getProduct()).filter(p -> p.getId() == product.getId()).collect(Collectors.toList()).isEmpty()) {
            carts.add(new Cart(product, 1L));
        } else {
            for (Cart c : carts) {
                if (c.getProduct().getId() == product.getId()) {
                    c.setQuantity(c.getQuantity() + 1);
                }
            }
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(carts);
        editor.putString("carts", json);

        editor.apply();
    }

    public static void deleteData(Context context, Cart cart) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", context.MODE_PRIVATE);

        List<Cart> carts = loadData(context);
        List<Cart> newCarts = carts.stream().filter(c -> c.getProduct().getId()!=cart.getProduct().getId()).collect(Collectors.toList());

        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(newCarts);
        editor.putString("carts", json);

        editor.apply();
    }

    public static void clearCarts(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", context.MODE_PRIVATE);


        List<Cart> carts=new ArrayList<>();

        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(carts);
        editor.putString("carts", json);

        editor.apply();
    }


}
