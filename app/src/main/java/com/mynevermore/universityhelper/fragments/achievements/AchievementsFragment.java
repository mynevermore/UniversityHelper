package com.mynevermore.universityhelper.fragments.achievements;//package au.edu.murdoch.ict376.universityquest.fragments.achievements;
//
//import android.app.Fragment;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.TextView;
//
//import java.util.List;
//
//import au.edu.murdoch.ict376.universityquest.R;
//import au.edu.murdoch.ict376.universityquest.helpers.DBHelper;
//import au.edu.murdoch.ict376.universityquest.helpers.ViewAdapters.AchievementAdapter;
//import au.edu.murdoch.ict376.universityquest.model.Achievement;
//

// For further development after the semester is completed.


//public class AchievementsFragment extends Fragment {
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.lists, container, false);
//
//        TextView titleText = (TextView) view.findViewById(R.id.listTitleText);
//        Button button = (Button) view.findViewById(R.id.addNoteButton);
//        button.setVisibility(View.GONE);
//        titleText.setText(R.string.achievements);
//        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(android.R.id.list);
//
//        final Achievement[] achievements = collectData();
//
//        final AchievementAdapter achievementAdapter = new AchievementAdapter(achievements);
//        mRecyclerView.setAdapter(achievementAdapter);
//
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        mRecyclerView.setLayoutManager(layoutManager);
//        return view;
//    }
//
//    private Achievement[] collectData() {
//        DBHelper myDBHelper = new DBHelper(getActivity());
//
//        List<Achievement> listAchievements;
//        listAchievements = myDBHelper.getAllAchievements();
//        Achievement[] achievements;
//
//        if (listAchievements != null && !listAchievements.isEmpty()) {
//            achievements = listAchievements.toArray(new Achievement[listAchievements.size()]);
//        } else {
//            achievements = new Achievement[1];
//            Achievement achievement = new Achievement();
//            achievement.setTitle("Nothing here.");
//            achievements[0] = achievement;
//        }
//
//        return achievements;
//
//    }
//}