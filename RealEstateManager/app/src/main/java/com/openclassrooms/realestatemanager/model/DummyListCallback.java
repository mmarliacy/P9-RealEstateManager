package com.openclassrooms.realestatemanager.model;

import com.openclassrooms.realestatemanager.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyListCallback {

    //-------------
    // DUMMY_USERS
    //-------------
    // 1 --:: Create method to return dummy users list value::--
    public static List<UserModel> getDummyUsers() {
        return new ArrayList<>(DUMMY_USERS);
    }
    // 2 --:: Create dummy users list ::--
    public static List<UserModel> DUMMY_USERS = Arrays.asList(
            new UserModel("1", "Patrick Markov", "patrick.marcov@gmail.com"),
            new UserModel("2", "Charlotte Claire", "cha.claire@orange.fr"),
            new UserModel("3", "Klaus Michelson", "mick_klaus@yahoo.fr"));

    //------------------
    // DUMMY_PROPERTIES
    //------------------
    // 1 --:: Create method to return dummy properties list value::--
    public static List<PropertyModel> getDummyProperties() {
        return new ArrayList<>(DUMMY_PROPERTIES);
    }
    // 2 --:: Create dummy properties list ::--
    public static List<PropertyModel> DUMMY_PROPERTIES = Arrays.asList(
            new PropertyModel("1", "Charmed",
                    "Loft", "2201-2265 Spring Hope Dr, Mt Pleasant, SC 29466, États-Unis",
                    "Grateful", "150", "5", "740000", "Available",
                    Arrays.asList("https://lh3.googleusercontent.com/5pCgliec4BlwoloG8Yz4pQJCo3Aw6rWBwWyXTm7d6V4KCydfj3urT3KayIXnUrLpSsASWj1tqXLw9mkle-2i3LDOfZCXlJ9fjUTm-usomw31=rj-w0-h1600-l80",
                            "https://lh3.googleusercontent.com/1k05SjfGJmKHFV47h3_HXvr6wqMueSd2TOAMPYpN71hTRzAuqoFgsg71mzIr1nhA_N2_b7XnLVqzuwSDVO1jemQqsg5S5etEWqVCxpXoJl8=rj-w0-h1600-l80",
                            "https://lh3.googleusercontent.com/u4zszPvBmF8OdQXLiv12QfkerSZNn4gKMB23VIdnkpNO5Bat4HWQeaXEPA3CNto-7MmNoh03RCvQOKN07fs5frSyRX_mZvALhiHzHFJ4gT24=rj-w0-h1600-l80",
                            "https://lh3.googleusercontent.com/SSSiGSEtWyWTbkCc5DNj--qWuotYQnIPZLK2hZKvvhsPnzHP1bRjQRPSpr7YQ6oHkF_UPN_sW7DFKxq7E9GpcRddVGmBqVtZfmWXoxaxcoo=rj-w0-h1600-l80"),
                    Arrays.asList(interestList().get(1), interestList().get(5), interestList().get(8)),
                    "01/10/2021", ""),
            new PropertyModel("2", "White Shield",
                    "Apartment", "4757 Hutchinson Rd, Awendaw, SC 29429, États-Unis",
                    "Clearly fabulous", "98", "3", "369000", "Available",
                    Arrays.asList("https://media.vrbo.com/lodging/85000000/84160000/84153700/84153628/0058ae2d.f10.jpg",
                            "https://media.vrbo.com/lodging/85000000/84160000/84153700/84153628/00d10b4a.f10.jpg",
                            "https://media.vrbo.com/lodging/85000000/84160000/84153700/84153628/ced2bc33.f10.jpg",
                            "https://media.vrbo.com/lodging/85000000/84160000/84153700/84153628/0058ae2d.f10.jpg"),
                    Arrays.asList(interestList().get(2), interestList().get(8), interestList().get(11)),
                    "12/12/2021", ""),
            new PropertyModel("3", "Run House",
                    "House", "2599-2301 Cat Tail Pond Rd, Seabrook Island, SC 29455, États-Unis",
                    "Place to live.", "1951", "4", "617000", "Unavailable",
                    Arrays.asList("https://media.vrbo.com/lodging/86000000/85290000/85282300/85282244/4900b587.f10.jpg",
                            "https://media.vrbo.com/lodging/86000000/85290000/85282300/85282244/8a7234e5.f10.jpg",
                            "https://media.vrbo.com/lodging/86000000/85290000/85282300/85282244/5b259844.f10.jpg",
                            "https://media.vrbo.com/lodging/86000000/85290000/85282300/85282244/f9825b6e.f10.jpg",
                            "https://media.vrbo.com/lodging/86000000/85290000/85282300/85282244/1d07a8d6.f10.jpg"),
                    Arrays.asList(interestList().get(3), interestList().get(4), interestList().get(7), interestList().get(6), interestList().get(3), interestList().get(10)),
                    "30/12/2021", "23/04/2022"),
            new PropertyModel("1", "Brown Champagne",
                    "Chalet", "2025 Bainbridge Ave, North Charleston, SC 29405, États-Unis",
                    "Beautiful to grew up with your family.", "226", "3", "315900", "Unavailable",
                    Arrays.asList("https://media.vrbo.com/lodging/19000000/19000000/18993200/18993178/e7c22638.f10.jpg",
                            "https://media.vrbo.com/lodging/19000000/19000000/18993200/18993178/1a0beb6d.f10.jpg",
                            "https://media.vrbo.com/lodging/19000000/19000000/18993200/18993178/3893b351.f10.jpg",
                            "https://media.vrbo.com/lodging/19000000/19000000/18993200/18993178/9cf9b69b.f10.jpg",
                            "https://media.vrbo.com/lodging/19000000/19000000/18993200/18993178/f6ac2f01.f10.jpg"),
                    Arrays.asList(interestList().get(1), interestList().get(7), interestList().get(6), interestList().get(10), interestList().get(9)),
                    "04/01/2022", "06/07/2022"),
            new PropertyModel("3", "Cosy Place",
                    "Mobil-Home", "2025 Bainbridge Ave, North Charleston, SC 29405, États-Unis",
                    "So cool to live in here.", "178", "3", "205450", "Available",
                    Arrays.asList("https://media.vrbo.com/lodging/71000000/70550000/70547100/70547030/b5c221bf.f10.jpg",
                            "https://media.vrbo.com/lodging/71000000/70550000/70547100/70547030/2045d0a2.f10.jpg",
                            "https://media.vrbo.com/lodging/71000000/70550000/70547100/70547030/0bbda94c.f10.jpg",
                            "https://media.vrbo.com/lodging/71000000/70550000/70547100/70547030/0f4e48b2.f10.jpg",
                            "https://media.vrbo.com/lodging/71000000/70550000/70547100/70547030/50766bcd.f10.jpg"),
                    Arrays.asList(interestList().get(4), interestList().get(5), interestList().get(11), interestList().get(0)),
                    "30/12/2021", "")

    );

    //--------------------
    // POINT OF INTERESTS
    //--------------------
    // 1 --:: Define interests list ::--
    public static List<String> interestList() {
        List<String> INTERESTS_NAME_LIST = new ArrayList<>();
        INTERESTS_NAME_LIST.add("Hotel");
        INTERESTS_NAME_LIST.add("Airport");
        INTERESTS_NAME_LIST.add("Restaurant");
        INTERESTS_NAME_LIST.add("School");
        INTERESTS_NAME_LIST.add("Bakery");
        INTERESTS_NAME_LIST.add("Hospital");
        INTERESTS_NAME_LIST.add("City Hall");
        INTERESTS_NAME_LIST.add("Mall");
        INTERESTS_NAME_LIST.add("Park");
        INTERESTS_NAME_LIST.add("Bank Center");
        INTERESTS_NAME_LIST.add("Pool");
        INTERESTS_NAME_LIST.add("Church");
        return INTERESTS_NAME_LIST;
    }

    // 2 --:: Define icons list ::--
    public static List<Integer> INTERESTS_ICON_LIST =
            Arrays.asList(
                    R.drawable.baseline_local_hotel_24,
                    R.drawable.baseline_flight_24,
                    R.drawable.baseline_restaurant_24,
                    R.drawable.baseline_school_24,
                    R.drawable.baseline_bakery_dining_24,
                    R.drawable.baseline_local_hospital_24,
                    R.drawable.baseline_location_city_24,
                    R.drawable.baseline_local_mall_24,
                    R.drawable.baseline_park_24,
                    R.drawable.baseline_account_balance_24,
                    R.drawable.baseline_pool_24,
                    R.drawable.baseline_church_24
            );

    // 3 --:: Bind interests to icons respectively ::--
    public static List<InterestModel> getInterestList() {
        List<InterestModel> pInterestModelList = new ArrayList<>();
        List<Integer> iconList = new ArrayList<>(INTERESTS_ICON_LIST);
        for (int i = 0; i < interestList().size(); i++) {
            InterestModel varInterestModel = new InterestModel(i, interestList().get(i), iconList.get(i));
            pInterestModelList.add(varInterestModel);
        }
        pInterestModelList.size();
        return pInterestModelList;
    }
}


