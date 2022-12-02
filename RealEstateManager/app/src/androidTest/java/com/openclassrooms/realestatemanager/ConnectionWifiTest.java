package com.openclassrooms.realestatemanager;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.openclassrooms.realestatemanager.utils.Utils.isInternetAvailable;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import androidx.test.rule.ActivityTestRule;

import com.openclassrooms.realestatemanager.utils.Utils;
import com.openclassrooms.realestatemanager.view.activities.MainActivity_HomeScreen;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ConnectionWifiTest {

    MainActivity_HomeScreen mActivity;
    //-- RULE/BEFORE -->
    @Rule
    public ActivityTestRule<MainActivity_HomeScreen> mActivityRule = new ActivityTestRule<>(MainActivity_HomeScreen.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    @Test
    public void verifyIfWifiIsConnected(){
        try {
            Thread.sleep(10000); //::--> wait 5 seconds to open app after authentication
        } catch (InterruptedException e) {
            //get the devices location
            e.printStackTrace();
        }
        onView(withId(R.id.fragment_list_properties)).check(matches(isDisplayed()));
        isInternetAvailable(mActivityRule.getActivity().getApplicationContext());
        assertEquals(true, Utils.isInternetAvailable(mActivityRule.getActivity().getApplicationContext()));
        assertTrue(isInternetAvailable(mActivityRule.getActivity().getApplicationContext()));
    }
}
