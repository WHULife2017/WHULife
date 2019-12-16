package com.example.WhuLife.ui.coursenotice;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CourseNoticeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CourseNoticeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}