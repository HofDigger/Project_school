package com.example.myapplication;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.CardItem;
import com.example.myapplication.R;

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
        holder.cardImage.setImageResource(cardItem.getImageResource());
        holder.cardText.setText(cardItem.getText());
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        ImageView cardImage;
        TextView cardText;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            cardImage = itemView.findViewById(R.id.card_image);
            cardText = itemView.findViewById(R.id.card_text);
        }
    }
}
