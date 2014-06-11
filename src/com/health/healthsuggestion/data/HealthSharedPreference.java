package com.health.healthsuggestion.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class HealthSharedPreference {
	
	private static final String TAG = "HealthSharedPreference";
	private SharedPreferences mSharedPreference = null;
	private Context mContext = null;
	
	private static final String PREFS_NAME = "HealthPrefsFile";
	private static final String PREFS_ITEM_KEY = "healthSuggestionItems";
	private static final String PREFS_DBVERSION_KEY = "healthDbVersion";
	
	public static String mDiseaseItemName = "Cold,Fever,Stomachache,Dysmenorrhea,Gynaecology,Insomnia";
	public static String mOldDiseaseItemName = "Cold,Fever,Stomachache,Dysmenorrhea,Gynaecology,Insomnia";
	
	public static String mSuggestionTextItemName = "11,22,33,44,55,66";
	
	public HealthSharedPreference(Context context)
	{
		mContext = context;
	}
	
	public void initPreference() {
		// TODO Auto-generated method stub
		mSharedPreference = mContext.getSharedPreferences(PREFS_NAME, mContext.MODE_PRIVATE);
		mDiseaseItemName = mSharedPreference.getString(PREFS_ITEM_KEY, "Cold,Fever,Stomachache,Dysmenorrhea,Gynaecology,Insomnia");
		Log.i(TAG, "initPreference,mDiseaseItemName = " + mDiseaseItemName);
		SQLiteHelper.DATABASE_VERSION = mSharedPreference.getInt(PREFS_DBVERSION_KEY, 1);
	}
	
	public String getPreferenceByString(String strKey)
	{
		mSharedPreference = mContext.getSharedPreferences(PREFS_NAME, mContext.MODE_PRIVATE);
		return mSharedPreference.getString(strKey, "");
	}
	
	public int getPreferenceByInt(String strKey)
	{
		mSharedPreference = mContext.getSharedPreferences(PREFS_NAME, mContext.MODE_PRIVATE);
		return mSharedPreference.getInt(strKey, 1);
	}
	
	public void updatePreferenceByString(String strValue)
	{
		mSharedPreference = mContext.getSharedPreferences(PREFS_NAME, mContext.MODE_PRIVATE);
		Editor editor = mSharedPreference.edit();
		editor.putString(PREFS_ITEM_KEY, strValue);
		editor.commit();
	}
	
	public void updatePreferenceByInt(int iValue)
	{
		mSharedPreference = mContext.getSharedPreferences(PREFS_NAME, mContext.MODE_PRIVATE);
		Editor editor = mSharedPreference.edit();
		editor.putInt(PREFS_DBVERSION_KEY, iValue);
		editor.commit();
	}
}
