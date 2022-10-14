package com.openclassrooms.realestatemanager.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Arrays;
import java.util.List;

/**
 * This model class implement Parcelable
 */
@Entity(tableName = "property_table", foreignKeys = @ForeignKey(
        entity = UserModel.class,
        parentColumns = "id",
        childColumns = "userId",
        onDelete = ForeignKey.CASCADE)
)
public class PropertyModel implements Parcelable {

    //-----------
    // VARIABLES
    //-----------
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;
    @ColumnInfo(name = "userId", index = true)
    private int userId;

    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "type")
    private String type;
    @ColumnInfo(name = "address")
    private String address;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "total_living_area")
    private int totalLeavingArea;
    @ColumnInfo(name = "room_number")
    private int rooms;
    @ColumnInfo(name = "price")
    private int price;
    @ColumnInfo(name = "photos_list")
    private List<String> photoProperty;
    @ColumnInfo(name = "interest_list")
    private List<String> propertyInterest;
    @ColumnInfo(name = "on_sale_date")
    private String onSaleDate;
    @ColumnInfo(name = "sold_date")
    private String soldDate;

    /** EMPTY CONSTRUCTOR */
    public PropertyModel() {
    }

    /** CONSTRUCTOR */
    public PropertyModel(long pId, int pUserId, String pName, String pType, String pAddress,
                         String pDescription, int pTotalLeavingArea, int pRooms, int pPrice,
                         List<String> pPhotoProperty, List<String> pPropertyInterest,
                         String pOnSaleDate, String pSoldDate) {
        id = pId;
        userId = pUserId;
        name = pName;
        type = pType;
        address = pAddress;
        description = pDescription;
        totalLeavingArea = pTotalLeavingArea;
        rooms = pRooms;
        price = pPrice;
        photoProperty = pPhotoProperty;
        propertyInterest = pPropertyInterest;
        onSaleDate = pOnSaleDate;
        soldDate = pSoldDate;
    }

    //------------------
    // DUMMY_PROPERTIES
    //------------------
    public static List<PropertyModel> DUMMY_PROPERTIES = Arrays.asList(
            new PropertyModel(1,1,"La Marquise", "Loft", "3, road Winston Churchill, 34029 London", "Greatful",115, 4, 175000, Arrays.asList("",""), Arrays.asList("",""),  "01/10/2021","23/04/2022"),
            new PropertyModel(2,2,"Black Shield", "Castle", "15, avenue 415-420 Broadway Coast East", "Fabulous",735, 10, 1567000, Arrays.asList("",""), Arrays.asList("",""),  "12/12/2021",""),
            new PropertyModel(3,3,"Run town", "House", "671, palace Wil Town, 837 Island", "Place to live",311, 3, 160000, Arrays.asList("",""), Arrays.asList("",""),  "30/12/2021","")
    );


    /**
     * Getters
     */
    public long getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public int getTotalLeavingArea() {
        return totalLeavingArea;
    }

    public int getRooms() {
        return rooms;
    }

    public int getPrice() {
        return price;
    }

    public List<String> getPhotoProperty() {
        return photoProperty;
    }

    public List<String> getPropertyInterest() {
        return propertyInterest;
    }

    public String getOnSaleDate() {
        return onSaleDate;
    }

    public String getSoldDate() {
        return soldDate;
    }

    public static List<PropertyModel> getDummyProperties() {
        return DUMMY_PROPERTIES;
    }

    /**
     * Setters
     */

    public void setId(long pId) {
        id = pId;
    }

    public void setUserId(int pUserId) {
        userId = pUserId;
    }

    public void setName(String pName) {
        name = pName;
    }

    public void setType(String pType) {
        type = pType;
    }

    public void setAddress(String pAddress) {
        address = pAddress;
    }

    public void setDescription(String pDescription) {
        description = pDescription;
    }

    public void setTotalLeavingArea(int pTotalLeavingArea) {
        totalLeavingArea = pTotalLeavingArea;
    }

    public void setRooms(int pRooms) {
        rooms = pRooms;
    }

    public void setPrice(int pPrice) {
        price = pPrice;
    }

    public void setPhotoProperty(List<String> pPhotoProperty) {
        photoProperty = pPhotoProperty;
    }

    public void setPropertyInterest(List<String> pPropertyInterest) {
        propertyInterest = pPropertyInterest;
    }

    public void setOnSaleDate(String pOnSaleDate) {
        onSaleDate = pOnSaleDate;
    }

    public void setSoldDate(String pSoldDate) {
        soldDate = pSoldDate;
    }

    public static void setDummyProperties(List<PropertyModel> pDummyProperties) {
        DUMMY_PROPERTIES = pDummyProperties;
    }

    /**
     * PARCELABLE IMPLEMENTATION
     */

    protected PropertyModel(Parcel in) {
        id = in.readLong();
        userId = in.readInt();
        name = in.readString();
        type = in.readString();
        address = in.readString();
        description = in.readString();
        totalLeavingArea = in.readInt();
        rooms = in.readInt();
        price = in.readInt();
        photoProperty = in.createStringArrayList();
        propertyInterest = in.createStringArrayList();
        onSaleDate = in.readString();
        soldDate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(userId);
        dest.writeString(name);
        dest.writeString(type);
        dest.writeString(address);
        dest.writeString(description);
        dest.writeInt(totalLeavingArea);
        dest.writeInt(rooms);
        dest.writeInt(price);
        dest.writeStringList(photoProperty);
        dest.writeStringList(propertyInterest);
        dest.writeString(onSaleDate);
        dest.writeString(soldDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PropertyModel> CREATOR = new Creator<>() {
        @Override
        public PropertyModel createFromParcel(Parcel in) {
            return new PropertyModel(in);
        }

        @Override
        public PropertyModel[] newArray(int size) {
            return new PropertyModel[size];
        }
    };
}
