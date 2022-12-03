package com.openclassrooms.realestatemanager.utils.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.openclassrooms.realestatemanager.MVVM.databases.room.RemDatabase;
import com.openclassrooms.realestatemanager.model.PropertyModel;

public class PropertyContentProvider extends ContentProvider {

    public static final String AUTHORITY = "com.openclassrooms.realestatemanager";

    public static final String TABLE_NAME = "property_table";

    public static final Uri URI_PROPERTY = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        if (getContext() != null) {
            String userId = String.valueOf(ContentUris.parseId(uri));
            final Cursor cursor = RemDatabase.getInstance(getContext()).propertyDAO().getPropertyWithCursor(userId);
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
            return cursor;
        }
        throw new IllegalArgumentException("Failed to query row for uri " + uri);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return "vnd.android.cursor.item/" + AUTHORITY + "." + TABLE_NAME;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        if (getContext() != null && contentValues != null) {
            final long id = RemDatabase.getInstance(getContext()).propertyDAO().insertProperty(PropertyModel.fromContentValues(contentValues));
            if (id != 0) {
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            }
        }
        throw new IllegalArgumentException("Failed to insert row into " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        throw new IllegalArgumentException("Failed to delete row into " + uri);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        if (getContext() != null && contentValues != null) {
            final int count = RemDatabase.getInstance(getContext()).propertyDAO().updateProperty(PropertyModel.fromContentValues(contentValues));
            getContext().getContentResolver().notifyChange(uri, null);
            return count;
        }
        throw new IllegalArgumentException("Failed to update row into " + uri);
    }
}
