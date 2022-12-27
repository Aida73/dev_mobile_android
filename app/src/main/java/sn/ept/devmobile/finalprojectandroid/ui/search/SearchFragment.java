package sn.ept.devmobile.finalprojectandroid.ui.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sn.ept.devmobile.finalprojectandroid.FragmentDetailsProduct;
import sn.ept.devmobile.finalprojectandroid.R;
import sn.ept.devmobile.finalprojectandroid.adpters.ListViewAdapter;
import sn.ept.devmobile.finalprojectandroid.adpters.RecycleProductAdapter;
import sn.ept.devmobile.finalprojectandroid.api.ApiConfig;
import sn.ept.devmobile.finalprojectandroid.models.Product;

public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener{

    RecycleProductAdapter recycleProductAdapter;
    RecyclerView recyclerView;
    ListView listView;
    ListViewAdapter adapter;
    SearchView editSearch;
    List<String> categories = new ArrayList<>();
    List<Product> products;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_search, container, false);

        recyclerView = view.findViewById(R.id.search_recycle_view);
        listView = view.findViewById(R.id.listview);
        getCategories(view);

        editSearch = view.findViewById(R.id.simple_search_view);
        editSearch.setQueryHint("Recherchez les catégories qui vous interesse....");
        editSearch.setOnQueryTextListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View myView, int position, long id) {
                getProductsByCategory(categories.get(position),view);
                Toast.makeText(view.getContext(),categories.get((position)),Toast.LENGTH_SHORT).show();
                Log.i("Getting Category products", categories.get(position));
            }
        });
        return view;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }


    public void getCategories(View view){

        ApiConfig.getApiClient().getAllCategories().enqueue(new Callback<List<String>>(){
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(response.isSuccessful()){
                    Log.i("Getting Categories", String.valueOf(response.code()));
                    setCategories(response.body());
                    setListViewAdapter(view);
                }
                else{
                    Log.i("indo",String.valueOf(response.code()));
                    return;
                }
            }
            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e("Getting categories", t.getMessage());
            }
        });

    }

    public void setListViewAdapter(View view){
        adapter = new ListViewAdapter(view.getContext(),categories);
        listView.setAdapter(adapter);
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void getProductsByCategory(String category,View view){

        ApiConfig.getApiClient().getProductsByCategory(category).enqueue(new Callback<List<Product>>(){
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful()){
                    Log.i("Getting Category Products", String.valueOf(response.code()));
                    setProducts(response.body());
                    setRecyclerView(view);

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

    public void setRecyclerView(@NonNull View view){
        recycleProductAdapter = new RecycleProductAdapter(products);
        //RecyclerView recyclerView = view.findViewById(R.id.search_recycle_view);
        recyclerView.setAdapter(recycleProductAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));

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




    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.filter(text);
        return false;
    }
}
