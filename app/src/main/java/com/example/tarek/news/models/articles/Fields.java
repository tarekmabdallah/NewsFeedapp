package com.example.tarek.news.models.articles;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Fields implements Parcelable {

	@SerializedName("trailText")
	private String trailText;

	@SerializedName("standfirst")
	private String standfirst;

	@SerializedName("main")
	private String main;

	@SerializedName("body")
	private String body;

	@SerializedName("headline")
	private String headline;

	@SerializedName("byline")
	private String authorName;

	private Fields(Parcel in) {
		trailText = in.readString();
		standfirst = in.readString();
		main = in.readString();
		body = in.readString();
		headline = in.readString();
		authorName = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(trailText);
		dest.writeString(standfirst);
		dest.writeString(main);
		dest.writeString(body);
		dest.writeString(headline);
		dest.writeString(authorName);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<Fields> CREATOR = new Creator<Fields>() {
		@Override
		public Fields createFromParcel(Parcel in) {
			return new Fields(in);
		}

		@Override
		public Fields[] newArray(int size) {
			return new Fields[size];
		}
	};

	public void setTrailText(String trailText){
		this.trailText = trailText;
	}

	public String getTrailText(){
		return trailText;
	}

	public void setStandfirst(String standfirst){
		this.standfirst = standfirst;
	}

	public String getStandfirst(){
		return standfirst;
	}

	public void setMain(String main){
		this.main = main;
	}

	public String getMain(){
		return main;
	}

	public void setBody(String body){
		this.body = body;
	}

	public String getBody(){
		return body;
	}

	public void setHeadline(String headline){
		this.headline = headline;
	}

	public String getHeadline(){
		return headline;
	}

	public void setAuthorName(String authorName){
		this.authorName = authorName;
	}

	public String getAuthorName(){
		return authorName;
	}

	@NonNull
	@Override
 	public String toString(){
		return 
			"Fields{" + 
			"trailText = '" + trailText + '\'' + 
			",standfirst = '" + standfirst + '\'' + 
			",main = '" + main + '\'' + 
			",body = '" + body + '\'' + 
			",headline = '" + headline + '\'' + 
			",authorName = '" + authorName + '\'' +
			"}";
		}
}