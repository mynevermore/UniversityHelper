package com.mynevermore.universityhelper.fragments.tasks;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.mynevermore.universityhelper.R;
import com.mynevermore.universityhelper.helpers.DBHelper;
import com.mynevermore.universityhelper.helpers.RecyclerItemClickListener;
import com.mynevermore.universityhelper.helpers.ViewAdapters.ListAdapter;
import com.mynevermore.universityhelper.model.Assignment;
import com.mynevermore.universityhelper.model.Task;

public class NewTaskListFragment extends Fragment {
    private EditText mListTitle;
    private Button mSaveList, mAddTask;
    private TextView mTitleText;
    private RecyclerView mRecyclerView;
    private ListAdapter mAdapter;
    private Bundle bundle;
    private int mAssignment, id;
    private DBHelper mDBHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        bundle = getArguments();
        mAssignment = 0;
        mDBHelper = new DBHelper(getContext());

        if(bundle != null) {
            mAssignment = bundle.getInt("assignment_id");
            id = bundle.getInt("task_id");
        }
        View view = inflater.inflate(R.layout.new_task_list, container, false);
        mAdapter = new ListAdapter();

        mListTitle = (EditText)view.findViewById(R.id.taskTitle);
        mSaveList = (Button)view.findViewById(R.id.addNoteButton);
        mAddTask = (Button)view.findViewById(R.id.newItem);
        mTitleText = (TextView)view.findViewById(R.id.listTitleText);
        mTitleText.setText(R.string.new_tasks);
        mRecyclerView = (RecyclerView)view.findViewById(android.R.id.list);

        if(bundle == null || mAssignment != 0) {
            mSaveList.setText(R.string.save_task_list);
            mSaveList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveTaskList();
                }
            });
        }
        else {
            Task task = mDBHelper.getTask(id);
            mListTitle.setText(task.getTitle());
            mTitleText.setText(R.string.edit_task_list);
            List<String> newList = new ArrayList<String>();
            newList.addAll(Arrays.asList(task.getText()));
            mAdapter.setTaskList(newList);
            mSaveList.setText(R.string.delete_task_list);
            mSaveList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteTaskList(bundle);
                }
            });
        }

        mAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTaskItem();
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        mAdapter.remove(position);
                    }
                })
        );

        return view;
    }

    private void deleteTaskList(Bundle bundle) {
        int id = bundle.getInt("task_id");
        DBHelper mDBhelper = new DBHelper(getContext());

        mDBhelper.deleteTask(id);

        Toast.makeText(getContext(), "Delete successful!", Toast.LENGTH_SHORT).show();
    }

    private void addTaskItem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Enter Task");
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mAdapter.update(input.getText().toString());
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void saveTaskList() {
        Task task = new Task();
        task.setTitle(mListTitle.getText().toString());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();

        sdf.setTimeZone(calendar.getTimeZone());

        task.setDate(sdf.format(calendar.getTime()));

        task.setText(mAdapter.getTaskList());
        task.setCompleted(new boolean[]{});

        DBHelper dbHelper = new DBHelper(getContext());

        dbHelper.addTask(task);
        Task check = dbHelper.getTaskTitle(task.getTitle());

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM assignments ORDER BY id DESC LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);
        int id = 0, tasks = 0;
        String dueDate = "", dueTime = "";

        if (cursor.moveToFirst()) {
            do {
                id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            } while (cursor.moveToNext());
        }
        cursor.close();

        if(mAssignment != 0){
            if(mAssignment > 0) {
                Assignment assignment = dbHelper.getAssignment(mAssignment);
                assignment.setTaskId(check.getId());
                dbHelper.updateAssignment(assignment);
            }
            else {
                Assignment assignment = dbHelper.getAssignment(id);
                assignment.setTaskId(check.getId());
                dbHelper.updateAssignment(assignment);
            }
        }

        Toast.makeText(getContext(), "Save successful!", Toast.LENGTH_SHORT).show();
    }
}
