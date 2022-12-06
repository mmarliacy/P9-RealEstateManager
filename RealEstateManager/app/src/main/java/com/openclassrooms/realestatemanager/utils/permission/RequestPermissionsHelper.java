package com.openclassrooms.realestatemanager.utils.permission;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import java.util.List;

public class RequestPermissionsHelper {

    public void startPermissionRequest(Fragment fr, RequestPermissionInterface rpi, List<String> manifestPermissions){
       ActivityResultLauncher<String[]> requestPermissionLauncher =
                fr.registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), isGranted -> rpi.isGranted(true));
        requestPermissionLauncher.launch(
                manifestPermissions.toArray(new String[0]));
    }
}
