/*
 * Copyright 2019 tarekmabdallah91@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.tarek.news.views.search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tarek.news.R;

import static com.example.tarek.news.utils.Constants.ZERO;
import static com.example.tarek.news.utils.Constants.makeTypeFaceTextStyle;


public class SearchHistoryAdapter extends ArrayAdapter<String> {

    private onClickItemListener itemListener;

    public SearchHistoryAdapter(Context context) {
        super(context, ZERO);
    }

    void setItemListener(onClickItemListener itemListener) {
        this.itemListener = itemListener;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        final Context context = parent.getContext();
        View root = convertView;
        final ListItemViewHolder listItemViewHolder ;
        if (null == root){
            root = LayoutInflater.from(context).inflate(R.layout.item_search_history_list, parent, false);
            listItemViewHolder = new ListItemViewHolder();
            listItemViewHolder.removeItem = root.findViewById(R.id.delete_icon);
            listItemViewHolder.textView = root.findViewById(R.id.search_item);
            makeTypeFaceTextStyle(listItemViewHolder.textView);
            root.setTag(listItemViewHolder);
        }else {
            listItemViewHolder = (ListItemViewHolder) root.getTag();
        }
        final String item = getItem(position);
        if (null != item && !item.isEmpty()){
            listItemViewHolder.textView.setText(getItem(position));
            View.OnClickListener onClickDeleteIconListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemListener.removeItem(item);
                }
            };
            listItemViewHolder.removeItem.setOnClickListener(onClickDeleteIconListener);
            View.OnClickListener onClickItemListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemListener.onClickItem(item);
                }
            };
            root.setOnClickListener(onClickItemListener);
        }
        return root;
    }

    class ListItemViewHolder {
        TextView textView;
        ImageView removeItem;
    }
}

