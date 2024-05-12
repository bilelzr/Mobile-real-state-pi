package com.example.RealEstateApp;

import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder> {

    ArrayList<Property> products;
    OnRecyclerViewClickListener listener;

    public PropertyAdapter(ArrayList<Property> products, OnRecyclerViewClickListener listener) {
        this.products = products;
        this.listener = listener;
    }

    public ArrayList<Property> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Property> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public PropertyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custome_card_products, null, false);
        PropertyViewHolder pvh = new PropertyViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyViewHolder holder, int position) {
        Property p = products.get(position);
        if (p.getImage() != 0) {
            holder.img.setImageResource(p.getImage());
        } else {
            holder.img.setImageResource(R.drawable.products);
        }
        holder.name.setText(p.getName());
        holder.price.setText(p.getPrice() + "$");
        holder.brand.setText("Location: " + p.getLocation());
        if (p.getDiscount() > 0) {
            holder.priceAfter.setText(p.getPrice() - (p.getPrice() * (p.getDiscount() / 100)) + "$");
            holder.price.setPaintFlags(holder.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);//وضع خط علي السعر القديم
            holder.price.setTextColor(Color.parseColor("#BFBFBF"));
        } else {
            holder.priceAfter.setText("");
            holder.price.setTextColor(Color.parseColor("#000000"));
        }

        holder.name.setTag(position + 1); //اوبجكت مخفي لكي اخزن product_id
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class PropertyViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView name, price, brand, priceAfter;

        public PropertyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.iv_card_products);
            name = itemView.findViewById(R.id.tv_name_card_products);
            price = itemView.findViewById(R.id.tv_price_card_products);
            brand = itemView.findViewById(R.id.tv_brand_card_products);
            priceAfter = itemView.findViewById(R.id.tv_priceafter_card_products);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = (int) name.getTag();
                    listener.OnItemClick(id);
                }
            });

        }
    }

}
