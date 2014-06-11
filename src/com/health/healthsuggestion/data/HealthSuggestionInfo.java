package com.health.healthsuggestion.data;

import android.net.Uri;

public class HealthSuggestionInfo {

	private final String TAG = "HealthSuggestionInfo";
	
	private String mDiseaseName;
	private String mSuggestTextValue;
	private Uri mSuggestImageValue;
	
	public HealthSuggestionInfo(String diseaseName, String suggestTextValue,
			Uri suggestImageValue) {
		super();
		this.mDiseaseName = diseaseName;
		this.mSuggestTextValue = suggestTextValue;
		this.mSuggestImageValue = suggestImageValue;
	}
	public String getmDiseaseName() {
		return mDiseaseName;
	}
	public void setmDiseaseName(String diseaseName) {
		this.mDiseaseName = diseaseName;
	}
	public String getmSuggestTextValue() {
		return mSuggestTextValue;
	}
	public void setmSuggestTextValue(String suggestTextValue) {
		this.mSuggestTextValue = suggestTextValue;
	}
	public Uri getmSuggestImageValue() {
		return mSuggestImageValue;
	}
	public void setmSuggestImageValue(Uri suggestImageValue) {
		this.mSuggestImageValue = suggestImageValue;
	}
	
	
	
}
