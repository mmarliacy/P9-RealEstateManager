package com.openclassrooms.realestatemanager.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.R;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class AddPhotoAdapter extends RecyclerView.Adapter<AddPhotoAdapter.AddPhotoViewHolder> {

    //---------------
    // DATA - FIELDS
    //---------------
    /** List */
    private final List<String> photosList;

    /** CONSTRUCTOR */
    public AddPhotoAdapter(List<String> photosList) {
        this.photosList = photosList;
    }

    //---------
    // ADAPTER
    //---------
    // 1 -- ON CREATE VIEW -->
    @Override @NotNull
    @NonNull
    public AddPhotoAdapter.AddPhotoViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);
        return new AddPhotoAdapter.AddPhotoViewHolder(view);
    }

    // 2 -- ON BIND VIEW -->
    @Override
    public void onBindViewHolder(@NonNull @NotNull AddPhotoAdapter.AddPhotoViewHolder holder, int position) {
        holder.setImageView(photosList.get(position),holder.itemView.getContext());
    }

    // 3 -- ITEM COUNT -->
    @Override
    public int getItemCount() {
        return photosList.size();
    }

    //---------------------------
    // INNER CLASS - VIEW HOLDER
    //---------------------------
    public static class AddPhotoViewHolder extends RecyclerView.ViewHolder {

        //---------------
        // DATA - FIELDS
        //---------------
        /** Graphics */
        ImageView fImageView;

        /** CONSTRUCTOR */
        public AddPhotoViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            fImageView = itemView.findViewById(R.id.added_photo);
        }

        // -- Set image to view -->
        void setImageView(String imageUrl, Context pContext) {
            try{
                Uri uri = Uri.parse(imageUrl);
                Glide.with(pContext).load(uri).into(fImageView);
            } catch (Exception pException) {
                pException.printStackTrace();
            }
        }
    }
}
