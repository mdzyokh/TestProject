package com.vibeit.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.vibeit.R;
import com.vibeit.api.payload.CategoryPayload;

/**
 * @author Maria Dzyokh
 */
public class CategoriesAdapter extends BaseAdapter {

    private CategoryPayload[] categories;
    private LayoutInflater li;

    public CategoriesAdapter(Context ctx, CategoryPayload[] categories) {
         this.li = LayoutInflater.from(ctx);
         this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories.length;
    }

    @Override
    public CategoryPayload getItem(int position) {
        return categories[position];
    }

    @Override
    public long getItemId(int position) {
        return categories[position].getCategory().getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = li.inflate(R.layout.category_list_item, null);
        }
        ((TextView)view).setText(getItem(position).getCategory().getName());
        return view;
    }
}
