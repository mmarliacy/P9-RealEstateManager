package com.openclassrooms.realestatemanager.local.database;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.openclassrooms.realestatemanager.local.database.DAO.PropertyDAO;
import com.openclassrooms.realestatemanager.local.database.DAO.UserDAO;
import com.openclassrooms.realestatemanager.model.PropertyModel;
import com.openclassrooms.realestatemanager.model.UserModel;

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
                        allowMainThreadQueries().
                        build();
            }
        }
        return instance;
    }

    // 4 -- CALLBACK -->
    private static Callback propertiesCallBack() {
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                prepopulateDatabase(db);
            }
        };
    }

    // 5 -- Insert Content Values in table -->
    private static void prepopulateDatabase(SupportSQLiteDatabase db) {
        List<UserModel> userList = UserModel.DUMMY_USERS;
        for (UserModel users : userList) {
            ContentValues usersValues = new ContentValues();
            usersValues.put("id", users.getId());
            usersValues.put("name", users.getName());
            usersValues.put("mail", users.getMail());
            usersValues.put("photo", users.getPhoto());
            db.insert("user_table", OnConflictStrategy.IGNORE, usersValues);
        }
    }
}
