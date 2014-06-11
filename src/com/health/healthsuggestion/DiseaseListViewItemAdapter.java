package com.health.healthsuggestion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


class DiseaseListViewItemAdapter extends BaseAdapter{

	private Context mContext;
	private ListViewItem mItem = null;
	private String[] mKeyArray = null;
	
	public DiseaseListViewItemAdapter(Context context) {
		mContext = context;
	}
	
	public DiseaseListViewItemAdapter(Context context,String[] data) {
		mContext = context;
		mKeyArray = data;
	}
	
	public void setAdapterData(String[] data)
	{
		mKeyArray = data;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mKeyArray.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mKeyArray[arg0];
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
			mItem = new ListViewItem();
			LayoutInflater inflator = LayoutInflater.from(mContext);
			convertView = inflator.inflate(R.layout.disease_list_view_item, parent, false);
			mItem.mKey = (TextView) convertView.findViewById(R.id.diagnosis_detail_list_view_item_key);
//			mItem.mValue = (TextView) convertView.findViewById(R.id.diagnosis_detail_list_view_item_value);
			convertView.setTag(mItem);
		}
		else
		{
			mItem = (ListViewItem) convertView.getTag();
		}
		mItem.mKey.setText(mKeyArray[position]);
//		mItem.mValue.setText("0%");
		return convertView;
	}
	
	class ListViewItem{
		TextView mKey;
		TextView mValue;
	}
	
}