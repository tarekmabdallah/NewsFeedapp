
package com.example.tarek.news.models.section;

import com.example.tarek.news.models.section.articles.Article;
import com.example.tarek.news.models.sections.Edition;
import com.example.tarek.news.models.sections.Section;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response extends CommonResponse {

    @SerializedName("edition")
    private Edition edition;

    @SerializedName("section")
    private Section section;

    @SerializedName("userTier")
    private String userTier;

    public Edition getEdition() {
        return edition;
    }

    public void setEdition(Edition edition) {
        this.edition = edition;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public String getUserTier() {
        return userTier;
    }

    public void setUserTier(String userTier) {
        this.userTier = userTier;
    }

}
