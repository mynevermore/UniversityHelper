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
import com.mynevermore.universityhelper.model.Tutes;

public class TuteAdapter extends RecyclerView.Adapter<TuteAdapter.TuteViewHolder>{
    private TextView mItemText;
    private TextView mDateText;

    private List<Tutes> mTutesList;
    private Tutes[] mTutes = new Tutes[1];

    public TuteAdapter(){}

    public class TuteViewHolder extends RecyclerView.ViewHolder{
        public TuteViewHolder(View itemView){
            super(itemView);

            mItemText = (TextView) itemView.findViewById(R.id.itemText);
            mDateText = (TextView) itemView.findViewById(R.id.dateText);
        }

        public void BindTutes(Tutes tute){
            mItemText.setText(tute.getTitle());
            mDateText.setText(tute.getDay());
        }
    }
    public TuteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.text_list_item, parent, false);

        TuteViewHolder viewHolder = new TuteViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TuteViewHolder holder, int position) {
        holder.BindTutes(mTutes[position]);
    }

    @Override
    public int getItemCount() {
        return mTutesList.size();
    }

    public int getId(int id) {
        mTutes = mTutesList.toArray(new Tutes[mTutesList.size()]);
        return mTutes[id].getId();
    }

    public void collectData(Context context) {
        DBHelper myDBHelper = new DBHelper(context);

        mTutesList = myDBHelper.getAllTutes();

        if(mTutesList == null || mTutesList.isEmpty()){
            Tutes tute = new Tutes();
            tute.setTitle("Nothing here.");
            mTutesList.add(tute);
            mTutes[0] = tute;
        }
        else{
            mTutes = mTutesList.toArray(new Tutes[mTutesList.size()]);
        }
    }

    public void updateData(Context context){
        mTutesList.clear();
        collectData(context);
        notifyDataSetChanged();
    }
}