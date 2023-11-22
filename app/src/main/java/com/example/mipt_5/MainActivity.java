package com.example.mipt_5;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private EditText filterEditText; // for the future, now it's irrelevant



    // MANO API KEY GALIOJA IKI GRUODŽIO 6D., TAD DĖL VISA KO ĮKELIU
    // DABAR GAUNAMO REZULTATO SCREENSHOTĄ
    // NUOTRAUKOS LINKAS: https://app.gemoo.com/share/image-annotation/586014574619897856?codeId=v6gaKg1gn1XdJ&origin=imageurlgenerator&card=586014572816347136



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.itemList);
        filterEditText = findViewById(R.id.filterEditText); // for the future, now it's irrelevant

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        listView.setAdapter(adapter);

        filterEditText.addTextChangedListener(new TextWatcher() { // for the future, now it's irrelevant
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String filterText = charSequence.toString();
                filterWeatherObservations(filterText);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        DataLoader dataLoader = new DataLoader(adapter);
        dataLoader.execute();
    }

    private void filterWeatherObservations(String filterText) { // for the future, now it's irrelevant
        List<String> filteredList = new ArrayList<>();

        for (int i = 0; i < adapter.getCount(); i++) {
            String currentItem = adapter.getItem(i);
            if (currentItem != null && currentItem.toLowerCase().contains(filterText.toLowerCase())) {
                filteredList.add(currentItem);
            }
        }

        adapter.clear();
        adapter.addAll(filteredList);
        adapter.notifyDataSetChanged();
    }
}
