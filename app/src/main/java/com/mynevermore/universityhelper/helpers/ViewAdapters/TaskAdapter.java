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
import com.mynevermore.universityhelper.model.Task;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder>{
    private TextView mTitle;
    private TextView mDate;
    private List<Task> mTaskList;
    private Task[] mTasks = new Task[1];

    public TaskAdapter(){}

    public class TaskViewHolder extends RecyclerView.ViewHolder{
        public TaskViewHolder(View itemView){
            super(itemView);

            mTitle = (TextView) itemView.findViewById(R.id.itemText);
            mDate = (TextView) itemView.findViewById(R.id.dateText);
        }

        public void BindTaskList(Task task){
            mTitle.setText(task.getTitle());
            mDate.setText(task.getDate());
        }
    }
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.text_list_item, parent, false);

        TaskViewHolder viewHolder = new TaskViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        holder.BindTaskList(mTasks[position]);
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    public int getId(int id) {
        mTasks = mTaskList.toArray(new Task[mTaskList.size()]);
        return mTasks[id].getId();
    }

    public void collectData(Context context) {
        DBHelper myDBHelper = new DBHelper(context);

        mTaskList = myDBHelper.getAllTasks();

        if(mTaskList == null || mTaskList.isEmpty()){
            Task task = new Task();
            task.setTitle("Nothing here.");
            mTaskList.add(task);
            mTasks[0] = task;
        }
        else{
            mTasks = mTaskList.toArray(new Task[mTaskList.size()]);
        }
    }

    public void updateData(Context context){
        mTaskList.clear();
        collectData(context);
        notifyDataSetChanged();
    }
}