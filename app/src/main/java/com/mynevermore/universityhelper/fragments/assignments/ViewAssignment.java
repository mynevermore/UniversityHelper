package com.mynevermore.universityhelper.fragments.assignments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mynevermore.universityhelper.R;
import com.mynevermore.universityhelper.fragments.tasks.NewTaskListFragment;
import com.mynevermore.universityhelper.helpers.DBHelper;
import com.mynevermore.universityhelper.model.Assignment;

public class ViewAssignment extends Fragment {
    private TextView mDueDate, mDueTime;
    private EditText mAssignmentTitle, mAssignmentNotes;
    private Button mTaskList, mSaveEdits, mDeleteAssignment;
    private Bundle mBundle;
    private Assignment mAssignment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_assignment, container, false);
        mAssignmentTitle = (EditText)view.findViewById(R.id.noteTitleEdit);
        mAssignmentNotes = (EditText)view.findViewById(R.id.assignmentNotes);
        mTaskList = (Button)view.findViewById(R.id.setTaskIdButton);
        mSaveEdits = (Button)view.findViewById(R.id.saveNoteButton);
        mDeleteAssignment = (Button)view.findViewById(R.id.deleteAssignmentButton);
        mDueDate = (TextView)view.findViewById(R.id.dayText);
        mDueTime = (TextView)view.findViewById(R.id.timeText);

        mBundle = getArguments();
        mAssignment = new Assignment();

        DBHelper mDBHelper = new DBHelper(getContext());

        mAssignment = mDBHelper.getAssignment(mBundle.getInt("assignment_id"));
        mAssignmentTitle.setText(mAssignment.getTitle());
        mAssignmentNotes.setText(mAssignment.getText());
        mDueDate.setText(mAssignment.getDueDate());
        mDueTime.setText(mAssignment.getDueTime());

        mTaskList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewTaskList();
            }
        });

        mSaveEdits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAssignment();
            }
        });

        mDeleteAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAssignment();
            }
        });

        return view;
    }

    private void deleteAssignment() {
        DBHelper mDBHelper = new DBHelper(getContext());

        mDBHelper.deleteAssignment(mAssignment.getId());
        Toast.makeText(getContext(), "Delete successful!", Toast.LENGTH_SHORT).show();
    }

    private void saveAssignment() {
        mAssignment.setTitle(mAssignmentTitle.getText().toString());
        mAssignment.setText(mAssignmentNotes.getText().toString());
        mAssignment.setCompleted(false);
        DBHelper mDBHelper = new DBHelper(getContext());

        mDBHelper.updateAssignment(mAssignment);

        Toast.makeText(getContext(), "Save successful!", Toast.LENGTH_SHORT).show();
    }

    private void viewTaskList() {
        Bundle bundle = new Bundle();
        bundle.putInt("assignment_id", mAssignment.getId());

        NewTaskListFragment taskListFragment = new NewTaskListFragment();
        taskListFragment.setArguments(bundle);

        FragmentManager mFragmentManager = getFragmentManager();

        mFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, taskListFragment)
                .addToBackStack(null)
                .commit();
    }
}
