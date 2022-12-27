package sn.ept.devmobile.finalprojectandroid.adpters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import sn.ept.devmobile.finalprojectandroid.R;
import sn.ept.devmobile.finalprojectandroid.models.Product;
import sn.ept.devmobile.finalprojectandroid.services.CartServices;

public class RecycleProductAdapter extends RecyclerView.Adapter<RecycleProductAdapter.MyViewHolder> {
    List<Product> products;
    private OnItemClickListener listener;


    public RecycleProductAdapter(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public RecycleProductAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);
        

        return new MyViewHolder(view);
    }

    public static String getTruncTitle(String title){
        if(title.length()>20){
            return title.substring(0,20)+"...";
        }

        return title;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nameProductTextView.setText(getTruncTitle(products.get(position).getTitle()));
        holder.priceProductTextView.setText(products.get(position).formatPrice());
        Picasso.get().load(products.get(position).getImage()).into(holder.imageProductView);
        holder.addToCart.setOnClickListener(v -> {
            CartServices.saveData(v.getContext(), products.get(position));
            String message = getTruncTitle(products.get(position).getTitle());
            Toast.makeText(v.getContext(), message+" ajouté au panier",Toast.LENGTH_LONG).show();

        });

    }

    @Override
    public int getItemCount() {
        return this.products.size();
    }

     class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameProductTextView;
        TextView priceProductTextView;
        ImageView imageProductView;
        Button addToCart;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nameProductTextView = itemView.findViewById(R.id.product_name_item);
            imageProductView = itemView.findViewById(R.id.product_image_item);
            priceProductTextView = itemView.findViewById(R.id.product_price_item);
            addToCart = itemView.findViewById(R.id.button_add_to_cart);


            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(products.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Product model);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener  = listener;
    }


}
