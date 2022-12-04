package com.openclassrooms.realestatemanager;

import static com.openclassrooms.realestatemanager.model.DummyListCallback.interestList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.openclassrooms.realestatemanager.model.PropertyModel;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PropertiesUnitTest {
    //-- Test : Create 2 properties and verify if property has been associated correctly to user --
    @Test
    public void test_properties() {
        assertEquals("Patrick Markov", property1.getUser().getName());
        assertEquals("Charlotte Claire", property2.getUser().getName());
    }

    //-- Test : Create a property and verify if property has been correctly created --
    @Test
    public void createProperties() {
        assertEquals("Run House", property3.getName());
        assertNotEquals("Charmed", property3.getName());
    }

    //-- Test : Count list of properties --
    @Test
    public void countListProperties(){
        List<PropertyModel> varPropertyModels = new ArrayList<>();
        varPropertyModels.add(property1);
        varPropertyModels.add(property2);
        varPropertyModels.add(property3);
        assertNotEquals(property1, property2);
        assertNotEquals(property2, property3);
        assertEquals(3, varPropertyModels.size());
        varPropertyModels.remove(2);
        assertNotEquals(3, varPropertyModels.size());
        assertEquals(2, varPropertyModels.size());
    }

    // - ALL PROPERTIES -->
    final PropertyModel property1 = new PropertyModel("1", "Charmed",
            "Loft", "2201-2265 Spring Hope Dr, Mt Pleasant, SC 29466, États-Unis",
            "Grateful", "150", "5", "740000", "Available",
            Arrays.asList(
                    "https://lh3.googleusercontent.com/5pCgliec4BlwoloG8Yz4pQJCo3Aw6rWBwWyXTm7d6V4KCydfj3urT3KayIXnUrLpSsASWj1tqXLw9mkle-2i3LDOfZCXlJ9fjUTm-usomw31=rj-w0-h1600-l80",
                    "https://lh3.googleusercontent.com/1k05SjfGJmKHFV47h3_HXvr6wqMueSd2TOAMPYpN71hTRzAuqoFgsg71mzIr1nhA_N2_b7XnLVqzuwSDVO1jemQqsg5S5etEWqVCxpXoJl8=rj-w0-h1600-l80",
                    "https://lh3.googleusercontent.com/u4zszPvBmF8OdQXLiv12QfkerSZNn4gKMB23VIdnkpNO5Bat4HWQeaXEPA3CNto-7MmNoh03RCvQOKN07fs5frSyRX_mZvALhiHzHFJ4gT24=rj-w0-h1600-l80",
                    "https://lh3.googleusercontent.com/SSSiGSEtWyWTbkCc5DNj--qWuotYQnIPZLK2hZKvvhsPnzHP1bRjQRPSpr7YQ6oHkF_UPN_sW7DFKxq7E9GpcRddVGmBqVtZfmWXoxaxcoo=rj-w0-h1600-l80"),
            Arrays.asList(interestList().get(1), interestList().get(5), interestList().get(8)),
            "01/10/2021", "");
    final PropertyModel property2 = new PropertyModel("2", "White Shield",
            "Apartment", "4757 Hutchinson Rd, Awendaw, SC 29429, États-Unis",
            "Clearly fabulous", "98", "3", "369000", "Available",
            Arrays.asList("https://media.vrbo.com/lodging/85000000/84160000/84153700/84153628/0058ae2d.f10.jpg",
                    "https://media.vrbo.com/lodging/85000000/84160000/84153700/84153628/00d10b4a.f10.jpg",
                    "https://media.vrbo.com/lodging/85000000/84160000/84153700/84153628/ced2bc33.f10.jpg",
                    "https://media.vrbo.com/lodging/85000000/84160000/84153700/84153628/0058ae2d.f10.jpg"),
            Arrays.asList(interestList().get(2), interestList().get(8), interestList().get(11)),
            "12/12/2021", "");
    final PropertyModel property3 = new PropertyModel("3", "Run House",
            "House", "2599-2301 Cat Tail Pond Rd, Seabrook Island, SC 29455, États-Unis",
            "Place to live.", "1951", "4", "617000", "Unavailable",
            Arrays.asList("https://media.vrbo.com/lodging/86000000/85290000/85282300/85282244/4900b587.f10.jpg",
                    "https://media.vrbo.com/lodging/86000000/85290000/85282300/85282244/8a7234e5.f10.jpg",
                    "https://media.vrbo.com/lodging/86000000/85290000/85282300/85282244/5b259844.f10.jpg",
                    "https://media.vrbo.com/lodging/86000000/85290000/85282300/85282244/f9825b6e.f10.jpg",
                    "https://media.vrbo.com/lodging/86000000/85290000/85282300/85282244/1d07a8d6.f10.jpg"),
            Arrays.asList(interestList().get(3), interestList().get(4), interestList().get(7), interestList().get(6), interestList().get(3), interestList().get(10)),
            "30/12/2021", "23/04/2022");
}
