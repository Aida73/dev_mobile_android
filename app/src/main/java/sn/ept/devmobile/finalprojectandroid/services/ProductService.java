package sn.ept.devmobile.finalprojectandroid.services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import sn.ept.devmobile.finalprojectandroid.models.Product;

public interface ProductService {



    @GET("/products/category/{category}")
    Call<List<Product>> getProductsByCategory(@Path("category") String category);

    @GET("/products/categories")
    Call<List<String>> getAllCategories();

    @GET("/products/{id}")
    Call<Product> getProduct(@Path("id") Long id);

    @GET("/products")
    public Call<List<Product>> getAllProducts();
}
