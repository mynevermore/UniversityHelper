package com.mynevermore.universityhelper.helpers.ViewAdapters;//package au.edu.murdoch.ict376.universityquest.helpers.ViewAdapters;
//
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CheckBox;
//import android.widget.TextView;
//
//import au.edu.murdoch.ict376.universityquest.R;
////import au.edu.murdoch.ict376.universityquest.model.Achievement;
//
//public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.AchievementViewHolder>{
//    private TextView mTitle;
//    private CheckBox mCompleted;
//
//    private Achievement[] mAchievements;
//
//    public AchievementAdapter(Achievement[] achievements){
//        mAchievements = achievements;
//    }
//
//    public class AchievementViewHolder extends RecyclerView.ViewHolder{
//        public AchievementViewHolder(View itemView){
//            super(itemView);
//
//            mTitle = (TextView) itemView.findViewById(R.id.itemText);
//            mCompleted = (CheckBox) itemView.findViewById(R.id.completedBox);
//        }
//
//        public void BindAchievement(Achievement achievement){
//            mTitle.setText(achievement.getTitle());
//            mCompleted.setChecked(achievement.isCompleted());
//        }
//    }
//    public AchievementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.check_list_item, parent, false);
//
//        AchievementViewHolder viewHolder = new AchievementViewHolder(view);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(AchievementViewHolder holder, int position) {
//        holder.BindAchievement(mAchievements[position]);
//    }
//
//    @Override
//    public int getItemCount() {
//        return mAchievements.length;
//    }
//}