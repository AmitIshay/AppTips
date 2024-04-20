package com.example.myapplication_tips;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.google.firebase.firestore.proto.Target;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private List<Shift> shiftList;

    public MyAdapter(Context context, List<Shift> shiftList) {
        this.context = context;
        this.shiftList = shiftList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shift_row,parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Shift currentShift = shiftList.get(position);
        holder.moneyCredit.setText(currentShift.getMoneyCredit() + "אשראי: ");
        holder.moneyCash.setText(currentShift.getMoneyCash() + "מזומן: ");
        holder.moneySixINS.setText(currentShift.getMoneySixINS() + "הפרשה: ");
        holder.moneyMinusSixIns.setText(currentShift.getMoneyMinusSixIns() + "פחות הפרשה: ");
        holder.moneyPerHour.setText(currentShift.getMoneyPerHour() + "טיפ לשעה: ");
        holder.totalHours.setText(currentShift.getTotalHours() + "סהכ שעות: ");
        holder.moneyTips.setText(currentShift.getMoneyTips() + "סהכ טיפ: ");
        holder.date.setText(currentShift.getTimeNow() + "תאריך: ");
        holder.workerList.setText(currentShift.getWorkerArray() + "");
        String imageUrl = currentShift.getImageUrl();
        currentShift.setImageUrl(imageUrl);
        Log.d("bbbbbbbbbbbbb",imageUrl);
        Glide.with(context).load(imageUrl).fitCenter().into(holder.image);
        //ImageLoader.initImageLoader(holder.image.getContext());
        //ImageLoader.getInstance().load(currentShift.getImageUrl(), holder.image);
    }

    @Override
    public int getItemCount() {
        return shiftList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public MaterialTextView moneyCredit, moneyCash, moneySixINS;
        public ImageView image;
        public String userId, username;
        public MaterialTextView moneyMinusSixIns, moneyPerHour, totalHours, moneyTips, workerList, date;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            moneyCredit = itemView.findViewById(R.id.shift_moneyCredit);
            moneyCash = itemView.findViewById(R.id.shift_moneyCash);
            moneySixINS = itemView.findViewById(R.id.shift_moneySixINS);
            image = itemView.findViewById(R.id.shift_image);
            moneyMinusSixIns = itemView.findViewById(R.id.shift_moneyMinusSixIns);
            moneyPerHour = itemView.findViewById(R.id.shift_moneyPerHour);
            totalHours = itemView.findViewById(R.id.shift_totalHours);
            moneyTips = itemView.findViewById(R.id.shift_moneyTips);
            workerList = itemView.findViewById(R.id.shif_workerList);
            date = itemView.findViewById(R.id.shift_date);



        }
    }
}
