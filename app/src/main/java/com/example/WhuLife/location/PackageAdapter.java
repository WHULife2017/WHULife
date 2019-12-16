package com.example.WhuLife.location;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.WhuLife.R;

import java.util.List;

public class PackageAdapter extends ArrayAdapter<APackage> {

    private int resourceId;
    private Context context;
    private AppCompatActivity activity;

    public PackageAdapter(Context context, int textViewResourceId,
                          List<APackage> objects){
        super(context, textViewResourceId, objects);
        this.context = context;
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent){
        APackage ourPackage = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        Button button = (Button) view.findViewById(R.id.add_button);
        TextView packageId = (TextView) view.findViewById(R.id.package_id);
        TextView packageLocation = (TextView) view.findViewById(R.id.package_lacation);
        TextView packageCompany = (TextView) view.findViewById(R.id.package_company);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
//                activity = (AppCompatActivity)context;
//                Intent intent = new Intent(activity, setLocation.class);
//                activity.startActivityForResult(intent, 1);
            }
        });
        packageId.setText(ourPackage.getPackageId());
        packageLocation.setText(ourPackage.getPackageLocation());
        packageCompany.setText(ourPackage.getPackageCompany());
        return view;
    }
}
