package com.example.WhuLife.ui.location;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.amap.api.location.AMapLocationClient;
import com.example.WhuLife.R;
import com.example.WhuLife.location.Locate;
import com.example.WhuLife.location.LocationMainActivity;
import com.example.WhuLife.location.PacakgeActivity;

public class LocationFragment extends Fragment {

    private LocationViewModel locationViewModel;

    private Button button;

//    public Locate myLocation = new Locate(getContext());
//    public AMapLocationClient mapLocationClient = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        locationViewModel =
                ViewModelProviders.of(this).get(LocationViewModel.class);
        View root = inflater.inflate(R.layout.location_activity_main, container, false);
//        final TextView textView = root.findViewById(R.id.text_location);
//        locationViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        button = root.findViewById(R.id.start);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.start:
                        Intent intent1 = new Intent(getContext(), PacakgeActivity.class);
                        startActivity(intent1);
                        break;
                }
            }

        });

        return root;
    }
//    @Override
//    protected void onDestroy(){
//        super.onDestroy();
//        myLocation.destroyLocation();
//    }

}