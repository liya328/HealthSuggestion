package com.health.healthsuggestion.data;

import com.health.healthsuggestion.utils.GlobalConstValues;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {
	
	private final String TAG = "SQLiteHelper";
	private Context mContext = null;
	public static int DATABASE_VERSION = 1;
	public final static String DATABASE_NAME = "health_suggestion.db";
	public final static String DATABASE_BACKUP_TABLE = "health_suggestion_backup";
	public final static String DATABASE_BACKUP_TEMP_TABLE = "health_suggestion_backup_temp";
	public final static String DATABASE_TABLE = "health_suggestion_table";
	private String mAddedColumn = null;
	private String mDeletedColumn = null;
	
	private final int DATABASE_TABLE_UPDATE_FLAG = 0;
	private final int DATABASE_TABLE_UPDATE_ADD_FLAG = DATABASE_TABLE_UPDATE_FLAG + 1;
	private final int DATABASE_TABLE_UPDATE_DELETE_FLAG = DATABASE_TABLE_UPDATE_FLAG - 1;
	private int mUpdateFlag = DATABASE_TABLE_UPDATE_FLAG;

	private static SQLiteHelper mSqliteHelper = null;
	
	private SQLiteHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		mContext = context;
	}

	private SQLiteHelper(Context context, String name, CursorFactory factory,int version) {
		super(context, name, factory, version);
		Log.i(TAG, "SQLiteHelper constructor,create health_suggestion.db in " + version + " version");
		mContext = context;
		// TODO Auto-generated constructor stub
	}
	
	public static SQLiteHelper getInstance(Context context)
	{
		if(mSqliteHelper == null)
		{
			synchronized (SQLiteHelper.class) {
				mSqliteHelper = new SQLiteHelper(context);
			}
		}
		return mSqliteHelper;
	}

	/*
	 * onCreate method in SQLiteOpenHelper class will be called only once when the database is not existed,
	 * so it will not be called once the database is created.
	 * 
	 * */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onCreate,create health_suggestion_table in " + DATABASE_NAME);
		String[] tableColumnName = GlobalConstValues.DISEASE_SUGGESTION_TABLE.split(",");
		String sql = "CREATE TABLE " + DATABASE_TABLE + " (id INTEGER PRIMARY KEY";
		for(int i = 0;i < tableColumnName.length - 1; i ++)
		{
			sql = sql + "," + tableColumnName[i].toLowerCase() + " TEXT";
		}
		sql = sql + "," + tableColumnName[tableColumnName.length - 1].toLowerCase() + " BLOB";
		sql = sql + ")";
		Log.i(TAG, "onCreate,create health_suggestion_table with sql = " + sql);
		db.execSQL(sql);
	}

	/*
	 * onUpgrade method in SQLiteOpenHelper class will be called if the version of database was changed,
	 * and the version is from the constructor method.
	 * 
	 * */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onUpgrade,update health_suggestion_table in " + DATABASE_NAME);
		Log.i(TAG, "onUpgrade,update health_suggestion_table from " + oldVersion + " version to " + newVersion + " version.");
		if(newVersion > oldVersion)
		{
			switch (mUpdateFlag) {
			case DATABASE_TABLE_UPDATE_ADD_FLAG:
				updateDBByAddItem(db);
				break;
			case DATABASE_TABLE_UPDATE_DELETE_FLAG:
				updateDBByDeleteItem(db);
				break;

			default:
				break;
			}
		}
	}
	
	public void updateDBByInsertValue(SQLiteDatabase db,ContentValues contentValue)
	{
		long rowId = db.insert(SQLiteHelper.DATABASE_TABLE, null, contentValue);
		if(rowId > 0)
		{
			Log.i(TAG, "updateDBByInsertValue,insert data to table success.");
		}
		else
		{
			Log.e(TAG, "updateDBByInsertValue,insert data to table failed.");
		}

	}
	
	// for debug
	public void retrieveDB(SQLiteDatabase db)
	{
		Cursor cursor = db.query(SQLiteHelper.DATABASE_TABLE, GlobalConstValues.DISEASE_SUGGESTION_TABLE.split(","), "", null, null, null, null);
		String[] items = GlobalConstValues.DISEASE_SUGGESTION_TABLE.toLowerCase().split(",");
		while(cursor.moveToNext()){
			String diseaseItemValue = null;
			long itemsValue = 0;
			String logText = "";
			diseaseItemValue = cursor.getString(cursor.getColumnIndex(items[0]));
			itemsValue = cursor.getLong(cursor.getColumnIndex(items[1]));
			logText = logText + "," + items[0] + " = " + diseaseItemValue + "," + items[1] + " = " + itemsValue;
			Log.i(TAG, "retrieveDB,the table item " + logText + " .");
		}
	}
	
	// for debug
		public HealthSuggestionInfo retrieveDBByKey(SQLiteDatabase db,String key)
		{
			HealthSuggestionInfo healthSuggestionInfo = null;
			String[] tableItems = GlobalConstValues.DISEASE_SUGGESTION_TABLE.toLowerCase().split(",");
			String[] diseaseNameValue = {key.toLowerCase()};
			Cursor cursor = db.query(SQLiteHelper.DATABASE_TABLE,tableItems,tableItems[0] + " = ?",diseaseNameValue , null, null, null);
			while(cursor.moveToNext()){
				String diseaseItemValue = null;
				String textSuggestionItemsValue = null;
				String logText = "";
				diseaseItemValue = cursor.getString(cursor.getColumnIndex(tableItems[0]));
				textSuggestionItemsValue = cursor.getString(cursor.getColumnIndex(tableItems[1]));
				logText = logText + "," + tableItems[0] + " = " + key + "," + tableItems[1] + " = " + textSuggestionItemsValue;
				Log.i(TAG, "retrieveDBByKey,the table item " + logText + " .");
				healthSuggestionInfo = new HealthSuggestionInfo(key, textSuggestionItemsValue, null);
			}
			
			return healthSuggestionInfo;
		}
	
	private void updateDBByAddItem(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		String sql = "ALTER TABLE " + DATABASE_TABLE + " ADD COLUMN " + mAddedColumn.toLowerCase() + " INTEGER";
		Log.i(TAG, "updateDBByAddItem,add new column " + mAddedColumn.toLowerCase());
		Log.i(TAG, "updateDBByAddItem,adding sql statement is " + sql);
		db.execSQL(sql);
		
	}
	
	private void updateDBByDeleteItem(SQLiteDatabase db)
	{
		db.beginTransaction();
		try{
			
			// create back-up table
			String sql = "DROP TABLE IF EXISTS " + DATABASE_BACKUP_TABLE;
			db.execSQL(sql);
			String[] tableColumnName = GlobalConstValues.DISEASE_SUGGESTION_TABLE.split(",");
			sql = "CREATE TABLE " + DATABASE_BACKUP_TABLE + " (id INTEGER PRIMARY KEY";
			Log.i(TAG, "updateDBByDeleteItem,add will be deleted column is " + mDeletedColumn);
			for(int i = 0;i < tableColumnName.length; i ++)
			{
//				if(tableColumnName[i].toLowerCase() != mDeletedColumn)
				{
					sql = sql + "," + tableColumnName[i].toLowerCase() + " INTEGER";
				}
			}
			sql = sql + ")";
			Log.i(TAG, "updateDBByDeleteItem,deleting one column sql statement is " + sql);
			db.execSQL(sql);
			
			//insert data without deleted column from now table
			sql = "INSERT INTO " + DATABASE_BACKUP_TABLE + " SELECT " + "id," 
			+ GlobalConstValues.DISEASE_SUGGESTION_TABLE.toLowerCase() + " FROM " + DATABASE_TABLE;
			Log.i(TAG, "updateDBByDeleteItem,sql statement of copying now table to back up table is " + sql);
			db.execSQL(sql);
			
//			//for debug
//			Cursor cursor = db.query(SQLiteHelper.DATABASE_BACKUP_TABLE, HealthSharedPreference.mOldDiagnosisItemName.split(","), "", null, null, null, null);
//			String[] items = HealthSharedPreference.mOldDiagnosisItemName.toLowerCase().split(",");
//			while(cursor.moveToNext()){
//				long itemsValue = 0;
//				String logText = "";
//				for(int i = 0;i < items.length; i ++)
//				{
//					itemsValue = cursor.getLong(cursor.getColumnIndex(items[i]));
//					logText = logText + "," + items[i] + " = " + itemsValue;
//				}
//				Log.i(TAG, "updateDBByDeleteItem,the back-up table item " + logText + " .");
//			}
			
			//create temp back-up table
			sql = "DROP TABLE IF EXISTS " + DATABASE_BACKUP_TEMP_TABLE;
			db.execSQL(sql);
			tableColumnName = GlobalConstValues.DISEASE_SUGGESTION_TABLE.split(",");
			sql = "CREATE TABLE " + DATABASE_BACKUP_TEMP_TABLE + " (id INTEGER PRIMARY KEY";
			Log.i(TAG, "updateDBByDeleteItem,add will be deleted column is " + mDeletedColumn);
			for(int i = 0;i < tableColumnName.length; i ++)
			{
//				if(tableColumnName[i].toLowerCase() != mDeletedColumn)
				{
					sql = sql + "," + tableColumnName[i].toLowerCase() + " INTEGER";
				}
			}
			sql = sql + ")";
			Log.i(TAG, "updateDBByDeleteItem,deleting one column sql statement is " + sql);
			db.execSQL(sql);
			
			//insert data without deleted column from now table
			sql = "INSERT INTO " + DATABASE_BACKUP_TEMP_TABLE + " SELECT " + "id," 
			+ GlobalConstValues.DISEASE_SUGGESTION_TABLE.toLowerCase() + " FROM " + DATABASE_TABLE; // + " where " + mDeletedColumn + " = 0" ;
			Log.i(TAG, "updateDBByDeleteItem,sql statement of copying now table to back up table is " + sql);
			db.execSQL(sql);
			
//			//for debug
//			cursor = db.query(SQLiteHelper.DATABASE_BACKUP_TEMP_TABLE, HealthSharedPreference.mOldDiagnosisItemName.split(","), "", null, null, null, null);
//			items = HealthSharedPreference.mOldDiagnosisItemName.toLowerCase().split(",");
//			while(cursor.moveToNext()){
//				long itemsValue = 0;
//				String logText = "";
//				for(int i = 0;i < items.length; i ++)
//				{
//					itemsValue = cursor.getLong(cursor.getColumnIndex(items[i]));
//					logText = logText + "," + items[i] + " = " + itemsValue;
//				}
//				Log.i(TAG, "updateDBByDeleteItem,the back-up table item " + logText + " .");
//			}
			
			//renew DATABASE_TABLE from DATABASE_BACKUP_TABLE
			sql = "DROP TABLE IF EXISTS " + DATABASE_TABLE;
			db.execSQL(sql);
			tableColumnName = GlobalConstValues.DISEASE_SUGGESTION_TABLE.split(",");
			sql = "CREATE TABLE " + DATABASE_TABLE + " (id INTEGER PRIMARY KEY";
			for(int i = 0;i < tableColumnName.length; i ++)
			{
//				if(tableColumnName[i].toLowerCase() != mDeletedColumn)
				{
					sql = sql + "," + tableColumnName[i].toLowerCase() + " INTEGER";
				}
			}
			sql = sql + ")";
			Log.i(TAG, "updateDBByDeleteItem,deleting one column sql statement is " + sql);
			db.execSQL(sql);
			
			//insert data without deleted column from now table
			sql = "INSERT INTO " + DATABASE_TABLE + " SELECT " + "id," 
			+ GlobalConstValues.DISEASE_SUGGESTION_TABLE.toLowerCase()+ " FROM " + DATABASE_BACKUP_TEMP_TABLE;
			Log.i(TAG, "updateDBByDeleteItem,sql statement of copying back up table to now table is " + sql);
			db.execSQL(sql);
			
//			//for debug
//			cursor = db.query(SQLiteHelper.DATABASE_TABLE, HealthSharedPreference.mDiagnosisItemName.split(","), "", null, null, null, null);
//			items = HealthSharedPreference.mDiagnosisItemName.toLowerCase().split(",");
//			while(cursor.moveToNext()){
//				long itemsValue = 0;
//				String logText = "";
//				for(int i = 0;i < items.length; i ++)
//				{
//					itemsValue = cursor.getLong(cursor.getColumnIndex(items[i]));
//					logText = logText + "," + items[i] + " = " + itemsValue;
//				}
//				Log.i(TAG, "updateDBByDeleteItem,the new now table item " + logText + " .");
//			}
			
			sql = "DROP TABLE IF EXISTS " + DATABASE_BACKUP_TEMP_TABLE;
			db.execSQL(sql);
//			db.delete(DATABASE_BACKUP_TEMP_TABLE, null, null);
			
			db.setTransactionSuccessful();
		}finally{
			db.endTransaction();
		}
		
//		db.close();
		
	}

	public void setAddedColumn(String addedColumn)
	{
		mAddedColumn = addedColumn;
		mUpdateFlag = DATABASE_TABLE_UPDATE_ADD_FLAG;
	}
	
	public void setDeletedColumn(String deletedColumn)
	{
		mDeletedColumn = deletedColumn;
		mUpdateFlag = DATABASE_TABLE_UPDATE_DELETE_FLAG;
	}

}
