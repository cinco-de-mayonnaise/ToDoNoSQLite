package com.abdullah.beginner.todonosqlite;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import android.view.View;
import android.widget.ArrayAdapter;

import com.abdullah.beginner.todonosqlite.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ArrayAdapter<String> itemsAdapter;
    ArrayList<String> items;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // load bindings and set xml as ui
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setSupportActionBar(binding.toolbar); // set toolbar

        // proper inset to not draw over system ui
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // set array of items
        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this.getApplicationContext(), R.layout.listitemview, items);
        binding.listView.setAdapter(itemsAdapter);
//
        items.add("Chocolate");
        items.add("Just Monika");    // add defaults
//
        // set up View Listener
        binding.listView.setOnItemLongClickListener((adapter, item, pos, id) -> {
            // pos: int, index of array that was selected
            items.remove(pos);
            // need to update list since we just changed it
            itemsAdapter.notifyDataSetChanged();
            // Return true consumes the long click event (marks it as handled)
            return true;
        });
    }

    public void click_addItem(View view)
    {
        String s = binding.editTextText.getText().toString();
        if (s.isEmpty())
            return;
        itemsAdapter.add(s);
        binding.editTextText.setText("");
    }
}