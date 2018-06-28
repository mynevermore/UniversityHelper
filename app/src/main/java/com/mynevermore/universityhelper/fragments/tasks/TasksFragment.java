package com.mynevermore.universityhelper.fragments.tasks;

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
import com.mynevermore.universityhelper.helpers.ViewAdapters.TaskAdapter;

public class TasksFragment extends Fragment {
    private TaskAdapter taskAdapter = new TaskAdapter();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lists, container, false);

        TextView titleText = (TextView)view.findViewById(R.id.listTitleText);
        titleText.setText(R.string.task_lists_title);
        Button mAddButton = (Button)view.findViewById(R.id.addNoteButton);
        RecyclerView mRecyclerView = (RecyclerView)view.findViewById(android.R.id.list);

        mAddButton.setText(R.string.new_task_list);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });

        taskAdapter.collectData(getContext());
        mRecyclerView.setAdapter(taskAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        viewTasks(taskAdapter.getId(position));
                    }
                })
        );

        return view;
    }

    private void viewTasks(int id) {
        Bundle args = new Bundle();
        args.putInt("task_id", id);

        NewTaskListFragment newTaskListFragment = new NewTaskListFragment();
        newTaskListFragment.setArguments(args);

        FragmentManager mFragmentManager = getFragmentManager();

        mFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, newTaskListFragment)
                .addToBackStack(null)
                .commit();
    }

    private void addTask() {
        NewTaskListFragment newTaskListFragment = new NewTaskListFragment();
        FragmentManager mFragmentManager = getFragmentManager();

        mFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, newTaskListFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(taskAdapter != null){
            taskAdapter.updateData(getContext());
        }
    }
}
