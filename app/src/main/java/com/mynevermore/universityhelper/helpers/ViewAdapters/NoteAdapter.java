package com.mynevermore.universityhelper.helpers.ViewAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.mynevermore.universityhelper.R;
import com.mynevermore.universityhelper.helpers.DBHelper;
import com.mynevermore.universityhelper.model.Note;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder>{
    private TextView mItemText;
    private TextView mDateText;

    private List<Note> mNoteList = new ArrayList<Note>();
    private Note[] mNotes = new Note[1];

    public NoteAdapter(){}

    public class NoteViewHolder extends RecyclerView.ViewHolder{
        public NoteViewHolder(View itemView){
            super(itemView);

            mItemText = (TextView) itemView.findViewById(R.id.itemText);
            mDateText = (TextView) itemView.findViewById(R.id.dateText);
        }

        public void BindNotes(Note note){
            mItemText.setText(note.getTitle());
            mDateText.setText(note.getDate());
        }
    }
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.text_list_item, parent, false);

        NoteViewHolder viewHolder = new NoteViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        holder.BindNotes(mNotes[position]);
    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }

    public int getId(int id) {
        mNotes = mNoteList.toArray(new Note[mNoteList.size()]);
        return mNotes[id].getId();
    }

    public void collectData(Context context) {
        DBHelper myDBHelper = new DBHelper(context);

        mNoteList = myDBHelper.getAllNotes();

        if(mNoteList == null || mNoteList.isEmpty()){
            Note note = new Note();
            note.setTitle("Nothing here.");
            mNoteList.add(note);
            mNotes[0] = note;
        }
        else{
            mNotes = mNoteList.toArray(new Note[mNoteList.size()]);
        }
    }

    public void updateData(Context context){
        mNoteList.clear();
        collectData(context);
        notifyDataSetChanged();
    }
}