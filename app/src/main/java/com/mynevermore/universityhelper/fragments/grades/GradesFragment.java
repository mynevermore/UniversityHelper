package com.mynevermore.universityhelper.fragments.grades;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mynevermore.universityhelper.R;
import com.mynevermore.universityhelper.helpers.DBHelper;
import com.mynevermore.universityhelper.helpers.RecyclerItemClickListener;
import com.mynevermore.universityhelper.helpers.ViewAdapters.GradeAdapter;
import com.mynevermore.universityhelper.model.Grade;

public class GradesFragment extends Fragment {
    private GradeAdapter gradeAdapter = new GradeAdapter();
    private RecyclerView mRecyclerView;
    private Button addUnitButton;
    private String mark = "";
    private TextView mGPAText;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grade, container, false);

        mGPAText = (TextView) view.findViewById(R.id.gpaTextView);
        mRecyclerView = (RecyclerView)view.findViewById(android.R.id.list);
        addUnitButton = (Button)view.findViewById(R.id.addUnitButton);

        addUnitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGrade();
            }
        });
        gradeAdapter.collectData(getContext());

        mRecyclerView.setAdapter(gradeAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        deleteGrade(gradeAdapter.getId(position));
                        gradeAdapter.updateData(getContext());
                        mGPAText.setText(gradeAdapter.calcGpa() + "");
                        Toast.makeText(getContext(), "Unit Deleted", Toast.LENGTH_SHORT);
                    }
                })
        );
        mGPAText.setText(gradeAdapter.calcGpa() + "");
        return view;
    }

    private void deleteGrade(int id) {
        DBHelper myDBHelper = new DBHelper(getActivity());
        myDBHelper.deleteGrade(id);
    }

    private void addGrade() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Enter Unit Details");

        final DBHelper myDBHelper = new DBHelper(getActivity());
        final EditText input = new EditText(getActivity());
        final String[] marks = {"HD", "D", "C", "P", "F"};

        final ArrayAdapter<String> adp = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, marks);

        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setSingleChoiceItems(marks, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which) {
                    case 0:
                        mark = "HD";
                        break;
                    case 1:
                        mark = "D";
                        break;
                    case 2:
                        mark = "C";
                        break;
                    case 3:
                        mark = "P";
                        break;
                    case 4:
                        mark = "F";
                        break;
                    default:
                        return;
                }
            }
        });

        builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String unitName = "";
                unitName = input.getText().toString();

                Grade grade = new Grade();
                grade.setUnit(unitName);
                grade.setGrade(mark);
                myDBHelper.addGrade(grade);
                gradeAdapter.updateData(getContext());
                mGPAText.setText(gradeAdapter.calcGpa() + "");
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

    @Override
    public void onResume() {
        super.onResume();
        if(gradeAdapter != null){
            gradeAdapter.updateData(getContext());
            mGPAText.setText(gradeAdapter.calcGpa() + "");
        }
    }
}
