package com.example.tarek.news.views.sections;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tarek.news.R;

import static com.example.tarek.news.utils.Constants.ZERO;
import static com.example.tarek.news.utils.Constants.makeTypeFaceTextStyle;


/**
 * used in the spinners
 */
public class SpinnerAdapter extends ArrayAdapter<String> {

    private SpinnerOnItemClickedListener spinnerOnItemClickedListener;

    SpinnerAdapter(Context context) {
        super(context, ZERO);
    }

    public void setSpinnerOnItemClickedListener(SpinnerOnItemClickedListener spinnerOnItemClickedListener) {
        this.spinnerOnItemClickedListener = spinnerOnItemClickedListener;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView (position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView (position, convertView, parent);
    }

    private View getCustomView(final int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        View root = convertView;
        ItemSpinnerViewHolder itemSpinnerViewHolder;
        if (null == root){
            root = LayoutInflater.from(context).inflate(R.layout.section_item, parent, false);
            itemSpinnerViewHolder = new ItemSpinnerViewHolder();
            itemSpinnerViewHolder.item = root.findViewById(R.id.section_title);
            root.setTag(itemSpinnerViewHolder);
        }else {
            itemSpinnerViewHolder = (ItemSpinnerViewHolder) root.getTag();
        }
        makeTypeFaceTextStyle(itemSpinnerViewHolder.item);
        itemSpinnerViewHolder.item.setText(getItem(position));
        if (null != spinnerOnItemClickedListener){
            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    spinnerOnItemClickedListener.onSelectItem(position);
                }
            });
        }
        return root;
    }

    class ItemSpinnerViewHolder {
        TextView item;
    }
}

