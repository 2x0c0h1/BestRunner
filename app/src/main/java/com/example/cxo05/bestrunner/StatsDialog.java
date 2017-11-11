package com.example.cxo05.bestrunner;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by xavie on 11/11/2017.
 */

public class StatsDialog extends Dialog implements View.OnClickListener{

    public StatsDialog (@NonNull Context context){
        super(context);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.info_dialog);
        ImageButton button = (ImageButton) findViewById(R.id.close);
        button.setOnClickListener(this);

        TextView details = (TextView) findViewById(R.id.stats_details);

        SharedPreferences sharedPref = getContext().getSharedPreferences("Preferences",Context.MODE_PRIVATE);

        String string = "Distance Traveled : " + sharedPref.getInt("Distance", 0);
        details.setText(string);
    }

    @Override
    public void onClick(View view) {
        dismiss();
    }
}


