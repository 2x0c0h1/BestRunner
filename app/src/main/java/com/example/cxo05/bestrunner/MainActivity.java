package com.example.cxo05.bestrunner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = this.getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        if (sharedPref.getString("ID", "000000" ).equals("000000")) {
            //Register
            setContentView(R.layout.activity_main);
            name = findViewById(R.id.nameField);
        }else{
            Intent intent = new Intent(getApplicationContext(), StartActivity.class);
            startActivity(intent);
        }
    }

    public void Signup(View v){
        if(name.getText() == null || name.getText().equals("")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("Name cannot be empty")
                    .setTitle("Field empty");

            AlertDialog dialog = builder.create();
            dialog.show();
            return;
        }

        SharedPreferences sharedPref = this.getSharedPreferences("Preferences",Context.MODE_PRIVATE);
        //TODO Generate a unique ID from that is not in firebase

        sharedPref.edit().putString("ID", "").apply();
        //TODO Send ID and Name to Fire Base
        Intent intent = new Intent(getApplicationContext(), StartActivity.class);
        startActivity(intent);
    }
}
