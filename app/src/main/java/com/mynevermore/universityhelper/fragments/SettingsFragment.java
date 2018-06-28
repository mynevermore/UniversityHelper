package com.mynevermore.universityhelper.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mynevermore.universityhelper.R;

public class SettingsFragment extends Fragment {
    // Shared Preferences used to store personal information about the student.
    // Name is used on home screen, emergency contact is used in the Emergency Fragment to call set contact.

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private static final String KEY_STUDENTNAME = "KEY_STUDENTNAME";
    private static final String KEY_STUDENTID = "KEY_STUDENTID";
    private static final String KEY_STUDENTMAJOR = "KEY_STUDENTMAJOR";
    private static final String KEY_EMERGCONTACT = "KEY_EMERGCONTACT";
    private static final String KEY_EMERGNUMBER = "KEY_EMERGNUMBER";
    private static final String PREFS_FILE = "au.edu.murdoch.ict376.universityquest.preferences";

    EditText nameEdit, idEdit, contactEdit, numberEdit, majorEdit;
    Button saveSettings;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // inflating the layout and setting the variables to the corresponding views.
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        nameEdit = (EditText)view.findViewById(R.id.editName);
        idEdit = (EditText)view.findViewById(R.id.editStudentId);
        majorEdit = (EditText)view.findViewById(R.id.editStudentMajor);
        contactEdit = (EditText)view.findViewById(R.id.editContact);
        numberEdit = (EditText)view.findViewById(R.id.editContactNumber);
        saveSettings = (Button)view.findViewById(R.id.saveButton);

        saveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveSharedPrefs();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Checking and setting shared preferences in the settings menu for updating.
        // setText will be blank if shared preference has not been set.
        SharedPrefsCheck();
        nameEdit.setText(mSharedPreferences.getString(KEY_STUDENTNAME,""));
        idEdit.setText(mSharedPreferences.getString(KEY_STUDENTID,""));
        majorEdit.setText(mSharedPreferences.getString(KEY_STUDENTMAJOR, ""));
        contactEdit.setText(mSharedPreferences.getString(KEY_EMERGCONTACT,""));
        numberEdit.setText(mSharedPreferences.getString(KEY_EMERGNUMBER,""));
    }

    public void SharedPrefsCheck(){
        // Checking for shared preferences, putting in empty strings if empty.

        mSharedPreferences = this.getActivity().getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mSharedPreferences.getString(KEY_STUDENTNAME,"");
        mSharedPreferences.getString(KEY_STUDENTID,"");
        mSharedPreferences.getString(KEY_EMERGCONTACT,"");
        mSharedPreferences.getString(KEY_EMERGNUMBER,"");
        mSharedPreferences.getString(KEY_STUDENTMAJOR, "");

        if(mSharedPreferences.getString(KEY_STUDENTNAME, "").isEmpty()){
            mEditor.putString(KEY_STUDENTNAME, "");
        }
        if(mSharedPreferences.getString(KEY_STUDENTID, "").isEmpty()){
            mEditor.putString(KEY_STUDENTID, "");
        }
        if(mSharedPreferences.getString(KEY_EMERGCONTACT, "").isEmpty()){
            mEditor.putString(KEY_EMERGCONTACT, "");
        }
        if(mSharedPreferences.getString(KEY_EMERGNUMBER, "").isEmpty()){
            mEditor.putString(KEY_EMERGNUMBER, "");
        }
        if(mSharedPreferences.getString(KEY_STUDENTMAJOR, "").isEmpty()){
            mEditor.putString(KEY_STUDENTMAJOR, "");
        }

        mEditor.apply();
    }

    public void SaveSharedPrefs(){
        // Saving the shared preferences to device.

        String name = nameEdit.getText().toString();
        String stid = idEdit.getText().toString();
        String emec = contactEdit.getText().toString();
        String emen = numberEdit.getText().toString();
        String stmaj = majorEdit.getText().toString();

        mEditor.putString(KEY_STUDENTNAME, name);
        mEditor.putString(KEY_STUDENTID, stid);
        mEditor.putString(KEY_EMERGCONTACT, emec);
        mEditor.putString(KEY_EMERGNUMBER, emen);
        mEditor.putString(KEY_STUDENTMAJOR, stmaj);
        mEditor.commit();
        Toast.makeText(getActivity(), "Your settings have been saved", Toast.LENGTH_SHORT).show();
    }
}
