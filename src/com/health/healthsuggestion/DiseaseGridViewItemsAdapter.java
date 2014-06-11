package com.health.healthsuggestion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DiseaseGridViewItemsAdapter extends BaseAdapter {
	
	private final String TAG = "DiseaseGridViewItemsAdapter";
	private Context mContext;
	private GridViewItem mGridViewItem;
	
	private String[] mDiseaseItemName = null;
	
	public DiseaseGridViewItemsAdapter(Context context)
	{
		mContext = context;
	}
	
	public DiseaseGridViewItemsAdapter(Context context,String[] data)
	{
		mContext = context;
		mDiseaseItemName = data;
	}
	
	public void setAdapterData(String[] data)
	{
		mDiseaseItemName = data;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mDiseaseItemName.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mDiseaseItemName[arg0];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null)
		{
			mGridViewItem = new GridViewItem();
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.disease_grid_view_item, parent, false);
			mGridViewItem.mTextView = (TextView) convertView.findViewById(R.id.disease_grid_view_item_textview);
			convertView.setTag(mGridViewItem);
		}
		else
		{
			mGridViewItem = (GridViewItem) convertView.getTag();
		}
		mGridViewItem.mTextView.setText(mDiseaseItemName[position]);
		return convertView;
	}
	
	class GridViewItem{// will be good for expand
		ImageView mImageView;
		TextView mTextView;
	}

}
