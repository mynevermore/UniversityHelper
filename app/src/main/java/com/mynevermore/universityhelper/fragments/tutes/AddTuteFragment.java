package com.mynevermore.universityhelper.fragments.tutes;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.CameraPosition;

import com.mynevermore.universityhelper.R;
import com.mynevermore.universityhelper.helpers.DBHelper;
import com.mynevermore.universityhelper.model.Tutes;

public class AddTuteFragment extends Fragment {//implements OnMapReadyCallback {
    private static final int REQUEST_CODE = 22;
    private int resultCode;
    private Intent intent;
    private EditText mTuteTitle, mClassType;
    private Button mDateTimePick, mSaveClass, mDeleteClass;
//    private GoogleMap mMap, map;
    private TextView mDayText, mTimeText;
    private CameraPosition mCameraPosition;
    private Bundle args;
    private DBHelper mDBhelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_tute, container, false);

        mTuteTitle = (EditText)view.findViewById(R.id.noteTitleEdit);
        mClassType = (EditText)view.findViewById(R.id.editClassType);
        mDateTimePick = (Button)view.findViewById(R.id.dateTimePick);
        mSaveClass = (Button)view.findViewById(R.id.saveNoteButton);
        mDeleteClass = (Button)view.findViewById(R.id.deleteClassButton);
        mDayText = (TextView)view.findViewById(R.id.dayText);
        mTimeText = (TextView)view.findViewById(R.id.timeText);
//        getGoogleMap();
        mDBhelper = new DBHelper(getContext());

        args = getArguments();

        if(args != null){
            int id = args.getInt("tute_id");
            Tutes tute = mDBhelper.getTutes(id);

            mTuteTitle.setText(tute.getTitle());
            mClassType.setText(tute.getTuteType());
            mDayText.setText(tute.getDay());
            mTimeText.setText(tute.getTime());
        }

        mDateTimePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newFragment();
            }
        });

        mSaveClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveClass();
            }
        });

        mDeleteClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteClass();
            }
        });

        return view;
    }

    private void newFragment(){
        DayPickerFragment dayPickerFragment = new DayPickerFragment();
        dayPickerFragment.setTargetFragment(this, REQUEST_CODE);
        dayPickerFragment.show(getActivity().getFragmentManager(), "datetimepick");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(getTargetRequestCode() == REQUEST_CODE){
            if(resultCode == getActivity().RESULT_OK){
                Bundle bundle = data.getExtras();
                String day = bundle.getString("weekday");
                String time = bundle.getString("time");
                mDayText.setText(day);
                mTimeText.setText(time);
            }
        }
    }

    private void deleteClass() {
        int id = args.getInt("tute_id");
        mDBhelper.deleteTutes(id);

        Toast.makeText(getContext(), "Delete successful!", Toast.LENGTH_SHORT).show();
    }

    private void saveClass() {
        Tutes tute = new Tutes();

        tute.setTitle(mTuteTitle.getText().toString());
        tute.setDay(mDayText.getText().toString());
        tute.setTime(mTimeText.getText().toString());
        tute.setTuteType(mClassType.getText().toString());
//        tute.setLocation(mLocation);

        mDBhelper.addTutes(tute);

        Toast.makeText(getContext(), "Save successful!", Toast.LENGTH_SHORT).show();
    }

//    private void getGoogleMap() {
//        if (mMap == null && getActivity() != null && getActivity().getFragmentManager()!= null) {
//            MapFragment mapFragment = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.mapview);
//            if (mapFragment != null) {
//                mapFragment.getMapAsync((OnMapReadyCallback) getActivity());
//            }
//        }
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        LatLng murdoch = new LatLng(-32.0678404,115.831348);
//
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(murdoch, 17));
//        map = googleMap;
//    }
}
