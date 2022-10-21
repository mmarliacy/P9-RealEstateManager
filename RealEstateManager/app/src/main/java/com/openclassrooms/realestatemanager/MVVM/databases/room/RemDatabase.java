package com.openclassrooms.realestatemanager.MVVM.databases.room;

import static com.openclassrooms.realestatemanager.model.DummyListCallback.getDummyProperties;
import static com.openclassrooms.realestatemanager.model.DummyListCallback.getDummyUsers;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.openclassrooms.realestatemanager.MVVM.databases.room.DAO.PropertyDAO;
import com.openclassrooms.realestatemanager.MVVM.databases.room.DAO.UserDAO;
import com.openclassrooms.realestatemanager.model.PropertyModel;
import com.openclassrooms.realestatemanager.model.UserModel;
import org.jetbrains.annotations.NotNull;
import java.util.List;

@Database(entities = {UserModel.class, PropertyModel.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class RemDatabase extends RoomDatabase {

    // 1 -- DATABASE INSTANCE -->
    private static volatile RemDatabase instance;

    // 2 -- DAO -->
    public abstract PropertyDAO propertyDAO();
    public abstract UserDAO userDAO();


    // 3 -- SINGLETON PATTERN -->
    public static RemDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (RemDatabase.class) {
                instance = Room.databaseBuilder(
                                context.getApplicationContext(),
                                RemDatabase.class,
                                "database").
                        addCallback(propertiesCallBack()).
                        fallbackToDestructiveMigration().
                        build();
            }
        }
        return instance;
    }

    // 4 -- Create Callback -->
    private static Callback propertiesCallBack() {
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                //--:: /!\ Prepopulate methods don't work in onCreate method ::--
                Log.d("DATABASE : " ,"prepopulateDatabase(db) has been called successfully ");
            }

            @Override
            public void onOpen(@NonNull @NotNull SupportSQLiteDatabase db) {
                super.onOpen(db);
                prepopulateDatabaseWithUsers(db);
                prepopulateDatabase(db);
                Log.i("CALLBACK : ", "CALLBACK has been called");
            }
        };
    }

    // 5 -- Insert Content Values in table : DUMMY_USERS -->
    private static void prepopulateDatabaseWithUsers(SupportSQLiteDatabase db){
        List<UserModel> usersList = getDummyUsers();
        for (UserModel user : usersList){
            ContentValues usersValues = new ContentValues();
            usersValues.put("id", user.getId());
            usersValues.put("name", user.getName());
            usersValues.put("mail", user.getMail());
            usersValues.put("photo", user.getPhoto());
            db.insert("user_table", OnConflictStrategy.IGNORE, usersValues);
        }
    }

    // 6 -- Insert Content Values in table : DUMMY_PROPERTIES -->
    private static void prepopulateDatabase(SupportSQLiteDatabase db) {
        List<PropertyModel> propertyList = getDummyProperties();
        for (PropertyModel properties : propertyList) {
            ContentValues propertiesValues = new ContentValues();
            propertiesValues.put("prop_id", properties.getId());
            propertiesValues.put("userId", properties.getUserId());
            propertiesValues.put("name", properties.getName());
            propertiesValues.put("type", properties.getType());
            propertiesValues.put("address", properties.getAddress());
            propertiesValues.put("description", properties.getDescription());
            propertiesValues.put("total_living_area", properties.getTotalLeavingArea());
            propertiesValues.put("room_number", properties.getRooms());
            propertiesValues.put("price", properties.getPrice());
            propertiesValues.put("status", properties.getStatus());
            propertiesValues.put("photos_list", String.valueOf(properties.getPhotoProperty()));
            propertiesValues.put("property_interest", String.valueOf(properties.getPropertyInterest()));
            propertiesValues.put("on_sale_date", properties.getOnSaleDate());
            propertiesValues.put("sold_date", properties.getSoldDate());
            db.insert("property_table", OnConflictStrategy.IGNORE, propertiesValues);
        }
    }
}
