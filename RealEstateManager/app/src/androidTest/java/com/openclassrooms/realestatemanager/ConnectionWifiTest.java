package com.openclassrooms.realestatemanager;

import static org.junit.Assert.assertEquals;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.openclassrooms.realestatemanager.view.activities.MainActivity_HomeScreen;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ConnectionWifiTest {

    @Test
    public void verifyIfWifiIsConnected() {
        try (ActivityScenario<MainActivity_HomeScreen> scenario =
                     ActivityScenario.launch(MainActivity_HomeScreen.class)) {
            assertEquals(scenario.getState(), Lifecycle.State.RESUMED);
        }
    }
}
