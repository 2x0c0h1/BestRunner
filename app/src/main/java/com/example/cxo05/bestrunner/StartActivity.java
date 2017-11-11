package com.example.cxo05.bestrunner;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Ang Family on 11/11/2017.
 */

public class StartActivity extends AppCompatActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		ConnectivityManager connectivityManager
				= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
			android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(this);
			dialog.setTitle("No internet connection")
					.setMessage("Please turn on Wi-Fi or data settings")
					.setPositiveButton("Settings",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
									finish();
								}
							}
					)
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									finish();
								}
							}
					);
			dialog.show();
		}
		LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		boolean gps_enabled = false;
		boolean network_enabled = false;
		try {
			gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
		} catch (Exception ex) {
		}

		try {
			network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		} catch (Exception ex) {
		}

		if (!gps_enabled && !network_enabled) {
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setTitle("Unable to retrieve location")
					.setMessage("Please turn on location settings")
					.setPositiveButton("Settings",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 1);
								}
							}
					)
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									finish();
								}
							}
					);
			dialog.setCancelable(false);
			dialog.show();
		}

		SharedPreferences sharedPref = this.getSharedPreferences("Preferences",Context.MODE_PRIVATE);

		if(!sharedPref.contains("Distance")){
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putInt("Distance", 0);
			editor.apply();
		}

		if(!sharedPref.contains("Wins")){
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putInt("Wins", 0);
			editor.apply();
		}

		if(!sharedPref.contains("Losses")){
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putInt("Losses", 0);
			editor.apply();
		}

		TextView levelText = findViewById(R.id.LevelText);
		ProgressBar expBar = findViewById(R.id.expBar);

		Toast.makeText(StartActivity.this, "Distance ran " + sharedPref.getInt("Distance", 9000),Toast.LENGTH_LONG).show();

		LevelSystem asd = new LevelSystem(getApplicationContext());
		String levelString = "Level " + String.valueOf(asd.getPlayerLevel().getLevel());
		levelText.setText(levelString);

		expBar.setMax(5000);
		expBar.setProgress(asd.getPlayerLevel().getFexp());

		//Toast.makeText(StartActivity.this,"Welcome, "+sharedPref.getString("ID","user"),Toast.LENGTH_SHORT).show();
	}

	public void Start(View v){
		Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
		startActivity(intent);
	}

	public void Stats(View v){
		StatsDialog asd = new StatsDialog(StartActivity.this);
		asd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		asd.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
		asd.show();
		asd.getWindow().getDecorView().setSystemUiVisibility(getWindow().getDecorView().getSystemUiVisibility());
		asd.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
	}
}
