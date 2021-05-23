package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> notes = new ArrayList<>();
    static ArrayAdapter<String> arrayAdapter;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.add_note){
        Intent intent = new Intent(getApplicationContext(),SecondActivity.class);
        startActivity(intent);
        return true;
        }
        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);


        //notes.add("Example Note");

         SharedPreferences sharedPreferences =getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
//        try {
//            notes =(ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("notes",ObjectSerializer.serialize(new ArrayList<String>())));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes",null);
        if(set==null){
            notes.add("Example Note");
        }else{
            notes = new ArrayList(set);
        }
        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,notes   );
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent intent = new Intent(getApplicationContext(),SecondActivity.class);
                intent.putExtra("noteId",i);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int i, long id) {
                final int itemToDelete =i;

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete the Note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                notes.remove(itemToDelete);
                                arrayAdapter.notifyDataSetChanged();
                               SharedPreferences sharedPreferences =getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                                HashSet<String> set = new HashSet(MainActivity.notes);
                                sharedPreferences.edit().putStringSet("notes",set).apply();
//                                try {
//                                    sharedPreferences.edit().putString("notes",ObjectSerializer.serialize(MainActivity.notes)).apply();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
                            }
                        })
                        .setNegativeButton("No",null).show();
                return true;
            }
        });
    }
}