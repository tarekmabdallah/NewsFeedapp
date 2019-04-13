package com.example.tarek.news.models.section.articles;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Fields implements Parcelable {

	@SerializedName("wordcount")
	private String wordcount;

	@SerializedName("shouldHideAdverts")
	private String shouldHideAdverts;

	@SerializedName("shouldHideReaderRevenue")
	private String shouldHideReaderRevenue;

	@SerializedName("charCount")
	private String charCount;

	@SerializedName("shortUrl")
	private String shortUrl;

	@SerializedName("showAffiliateLinks")
	private String showAffiliateLinks;

	@SerializedName("main")
	private String main;

	@SerializedName("body")
	private String body;

	@SerializedName("productionOffice")
	private String productionOffice;

	@SerializedName("newspaperEditionDate")
	private String newspaperEditionDate;

	@SerializedName("bodyText")
	private String bodyText;

	@SerializedName("publication")
	private String publication;

	@SerializedName("trailText")
	private String trailText;

	@SerializedName("lang")
	private String lang;

	@SerializedName("headline")
	private String headline;

	@SerializedName("byline")
	private String authorName;

	@SerializedName("isInappropriateForSponsorship")
	private String isInappropriateForSponsorship;

	@SerializedName("commentable")
	private String commentable;

	@SerializedName("firstPublicationDate")
	private String firstPublicationDate;

	@SerializedName("thumbnail")
	private String thumbnail;

	@SerializedName("isPremoderated")
	private String isPremoderated;

	@SerializedName("newspaperPageNumber")
	private String newspaperPageNumber;

	@SerializedName("commentCloseDate")
	private String commentCloseDate;

	@SerializedName("standfirst")
	private String standfirst;

	@SerializedName("showInRelatedContent")
	private String showInRelatedContent;

	@SerializedName("lastModified")
	private String lastModified;

	@SerializedName("legallySensitive")
	private String legallySensitive;

	protected Fields(Parcel in) {
		wordcount = in.readString();
		shouldHideAdverts = in.readString();
		shouldHideReaderRevenue = in.readString();
		charCount = in.readString();
		shortUrl = in.readString();
		showAffiliateLinks = in.readString();
		main = in.readString();
		body = in.readString();
		productionOffice = in.readString();
		newspaperEditionDate = in.readString();
		bodyText = in.readString();
		publication = in.readString();
		trailText = in.readString();
		lang = in.readString();
		headline = in.readString();
		authorName = in.readString();
		isInappropriateForSponsorship = in.readString();
		commentable = in.readString();
		firstPublicationDate = in.readString();
		thumbnail = in.readString();
		isPremoderated = in.readString();
		newspaperPageNumber = in.readString();
		commentCloseDate = in.readString();
		standfirst = in.readString();
		showInRelatedContent = in.readString();
		lastModified = in.readString();
		legallySensitive = in.readString();
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

	public void setWordcount(String wordcount){
		this.wordcount = wordcount;
	}

	public String getWordcount(){
		return wordcount;
	}

	public void setShouldHideAdverts(String shouldHideAdverts){
		this.shouldHideAdverts = shouldHideAdverts;
	}

	public String getShouldHideAdverts(){
		return shouldHideAdverts;
	}

	public void setShouldHideReaderRevenue(String shouldHideReaderRevenue){
		this.shouldHideReaderRevenue = shouldHideReaderRevenue;
	}

	public String getShouldHideReaderRevenue(){
		return shouldHideReaderRevenue;
	}

	public void setCharCount(String charCount){
		this.charCount = charCount;
	}

	public String getCharCount(){
		return charCount;
	}

	public void setShortUrl(String shortUrl){
		this.shortUrl = shortUrl;
	}

	public String getShortUrl(){
		return shortUrl;
	}

	public void setShowAffiliateLinks(String showAffiliateLinks){
		this.showAffiliateLinks = showAffiliateLinks;
	}

	public String getShowAffiliateLinks(){
		return showAffiliateLinks;
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

	public void setProductionOffice(String productionOffice){
		this.productionOffice = productionOffice;
	}

	public String getProductionOffice(){
		return productionOffice;
	}

	public void setNewspaperEditionDate(String newspaperEditionDate){
		this.newspaperEditionDate = newspaperEditionDate;
	}

	public String getNewspaperEditionDate(){
		return newspaperEditionDate;
	}

	public void setBodyText(String bodyText){
		this.bodyText = bodyText;
	}

	public String getBodyText(){
		return bodyText;
	}

	public void setPublication(String publication){
		this.publication = publication;
	}

	public String getPublication(){
		return publication;
	}

	public void setTrailText(String trailText){
		this.trailText = trailText;
	}

	public String getTrailText(){
		return trailText;
	}

	public void setLang(String lang){
		this.lang = lang;
	}

	public String getLang(){
		return lang;
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

	public void setIsInappropriateForSponsorship(String isInappropriateForSponsorship){
		this.isInappropriateForSponsorship = isInappropriateForSponsorship;
	}

	public String getIsInappropriateForSponsorship(){
		return isInappropriateForSponsorship;
	}

	public void setCommentable(String commentable){
		this.commentable = commentable;
	}

	public String getCommentable(){
		return commentable;
	}

	public void setFirstPublicationDate(String firstPublicationDate){
		this.firstPublicationDate = firstPublicationDate;
	}

	public String getFirstPublicationDate(){
		return firstPublicationDate;
	}

	public void setThumbnail(String thumbnail){
		this.thumbnail = thumbnail;
	}

	public String getThumbnail(){
		return thumbnail;
	}

	public void setIsPremoderated(String isPremoderated){
		this.isPremoderated = isPremoderated;
	}

	public String getIsPremoderated(){
		return isPremoderated;
	}

	public void setNewspaperPageNumber(String newspaperPageNumber){
		this.newspaperPageNumber = newspaperPageNumber;
	}

	public String getNewspaperPageNumber(){
		return newspaperPageNumber;
	}

	public void setCommentCloseDate(String commentCloseDate){
		this.commentCloseDate = commentCloseDate;
	}

	public String getCommentCloseDate(){
		return commentCloseDate;
	}

	public void setStandfirst(String standfirst){
		this.standfirst = standfirst;
	}

	public String getStandfirst(){
		return standfirst;
	}

	public void setShowInRelatedContent(String showInRelatedContent){
		this.showInRelatedContent = showInRelatedContent;
	}

	public String getShowInRelatedContent(){
		return showInRelatedContent;
	}

	public void setLastModified(String lastModified){
		this.lastModified = lastModified;
	}

	public String getLastModified(){
		return lastModified;
	}

	public void setLegallySensitive(String legallySensitive){
		this.legallySensitive = legallySensitive;
	}

	public String getLegallySensitive(){
		return legallySensitive;
	}

	@Override
 	public String toString(){
		return 
			"Fields{" + 
			"wordcount = '" + wordcount + '\'' + 
			",shouldHideAdverts = '" + shouldHideAdverts + '\'' + 
			",shouldHideReaderRevenue = '" + shouldHideReaderRevenue + '\'' + 
			",charCount = '" + charCount + '\'' + 
			",shortUrl = '" + shortUrl + '\'' + 
			",showAffiliateLinks = '" + showAffiliateLinks + '\'' + 
			",main = '" + main + '\'' + 
			",body = '" + body + '\'' + 
			",productionOffice = '" + productionOffice + '\'' + 
			",newspaperEditionDate = '" + newspaperEditionDate + '\'' + 
			",bodyText = '" + bodyText + '\'' + 
			",publication = '" + publication + '\'' + 
			",trailText = '" + trailText + '\'' + 
			",lang = '" + lang + '\'' + 
			",headline = '" + headline + '\'' + 
			",authorName = '" + authorName + '\'' +
			",isInappropriateForSponsorship = '" + isInappropriateForSponsorship + '\'' + 
			",commentable = '" + commentable + '\'' + 
			",firstPublicationDate = '" + firstPublicationDate + '\'' + 
			",thumbnail = '" + thumbnail + '\'' + 
			",isPremoderated = '" + isPremoderated + '\'' + 
			",newspaperPageNumber = '" + newspaperPageNumber + '\'' + 
			",commentCloseDate = '" + commentCloseDate + '\'' + 
			",standfirst = '" + standfirst + '\'' + 
			",showInRelatedContent = '" + showInRelatedContent + '\'' + 
			",lastModified = '" + lastModified + '\'' + 
			",legallySensitive = '" + legallySensitive + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(wordcount);
		dest.writeString(shouldHideAdverts);
		dest.writeString(shouldHideReaderRevenue);
		dest.writeString(charCount);
		dest.writeString(shortUrl);
		dest.writeString(showAffiliateLinks);
		dest.writeString(main);
		dest.writeString(body);
		dest.writeString(productionOffice);
		dest.writeString(newspaperEditionDate);
		dest.writeString(bodyText);
		dest.writeString(publication);
		dest.writeString(trailText);
		dest.writeString(lang);
		dest.writeString(headline);
		dest.writeString(authorName);
		dest.writeString(isInappropriateForSponsorship);
		dest.writeString(commentable);
		dest.writeString(firstPublicationDate);
		dest.writeString(thumbnail);
		dest.writeString(isPremoderated);
		dest.writeString(newspaperPageNumber);
		dest.writeString(commentCloseDate);
		dest.writeString(standfirst);
		dest.writeString(showInRelatedContent);
		dest.writeString(lastModified);
		dest.writeString(legallySensitive);
	}
}