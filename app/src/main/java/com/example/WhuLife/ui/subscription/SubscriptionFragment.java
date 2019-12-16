package com.example.WhuLife.ui.subscription;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.WhuLife.R;
import com.example.WhuLife.subscription.GetNewsActivity;
import com.example.WhuLife.subscription.NoticeActivity;
import com.example.WhuLife.subscription.PreferenceActivity;

public class SubscriptionFragment extends Fragment {

    private SubscriptionViewModel subscriptionViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        subscriptionViewModel =
                ViewModelProviders.of(this).get(SubscriptionViewModel.class);
        View root = inflater.inflate(R.layout.subscription_activity_main, container, false);

        Button button1=(Button)root.findViewById(R.id.button_preference);
        Button button2=(Button)root.findViewById(R.id.button_changepreference);
        Button button3=(Button)root.findViewById(R.id.button_setnotice);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), PreferenceActivity.class);
                Intent intent2=new Intent(getActivity(), GetNewsActivity.class);
                if(IsPreferenceEmpty())
                    startActivity(intent);
                else
                    startActivity(intent2);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), PreferenceActivity.class);
                startActivity(intent);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), NoticeActivity.class);
                startActivity(intent);
            }
        });
        final TextView textView = root.findViewById(R.id.text_subscription);
//        subscriptionViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//
//            }
//        });

        return root;
    }

    /*判断用户是否已经设置了自己的喜好信息，如果没设置的话则紧喜好设置，否则跳转进推送主页*/
    protected boolean IsPreferenceEmpty(){
        SharedPreferences pref=getContext().getSharedPreferences("data",Context.MODE_PRIVATE);
        /*其实理论上如果还没有设置的话应该没有该文件 但也是返回true 为空*/
        if(pref.getString("Status","None").equals("edited"))
            return false;
        else
            return true;
//        FileInputStream in=null;
//        BufferedReader reader=null;
//        try{
//            in=openFileInput("data");
//            reader=new BufferedReader(new InputStreamReader(in));
//            /*read()读取的是字节数*/
//            if(reader.read()!=0)
//                return false;
//            else
//                return true;
//        }catch (IOException e){
//            e.printStackTrace();
//        }finally {
//            try{
//                if(reader!=null){
//                    reader.close();
//                }
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//        }
//        return true;//读取发生异常 很可能没有该文件需要重新设置

    }
}