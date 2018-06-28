package com.mynevermore.universityhelper.fragments.notes;

import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import com.mynevermore.universityhelper.R;
import com.mynevermore.universityhelper.helpers.DBHelper;
import com.mynevermore.universityhelper.helpers.Tesseract;
import com.mynevermore.universityhelper.model.Note;

import static android.app.Activity.RESULT_OK;

public class AddNoteFragment extends Fragment {
    private final int REQ_CODE_SPEECH_INPUT = 50;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private View view;
    private TextView mNoteTitle;
    private EditText mTitleInput, mInput;
    private Button mCameraButton, mSpeechButton, mSaveNoteButton, mDeleteButton;
    private Tesseract mTesseract;
    private Uri outputFile;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final Bundle bundle = getArguments();

        view = inflater.inflate(R.layout.new_notes, container, false);

        mNoteTitle = (TextView) view.findViewById(R.id.newNoteTitleText);
        mTitleInput = (EditText) view.findViewById(R.id.noteTitleEdit);
        mInput = (EditText) view.findViewById(R.id.noteInputEditText);
        mCameraButton = (Button) view.findViewById(R.id.cameraRecordButton);
        mSpeechButton = (Button) view.findViewById(R.id.speechRecordButton);
        mSaveNoteButton = (Button) view.findViewById(R.id.saveNoteButton);
        mDeleteButton = (Button)view.findViewById(R.id.deleteButton);

        mSpeechButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
        mCameraButton.setVisibility(View.GONE);
        if(bundle == null){
            mDeleteButton.setVisibility(View.GONE);
        }

//        mCameraButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //takeAPictureIntent();
//            }
//        });

        mSaveNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bundle == null) {
                    saveNote();
                }
                else {
                    updateNote(bundle);
                }
            }
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote(bundle);
            }
        });

        if (bundle != null) {
            setNoteData(bundle);
        }

        return view;
    }

    private void deleteNote(Bundle bundle) {
        int id = bundle.getInt("note_id");
        DBHelper mDBhelper = new DBHelper(getContext());

        mDBhelper.deleteNote(id);

        Toast.makeText(getContext(), "Delete successful!", Toast.LENGTH_SHORT).show();
    }

    private void setNoteData(Bundle bundle) {
        Note note;
        DBHelper mDBhelper = new DBHelper(getContext());
        int noteId = bundle.getInt("note_id");

        note = mDBhelper.getNote(noteId);

        mNoteTitle.setText(R.string.edit_note);
        mTitleInput.setText(note.getTitle());
        mInput.setText(note.getText());
    }

    private void saveNote() {
        Note note = new Note();
        DBHelper mDBhelper = new DBHelper(getContext());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();

        sdf.setTimeZone(calendar.getTimeZone());

        note.setTitle(mTitleInput.getText().toString());
        note.setText(mInput.getText().toString());
        note.setDate(sdf.format(calendar.getTime()));

        mDBhelper.addNote(note);

        Toast.makeText(getContext(), "Save successful!", Toast.LENGTH_SHORT).show();
    }

    private void updateNote(Bundle bundle) {
        Note note;
        DBHelper mDBhelper = new DBHelper(getContext());
        int noteId = bundle.getInt("note_id");

        note = mDBhelper.getNote(noteId);
        note.setTitle(mTitleInput.getText().toString());
        note.setText(mInput.getText().toString());

        mDBhelper.updateNote(note);

        Toast.makeText(getContext(), "Save successful!", Toast.LENGTH_SHORT).show();
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.say_something));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getContext(),
                    getString(R.string.audio_input_unsupported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && null != data) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                mInput = (EditText) view.findViewById(R.id.noteInputEditText);
                mInput.append(result.get(0));
            }
        }
//          The following code is throwing a NullPointerException and it's going to take me time to debug, therefore it's commented out.
//
//        if (requestCode == REQUEST_IMAGE_CAPTURE) {
//            if(resultCode == RESULT_OK){
//                SimpleTarget st = new SimpleTarget<BitmapDrawable>(800,600){
//                    @Override
//                    public void onResourceReady(BitmapDrawable resource, Transition<? super BitmapDrawable> transition) {
//                        Bitmap bitmap = resource.getBitmap();
//                        String temp = mTesseract.getOCRResult(bitmap);
//                        mInput.setText(temp);
//                    }
//                };
//
//                Glide.with(this).load(outputFile).into(st);
//            }
//        }
    }

//    private void takeAPictureIntent() {
//        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//        File file = new File(Environment.getExternalStorageDirectory(), "tess.jpg");
//        outputFile = Uri.fromFile(file);
//        pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFile);
//        if (pictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
//            startActivityForResult(pictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
//    }

}