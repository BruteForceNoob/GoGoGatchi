package com.gogogatchi.gogogatchi.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gogogatchi.gogogatchi.R;
import com.gogogatchi.gogogatchi.activities.LocationViewActivity;
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
        FeedViewHolder pvh = new FeedViewHolder(v,locationList,context);
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

    public static class FeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cv;
        TextView locationTitle;
        ImageView locationImage;
        List<LocationData> locationDataList=new ArrayList<>();
        Context context;


        FeedViewHolder(View itemView, List<LocationData> locationDataList, Context context) {
            super(itemView);
            this.locationDataList=locationDataList;
            this.context=context;
            cv = (CardView)itemView.findViewById(R.id.cv);
            cv.setOnClickListener(this);
            locationTitle = (TextView)itemView.findViewById(R.id.cardTitle);
            locationImage = (ImageView)itemView.findViewById(R.id.cardImage);
        }

        @Override
        public void onClick(View view) {
            int position=getAdapterPosition();
            LocationData locationData=locationDataList.get(position);
            Intent intent = new Intent(this.context, LocationViewActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("mLocationProfile", locationData);
            intent.putExtras(bundle);
            this.context.startActivity(intent);

        }

    }

}
