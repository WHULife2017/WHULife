package com.example.WhuLife.location;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.amap.api.location.AMapLocationClient;
import com.example.WhuLife.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class PacakgeActivity extends AppCompatActivity implements View.OnClickListener {

    public Locate myLocation = new Locate(this);
    public AMapLocationClient mapLocationClient = null;
    Location goal_l = new Location(0,0,"null");

    private List<APackage> packageList = new ArrayList<>();
    private PackageAdapter adapter;
    private int i;

    public FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacakge);

        fab = findViewById(R.id.add_button);
        fab.setOnClickListener(this);

        //initPackage();
        adapter = new PackageAdapter(this, R.layout.package_item, packageList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

//        mapLocationClient = new AMapLocationClient((this.getApplicationContext()));
//        myLocation.initLocation(mapLocationClient);
//        myLocation.setLocation(30, 114);
        //myLocation.startLocation();
    }

//    private void initPackage(){
//
//    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        myLocation.destroyLocation();
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.add_button:
                view.setFocusableInTouchMode(true);
                Intent intent = new Intent(this, setLocation.class);
                startActivityForResult(intent, 1);

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if(resultCode == RESULT_OK){
                    goal_l.setfetchLocation(data.getStringExtra("location"));
                    goal_l.setLatitude(data.getDoubleExtra("latitude", 0));
                    goal_l.setLongitude(data.getDoubleExtra("longtitude", 0));
                    goal_l.setCompany(data.getStringExtra("company"));
                    String packageId = data.getStringExtra("packageId");
                    APackage add_one = new APackage(packageId, goal_l.getfetchLocation(), "0", goal_l.getCompany());
                    adapter.notifyDataSetChanged();
                    packageList.add(add_one);
                }
                break;
            default:
        }

    }
}
