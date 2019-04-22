package com.gst.socialcomponents.adapters;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gst.socialcomponents.R;
import com.gst.socialcomponents.model.Factureitemdata;

import java.util.ArrayList;

public class CustomFacturesMonthsAdapter extends ArrayAdapter<Factureitemdata> implements View.OnClickListener{

    private ArrayList<Factureitemdata> dataSet;
    Context mContext;

     private static class ViewHolder {
        TextView txtName;
        TextView txtType;
        TextView txtVersion;
        ImageView imageview;
     }

    public CustomFacturesMonthsAdapter(ArrayList<Factureitemdata> data, Context context) {
        super(context, R.layout.simplerow, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        Factureitemdata dataModel=(Factureitemdata) object;

        switch (v.getId())
        {
            case R.id.item_info:

                break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         Factureitemdata dataModel = getItem(position);
         ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.simplerow, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtType = (TextView) convertView.findViewById(R.id.version_heading);
            viewHolder.txtVersion = (TextView) convertView.findViewById(R.id.version_number);
            viewHolder.imageview=(ImageView)convertView.findViewById(R.id.item_info);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

         lastPosition = position;

        viewHolder.txtName.setText(dataModel.getName());
        viewHolder.txtType.setText(dataModel.getMontant());
        viewHolder.txtVersion.setText(dataModel.getRemise_cle());
        viewHolder.imageview.setImageResource(R.drawable.ic_done);
        return convertView;
    }
}
