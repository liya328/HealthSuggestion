package com.health.healthsuggestion;

import com.health.healthsuggestion.data.HealthSuggestionInfo;
import com.health.healthsuggestion.data.SQLiteHelper;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class DiseaseSuggestionDetailActivity extends Activity {
	
	final static String TAG = "DiseaseSuggestionDetailActivity";
	
	private SQLiteHelper mSqliteHelper = null;
	
	private TextView mDiseaseName = null;
	private TextView mTextSuggestionValue = null;
	private ImageView mImageSuggestionValue = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_disease_suggestion_detail);
		
		initUI();
	}

	private void initUI() {
		// TODO Auto-generated method stub
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		String diseaseName = bundle.getString(Intent.EXTRA_TEXT);
		Log.i(TAG, "initUI,diseaseName = " + diseaseName);
		
		if(mSqliteHelper == null)
		{
			mSqliteHelper = SQLiteHelper.getInstance(this);
		}
		//query database
		HealthSuggestionInfo suggestionInfo = null;
		SQLiteDatabase db = null;
		if(mSqliteHelper != null)
		{
			db = mSqliteHelper.getReadableDatabase();
			suggestionInfo = mSqliteHelper.retrieveDBByKey(db,diseaseName);
			db.close();
		}
		
		mDiseaseName = (TextView) findViewById(R.id.disease_name);
		mTextSuggestionValue = (TextView) findViewById(R.id.disease_text_suggestion);
		mImageSuggestionValue = (ImageView) findViewById(R.id.disease_image_suggestion);
		
		mDiseaseName.setText(suggestionInfo.getmDiseaseName()+ ": ");
		mTextSuggestionValue.setText(suggestionInfo.getmSuggestTextValue());
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

}
