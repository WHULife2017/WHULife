package com.example.WhuLife.location;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


import com.amap.api.location.AMapLocationClient;
import com.example.WhuLife.R;


public class LocationMainActivity extends AppCompatActivity implements OnClickListener{

    private Button button;

    public Locate myLocation = new Locate(LocationMainActivity.this);
    public AMapLocationClient mapLocationClient = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_activity_main);

        button = findViewById(R.id.start);
        button.setOnClickListener(this);
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        myLocation.destroyLocation();
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.start:
                Intent intent1 = new Intent(this, PacakgeActivity.class);
                startActivity(intent1);
                break;
        }
    }

}
