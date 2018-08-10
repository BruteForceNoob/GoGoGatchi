package com.gogogatchi.gogogatchi.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gogogatchi.gogogatchi.R;
import com.gogogatchi.gogogatchi.core.LocationData;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder>{

    List<LocationData> locationList=new ArrayList<>();
    Context context;

    public FeedAdapter(List<LocationData> locationList,Context context){
        this.locationList = locationList;
        this.context=context;
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_card, parent, false);
        FeedViewHolder pvh = new FeedViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, int position) {

        try {

           // holder.locationImage.setImageURI(android.net.Uri.parse(locationList.get(position).getImageUrl().toString()));
            Glide.with(context).load(locationList.get(position).getImageUrl()).into(holder.locationImage);
            holder.locationTitle.setText(locationList.get(position).getLocationName());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class FeedViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView locationTitle;
        ImageView locationImage;

        FeedViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            locationTitle = (TextView)itemView.findViewById(R.id.cardTitle);
            locationImage = (ImageView)itemView.findViewById(R.id.cardImage);
        }
    }

}
