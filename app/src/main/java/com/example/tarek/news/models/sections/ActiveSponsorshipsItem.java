package com.example.tarek.news.models.sections;

import com.google.gson.annotations.SerializedName;

public class ActiveSponsorshipsItem{

	@SerializedName("aboutLink")
	private String aboutLink;

	@SerializedName("sponsorLogoDimensions")
	private SponsorLogoDimensions sponsorLogoDimensions;

	@SerializedName("sponsorName")
	private String sponsorName;

	@SerializedName("sponsorLink")
	private String sponsorLink;

	@SerializedName("sponsorshipType")
	private String sponsorshipType;

	@SerializedName("sponsorLogo")
	private String sponsorLogo;

	@SerializedName("validFrom")
	private String validFrom;

	@SerializedName("validTo")
	private String validTo;

	public void setAboutLink(String aboutLink){
		this.aboutLink = aboutLink;
	}

	public String getAboutLink(){
		return aboutLink;
	}

	public void setSponsorLogoDimensions(SponsorLogoDimensions sponsorLogoDimensions){
		this.sponsorLogoDimensions = sponsorLogoDimensions;
	}

	public SponsorLogoDimensions getSponsorLogoDimensions(){
		return sponsorLogoDimensions;
	}

	public void setSponsorName(String sponsorName){
		this.sponsorName = sponsorName;
	}

	public String getSponsorName(){
		return sponsorName;
	}

	public void setSponsorLink(String sponsorLink){
		this.sponsorLink = sponsorLink;
	}

	public String getSponsorLink(){
		return sponsorLink;
	}

	public void setSponsorshipType(String sponsorshipType){
		this.sponsorshipType = sponsorshipType;
	}

	public String getSponsorshipType(){
		return sponsorshipType;
	}

	public void setSponsorLogo(String sponsorLogo){
		this.sponsorLogo = sponsorLogo;
	}

	public String getSponsorLogo(){
		return sponsorLogo;
	}

	public void setValidFrom(String validFrom){
		this.validFrom = validFrom;
	}

	public String getValidFrom(){
		return validFrom;
	}

	public void setValidTo(String validTo){
		this.validTo = validTo;
	}

	public String getValidTo(){
		return validTo;
	}

	@Override
 	public String toString(){
		return 
			"ActiveSponsorshipsItem{" + 
			"aboutLink = '" + aboutLink + '\'' + 
			",sponsorLogoDimensions = '" + sponsorLogoDimensions + '\'' + 
			",sponsorName = '" + sponsorName + '\'' + 
			",sponsorLink = '" + sponsorLink + '\'' + 
			",sponsorshipType = '" + sponsorshipType + '\'' + 
			",sponsorLogo = '" + sponsorLogo + '\'' + 
			",validFrom = '" + validFrom + '\'' + 
			",validTo = '" + validTo + '\'' + 
			"}";
		}
}