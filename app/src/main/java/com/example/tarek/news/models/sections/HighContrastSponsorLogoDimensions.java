package com.example.tarek.news.models.sections;

import com.google.gson.annotations.SerializedName;

public class HighContrastSponsorLogoDimensions{

	@SerializedName("width")
	private int width;

	@SerializedName("height")
	private int height;

	public void setWidth(int width){
		this.width = width;
	}

	public int getWidth(){
		return width;
	}

	public void setHeight(int height){
		this.height = height;
	}

	public int getHeight(){
		return height;
	}

	@Override
 	public String toString(){
		return 
			"HighContrastSponsorLogoDimensions{" + 
			"width = '" + width + '\'' + 
			",height = '" + height + '\'' + 
			"}";
		}
}