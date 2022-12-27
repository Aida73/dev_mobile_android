package sn.ept.devmobile.finalprojectandroid.adpters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;


import java.util.List;

import sn.ept.devmobile.finalprojectandroid.GSActivity;
import sn.ept.devmobile.finalprojectandroid.R;
import sn.ept.devmobile.finalprojectandroid.models.Cart;
import sn.ept.devmobile.finalprojectandroid.models.Product;
import sn.ept.devmobile.finalprojectandroid.services.CartServices;

public class RecycleCartAdapter extends RecyclerView.Adapter<RecycleCartAdapter.MyViewHolder>{

    private List<Cart> carts;
    private RecycleProductAdapter.OnItemClickListener listener;

    public RecycleCartAdapter(List<Cart> carts) {
        this.carts = carts;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.titleView.setText(carts.get(position).getProduct().getTitle());
        holder.priceView.setText(carts.get(position).getProduct().formatPrice());
        holder.editText.setText(carts.get(position).getQuantity()+"");
        Picasso.get().load(carts.get(position).getProduct().getImage()).into(holder.imageView);

        holder.deleteBtn.setOnClickListener(v -> {
            CartServices.deleteData(v.getContext(),carts.get(position));
            Toast.makeText(v.getContext(), "Produit supprimé du panier",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(v.getContext(), GSActivity.class);
            v.getContext().startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView titleView;
        TextView priceView;
        EditText editText;
        Button deleteBtn;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.cart_product_image);
            titleView = itemView.findViewById(R.id.cart_product_title);
            priceView = itemView.findViewById(R.id.cart_product_price);
            editText = itemView.findViewById(R.id.cart_product_qte);
            deleteBtn = itemView.findViewById(R.id.cart_btn_delete);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Product model);
    }

    public void setOnItemClickListener(RecycleProductAdapter.OnItemClickListener listener) {
        this.listener  = listener;
    }


}
