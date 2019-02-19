package com.example.skambo.oyepaisa;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Customadapter extends ArrayAdapter<Listitems> {

    private Context mContext;
    private List<Listitems> recordlist = new ArrayList<Listitems>();


    public Customadapter(Context context, ArrayList<Listitems> list) {
        super(context, 0, list);
        mContext = context;
        recordlist = list;


    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitem = convertView;
        if (listitem == null)
            listitem = LayoutInflater.from(mContext).inflate(R.layout.listitem, parent, false);
        Listitems items = recordlist.get(position);
        TextView description = listitem.findViewById(R.id.list_description);
        description.setText(items.getMdescription());
        TextView amount = listitem.findViewById(R.id.list_amount);
        amount.setText(items.getMamount());

        return listitem;

    }
}
