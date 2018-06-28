package com.mynevermore.universityhelper.fragments.tutes;

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
import com.mynevermore.universityhelper.helpers.ViewAdapters.TuteAdapter;

public class TutesFragment extends Fragment {
    private TuteAdapter tuteAdapter = new TuteAdapter();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lists, container, false);

        TextView titleText = (TextView)view.findViewById(R.id.listTitleText);
        titleText.setText(R.string.classes_list);
        Button mAddButton = (Button)view.findViewById(R.id.addNoteButton);
        RecyclerView mRecyclerView = (RecyclerView)view.findViewById(android.R.id.list);

        mAddButton.setText(R.string.new_class);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTutes();
            }
        });

        tuteAdapter.collectData(getContext());
        mRecyclerView.setAdapter(tuteAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        int id = tuteAdapter.getId(position);
                        editTute(id);
                    }
                })
        );

        return view;
    }

    private void editTute(int id) {
        Bundle args = new Bundle();
        args.putInt("tute_id", id);

        AddTuteFragment addTuteFragment = new AddTuteFragment();
        addTuteFragment.setArguments(args);

        FragmentManager mFragmentManager = getFragmentManager();

        mFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, addTuteFragment)
                .addToBackStack(null)
                .commit();
    }

    private void addTutes() {
        AddTuteFragment addTuteFragment = new AddTuteFragment();
        FragmentManager mFragmentManager = getFragmentManager();

        mFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, addTuteFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(tuteAdapter != null){
            tuteAdapter.updateData(getContext());
        }
    }
}
