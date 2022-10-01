package com.openclassrooms.realestatemanager.view.activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.annotation.SuppressLint;
import android.os.Bundle;
import com.openclassrooms.realestatemanager.R;

public class MainActivity_HomeScreen extends AppCompatActivity {

    Toolbar fToolbar;

    @SuppressLint({"UseSupportActionBar", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.real_estate_sheet_layout);

        fToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(fToolbar);

    }
}
