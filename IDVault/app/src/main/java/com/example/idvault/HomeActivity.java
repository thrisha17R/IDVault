package com.example.idvault;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;

import java.io.*;
import java.util.*;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<CardModel> list;
    CardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerView);

        findViewById(R.id.addBtn).setOnClickListener(v ->
                startActivity(new Intent(this, AddCardActivity.class))
        );

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 👉 Swipe to delete
        ItemTouchHelper.SimpleCallback swipe =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

                    @Override
                    public boolean onMove(RecyclerView rv, RecyclerView.ViewHolder vh, RecyclerView.ViewHolder t) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder vh, int dir) {
                        int pos = vh.getAdapterPosition();
                        deleteCard(pos);
                    }
                };

        new ItemTouchHelper(swipe).attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        list = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(openFileInput("cards.txt"))
            );

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                list.add(new CardModel(parts[0], parts[1]));
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        adapter = new CardAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }

    private void deleteCard(int pos) {
        try {
            CardModel m = list.get(pos);
            deleteFile(m.fileName);
            list.remove(pos);
            adapter.notifyItemRemoved(pos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}