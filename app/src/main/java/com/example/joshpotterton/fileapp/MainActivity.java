package com.example.joshpotterton.fileapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private Button saveBtn;
    private Button openBtn;
    private EditText editText;

    private static final String FILENAME = "myFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saveBtn = (Button) findViewById(R.id.saveBtn);
        openBtn = (Button) findViewById(R.id.openBtn);
        editText = (EditText) findViewById(R.id.editText);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileOutputStream outputStream;
                    String string = editText.getText().toString();
                    Log.v("Debug", "EditText contains " + editText.getText().toString());
                    outputStream = openFileOutput(FILENAME, Context.MODE_PRIVATE);
                    outputStream.write(string.getBytes());
                    outputStream.flush();
                    outputStream.close();
                } catch (Exception e) {
                    Log.v("Error", e.getMessage());
                }
            }
        });

        openBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try{
                    //FileReader f = new FileReader(FILENAME);
                    FileInputStream f = openFileInput(FILENAME);
                    InputStreamReader inputStreamReader = new InputStreamReader(f);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String str;

                    while((str = bufferedReader.readLine()) != null){
                        editText.append(str);
                    }
                    bufferedReader.close();
                }
                catch(Exception e){
                    Log.v("Error", e.getMessage());
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
