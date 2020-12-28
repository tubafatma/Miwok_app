package com.example.android.miwok;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {
    //Resource id for color for list of words
    private int mColorId;
    @Override
    public View getView(int position,View convertView,ViewGroup parent)
    {
        //check if the existing view is being reused
        View listItemView=convertView;
        if(listItemView==null) {
           listItemView= LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        Word currentword=getItem(position);
        TextView name=(TextView)listItemView.findViewById(R.id.miwok_text_view);
        name.setText(currentword.getMiwokTranslation());
        TextView def=(TextView)listItemView.findViewById(R.id.default_text_view);
        def.setText(currentword.getDefaultTranslation());
        ImageView images=(ImageView)listItemView.findViewById(R.id.Images);
        if(currentword.hasImage())
        images.setImageResource(currentword.getImageResourceId());
        else
            images.setVisibility(View.GONE);
        View textContainer=listItemView.findViewById(R.id.textContainer);
        //Find the color that resource id maps to
        int color= ContextCompat.getColor(getContext(),mColorId);
        textContainer.setBackgroundColor(color);
        return listItemView;

    }
   public WordAdapter(Activity context, ArrayList<Word>words,int ColorId)
   {
       super(context,0,words);
       mColorId=ColorId;
   }
}
