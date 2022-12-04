package com.openclassrooms.realestatemanager;

import static com.openclassrooms.realestatemanager.model.DummyListCallback.interestList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.openclassrooms.realestatemanager.MVVM.databases.room.DAO.PropertyDAO;
import com.openclassrooms.realestatemanager.MVVM.databases.room.DAO.UserDAO;
import com.openclassrooms.realestatemanager.MVVM.databases.room.RemDatabase;
import com.openclassrooms.realestatemanager.model.PropertyModel;
import com.openclassrooms.realestatemanager.model.UserModel;
import com.openclassrooms.realestatemanager.utils.LiveDataTestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RunWith(AndroidJUnit4.class)
public class DaoTest {

    //-- DATA -->
    private RemDatabase dataBase;
    private UserDAO fUserDAO;
    private PropertyDAO fPropertyDAO;
    private List<PropertyModel> properties;
    private static final UserModel USER_DEMO = new UserModel("1", "Patrick Markov", "patrick.marcov@gmail.com");
    private static final PropertyModel PROPERTY_DEMO = new PropertyModel("1", "Brown Champagne",
            "Chalet", "2025 Bainbridge Ave, North Charleston, SC 29405, États-Unis",
            "Beautiful to grew up with your family.", "226", "3", "315900", "Unavailable",
            Arrays.asList("https://media.vrbo.com/lodging/19000000/19000000/18993200/18993178/e7c22638.f10.jpg",
                    "https://media.vrbo.com/lodging/19000000/19000000/18993200/18993178/1a0beb6d.f10.jpg",
                    "https://media.vrbo.com/lodging/19000000/19000000/18993200/18993178/3893b351.f10.jpg",
                    "https://media.vrbo.com/lodging/19000000/19000000/18993200/18993178/9cf9b69b.f10.jpg",
                    "https://media.vrbo.com/lodging/19000000/19000000/18993200/18993178/f6ac2f01.f10.jpg"),
            Arrays.asList(interestList().get(1), interestList().get(7), interestList().get(6), interestList().get(10), interestList().get(9)),
            "04/01/2022", "06/07/2022");


    //-------------------
    //-- INITIATING... --
    //-------------------

    //-- Initialize JUnit Test Rule to execute task synchronously --
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule =
            new InstantTaskExecutorRule();
    //-- Init database --
    @Before
    public void initDb() {
        dataBase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(),
                RemDatabase.class).allowMainThreadQueries().build();
        fPropertyDAO = dataBase.propertyDAO();
        fUserDAO = dataBase.userDAO();
    }
    //-- Close database --
    @After
    public void closeDb() {
        dataBase.close();
    }

    //-- Test : insert property into property_table and check with assert function --
    @Test
    public void insertProperty(){
        fUserDAO.insertUser(USER_DEMO);
        fPropertyDAO.insertProperty(PROPERTY_DEMO);
        properties = LiveDataTestUtils.getValue(this.dataBase.propertyDAO().getAllProperties());
        assertEquals(1, properties.size());
        assertEquals("Brown Champagne",properties.get(0).getName());
    }


    //-- Test : Delete user, delete also properties associated --
    @Test
    public void deleteUserDeleteAssociatedProperties(){
        //-- create task and verify if task and project are really assigned --
        fUserDAO.insertUser(USER_DEMO);
        fPropertyDAO.insertProperty(PROPERTY_DEMO);
        properties = LiveDataTestUtils.getValue(fPropertyDAO.getAllProperties());
        assertEquals("Brown Champagne",properties.get(0).getName());
        assertEquals("Chalet", Objects.requireNonNull(properties.get(0).getType()));
        //-- get "PROJECT_DEMO" value and delete it --
        UserModel userDemo = LiveDataTestUtils.getValue(fUserDAO.getUsers()).get(0);
        fUserDAO.deleteUser(userDemo);
        //-- Check if the associated task has been deleted after project deletion --
        properties = LiveDataTestUtils.getValue(fPropertyDAO.getAllProperties());
        assertEquals(0, properties.size());
    }

    //------------------
    //-- ABOUT TASKS--
    //------------------

    //-- Test : get properties' List even if the database is empty --
    @Test
    public void getPropertiesWhenDatabaseIsEmpty(){
        properties = LiveDataTestUtils.getValue(fPropertyDAO.getAllProperties());
        assertTrue(properties.isEmpty());
    }

    //-- Test : insert property into property_table and check with assert function --
    @Test
    public void createProperty(){
        fUserDAO.insertUser(USER_DEMO);
        fPropertyDAO.insertProperty(PROPERTY_DEMO);
        PropertyModel propertyDemo = LiveDataTestUtils.getValue(fPropertyDAO.getAllProperties()).get(0);
        assertEquals(PROPERTY_DEMO.getName(), propertyDemo.getName());
    }

    //-- Test : update property from property_table and check with assert function --
    @Test
    public void updateProperty(){
        fUserDAO.insertUser(USER_DEMO);
        fPropertyDAO.insertProperty(PROPERTY_DEMO);
        PropertyModel propertyDemo = LiveDataTestUtils.getValue(fPropertyDAO.getAllProperties()).get(0);
        propertyDemo.setName("Brown");
        fPropertyDAO.updateProperty(propertyDemo);
        properties = LiveDataTestUtils.getValue(fPropertyDAO.getAllProperties());
        assertEquals(1, properties.size());
        assertEquals(propertyDemo.getName(), "Brown");
        assertEquals(propertyDemo.getType(), "Chalet");
    }
}
