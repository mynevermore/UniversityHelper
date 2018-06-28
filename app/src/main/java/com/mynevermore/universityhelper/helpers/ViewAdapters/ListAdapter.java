package com.mynevermore.universityhelper.helpers.ViewAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.mynevermore.universityhelper.R;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder>{
    private TextView mItemText;
    private TextView mDateText;

    private List<String> mTaskList = new ArrayList<String>(){};

    public ListAdapter(){}

    public class ListViewHolder extends RecyclerView.ViewHolder{
        public ListViewHolder(View itemView){
            super(itemView);

            mItemText = (TextView) itemView.findViewById(R.id.itemText);
        }

        public void BindList(String item){
            mItemText.setText(item);
        }
    }
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.text_list_item, parent, false);

        ListViewHolder viewHolder = new ListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        holder.BindList(mTaskList.get(position));
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    public void update(String item) {
        mTaskList.add(item);
        notifyDataSetChanged();
    }

    public String[] getTaskList(){
        return mTaskList.toArray(new String[mTaskList.size()]);
    }

    public void setTaskList(List<String> taskList) {
        mTaskList = taskList;
    }

    public void remove(int position){
        mTaskList.remove(position);
        notifyItemRemoved(position);
    }
}