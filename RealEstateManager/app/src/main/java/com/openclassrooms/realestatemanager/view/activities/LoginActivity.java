package com.openclassrooms.realestatemanager.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.openclassrooms.realestatemanager.MVVM.injection.firebase.FirebaseInjection;
import com.openclassrooms.realestatemanager.MVVM.injection.firebase.FirebaseViewModelFactory;
import com.openclassrooms.realestatemanager.MVVM.injection.room.RoomInjection;
import com.openclassrooms.realestatemanager.MVVM.injection.room.RoomViewModelFactory;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.UserModel;
import com.openclassrooms.realestatemanager.view.viewmodel.FirebaseViewModel;
import com.openclassrooms.realestatemanager.view.viewmodel.RoomViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    //----------
    // FOR DATA
    //----------
    private static final String TAG = "Problem in : " + LoginActivity.class.getName();
    private UserModel userModel;

    /**
     * LIVE DATA - VIEW MODELS
     */
    RoomViewModel roomViewModel;
    private FirebaseViewModel fFirebaseViewModel;

    //---------------------------
    // ON-CREATE : LOGIN ACTIVITY
    //---------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activty);
        configureLocalRoomViewModel();
        configureFirebaseViewModel();
        loginProcess();
    }

    //-----------------------
    // AUTHENTICATION'S USER
    //-----------------------
    private void loginProcess() {
        //--:: 1 -- Choose authentication providers ::-->
        List<AuthUI.IdpConfig> varProviders = List.of(
                new AuthUI.IdpConfig.GoogleBuilder().build());

        //--:: 2 -- Create and launch sign-in intent ::-->
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setTheme(R.style.LoginTheme_NoActionBar)
                .setAvailableProviders(varProviders)
                .setIsSmartLockEnabled(false, true)
                .setLogo(R.drawable.round_roofing_24)
                .build();
        signInLauncher.launch(signInIntent);
    }

    //--:: 3 -- Get the sign-in result ::-->
    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            this::onSignInResult
    );

    //--:: 4 -- Handle the result ::-->
    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            //--::> Successfully signed in
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            assert FirebaseAuth.getInstance().getCurrentUser() != null;
            assert user != null;
            userModel = new UserModel(user.getUid(), user.getDisplayName(), user.getEmail());
            //--::> Create User in both databases
            getAndObserveUsers();
            fFirebaseViewModel.createUser(user.getDisplayName(), new UserModel(user.getUid(), user.getDisplayName(), user.getEmail()));
            //--::> Launch intent
            Intent loginIntent = new Intent(this, MainActivity_HomeScreen.class);
            startActivity(loginIntent);
            //--::> Otherwise...
        } else {
            Toast.makeText(this, R.string.cancelled_authentication, Toast.LENGTH_SHORT).show();
            if (response != null && response.getError() != null) {
                Log.e(TAG, getString(R.string.cancelled_authentication_because_of) + result);
            }
        }
    }

    //---------------------
    // FIREBASE VIEW MODEL
    //---------------------
    // 1 -- Configure Firebase View Model & Retrieve all properties and connected User -->
    private void configureFirebaseViewModel() {
        FirebaseViewModelFactory varFirebaseViewModelFactory = FirebaseInjection.provideFirebaseViewModelFactory();
        fFirebaseViewModel = varFirebaseViewModelFactory.create(FirebaseViewModel.class);
    }

    //-----------------------
    // LOCAL ROOM VIEW MODEL
    //-----------------------
    // 1 -- Configure ROOM View Model & Retrieve all properties stuck on phone and last connected User -->
    private void configureLocalRoomViewModel() {
        RoomViewModelFactory roomViewModelFactory = RoomInjection.provideViewModelFactory(this);
        roomViewModel = roomViewModelFactory.create(RoomViewModel.class);
        roomViewModel.initAllUsers();
    }

    private void getAndObserveUsers() {
        this.roomViewModel.getAllUsers().observe(this, this::getRoomUsers);
    }

    public void getRoomUsers(List<UserModel> users) {
        List<UserModel> allUsers = new ArrayList<>(users);
        if (allUsers.size() != 0){
            for (UserModel user : allUsers){
                if (Objects.equals(user.getId(), userModel.getId())){
                    roomViewModel.getUser(userModel.getId());
                } else {
                    roomViewModel.insertUser(userModel);
                    return;
                }
            }
        } else {
            roomViewModel.insertUser(userModel);
        }

    }
}