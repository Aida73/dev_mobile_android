package sn.ept.devmobile.finalprojectandroid.ui.home;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sn.ept.devmobile.finalprojectandroid.FragmentDetailsProduct;
import sn.ept.devmobile.finalprojectandroid.R;
import sn.ept.devmobile.finalprojectandroid.adpters.RecycleProductAdapter;
import sn.ept.devmobile.finalprojectandroid.api.ApiConfig;
import sn.ept.devmobile.finalprojectandroid.models.Product;


public class FragmentHomeProduct extends Fragment {


    private List<Product> products = new ArrayList<>();
    TextView title;
    Button addToCart;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View myView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_home_product, container, false);
        title = myView.findViewById(R.id.product_name_item);
        addToCart = myView.findViewById(R.id.button_add_to_cart);


        getProducts();
        return myView;

    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void getProducts(){

        ApiConfig.getApiClient().getAllProducts().enqueue(new Callback<List<Product>>(){
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful()){
                    Log.i("Getting Products", String.valueOf(response.code()));
                    setProducts(response.body());
                    setRecyclerView();
                }
                else{
                    Log.i("indo",String.valueOf(response.code()));
                    return;
                }
            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("Getting products", t.getMessage());
            }
        });

    }

    public void setRecyclerView(){
        RecycleProductAdapter recycleProductAdapter = new RecycleProductAdapter(products);
        RecyclerView recyclerView = getView().findViewById(R.id.product_list_recycler);
        recyclerView.setAdapter(recycleProductAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));

        recycleProductAdapter.setOnItemClickListener(product -> {
            Bundle data = new Bundle();
            data.putLong("id",product.getId());
            FragmentDetailsProduct fragmentDetailsProduct = new FragmentDetailsProduct();
            fragmentDetailsProduct.setArguments(data);
            FragmentManager fm = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout_home, fragmentDetailsProduct);
            fragmentTransaction.commit();
        });
}
}