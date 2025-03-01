package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Card;
import com.example.myapplication.R;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private List<Book> cardItemList;

    public CardAdapter(List<Book> cardItemList) {
        this.cardItemList = cardItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("CardAdapter", "יצרנו ViewHolder חדש");  // לוג על יצירת ViewHolder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book cardItem = cardItemList.get(position);

        Log.d("CardAdapter", "מתחילים לטעון תמונה לכרטיס בעמדה: " + position);

        // טעינת התמונה בעזרת Glide
        Glide.with(holder.imageView.getContext())
                .load(cardItem.getImageUrl())
                .into(holder.imageView);

        Log.d("CardAdapter", "התמונה הועמדה לכרטיס בעמדה: " + position);
    }

    @Override
    public int getItemCount() {
        Log.d("CardAdapter", "מספר הכרטיסים ברשימה: " + cardItemList.size());
        return cardItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            Log.d("CardAdapter", "יצרנו ViewHolder עם ImageView");
        }
    }
}
