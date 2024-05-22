package com.example.RealEstateApp.Admin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.RealEstateApp.R;
import com.example.RealEstateApp.RealStateDatabase;
import com.example.RealEstateApp.models.Sales;

import java.util.ArrayList;

public class SalesAdminAdapter extends BaseAdapter {

    ArrayList<Sales> sales;
    Context context;
    RealStateDatabase db;

    public SalesAdminAdapter(ArrayList<Sales> sales, Context context, RealStateDatabase db) {
        this.sales = sales;
        this.context = context;
        this.db = db;
    }

    @Override
    public int getCount() {
        return sales.size();
    }

    @Override
    public Sales getItem(int i) {
        return sales.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint({"ResourceType", "SetTextI18n"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = view;
        if (v == null) {
            v = LayoutInflater.from(context).inflate(R.layout.custome_admin_sales, null, false);
        }

        ImageView img = (ImageView) v.findViewById(R.id.img_property_appointment);
        ImageView imgDelete = (ImageView) v.findViewById(R.id.img_delete);
        ImageView imgDone = (ImageView) v.findViewById(R.id.img_accept);
        imgDone.setImageResource(R.drawable.correct);
        imgDelete.setImageResource(R.drawable.delete);
        TextView tv_name = v.findViewById(R.id.tv_name_property);
        TextView tv_price = v.findViewById(R.id.tv_price_property);
        TextView tv_type = v.findViewById(R.id.tv_type_property);
        TextView tv_dateApointment = v.findViewById(R.id.tv_date_appointment_property);
        TextView tv_paymentType = v.findViewById(R.id.tv_payement_sales_property);
        TextView tv_commission = v.findViewById(R.id.tv_commission_sales_property);
        TextView tv_status = v.findViewById(R.id.tv_status_sales_property);

        Sales sales = getItem(i);
        if (Integer.parseInt(sales.getProperty().getImage()) != 0) {
            img.setImageResource(Integer.parseInt(sales.getProperty().getImage()));
        } else {
            img.setImageResource(R.drawable.products);
        }
        tv_name.setText(sales.getProperty().getName());
        tv_price.setText(sales.getProperty().getPrice() + " DT");
        tv_type.setText(sales.getProperty().getType());
        tv_dateApointment.setText(sales.getDate());
        tv_paymentType.setText("payment: " + sales.getPayementMethod());
        tv_commission.setText("commission :" + sales.getCommission() + " DT");
        tv_status.setText("STATUS :" + sales.getStatus());
        if (sales.getStatus().equals("accepted")) {
            imgDelete.setVisibility(View.GONE);
            imgDone.setVisibility(View.GONE);
        }
        int id = sales.getId();
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
                db.updateSaleStatusToRejected(id);
                notifyDataSetChanged();
            }
        });

        imgDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
                db.updateSaleStatusToAccepted(sales.getId());
                notifyDataSetChanged();

            }
        });
        return v;
    }
}
