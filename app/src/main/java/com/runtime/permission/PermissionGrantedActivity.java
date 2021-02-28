package com.runtime.permission;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.runtime.permission.utils.FragmentUtil;

public class PermissionGrantedActivity extends AppCompatActivity {

    BlankFragment blankFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_granted);
        Bundle bundle = new Bundle();
        bundle.putString("some_value", "black fragment");
        blankFragment =  BlankFragment.newInstance(bundle);
        FragmentUtil.setFragment(getSupportFragmentManager(), blankFragment, BlankFragment.TAG, R.id.container, true, true, false);
    }
}