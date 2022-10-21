package com.openclassrooms.realestatemanager.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.util.List;


@Entity(tableName = "property_table", foreignKeys =
        @ForeignKey(
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
    @ColumnInfo(name = "prop_id", index = true)
    private long id;

    @ColumnInfo(name = "userId")
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

    @ColumnInfo(name = "status")
    private String status;

    @ColumnInfo(name = "photos_list")
    private List<String> photoProperty;

    @ColumnInfo(name = "property_interest")
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
                         String pStatus, List<String> pPhotoProperty, List<String> pPropertyInterest,
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
        status = pStatus;
        photoProperty = pPhotoProperty;
        propertyInterest = pPropertyInterest;
        onSaleDate = pOnSaleDate;
        soldDate = pSoldDate;
    }

    /** Getters */
    public long getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    /** Returns the project associated to the task */
    public UserModel getUser() {
        return UserModel.getUserById(userId);
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

    public String getStatus() {
        return status;
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

    /** Setters */
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

    public void setStatus(String pStatus) {
        status = pStatus;
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
        status = in.readString();
        photoProperty = in.createStringArrayList();
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
        dest.writeString(status);
        dest.writeStringList(photoProperty);
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
