package com.example.eio.ViewHolder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eio.R;
import com.example.eio.Models.RentModel;

import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RentViewHolder> {

    List<RentModel> list;
    Context context;

    public RecyclerAdapter(List<RentModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.rent_row_item, parent, false);
        RentViewHolder rentViewHolder = new RentViewHolder(view);
        return rentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RentViewHolder holder, int position) {
        RentModel rentModel = list.get(position);
        holder.advance_value.setText(Integer.toString(rentModel.getAdvance()));
        holder.rent_value.setText(Integer.toString(rentModel.getRent()));
        holder.place_text.setText(rentModel.getCity() + ", " + rentModel.getDistrict());
        holder.posted_on_text.setText(rentModel.getPosted_on().toString());
    }

    @Override
    public int getItemCount() {
        if(this.list != null) {
            return list.size();
        }
        return 0;
    }
}



class RentViewHolder extends RecyclerView.ViewHolder {

    TextView advance_value, rent_value;
    TextView place_text, posted_on_text;

    public RentViewHolder(View itemView) {
        super(itemView);
        advance_value = itemView.findViewById(R.id.advance);
        rent_value = itemView.findViewById(R.id.rent);
        place_text = itemView.findViewById(R.id.place);
        posted_on_text = itemView.findViewById(R.id.posted_on);
    }
}


