package com.openclassrooms.realestatemanager.adapters;

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
import com.openclassrooms.realestatemanager.model.DummyListCallback;
import com.openclassrooms.realestatemanager.model.InterestModel;
import com.openclassrooms.realestatemanager.model.PropertyModel;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class InterestListAdapter extends RecyclerView.Adapter<InterestListAdapter.InterestViewHolder> {

    //---------------
    // DATA - FIELDS
    //---------------
    /** Lists */
    private static List<String> interestList;

    /** CONSTRUCTOR */
    public InterestListAdapter(PropertyModel property) {
        interestList = property.getPropertyInterest();
    }

    //---------
    // ADAPTER
    //---------
    // 1 -- ON CREATE VIEW -->
    @NotNull
    @Override
    public InterestListAdapter.InterestViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_interest_row, parent, false);
        return new InterestListAdapter.InterestViewHolder(view);
    }

    // 2 -- ON BIND VIEW -->
    @Override
    public void onBindViewHolder(@NonNull @NotNull InterestListAdapter.InterestViewHolder holder, int position) {
        String interest = interestList.get(position);
        holder.bind(interest, holder.itemView.getContext());
    }

    // 3 -- ITEM COUNT -->
    @Override
    public int getItemCount() {
        return interestList.size();
    }

    //---------------------------
    // INNER CLASS - VIEW HOLDER
    //---------------------------
    public static class InterestViewHolder extends RecyclerView.ViewHolder {

        //---------------
        // DATA - FIELDS
        //---------------
        /** Data */
        private int iconData;

        /** Graphics */
        private final TextView name;
        private final ImageView icon;

        /** CONSTRUCTOR */
        public InterestViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            // 1 --:: Graphic binding ::--
            name = itemView.findViewById(R.id.interest_name);
            icon = itemView.findViewById(R.id.interest_icon);
        }

        // 2 --:: Code binding ::--
        public void bind(String interestModel, Context pContext) {
            name.setText(interestModel);
            getGoodIcon(interestModel);
            Glide.with(pContext)
                    .load(iconData)
                    .into(icon);
        }

        // 3 --:: Get right icon for interest  ::--
        public void getGoodIcon(String interestModel) {
            List<InterestModel> interestsList = DummyListCallback.getInterestList();
            try {
                for(int i = 0; i<interestsList.size(); i++) {
                    if (interestModel.equals(interestsList.get(i).getName())) {
                        iconData = interestsList.get(i).getIcon();
                        return;
                    }
                }
            } catch (Exception pException) {
                pException.printStackTrace();
            }
        }
    }
}
