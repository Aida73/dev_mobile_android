package sn.ept.devmobile.finalprojectandroid.ui.cart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

import sn.ept.devmobile.finalprojectandroid.GSActivity;
import sn.ept.devmobile.finalprojectandroid.R;
import sn.ept.devmobile.finalprojectandroid.adpters.RecycleCartAdapter;
import sn.ept.devmobile.finalprojectandroid.models.Cart;
import sn.ept.devmobile.finalprojectandroid.services.CartServices;

public class CartHomeFragment extends Fragment {

    private static final DecimalFormat decimalformat = new DecimalFormat("0.00");




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_cart_home, container, false);
        TextView messageView = view.findViewById(R.id.cart_message);
        TextView totalView = view.findViewById(R.id.cart_total);
        Button deleteAll = view.findViewById(R.id.cart_delete_all);
        Button validate = view.findViewById(R.id.cart_validate);



        List<Cart> carts = CartServices.loadData(view.getContext());

        Double total = carts.stream().map(cart -> cart.getProduct().getPrice()).reduce((double) 0,(subtotal, element) -> subtotal + element);
        totalView.setText("Total: "+decimalformat.format(total*655)+"FCFA");
        if(carts.isEmpty()){
            messageView.setText("Vous n'avez selectionné aucun article !");
        }
        setRecycleView(view,carts);

        deleteAll.setOnClickListener(v -> {
            CartServices.clearCarts(v.getContext());
            setRecycleView(view,carts);
            Toast.makeText(v.getContext(), "Vous avez vidé votre Panier",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getActivity().getApplication(), GSActivity.class);
            startActivity(intent);

        });

        validate.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Cette fonctionnalité sera bientôt développée!",Toast.LENGTH_LONG).show();

        });

        




        return view;

    }

    public void setRecycleView(View view,List<Cart> carts){
        RecyclerView recyclerView = view.findViewById(R.id.cart_recycle_view);
        RecycleCartAdapter recycleCartAdapter = new RecycleCartAdapter(carts);
        recyclerView.setAdapter(recycleCartAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }




}
