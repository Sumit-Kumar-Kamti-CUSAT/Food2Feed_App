package com.rsn.food2feedo.ui.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rsn.food2feedo.R;
import com.rsn.food2feedo.Terms;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.zip.Inflater;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    Context context;
    List<FoodModel> foodModelList;

    public FoodAdapter(Context context, List<FoodModel> foodModelList) {
        this.context = context;
        this.foodModelList = foodModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycleview,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodModel fm=foodModelList.get(position);
        String imgurl=null;
        imgurl=fm.getImage();
        Picasso.get().load(imgurl).into(holder.img);
        holder.fname.setText("FoodName "+fm.getFoodName());
        holder.desc.setText("Descrpiton "+fm.getDescription());
    }

    @Override
    public int getItemCount() {
        return foodModelList.size();
    }

    public static  class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView fname,desc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.imgRec);
            fname=itemView.findViewById(R.id.name);
            desc=itemView.findViewById(R.id.descp);
        }
    }
}
