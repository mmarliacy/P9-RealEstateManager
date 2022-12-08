package com.openclassrooms.realestatemanager.MVVM.repositories.firebase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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
        List<PropertyModel> varPropertyModels = new ArrayList<>();
        FirebaseFirestore.getInstance().collection("Properties").get().addOnSuccessListener(pQueryDocumentSnapshots -> {
            varPropertyModels.clear();
            if (!pQueryDocumentSnapshots.isEmpty()) {
                for (QueryDocumentSnapshot propertyDocument : pQueryDocumentSnapshots) {
                    PropertyModel property = propertyDocument.toObject(PropertyModel.class);
                    varPropertyModels.add(property);
                }
                mutablePropertiesList.setValue(varPropertyModels);
            }
        });
        return mutablePropertiesList;
    }

    //---------------------------
    // CREATE - UPDATE - DELETE
    //---------------------------
    // -- CREATE :: PROPERTY IN FIREBASE DATABASE -->
    public void createProperty(PropertyModel property) {
        getPropertiesCollection().document().set(property);
    }

    // -- UPDATE :: PROPERTY IN FIREBASE DATABASE -->
    public void updateProperty(String propertyName, PropertyModel pPropertyModel) {
        FirebaseFirestore.getInstance().collection("Properties").get().addOnSuccessListener(pQueryDocumentSnapshots -> {
            if (!pQueryDocumentSnapshots.isEmpty()) {
                for (QueryDocumentSnapshot propertyDocument : pQueryDocumentSnapshots) {
                    PropertyModel propertyModel = propertyDocument.toObject(PropertyModel.class);
                    if(propertyModel.getName().equals(propertyName)){
                        getPropertiesCollection().document(propertyDocument.getId()).set(pPropertyModel, SetOptions.merge());
                    }
                }
            }
        });
    }
}