package com.example.anirudhs.redditapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;



public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener {



private List<Listitem> listitems;
    private Context context;
    MyDBHandler dbHandler = new MyDBHandler(context,null,null,1);
    private Listitem listitem;


    public MyAdapter(List<Listitem> listitems, Context context) {
        this.listitems = listitems;
        this.context = context;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item ,parent, false);

        v.setOnClickListener(new MyOnClickListener());
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        listitem = listitems.get(position);
        holder.myText.setText(listitem.getDescr());
        holder.commentsText.setText(listitem.getComm() + " comments");
        Picasso.with(context).load(listitem.getImgurl()).into(holder.myImage);


    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    @Override
    public void onClick(View v) {

        dbHandler.addReddit(listitem);


    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView myText;
        public ImageView myImage;
        public TextView commentsText;


        public ViewHolder(View itemView) {
            super(itemView);

            myText = (TextView)itemView.findViewById(R.id.myText);
            myImage = (ImageView)itemView.findViewById(R.id.myImage);
            commentsText = (TextView)itemView.findViewById(R.id.commentText);

        }
    }
}
