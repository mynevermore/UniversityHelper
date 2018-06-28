package com.mynevermore.universityhelper.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mynevermore.universityhelper.R;
import com.mynevermore.universityhelper.fragments.assignments.AssignmentsFragment;
import com.mynevermore.universityhelper.fragments.grades.GradesFragment;
import com.mynevermore.universityhelper.fragments.notes.NotesFragment;
import com.mynevermore.universityhelper.fragments.tasks.TasksFragment;
import com.mynevermore.universityhelper.fragments.tutes.TutesFragment;

//import au.edu.murdoch.ict376.universityquest.fragments.achievements.AchievementsFragment;


public class HomeFragment extends Fragment {
    private SharedPreferences mSharedPreferences;
    private static final String KEY_STUDENTNAME = "KEY_STUDENTNAME";
    private static final String PREFS_FILE = "au.edu.murdoch.ict376.universityquest.preferences";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ImageView settingsButton = (ImageView)view.findViewById(R.id.settingsButton);
        ImageView emergencyButton = (ImageView)view.findViewById(R.id.emergencyButton);
        ImageView notesButton = (ImageView)view.findViewById(R.id.notesImageButton);
        ImageView tasksButton = (ImageView)view.findViewById(R.id.tasksImageButton);
        ImageView assignmentsButton = (ImageView)view.findViewById(R.id.assignmentsImageButton);
//      ImageView achievementsButton = (ImageView)view.findViewById(R.id.achievementsImageButton);
        ImageView classesButton = (ImageView)view.findViewById(R.id.classesImageButton);
        ImageView gradesButton = (ImageView)view.findViewById(R.id.gradesImageButton);
        TextView greetingText = (TextView)view.findViewById(R.id.greetingTextView);
        mSharedPreferences = this.getActivity().getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);

        if(mSharedPreferences.getString(KEY_STUDENTNAME, "").isEmpty()){
            greetingText.setText(R.string.hello_friend);
        }
        else
        {
            String temp = "Hello " + mSharedPreferences.getString(KEY_STUDENTNAME, "");
            greetingText.setText(temp);
        }

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsFragment settingsFragment = new SettingsFragment();
                FragmentManager mFragmentManager = getFragmentManager();

                mFragmentManager.beginTransaction()
                                .replace(R.id.fragmentContainer, settingsFragment)
                                .addToBackStack(null)
                                .commit();
            }
        });

        emergencyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmergencyFragment emergencyFragment = new EmergencyFragment();
                FragmentManager mFragmentManager = getFragmentManager();

                mFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, emergencyFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        notesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotesFragment notesFragment = new NotesFragment();
                FragmentManager mFragmentManager = getFragmentManager();

                mFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, notesFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        classesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TutesFragment tutesFragment = new TutesFragment();
                FragmentManager mFragmentManager = getFragmentManager();

                mFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, tutesFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        tasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TasksFragment tasksFragment = new TasksFragment();
                FragmentManager mFragmentManager = getFragmentManager();

                mFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, tasksFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        assignmentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssignmentsFragment assignmentsFragment = new AssignmentsFragment();
                FragmentManager mFragmentManager = getFragmentManager();

                mFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, assignmentsFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

//        achievementsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AchievementsFragment achievementsFragment = new AchievementsFragment();
//                FragmentManager mFragmentManager = getFragmentManager();
//
//                mFragmentManager.beginTransaction()
//                        .replace(R.id.fragmentContainer, achievementsFragment)
//                        .addToBackStack(null)
//                        .commit();
//            }
//        });

        gradesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GradesFragment gradesFragment = new GradesFragment();
                FragmentManager mFragmentManager = getFragmentManager();

                mFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, gradesFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }
}
