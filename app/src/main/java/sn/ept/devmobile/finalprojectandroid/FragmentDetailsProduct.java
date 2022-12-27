package sn.ept.devmobile.finalprojectandroid;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sn.ept.devmobile.finalprojectandroid.adpters.RecycleProductAdapter;
import sn.ept.devmobile.finalprojectandroid.api.ApiConfig;
import sn.ept.devmobile.finalprojectandroid.models.Product;
import sn.ept.devmobile.finalprojectandroid.services.CartServices;


public class FragmentDetailsProduct extends Fragment {

    private Product product;
    TextView titleView;
    TextView categoryView;
    TextView descriptionView;
    TextView priceView;
    ImageView imageView;
    EditText editText;
    Button addToCart;



    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_details_product, container, false);

        titleView = view.findViewById(R.id.product_detail_title);
        categoryView = view.findViewById(R.id.product_detail_category);
        descriptionView = view.findViewById(R.id.product_detail_description);
        priceView = view.findViewById(R.id.product_detail_price);
        imageView = view.findViewById(R.id.product_detail_image);
        editText = view.findViewById(R.id.editTextNumber);
        editText.setText("1");
        addToCart = view.findViewById(R.id.product_detail_button_addToCart);
        addToCart.setOnClickListener(v -> {
            CartServices.saveData(v.getContext(), product);
            String message = RecycleProductAdapter.getTruncTitle(product.getTitle());
            Toast.makeText(v.getContext(), message+" ajouté au panier",Toast.LENGTH_LONG).show();

        });

        Bundle extras = getArguments();
        getProduct(extras.getLong("id"));
        return view;
    }


    public void getProduct(Long id){

        ApiConfig.getApiClient().getProduct(id).enqueue(new Callback<Product>(){
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {

                if(response.isSuccessful()){
                    Log.i("Getting Product", String.valueOf(response.code()));
                    setProduct(response.body());
                    setContentViews();

                }
                else{
                    Log.i("indo",String.valueOf(response.code()));
                    return;
                }
            }
            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.e("Getting products", t.getMessage());
            }
        });

    }

    public void setContentViews(){

        titleView.setText(product.getTitle());
        categoryView.setText(product.getCategory());
        descriptionView.setText(product.getDescription());
        priceView.setText(product.formatPrice());
        Picasso.get().load(product.getImage()).into(imageView);
    }




}