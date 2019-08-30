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

package com.gmail.tarekmabdallah91.smooth.ui.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gmail.tarekmabdallah91.news.R;
import com.gmail.tarekmabdallah91.news.databinding.FragmentDetailsBinding;
import com.gmail.tarekmabdallah91.news.models.articles.Article;
import com.gmail.tarekmabdallah91.smooth.ui.viewmodel.ArticleDetailsViewModel;

import static com.gmail.tarekmabdallah91.news.utils.ViewsUtils.loadImage;

public class ArticleDetailsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate view and obtain an instance of the binding class.
        final FragmentDetailsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false);
        ArticleDetailsViewModel viewModel = ViewModelProviders.of(getActivity()).get(ArticleDetailsViewModel.class);
        View view = binding.getRoot();
        viewModel.getArticle().observe(this, new Observer<Article>() {
            @Override
            public void onChanged(@Nullable Article article) {
                binding.setArticle(article);
            }
        });
        return view;
    }

    @BindingAdapter("android:src")
    public static void setImageUrl(ImageView view, String url) {
        if(url != null) loadImage(url, view);
    }
}
