package com.example.WhuLife.ui.coursenotice;

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

import com.example.WhuLife.R;
import com.example.WhuLife.course.EditTextActivity;
import com.example.WhuLife.course.MainActivity;

public class CourseNoticeFragment extends Fragment {

    private CourseNoticeViewModel courseNoticeViewModel;


    private TextView tv_1;
    private Button mBtnTextView;
    private Button mBtnEditText;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        courseNoticeViewModel =
                ViewModelProviders.of(this).get(CourseNoticeViewModel.class);
        View root = inflater.inflate(R.layout.course_activity_main, container, false);
        //final TextView textView = root.findViewById(R.id.text_coursenotice);
        mBtnEditText=(Button)root.findViewById(R.id.btn_course_edittext);
        mBtnEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), EditTextActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }
}