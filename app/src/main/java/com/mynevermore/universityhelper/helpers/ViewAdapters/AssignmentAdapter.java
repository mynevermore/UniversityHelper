package com.mynevermore.universityhelper.helpers.ViewAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import com.mynevermore.universityhelper.R;
import com.mynevermore.universityhelper.helpers.DBHelper;
import com.mynevermore.universityhelper.model.Assignment;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder>{
    private TextView mItemText;
    private TextView mDateText;
    private CheckBox mCompleted;
    private List<Assignment> mAssignmentList;
    private Assignment[] mAssignments = new Assignment[1];

    public AssignmentAdapter(){}

    public class AssignmentViewHolder extends RecyclerView.ViewHolder{
        public AssignmentViewHolder(View itemView){
            super(itemView);

            mItemText = (TextView) itemView.findViewById(R.id.itemText);
            mDateText = (TextView) itemView.findViewById(R.id.dateText);
            mCompleted = (CheckBox) itemView.findViewById(R.id.completedBox);
        }

        public void BindAssignment(Assignment assignment){
            mItemText.setText(assignment.getTitle());
            mDateText.setText(assignment.getDueDate());
            mCompleted.setChecked(assignment.isCompleted());
        }
    }
    public AssignmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.check_list_item, parent, false);

        AssignmentViewHolder viewHolder = new AssignmentViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AssignmentViewHolder holder, int position) {
        holder.BindAssignment(mAssignments[position]);
    }

    @Override
    public int getItemCount() {
        return mAssignmentList.size();
    }

    public int getId(int id) {
        mAssignments = mAssignmentList.toArray(new Assignment[mAssignmentList.size()]);
        return mAssignments[id].getId();
    }

    public void collectData(Context context) {
        DBHelper myDBHelper = new DBHelper(context);

        mAssignmentList = myDBHelper.getAllAssignments();

        if(mAssignmentList == null || mAssignmentList.isEmpty()){
            Assignment assignment = new Assignment();
            assignment.setTitle("Nothing here.");
            mAssignmentList.add(assignment);
            mAssignments[0] = assignment;
        }
        else{
            mAssignments = mAssignmentList.toArray(new Assignment[mAssignmentList.size()]);
        }
    }

    public void updateData(Context context){
        mAssignmentList.clear();
        collectData(context);
        notifyDataSetChanged();
    }
}