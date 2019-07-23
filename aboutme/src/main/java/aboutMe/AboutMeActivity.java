/*
 *
 * Copyright 2019 tarekmabdallah91@gmail.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package aboutMe;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.gmail.tarekmabdallah91.aboutme.R;

public class AboutMeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        ImageView callIcon = findViewById(R.id.mobile);
        ImageView emailIcon = findViewById(R.id.email);
        ImageView linkedinIcon = findViewById(R.id.linkedin);
        ImageView githubIcon = findViewById(R.id.github);
        setOnClickListenerForViews(callIcon, emailIcon, linkedinIcon, githubIcon);
    }

    void onClickCallIcon() {
        Intent callNumber = new Intent(Intent.ACTION_DIAL);
        String uriData = String.format("tel:%s", getString(R.string.mobile_number));
        callNumber.setData(Uri.parse(uriData));
        startActivity(callNumber);
    }

    void onClickEmailIcon() {
        final String EMAIL_INTENT = "mailto:";
        final String TEXT_TYPE = "text/plain";
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse(EMAIL_INTENT));
        emailIntent.setType(TEXT_TYPE);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.email)});
        startActivity(emailIntent);
    }

    private void onClickLinkedinIcon() {
        final String youtubePage = "http://bit.ly/2kfdLeB"; // my linkedin page
        final String youtubePackage = "vnd.linkedin:";
        startExternalIntent(youtubePackage, youtubePage);
    }

    private void onClickGithubIcon() {
        final String youtubePage = "http://bit.ly/2Pi2h84"; // my github page
        final String youtubePackage = "vnd.github:";
        startExternalIntent(youtubePackage, youtubePage);
    }

    private Intent setExternalIntent(String externalAppPackage, String pageUrl) {
        Uri uri = Uri.parse(pageUrl);
        Intent externalIntent = new Intent(Intent.ACTION_VIEW);
        externalIntent.setPackage(externalAppPackage);
        externalIntent.setData(uri);
        return externalIntent;
    }

    private void startExternalIntent(String externalAppPackage, String pageUrl) {
        Intent intent = setExternalIntent(externalAppPackage, pageUrl);
        if (!isPackageInstalled(this, externalAppPackage))
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(pageUrl));
        startActivity(intent);
    }

    private boolean isPackageInstalled(Context c, String targetPackage) {
        PackageManager pm = c.getPackageManager();
        try {
            pm.getPackageInfo(targetPackage, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.mobile == id) onClickCallIcon();
        else if (R.id.email == id) onClickEmailIcon();
        else if (R.id.linkedin == id) onClickLinkedinIcon();
        else if (R.id.github == id) onClickGithubIcon();
    }

    private void setOnClickListenerForViews(View... views) {
        for (View view : views) view.setOnClickListener(this);
    }

    public static void openAboutMeActivity(Context context) {
        Intent openAboutMeActivity = new Intent(context, AboutMeActivity.class);
        context.startActivity(openAboutMeActivity);
    }
}
