package sn.ept.devmobile.finalprojectandroid.adpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import sn.ept.devmobile.finalprojectandroid.R;

public class ListViewAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    private List<String> categoryList = null;
    ArrayList<String> arraylist = new ArrayList<>();


    public ListViewAdapter(Context context, List<String> categoryList) {
        mContext = context;
        this.categoryList = categoryList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist.addAll(categoryList);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public String getItem(int position) {
        return categoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.list_view_item, null);
            holder.name = view.findViewById(R.id.listView_name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.name.setText(categoryList.get(position));
        return view;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        categoryList.clear();
        if (charText.length() == 0) {
            categoryList.addAll(arraylist);
        } else {
            for (String cat : arraylist) {
                if (cat.toLowerCase(Locale.getDefault()).contains(charText)) {
                    categoryList.add(cat);
                }
            }
        }
        notifyDataSetChanged();
    }
}
