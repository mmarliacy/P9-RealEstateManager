package com.openclassrooms.realestatemanager;

import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static com.openclassrooms.realestatemanager.model.DummyListCallback.interestList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;

import com.openclassrooms.realestatemanager.MVVM.databases.room.RemDatabase;
import com.openclassrooms.realestatemanager.utils.provider.PropertyContentProvider;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class PropertyContentProviderTest {

    // FOR DATA
    private ContentResolver mContentResolver;
    // DATA SET FOR TEST
    private static final long USER_ID = 7;

    @Before
    public void setUp() {
        Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(),
                        RemDatabase.class)
                        .allowMainThreadQueries()
                        .build();
        mContentResolver = InstrumentationRegistry.getInstrumentation().getContext()
                            .getContentResolver();
    }

    @Test
    public void getItemsWhenNoItemInserted() {
        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(PropertyContentProvider.URI_PROPERTY, USER_ID), null, null, null, null);
        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), is(3));
        cursor.close();
    }

    @Test

    public void insertAndGetItem() {
        // BEFORE : Adding demo item
        final Uri userUri = mContentResolver.insert(PropertyContentProvider.URI_PROPERTY, generateItem());
        // TEST
        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(PropertyContentProvider.URI_PROPERTY, USER_ID), null, null, null, null);
        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), is(1));
        assertThat(cursor.moveToFirst(), is(true));
        assertThat(cursor.getString(cursor.getColumnIndexOrThrow("name")), is("Chocolate"));
    }

    // ---

    private ContentValues generateItem() {
        final ContentValues values = new ContentValues();
        values.put("userId", "1");
        values.put("name", "Chocolate");
        values.put("type", "Loft");
        values.put("address", "1900-1300 Bondsview Rd, Macon, GA 31217, États-Unis");
        values.put("description", "So beautiful");
        values.put("totalLeavingArea", "130");
        values.put("rooms", "2");
        values.put("price", "220700");
        values.put("status", "Available");
        values.put("photoProperty",
                String.valueOf(
                        Arrays.asList(
                        "https://media.vrbo.com/lodging/71000000/70550000/70547100/70547030/b5c221bf.f10.jpg",
                        "https://media.vrbo.com/lodging/71000000/70550000/70547100/70547030/2045d0a2.f10.jpg",
                        "https://media.vrbo.com/lodging/71000000/70550000/70547100/70547030/0bbda94c.f10.jpg",
                        "https://media.vrbo.com/lodging/71000000/70550000/70547100/70547030/0f4e48b2.f10.jpg",
                        "https://media.vrbo.com/lodging/71000000/70550000/70547100/70547030/50766bcd.f10.jpg"
                        )));
        values.put("propertyInterest",
                String.valueOf(
                        Arrays.asList(
                                interestList().get(1),
                                interestList().get(7),
                                interestList().get(6)
                        )));
        values.put("onSaleDate", "24/11/2022");
        values.put("onSaleDate", "");
        return values;

    }
}
