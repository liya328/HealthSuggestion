package com.health.healthsuggestion;

import com.health.healthsuggestion.data.HealthSharedPreference;
import com.health.healthsuggestion.data.SQLiteHelper;
import com.health.healthsuggestion.utils.GlobalConstValues;

import android.os.Bundle;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.TextView.OnEditorActionListener;

public class HealthSuggestionActivity extends Activity {
	
	final static String TAG = "HealthSuggestionActivity";
	private EditText mActionSearchEditText = null;
	private ListView mDiseaseItemsListView = null;
	private DiseaseListViewItemAdapter mGridViewAdapter = null;
	private HealthSharedPreference mHealthSharedPrefs = null;
	private SQLiteHelper mSqliteHelper = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_health_suggestion);
		
		initPreference();
		createDB();
		initUI();
	}
	
	private void initPreference() {
		// TODO Auto-generated method stub
		mHealthSharedPrefs = new HealthSharedPreference(this);
		mHealthSharedPrefs.initPreference();
	}

	private void createDB() {
		// TODO Auto-generated method stub
		mSqliteHelper = SQLiteHelper.getInstance(this);


		SQLiteDatabase db = null;
/*		db = mSqliteHelper.getWritableDatabase();
		String[] diseaseItems = HealthSharedPreference.mDiseaseItemName.split(",");
		for(int i = 0; i < diseaseItems.length;i ++)
		{
			ContentValues values = new ContentValues();
			String[] tableItems = GlobalConstValues.DISEASE_SUGGESTION_TABLE.split(",");
			String[] suggestionTextItems = HealthSharedPreference.mSuggestionTextItemName.split(",");
			values.put(tableItems[0], diseaseItems[i].toLowerCase());
			values.put(tableItems[1], suggestionTextItems[i]);
			mSqliteHelper.updateDBByInsertValue(db, values);
		}
		db.close();
*/		
		//for debug
		{
			db = mSqliteHelper.getReadableDatabase();
			mSqliteHelper.retrieveDB(db);
			db.close();
		}
		
	}
	
	private void initUI() {
		// TODO Auto-generated method stub
		Log.i(TAG, "initUI enter.");
		
		mDiseaseItemsListView = (ListView) (findViewById(R.id.disease_items_list_view));
		mGridViewAdapter = new DiseaseListViewItemAdapter(this,getData());
		mDiseaseItemsListView.setAdapter(mGridViewAdapter);
		mGridViewAdapter.setAdapterData(HealthSharedPreference.mDiseaseItemName.split(","));
		mGridViewAdapter.notifyDataSetChanged();
		
		mDiseaseItemsListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				gridViewItemClickProcess(view,position);
			}
		});
		
		mDiseaseItemsListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				return true;//gridViewItemLongClickProcess(position);
			}
		});
		
		Log.i(TAG, "initUI exit.");
	}
	
	private void gridViewItemClickProcess(View view,int position) {
		//will query database and display detail suggestion
		String diseaseName = (String) ((TextView)view.findViewById(R.id.diagnosis_detail_list_view_item_key)).getText();
		Log.i(TAG, "gridViewItemClickProcess,position = " + position + ",disease name = " + diseaseName);
		
//		//query database
//		SQLiteDatabase db = null;
//		if(mSqliteHelper != null)
//		{
//			db = mSqliteHelper.getReadableDatabase();
//			mSqliteHelper.retrieveDBByKey(db,diseaseName);
//			db.close();
//		}
		
		//send key to target activity
		Intent intent = new Intent();
		ComponentName component = new ComponentName(getPackageName(), getPackageName() + ".DiseaseSuggestionDetailActivity");
		intent.setComponent(component);
		intent.setType("text/*");
		intent.putExtra(Intent.EXTRA_TEXT, diseaseName);
		startActivity(intent);
	}
	
	private String[] getData() {
		// TODO Auto-generated method stub
		return HealthSharedPreference.mDiseaseItemName.split(",");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.health_suggestion, menu);
		MenuItem addItem = menu.findItem(R.id.action_add);
		MenuItem SearchEditItem = menu.findItem(R.id.action_search);
		
		mActionSearchEditText = (EditText) MenuItemCompat.getActionView(SearchEditItem);
		MenuItemCompat.setOnActionExpandListener(SearchEditItem, new OnActionExpandListener() {
			
			@Override
			public boolean onMenuItemActionExpand(MenuItem arg0) {
				// TODO Auto-generated method stub
				Log.i(TAG, "onCreateOptionsMenu,onMenuItemActionExpand.");
				configActionViewEditText();
				
				return true;
			}
			
			@Override
			public boolean onMenuItemActionCollapse(MenuItem arg0) {
				// TODO Auto-generated method stub
				Log.i(TAG, "onCreateOptionsMenu,onMenuItemActionCollapse.");
				return true;
			}
		});
		
		return true;
	}
	
	protected void configActionViewEditText() {
		// TODO Auto-generated method stub
		mActionSearchEditText.setHint("Add health item");
//		editView.setFocusable(true);
		mActionSearchEditText.setId(GlobalConstValues.ACTION_SEARCH_EDIT_TEXT_ID);
		mActionSearchEditText.setInputType(EditorInfo.TYPE_TEXT_FLAG_CAP_WORDS);
		mActionSearchEditText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
		mActionSearchEditText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mActionSearchEditText.setText("");
			}
		});
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		lp.gravity = Gravity.CENTER;
		mActionSearchEditText.setLayoutParams(lp);
		
		mActionSearchEditText.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO Auto-generated method stub
				boolean handled = false;
				if(actionId == EditorInfo.IME_ACTION_SEARCH){
					handleSearchInputMessage();
					handled = true;
				}
				return handled;
			}
		});
	}
	
	protected void handleSearchInputMessage()
	{
		//will handle the search query
	}

}
