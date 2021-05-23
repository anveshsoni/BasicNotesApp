package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashSet;

public class SecondActivity extends AppCompatActivity {
    int noteId;
    EditText editText;
     SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        editText = (EditText) findViewById(R.id.editText);
        Intent intent = getIntent();

         noteId =intent.getIntExtra("noteId",-1);
        if(noteId != -1){
            editText.setText(MainActivity.notes.get(noteId));
        }else{
            MainActivity.notes.add("");
            noteId = MainActivity.notes.size()-1;
            MainActivity.arrayAdapter.notifyDataSetChanged();


        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.notes.set(noteId,String.valueOf(s));
                MainActivity.arrayAdapter.notifyDataSetChanged();
                 sharedPreferences =getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet(MainActivity.notes);
                sharedPreferences.edit().putStringSet("notes",set).apply();
//                try {
//                    sharedPreferences.edit().putString("notes",ObjectSerializer.serialize(MainActivity.notes)).apply();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

}