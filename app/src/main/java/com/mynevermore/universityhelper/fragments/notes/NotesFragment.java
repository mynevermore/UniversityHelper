package com.mynevermore.universityhelper.fragments.notes;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mynevermore.universityhelper.R;
import com.mynevermore.universityhelper.helpers.RecyclerItemClickListener;
import com.mynevermore.universityhelper.helpers.ViewAdapters.NoteAdapter;

public class NotesFragment extends Fragment {
    private NoteAdapter noteAdapter = new NoteAdapter();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lists, container, false);

        TextView mTitleText = (TextView)view.findViewById(R.id.listTitleText);
        Button mAddButton = (Button)view.findViewById(R.id.addNoteButton);
        RecyclerView mRecyclerView = (RecyclerView)view.findViewById(android.R.id.list);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote();
            }
        });

        noteAdapter.collectData(getContext());
        mRecyclerView.setAdapter(noteAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        viewNote(noteAdapter.getId(position));
                    }
                })
        );

        return view;
    }

    private void viewNote(int id) {
        Bundle args = new Bundle();
        args.putInt("note_id", id);

        AddNoteFragment addNoteFragment = new AddNoteFragment();
        addNoteFragment.setArguments(args);

        FragmentManager mFragmentManager = getFragmentManager();

        mFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, addNoteFragment)
                .addToBackStack(null)
                .commit();
    }



    private void addNote() {
        AddNoteFragment addNoteFragment = new AddNoteFragment();
        FragmentManager mFragmentManager = getFragmentManager();

        mFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, addNoteFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(noteAdapter != null){
            noteAdapter.updateData(getContext());
        }
    }
}
