package com.gaurav.foodnepal.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.gaurav.foodnepal.model.SubCategory;
import com.gaurav.foodnepal.R;


import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.SubCategoryViewHolder> {

    private List<SubCategory> subCategoryList;

    public SubCategoryAdapter(List<SubCategory> subCategoryList) {
        this.subCategoryList = subCategoryList;
    }


    @NonNull
    @Override
    public SubCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sub_category_card, parent, false);

        return new SubCategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryViewHolder holder, int position) {

        SubCategory subCategory = subCategoryList.get(position);
        holder.name.setText(subCategory.getName());
        holder.imageView.setImageResource(subCategory.getImagePath());
    }

    @Override
    public int getItemCount() {
        return subCategoryList.size();
    }

    public class SubCategoryViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView imageView;


        public SubCategoryViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.subCategoryTitle);
            imageView = itemView.findViewById(R.id.subCategoryImage);
        }
    }
}
