package com.openclassrooms.realestatemanager.MVVM.repositories.firebase;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;
import com.openclassrooms.realestatemanager.model.PropertyModel;
import java.util.ArrayList;
import java.util.List;

public class FirebasePropertyHelper {

    //-------------
    // QUERY - GET
    //-------------
    // -- QUERY :: GET PROPERTIES COLLECTION -->
    public CollectionReference getPropertiesCollection() {
        String varPROPERTIES = "Properties";
        return FirebaseFirestore.getInstance().collection(varPROPERTIES);
    }

    // -- QUERY :: GET ALL PROPERTIES IN FIREBASE DATABASE -->
    public LiveData<List<PropertyModel>> getPropertiesData() {
        MutableLiveData<List<PropertyModel>> mutablePropertiesList = new MutableLiveData<>();
        FirebaseFirestore.getInstance().collection("Properties").get().addOnSuccessListener(pQueryDocumentSnapshots -> {
            Log.d("FIRESTORE DATA", "FirebaseFirestore.getInstance().collection('Properties').get() called with SUCCESS!");

            if (!pQueryDocumentSnapshots.isEmpty()) {
                List<PropertyModel> varPropertyModels = new ArrayList<>();
                for (QueryDocumentSnapshot propertyDocument : pQueryDocumentSnapshots) {
                    PropertyModel property = propertyDocument.toObject(PropertyModel.class);
                    Log.d("FIRESTORE DATA", "FirebaseFirestore.getInstance().collection('Properties').get() called, property = " + property);
                    varPropertyModels.add(property);
                }
                mutablePropertiesList.setValue(varPropertyModels);
                Log.d("FIRESTORE DATA", "Mutable List Data contains : " + mutablePropertiesList.getValue());
            }
        });
        Log.d("FIRESTORE DATA", "MUTABLE LIVE DATA : has been called  " + mutablePropertiesList.getValue());
        return mutablePropertiesList;
    }

    //---------------------------
    // CREATE - UPDATE - DELETE
    //---------------------------
    // -- CREATE :: PROPERTY IN FIREBASE DATABASE -->
    public Task<Void> createProperty(String propertyId, PropertyModel property) {
        return getPropertiesCollection().document(propertyId).set(property);
    }
    // -- UPDATE :: PROPERTY IN FIREBASE DATABASE -->
    public Task<Void> updateProperty(String propertyId, PropertyModel property) {
        return getPropertiesCollection().document(propertyId).set(property, SetOptions.merge());
    }
    // -- DELETE :: PROPERTY FROM FIREBASE DATABASE -->
    public Task<Void> deleteProperty(String propertyId) {
        return getPropertiesCollection().document(propertyId).delete();
    }
}
