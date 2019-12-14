package com.example.WhuLife.ui.coursenotice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.WhuLife.R;

public class CourseNoticeFragment extends Fragment {

    private CourseNoticeViewModel courseNoticeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        courseNoticeViewModel =
                ViewModelProviders.of(this).get(CourseNoticeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_coursenotice, container, false);
        final TextView textView = root.findViewById(R.id.text_coursenotice);
        courseNoticeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}