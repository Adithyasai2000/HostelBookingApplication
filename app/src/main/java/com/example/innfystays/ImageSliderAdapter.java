package com.example.innfystays;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;

public class ImageSliderAdapter extends SliderViewAdapter<ImageSliderAdapter.SliderViewHolder> {
    Context context;
    ArrayList<SliderData> roomImageList;

    public ImageSliderAdapter() {
    }

    public ImageSliderAdapter(Context context, ArrayList<SliderData> roomImagesList) {
        this.context=context;
        this.roomImageList=roomImagesList;
    }


    @Override
    public ImageSliderAdapter.SliderViewHolder onCreateViewHolder(ViewGroup parent) {
        return new SliderViewHolder(LayoutInflater.from(context).inflate(R.layout.slider_image,parent,false));
    }

    @Override
    public void onBindViewHolder(ImageSliderAdapter.SliderViewHolder viewHolder, int position) {


        // Toast.makeText(context,roomImageList.get(position).getImageUrl().toString(),Toast.LENGTH_SHORT).show();

        Glide.with(viewHolder.itemView)
                .load(roomImageList.get(position).getImageUrl()).placeholder(R.mipmap.ic_banner)
                .fitCenter()
                .into(viewHolder.imageView);

    }

    @Override
    public int getCount() {
        return roomImageList.size();
    }


    static public class SliderViewHolder extends SliderViewAdapter.ViewHolder {
        View itemView;
        ImageView imageView;

        public SliderViewHolder(View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.idroomimageslider);
            this.itemView=itemView;

        }
    }
}
