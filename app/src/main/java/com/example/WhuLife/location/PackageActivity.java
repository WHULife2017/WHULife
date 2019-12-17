package com.example.WhuLife.location;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.amap.api.location.AMapLocationClient;
import com.example.WhuLife.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class PackageActivity extends AppCompatActivity implements View.OnClickListener {

    public Locate myLocation;
    public AMapLocationClient mapLocationClient = null;

    private LocationDB dbHelper;
    private SQLiteDatabase db;
    private ContentValues values = new ContentValues();
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

        dbHelper = new LocationDB(this, "package.db", null, 2); //新创建时可能有问题
        dbHelper.getWritableDatabase();
        db = dbHelper.getWritableDatabase();

        adapter = new PackageAdapter(this, R.layout.package_item, packageList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        initPackage();

        myLocation = new Locate(this, db);
        mapLocationClient = new AMapLocationClient((this.getApplicationContext()));
        myLocation.initLocation(mapLocationClient);
        myLocation.startLocation();
    }

    private void initPackage(){
        Cursor cursor = db.query("package", null,null,null,null,null, null);
        if(cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String packageLocation = cursor.getString(cursor.getColumnIndex("package_location"));
                String packageCompany = cursor.getString(cursor.getColumnIndex("package_company"));
                String informTime = cursor.getString(cursor.getColumnIndex("inform_time"));
                APackage add_one = new APackage(id+"", packageLocation, "null", packageCompany);
                adapter.notifyDataSetChanged();
                packageList.add(add_one);
            }while (cursor.moveToNext());
        }
        cursor.close();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

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
                    String fetchLocation = data.getStringExtra("location");
                    String company = data.getStringExtra("company");
                    double latitude = data.getDoubleExtra("latitude", 0);
                    double longtitude = data.getDoubleExtra("longtitude", 0);
                    String packageId = data.getStringExtra("packageId");
                    if(fetchLocation!=null&&company!=null&&packageId!=null){
                        APackage add_one = new APackage(packageId, fetchLocation, "null", company);
                        adapter.notifyDataSetChanged();
                        packageList.add(add_one);

                        //data
                        values.put("id", packageId);
                        values.put("package_location", fetchLocation);
                        values.put("package_company", company);
                        values.put("latitude", latitude);
                        values.put("longtitude", longtitude);
                        values.put("inform_time", "null");
                        //insert
                        db.insert("package", null, values);
                        values.clear();
                        myLocation.setLocation(latitude, longtitude);
                    }
                }
                break;
            default:
        }

    }
}
