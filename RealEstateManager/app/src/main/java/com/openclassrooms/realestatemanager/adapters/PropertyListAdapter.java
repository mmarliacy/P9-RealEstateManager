package com.openclassrooms.realestatemanager.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.PropertyModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PropertyListAdapter extends RecyclerView.Adapter<PropertyListAdapter.PropertyViewHolder> {

    //---------------
    // DATA - FIELDS
    //---------------

    /** Lists */
    List<PropertyModel> propertiesList;
    /** Application */
    Context context;

    /** CONSTRUCTOR */
    public PropertyListAdapter(Context context, List<PropertyModel> propertiesList) {
        this.propertiesList = propertiesList;
        this.context = context;
    }

    //----------
    //  ADAPTER
    //----------
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
        holder.bind(item);
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
    // INNER CLASS - VIEW HOLDER (((--:: /!\ Missing graphics data ::--)))
    //---------------------------
    public static class PropertyViewHolder extends RecyclerView.ViewHolder {

        //---------------
        // DATA - FIELDS
        //---------------
        /** Graphics */
        private final TextView property_name;
        private final TextView property_address;
        private final TextView property_cost;

        //--:: /!\ Need to be connected ::--
       // private final ImageView property_photo;
       //private ImageView property_availability;



        /** CONSTRUCTOR */
        public PropertyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            // 1 --:: Graphic binding ::--
            property_name = itemView.findViewById(R.id.prop_name);
            property_address = itemView.findViewById(R.id.prop_place);
            property_cost = itemView.findViewById(R.id.prop_cost);
            //--:: /!\ Need to be connected ::--
            //property_photo = itemView.findViewById(R.id.prop_photo);
        }

        // 2 --:: Code binding ::--
        public void bind(PropertyModel pPropertyModel) {
            property_name.setText(pPropertyModel.getName());
            property_address.setText(pPropertyModel.getAddress());
            property_cost.setText(pPropertyModel.getType());
        }
    }

}
