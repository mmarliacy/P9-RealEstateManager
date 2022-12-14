package com.openclassrooms.realestatemanager.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.R;

import org.jetbrains.annotations.NotNull;
import java.util.List;

public class PhotoListAdapter  extends RecyclerView.Adapter<PhotoListAdapter.PhotoListViewHolder>{

    //---------------
    // DATA - FIELDS
    //---------------
    /** List */
    List<String> photosList;
    List<String> descriptionList;
    /** Graphics */
    ViewPager2 fViewPager2;

    /** CONSTRUCTOR */
    public PhotoListAdapter(List<String> photosList, List<String> descriptionList, ViewPager2 fViewPager2) {
        this.photosList = photosList;
        this.descriptionList = descriptionList;
        this.fViewPager2 = fViewPager2;
    }

    //---------
    // ADAPTER
    //---------
    // 1 -- ON CREATE VIEW -->
    @Override @NotNull @NonNull
    public PhotoListViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_row, parent, false);
        return new PhotoListViewHolder(view);
    }

    // 2 -- ON BIND VIEW -->
    @Override
    public void onBindViewHolder(@NonNull @NotNull PhotoListViewHolder holder, int position) {
                holder.setViewPagerContainer(
                        photosList.get(position),
                        descriptionList.get(position),
                        holder.itemView.getContext());
    }

    // 3 -- ITEM COUNT -->
    @Override
    public int getItemCount() {
        return Math.min(photosList.size(), 5);
    }


    //---------------------------
    // INNER CLASS - VIEW HOLDER
    //---------------------------
    public static class PhotoListViewHolder extends RecyclerView.ViewHolder {

        //---------------
        // DATA - FIELDS
        //---------------
        /** Graphics */
        ImageView fImageView;
        TextView fTextView;

        /** CONSTRUCTOR */
        public PhotoListViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            fImageView = itemView.findViewById(R.id.view_pager_photo_container);
            fTextView = itemView.findViewById(R.id.view_pager_description_container);
        }

        // -- Set image to view -->
        void setViewPagerContainer(String imageUrl, String description, Context pContext) {
            fTextView.setText(description);
            try{
                Uri uri = Uri.parse(imageUrl);
                Glide.with(pContext).load(uri).into(fImageView);
            } catch (Exception pException) {
                pException.printStackTrace();
            }
        }
    }
}
