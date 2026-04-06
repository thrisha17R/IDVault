package com.example.idvault;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    Context context;
    ArrayList<CardModel> list;

    public CardAdapter(Context context, ArrayList<CardModel> list) {
        this.context = context;
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name;
        Button share;

        public ViewHolder(View v) {
            super(v);
            img = v.findViewById(R.id.cardImg);
            name = v.findViewById(R.id.cardName);
            share = v.findViewById(R.id.shareBtn);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {

        CardModel m = list.get(position);
        h.name.setText(m.name);

        // Load image
        try {
            FileInputStream fis = context.openFileInput(m.fileName);
            h.img.setImageBitmap(BitmapFactory.decodeStream(fis));
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 👉 Open fullscreen
        h.img.setOnClickListener(v -> {
            Intent intent = new Intent(context, CardDetailActivity.class);
            intent.putExtra("file", m.fileName);
            context.startActivity(intent);
        });

        // 👉 Share image
        h.share.setOnClickListener(v -> {
            try {
                File file = new File(context.getFilesDir(), m.fileName);

                Uri uri = FileProvider.getUriForFile(
                        context,
                        context.getPackageName() + ".provider",
                        file
                );

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                context.startActivity(Intent.createChooser(intent, "Share Card"));

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}