package com.openclassrooms.realestatemanager.adapters;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
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
    private final List<String> photosDescription;
    private final Context context;

    /** CONSTRUCTOR */
    public AddPhotoAdapter(List<String> photosList, List<String> photosDescription, Context pContext) {
        this.photosList = photosList;
        this.photosDescription = photosDescription;
        this.context = pContext;
    }

    //---------
    // ADAPTER
    //---------
    // 1 -- ON CREATE VIEW -->
    @Override @NotNull
    @NonNull
    public AddPhotoAdapter.AddPhotoViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_item_photo, parent, false);
        return new AddPhotoAdapter.AddPhotoViewHolder(view);
    }

    // 2 -- ON BIND VIEW -->
    @Override
    public void onBindViewHolder(@NonNull @NotNull AddPhotoAdapter.AddPhotoViewHolder holder, int position) {
        holder.setItemView(photosList.get(position), photosDescription.get(position), holder.itemView.getContext());
        holder.photo_description_textview.setOnClickListener(pView -> {
            Dialog dialog = new Dialog(context);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.dialog_photo_description);

            EditText enterTitle = dialog.findViewById(R.id.enter_title_description);
            Button okBtn = dialog.findViewById(R.id.validate_description);

            enterTitle.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence pCharSequence, int pI, int pI1, int pI2) {
                }
                @Override
                public void onTextChanged(CharSequence pCharSequence, int pI, int pI1, int pI2) {
                }
                @Override
                public void afterTextChanged(Editable pEditable) {
                    int stringSize = pEditable.toString().length();
                    int limit = 25;
                    if (stringSize > limit){
                        enterTitle.setError("Too much longer, < 25 characters");
                    } else{
                        okBtn.setOnClickListener(pView1 -> {
                            String description = "";
                            if (!enterTitle.getText().toString().equals("")){
                                description = enterTitle.getText().toString();
                            } else {
                                Toast.makeText(context, "Please give a name to your picture !", Toast.LENGTH_SHORT).show();
                            }
                            holder.photo_description_textview.setText(enterTitle.getText());
                            photosDescription.set(holder.getAbsoluteAdapterPosition(), description);
                            notifyItemChanged(holder.getAbsoluteAdapterPosition());
                            dialog.dismiss();
                        });
                    }
                }
            });
            dialog.show();
        }
        );
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
        ImageView added_photo;
        TextView photo_description_textview;

        /** CONSTRUCTOR */
        public AddPhotoViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            added_photo = itemView.findViewById(R.id.added_photo);
            photo_description_textview = itemView.findViewById(R.id.photo_description);
        }

        // -- Set image to view -->
        void setItemView(String imageUrl,String description, Context pContext) {
            photo_description_textview.setText(description);
            try {
                Uri uri = Uri.parse(imageUrl);
                Glide.with(pContext).load(uri).into(added_photo);
            } catch (Exception pException) {
                pException.printStackTrace();
            }
        }
    }
}
