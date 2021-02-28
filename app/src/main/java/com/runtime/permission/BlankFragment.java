package com.runtime.permission;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.runtime.permission.permission.helper.FragmentPermissionHelper;
import com.runtime.permission.permission.PermissionGroups;
import com.runtime.permission.utils.ActivityUtil;

public class BlankFragment extends Fragment {

    public static final String TAG = BlankFragment.class.getSimpleName();

    private View view;
    private Button smsPermissionButton;
    private FragmentPermissionHelper fragmentPermissionHelper;
    private String bundleData;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param bundle
     * @return A new instance of fragment AboutUsFragment.
     */
    public static BlankFragment newInstance(Bundle bundle) {
        BlankFragment fragment = new BlankFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bundleData = getArguments().getString("some_value");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_blank, container, false);
        initializeView();
        initializeObject();
        initializeEvent();
        return view;
    }

    private void initializeView() {
        smsPermissionButton = view.findViewById(R.id.smsPermissionButton);
    }

    private void initializeObject() {
        fragmentPermissionHelper = new FragmentPermissionHelper(getActivity(), getContext(),this);
    }

    private void initializeEvent() {
        smsPermissionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentPermissionHelper.hasSinglePermission(Manifest.permission.SEND_SMS))
                {
                    System.out.println("================= Permission Already Granted =================");
                }
                else
                {
                    System.out.println("================= Permission Is Not Granted. Request For Permission =================");
                    fragmentPermissionHelper.askSinglePermission(Manifest.permission.SEND_SMS, PermissionGroups.SMS_GROUP_REQUEST_CODE);
                }
            }
        });
    }

    /**
     * Callback received when a permissions request has been completed.
     * This method will be called when the user will tap on allow or deny
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode)
        {
            case PermissionGroups.SMS_GROUP_REQUEST_CODE:
                /*
                 * If request is cancelled, the grantResults arrays are empty.
                 */
                if(fragmentPermissionHelper.verifyPermission(grantResults))
                {
                    /*
                     * Permission is granted. Continue the action or workflow in your app.
                     */
                    System.out.println("================= SEND_SMS Permission Granted =================");
                }
                else
                {
                    /*
                     * Explain to the user that the feature is unavailable because the features requires a permission that the user has denied.
                     * At the same time, respect the user's decision. Don't link to system settings in an effort to convince the user to change their decision.
                     */
                    fragmentPermissionHelper.shouldShowRationale(
                            Manifest.permission.SEND_SMS,
                            PermissionGroups.SMS_GROUP_REQUEST_CODE,
                            R.drawable.permission_ic_sms,
                            "SMS Permission",
                            "Kindly allow SMS Permission, without this permission the app is unable to provide send message feature.",
                            "Kindly allow Camera Permission from Settings, without this permission the app is unable to provide send message feature."+"\n\n"+"Please turn on permissions at [Setting] -> [Permissions]"
                    );
                }
                break;
            default:
                System.out.println("================= something what wrong =================");
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PermissionGroups.PERMISSIONS_FROM_SETTING_REQUEST_CODE)
        {
            if(fragmentPermissionHelper.hasSinglePermission(PermissionGroups.SEND_SMS))
            {
                System.out.println("================= SEND_SMS Permission Already Granted =================");
                ActivityUtil.launchActivity(getActivity(),RuntimePermissionActivity.class);
            }
            else
            {
                System.out.println("================= Permission Is Not Granted. Request For Permission =================");
                fragmentPermissionHelper.askSinglePermission(PermissionGroups.SEND_SMS,PermissionGroups.SMS_GROUP_REQUEST_CODE);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}