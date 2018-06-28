package com.mynevermore.universityhelper.fragments.assignments;

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
import com.mynevermore.universityhelper.helpers.ViewAdapters.AssignmentAdapter;

public class AssignmentsFragment extends Fragment {
    private AssignmentAdapter assignmentAdapter = new AssignmentAdapter();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lists, container, false);

        TextView titleText = (TextView) view.findViewById(R.id.listTitleText);
        titleText.setText(R.string.assignment_title);
        Button mAddButton = (Button) view.findViewById(R.id.addNoteButton);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(android.R.id.list);

        mAddButton.setText(R.string.add_new_assignment);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAssignment();
            }
        });
        assignmentAdapter.collectData(getContext());

        mRecyclerView.setAdapter(assignmentAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        viewAssignment(assignmentAdapter.getId(position));
                    }
                })
        );

        return view;
    }

    private void addAssignment() {
        AddAssignment addAssignment = new AddAssignment();
        FragmentManager mFragmentManager = getFragmentManager();

        mFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, addAssignment)
                .addToBackStack(null)
                .commit();
    }

    private void viewAssignment(int id){
        Bundle args = new Bundle();
        args.putInt("assignment_id", id);
        ViewAssignment viewAssignment = new ViewAssignment();
        viewAssignment.setArguments(args);

        FragmentManager mFragmentManager = getFragmentManager();

        mFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, viewAssignment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(assignmentAdapter != null){
            assignmentAdapter.updateData(getContext());
        }
    }
}
