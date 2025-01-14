package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
    private Context context;
    private List<CardItem> cardList;

    public CardAdapter(Context context, List<CardItem> cardList) {
        this.context = context;
        this.cardList = cardList;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_item, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        CardItem cardItem = cardList.get(position);

        Log.d("CardAdapter", "Loading image for position " + position);
        String imageUrl = cardItem.getThumbnail();

        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(context)
                    .load(imageUrl)
                    .into(holder.cardImage); // טוען את התמונה ל-ImageButton
        } else {
            holder.cardImage.setImageResource(R.drawable.images); // מציג תמונה ברירת מחדל אם אין URL
        }
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    // ViewHolder שמנהל את ה-ImageButton
    public static class CardViewHolder extends RecyclerView.ViewHolder {
        ImageButton cardImage;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            cardImage = itemView.findViewById(R.id.card_image); // מקשר ל-ImageButton ב-XML
        }
    }
}
