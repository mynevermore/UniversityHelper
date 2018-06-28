package com.mynevermore.universityhelper.fragments;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mynevermore.universityhelper.R;
import com.mynevermore.universityhelper.controller.MainActivity;

public class EmergencyFragment extends Fragment {
    private SharedPreferences mSharedPreferences;
    private static final String KEY_EMERGCONTACT = "KEY_EMERGCONTACT";
    private static final String KEY_EMERGNUMBER = "KEY_EMERGNUMBER";
    private static final String PREFS_FILE = "au.edu.murdoch.ict376.universityquest.preferences";
    private String contactNumber;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_emergency, container, false);
        ImageView sos = (ImageView)view.findViewById(R.id.sos);
        TextView holdCall = (TextView)view.findViewById(R.id.holdInstruction);
        mSharedPreferences = this.getActivity().getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);

        contactNumber = EmergencyCall(holdCall);

        sos.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(getActivity().checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    ((MainActivity)getActivity()).checkPermissions();
                    return false;
                }
                else {
                    Intent call = new Intent(Intent.ACTION_CALL);
                    call.setData(Uri.parse("tel:" + contactNumber));
                    startActivity(call);
                    return true;
                }
            }
        });
        return view;
    }

    private String EmergencyCall(TextView holdCall) {
        if(mSharedPreferences.getString(KEY_EMERGNUMBER,"").isEmpty() && mSharedPreferences.getString(KEY_EMERGCONTACT, "").isEmpty()){
            contactNumber = "000";
            holdCall.setText(R.string.hold_to_call_000);
        }
        else{
            contactNumber = mSharedPreferences.getString(KEY_EMERGNUMBER, "");
            holdCall.setText("Hold to call " + mSharedPreferences.getString(KEY_EMERGCONTACT, ""));
        }
        return contactNumber;
    }
}
