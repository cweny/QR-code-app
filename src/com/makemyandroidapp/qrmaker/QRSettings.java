package com.makemyandroidapp.qrmaker;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class QRSettings extends PreferenceActivity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.menu_settings);
		addPreferencesFromResource(R.xml.preferences);
	}

}
