package com.runtime.permission;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.runtime.permission.permission.PermissionGroups;
import com.runtime.permission.permission.helper.ActivityPermissionHelper;
import com.runtime.permission.utils.ActivityUtil;

public class RuntimePermissionActivity extends AppCompatActivity {

    private Button singlePermission, multiplePermission;
    private ActivityPermissionHelper activityPermissionHelper;

    public static final int MULTIPLE_PERMISSIONS_GROUP_REQUEST_CODE     = 2006;
    public static final String[] MULTIPLE_PERMISSIONS_GROUP             =
            {
                    PermissionGroups.READ_CALENDAR,
                    PermissionGroups.WRITE_CALENDAR,

                    PermissionGroups.CAMERA,

                    PermissionGroups.READ_CONTACTS,
                    PermissionGroups.WRITE_CONTACTS,
                    PermissionGroups.GET_ACCOUNTS,

                    PermissionGroups.ACCESS_FINE_LOCATION,
                    PermissionGroups.ACCESS_COARSE_LOCATION,
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runtime_permission);
        initializeView();
        initializeObject();
        initializeEvent();
    }

    private void initializeView() {
        singlePermission    = findViewById(R.id.singlePermission);
        multiplePermission  = findViewById(R.id.multiplePermission);
    }

    private void initializeObject() {
        activityPermissionHelper = new ActivityPermissionHelper(this,getApplicationContext());
    }

    private void initializeEvent() {
        singlePermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activityPermissionHelper.hasSinglePermission(Manifest.permission.READ_EXTERNAL_STORAGE))
                {
                    System.out.println("================= Permission Already Granted =================");
                    Intent intent = new Intent(RuntimePermissionActivity.this, PermissionGrantedActivity.class);
                    startActivity(intent);
                }
                else
                {
                    System.out.println("================= Permission Is Not Granted. Request For Permission =================");
                    activityPermissionHelper.askSinglePermission(Manifest.permission.READ_EXTERNAL_STORAGE,PermissionGroups.STORAGE_GROUP_REQUEST_CODE);
                }
            }
        });

        multiplePermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(activityPermissionHelper.hasMultiplePermissions(MULTIPLE_PERMISSIONS_GROUP))
                {
                    System.out.println("================= Permission Already Granted =================");
                    Intent intent = new Intent(RuntimePermissionActivity.this, PermissionGrantedActivity.class);
                    startActivity(intent);
                }
                else
                {
                    System.out.println("================= Permission Is Not Granted. Request For Permission =================");
                    activityPermissionHelper.askMultiplePermission(MULTIPLE_PERMISSIONS_GROUP,MULTIPLE_PERMISSIONS_GROUP_REQUEST_CODE);
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
            case PermissionGroups.STORAGE_GROUP_REQUEST_CODE :
                /*
                 * If request is cancelled, the grantResults arrays are empty.
                 */
                if(activityPermissionHelper.verifyPermission(grantResults))
                {
                    /*
                     * Permission is granted. Continue the action or workflow in your app.
                     */
                    System.out.println("================= READ_EXTERNAL_STORAGE Permission Granted =================");
                    Intent intent = new Intent(RuntimePermissionActivity.this, PermissionGrantedActivity.class);
                    startActivity(intent);
                }
                else
                {
                    /*
                     * Explain to the user that the feature is unavailable because the features requires a permission that the user has denied.
                     * At the same time, respect the user's decision. Don't link to system settings in an effort to convince the user to change their decision.
                     */
                    activityPermissionHelper.shouldShowRationale(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            PermissionGroups.STORAGE_GROUP_REQUEST_CODE,
                            R.drawable.permission_ic_storage,
                            "Storage Permission",
                            "Kindly allow Storage Permission, without this permission the app is unable to provide file read write feature.",
                            "Kindly allow Storage Permission from Settings, without this permission the app is unable to provide file read write feature."+"\n\n"+"Please turn on permissions at [Setting] -> [Permissions]"
                    );
                }
                break;
            case MULTIPLE_PERMISSIONS_GROUP_REQUEST_CODE :
                if (grantResults.length > 0)
                {
                    for (int i = 0; i < permissions.length; i++)
                    {
                        switch (permissions[i])
                        {
                            case Manifest.permission.READ_CALENDAR:
                                if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                                {
                                    /*
                                     * Permission is granted. Continue the action or workflow in your app.
                                     */
                                    System.out.println("================= CALENDAR Permission Granted =================");
                                }
                                else
                                {
                                    activityPermissionHelper.shouldShowRationale(
                                            Manifest.permission.READ_CALENDAR,
                                            PermissionGroups.CALENDAR_GROUP_REQUEST_CODE,
                                            R.drawable.permission_ic_calendar,
                                            "Calendar Permission",
                                            "Kindly allow Calendar Permission, without this permission the app is unable to provide select date feature.",
                                            "Kindly allow Calendar Permission from Settings, without this permission the app is unable to provide select date feature."+"\n\n"+"Please turn on permissions at [Setting] -> [Permissions]"
                                    );
                                }
                                break;
                            case Manifest.permission.CAMERA:
                                if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                                {
                                    /*
                                     * Permission is granted. Continue the action or workflow in your app.
                                     */
                                    System.out.println("================= CAMERA Permission Granted =================");
                                }
                                else
                                {
                                    activityPermissionHelper.shouldShowRationale(
                                            Manifest.permission.CAMERA,
                                            PermissionGroups.CAMERA_GROUP_REQUEST_CODE,
                                            R.drawable.permission_ic_camera,
                                            "Camera Permission",
                                            "Kindly allow Camera Permission, without this permission the app is unable to provide photo capture feature.",
                                            "Kindly allow Camera Permission from Settings, without this permission the app is unable to provide photo capture feature."+"\n\n"+"Please turn on permissions at [Setting] -> [Permissions]"
                                    );
                                }
                                break;
                            case Manifest.permission.READ_CONTACTS:
                                if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                                {
                                    /*
                                     * Permission is granted. Continue the action or workflow in your app.
                                     */
                                    System.out.println("================= READ_CONTACTS Permission Granted =================");
                                }
                                else
                                {
                                    activityPermissionHelper.shouldShowRationale(
                                            Manifest.permission.READ_CONTACTS,
                                            PermissionGroups.CONTACTS_GROUP_REQUEST_CODE,
                                            R.drawable.permission_ic_contacts,
                                            "Contacts Permission",
                                            "Kindly allow Contact Permission, without this permission the app is unable to provide contact read write feature.",
                                            "Kindly allow Contact Permission from Settings, without this permission the app is unable to provide contact read write feature."+"\n\n"+"Please turn on permissions at [Setting] -> [Permissions]"
                                    );
                                }
                                break;
                            case Manifest.permission.ACCESS_COARSE_LOCATION:
                                if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                                {
                                    /*
                                     * Permission is granted. Continue the action or workflow in your app.
                                     */
                                    System.out.println("================= ACCESS_COARSE_LOCATION Permission Granted =================");
                                }
                                else
                                {
                                    activityPermissionHelper.shouldShowRationale(
                                            Manifest.permission.ACCESS_COARSE_LOCATION,
                                            PermissionGroups.LOCATION_GROUP_REQUEST_CODE,
                                            R.drawable.permission_ic_location,
                                            "Location Permission",
                                            "Kindly allow Location Permission, without this permission the app is unable to provide your current location feature.",
                                            "Kindly allow Location Permission from Settings, without this permission the app is unable to provide your current location feature."+"\n\n"+"Please turn on permissions at [Setting] -> [Permissions]"
                                    );
                                }
                                break;
                        }
                    }
                }
                break;
            default:
                System.out.println("================= something what wrong =================");
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        System.out.println("================= READ_EXTERNAL_STORAGE Permission Already Granted =================");
        if (requestCode == PermissionGroups.PERMISSIONS_FROM_SETTING_REQUEST_CODE)
        {
            if(activityPermissionHelper.hasSinglePermission(PermissionGroups.READ_EXTERNAL_STORAGE))
            {
                System.out.println("================= READ_EXTERNAL_STORAGE Permission Already Granted =================");
                ActivityUtil.launchActivity(this,PermissionGrantedActivity.class);
            }
            else
            {
                System.out.println("================= Permission Is Not Granted. Request For Permission =================");
                activityPermissionHelper.askSinglePermission(PermissionGroups.READ_EXTERNAL_STORAGE,PermissionGroups.STORAGE_GROUP_REQUEST_CODE);
            }

            if(activityPermissionHelper.hasSinglePermission(PermissionGroups.READ_CALENDAR))
            {
                System.out.println("================= READ_CALENDAR Permission Already Granted =================");
            }
            else
            {
                System.out.println("================= Permission Is Not Granted. Request For Permission =================");
               // activityPermissionHelper.askSinglePermission(PermissionGroups.READ_CALENDAR,PermissionGroups.CALENDAR_GROUP_REQUEST_CODE);
            }

            if(activityPermissionHelper.hasSinglePermission(PermissionGroups.CAMERA))
            {
                System.out.println("================= CAMERA Permission Already Granted =================");
            }
            else
            {
                System.out.println("================= Permission Is Not Granted. Request For Permission =================");
                //activityPermissionHelper.askSinglePermission(PermissionGroups.CAMERA,PermissionGroups.CAMERA_GROUP_REQUEST_CODE);
            }

            if(activityPermissionHelper.hasSinglePermission(PermissionGroups.READ_CONTACTS))
            {
                System.out.println("================= READ_CONTACTS Permission Already Granted =================");
            }
            else
            {
                System.out.println("================= Permission Is Not Granted. Request For Permission =================");
                //activityPermissionHelper.askSinglePermission(PermissionGroups.READ_CONTACTS,PermissionGroups.CONTACTS_GROUP_REQUEST_CODE);
            }


            if(activityPermissionHelper.hasSinglePermission(PermissionGroups.ACCESS_FINE_LOCATION))
            {
                System.out.println("================= ACCESS_FINE_LOCATION Permission Already Granted =================");
            }
            else
            {
                System.out.println("================= Permission Is Not Granted. Request For Permission =================");
                //activityPermissionHelper.askSinglePermission(PermissionGroups.ACCESS_FINE_LOCATION,PermissionGroups.LOCATION_GROUP_REQUEST_CODE);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}