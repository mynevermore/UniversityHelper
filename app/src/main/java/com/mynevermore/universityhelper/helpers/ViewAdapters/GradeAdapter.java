package com.mynevermore.universityhelper.helpers.ViewAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.mynevermore.universityhelper.R;
import com.mynevermore.universityhelper.helpers.DBHelper;
import com.mynevermore.universityhelper.model.Grade;

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.GradeViewHolder>{
    private TextView mUnit;
    private TextView mGrade;
    private List<Grade> mGradeList;
    private Grade[] mGrades = new Grade[1];

    public GradeAdapter(){}

    public class GradeViewHolder extends RecyclerView.ViewHolder{
        public GradeViewHolder(View itemView){
            super(itemView);

            mUnit = (TextView) itemView.findViewById(R.id.itemText);
            mGrade = (TextView) itemView.findViewById(R.id.dateText);
        }

        public void BindGrades(Grade grade){
            mUnit.setText(grade.getUnit());
            mGrade.setText(grade.getGrade());
        }
    }
    public GradeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.text_list_item, parent, false);

        GradeViewHolder viewHolder = new GradeViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GradeViewHolder holder, int position) {
        holder.BindGrades(mGrades[position]);
    }

    @Override
    public int getItemCount() {
        return mGradeList.size();
    }

    public int getId(int id) {
        mGrades = mGradeList.toArray(new Grade[mGradeList.size()]);
        return mGrades[id].getId();
    }

    public void collectData(Context context) {
        DBHelper myDBHelper = new DBHelper(context);

        mGradeList = myDBHelper.getAllGrades();

        if(mGradeList == null || mGradeList.isEmpty()){
            Grade grade = new Grade();
            grade.setUnit("Nothing here.");
            mGradeList.add(grade);
            mGrades[0] = grade;
        }
        else{
            mGrades = mGradeList.toArray(new Grade[mGradeList.size()]);
        }
    }

    public double calcGpa(){
        double gpa = 0.0;
        String mark = "";

        if(mGrades != null) {
            for (int i = 0; i < mGrades.length; i++) {
                if(mGrades[i].getGrade() == null){
                    mGrades[i].setGrade(mark);
                }

                switch (mGrades[i].getGrade()) {
                    case "HD":
                        gpa += 4.0;
                        break;
                    case "D":
                        gpa += 3.0;
                        break;
                    case "C":
                        gpa += 2.0;
                        break;
                    case "P":
                        gpa += 1.0;
                        break;
                    default:
                        gpa += 0;
                }
            }
        }

        if(mGrades.length > 0) {
            gpa = gpa / mGrades.length;
        }
        else {
            gpa = 0.0;
        }
        gpa = Math.round(gpa * 100);
        gpa = gpa / 100;

        return gpa;
    }

    public void updateData(Context context){
        mGradeList.clear();
        collectData(context);
        notifyDataSetChanged();
    }
}