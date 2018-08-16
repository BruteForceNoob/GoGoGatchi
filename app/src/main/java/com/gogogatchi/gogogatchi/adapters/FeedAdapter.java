package com.gogogatchi.gogogatchi.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gogogatchi.gogogatchi.R;
import com.gogogatchi.gogogatchi.activities.HomeSwipeActivity;
import com.gogogatchi.gogogatchi.activities.LocationViewActivity;
import com.gogogatchi.gogogatchi.core.LocationData;
import com.gogogatchi.gogogatchi.util.UserUtil;

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
        FeedViewHolder pvh = new FeedViewHolder(v,locationList,context,this);
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

    // adapter to manage contents in the feed
    public static class FeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cv;
        ImageButton imageButton;
        TextView locationTitle;
        ImageView locationImage;
        List<LocationData> locationDataList=new ArrayList<>();
        Context context;
        FeedAdapter feedAdapter;


        FeedViewHolder(View itemView, List<LocationData> locationDataList, Context context, FeedAdapter feedAdapter) {
            super(itemView);
            this.locationDataList=locationDataList;
            this.context=context;
            imageButton=(ImageButton) itemView.findViewById(R.id.deleteButton);
            imageButton.setOnClickListener(this);
            this.feedAdapter=feedAdapter;


            cv = (CardView)itemView.findViewById(R.id.cv);
            cv.setOnClickListener(this);
            locationTitle = (TextView)itemView.findViewById(R.id.cardTitle);
            locationImage = (ImageView)itemView.findViewById(R.id.cardImage);
        }



        @Override
        public void onClick(View view) {

            int position = getAdapterPosition();
            // checks type of view
            if(view.getId()==R.id.cv) {

                LocationData locationData = locationDataList.get(position);
                Intent intent = new Intent(this.context, LocationViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("mLocationProfile", locationData);
                intent.putExtras(bundle);
                this.context.startActivity(intent);
            }
            else if(view.getId()==R.id.deleteButton)
            {

                Log.i("position", String.valueOf(position)+" "+String.valueOf(HomeSwipeActivity.locationDataList.size())+" "+String.valueOf(locationDataList.size()));
                locationDataList.remove(position);
                //HomeSwipeActivity.locationDataList.remove(position);
                feedAdapter.notifyItemRemoved(position);





            }

        }

    }

}
