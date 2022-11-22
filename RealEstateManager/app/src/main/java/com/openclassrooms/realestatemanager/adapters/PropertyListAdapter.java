package com.openclassrooms.realestatemanager.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.PropertyModel;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class PropertyListAdapter extends RecyclerView.Adapter<PropertyListAdapter.PropertyViewHolder> {

    //---------------
    // DATA - FIELDS
    //---------------
    /** Lists */
    private List<PropertyModel> propertiesList;
    /** Application */
    private final Context context;
    private final OnItemClickListener fItemClickListener;

    /** CONSTRUCTOR */
    public PropertyListAdapter(Context context, List<PropertyModel> propertiesList, OnItemClickListener clickListener) {
        this.propertiesList = propertiesList;
        this.context = context;
        this.fItemClickListener = clickListener;
    }

    //---------
    // ADAPTER
    //---------
    // 1 -- ON CREATE VIEW -->
    @NotNull
    @Override
    public PropertyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_property, parent, false);
        return new PropertyViewHolder(view);
    }

    // 2 -- ON BIND VIEW -->
    @Override
    public void onBindViewHolder(@NonNull @NotNull PropertyViewHolder holder, int position) {
        PropertyModel item = propertiesList.get(position);
        holder.bind(item, context);
        holder.itemView.setOnClickListener(pView -> fItemClickListener.onItemClick(item));
    }

    // 3 -- ITEM COUNT -->
    @Override
    public int getItemCount() {
        return propertiesList.size();
    }


    //------------------------------------
    // LIVE DATA OBSERVER : UPDATE METHOD
    //------------------------------------
    @SuppressLint("NotifyDataSetChanged")
    public void updateProperties(@NonNull List<PropertyModel> properties) {
        this.propertiesList = properties;
        notifyDataSetChanged();
    }

    //---------------------------
    // INNER CLASS - VIEW HOLDER
    //---------------------------
    public static class PropertyViewHolder extends RecyclerView.ViewHolder {

        //---------------
        // DATA - FIELDS
        //---------------
        /** Graphics */
        private final TextView property_name;
        private final TextView property_address;
        private final TextView property_cost;
        private final ImageView property_photo;
        private final ImageView property_availability;

        /** CONSTRUCTOR */
        public PropertyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            // 1 --:: Graphic binding ::--
            property_name = itemView.findViewById(R.id.prop_name);
            property_address = itemView.findViewById(R.id.prop_place);
            property_cost = itemView.findViewById(R.id.prop_cost);
            property_photo = itemView.findViewById(R.id.prop_photo);
            property_availability = itemView.findViewById(R.id.availability_icon);
        }

        // 2 --:: Code binding ::--
        @SuppressLint("SetTextI18n")
        public void bind(PropertyModel pPropertyModel, Context pContext) {
            property_name.setText(pPropertyModel.getName());
            property_address.setText(pPropertyModel.getAddress());
            property_cost.setText("" + pPropertyModel.getPrice() + " $");
            if (pPropertyModel.getPhotoProperty().size() == 0){
                Glide.with(pContext)
                        .load(R.drawable.long_island)
                        .into(property_photo);

            }else {
                Glide.with(pContext)
                        .load(pPropertyModel.getPhotoProperty().get(0))
                        .into(property_photo);
            }
            if (pPropertyModel.getStatus() != null){
                if (pPropertyModel.getStatus().matches("Available")){
                    property_availability.setColorFilter(itemView.getResources().getColor(R.color.green));
                } else if (pPropertyModel.getStatus().matches("")) {
                    property_availability.setColorFilter(itemView.getResources().getColor(R.color.chain_grey));
                }
            }
        }
    }

    //--------------------------------
    // INTERFACE - ITEM CLICK LISTENER
    //--------------------------------
    public interface OnItemClickListener {
        void onItemClick(PropertyModel property);
    }

}
