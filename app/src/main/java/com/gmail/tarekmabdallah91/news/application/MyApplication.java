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

package com.gmail.tarekmabdallah91.news.application;

import android.app.Activity;
import android.app.Application;

import com.gmail.tarekmabdallah91.news.di.modules.ApplicationModule;
import com.gmail.tarekmabdallah91.news.views.section.di.DaggerSectionActivityComponent;
import com.gmail.tarekmabdallah91.news.views.section.di.SectionActivityComponent;
import com.gmail.tarekmabdallah91.news.views.section.di.SectionActivityModule;

public class MyApplication extends Application {

    //    private ArticleFragmentComponent articleFragmentComponent;
    private SectionActivityComponent sectionActivityComponent;
//    private ApplicationComponent applicationComponent;

    public static MyApplication getMyApplication(Activity activity) {
        return (MyApplication) activity.getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Use it only in debug builds
//        if (BuildConfig.DEBUG) {
//            AndroidDevMetrics.initWith(this);
//        }
        ApplicationModule applicationModule = new ApplicationModule(this);
//        articleFragmentComponent = DaggerArticleFragmentComponent.builder()
//                .applicationModule(applicationModule)
//                .retrofitModule(new RetrofitModule())
//                .articleFragmentUtilsModule(new ArticleFragmentUtilsModule(getBaseContext()))
//                .build();
        sectionActivityComponent = DaggerSectionActivityComponent.builder()
                .sectionActivityModule(new SectionActivityModule())
                .build();
        // use .create if there is not any dependencies in the component
        // if there are so we use builder. (new instances).build();
//        applicationComponent = DaggerApplicationComponent.builder().applicationModule(applicationModule).build();
    }

//    public ArticleFragmentComponent getArticleFragmentComponent() {
//        return articleFragmentComponent;
//    }

    public SectionActivityComponent getSectionActivityComponent() {
        return sectionActivityComponent;
    }

    //    public ApplicationComponent getApplicationComponent() {
//        return applicationComponent;
//    }
}