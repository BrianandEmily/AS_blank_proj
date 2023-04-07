package com.example.simpletodo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /*Define variables/elements here*/
    List<String> items;

    Button btnAdd;
    EditText etItem;
    RecyclerView rvItems;
    ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Find references to elements*/
        btnAdd = findViewById(R.id.btnAdd);
        etItem = findViewById(R.id.etItem);
        rvItems = findViewById(R.id.rvItems);

        /*Test Code*/
        //etItem.setText("Hardcoded data!");

        loadItems();
        /*Test Code*/
        //items.add("Learn Android MAD");
        //items.add("Make an awesome app");
        //items.add("Win FullyHacks!");

        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener() {
            @Override
            public void OnItemLongClicked(int position) {
                /*Delete item from model*/
                items.remove(position);
                /*Notify adapter*/
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Item removed!", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };

        /*Set adapter*/
        itemsAdapter = new ItemsAdapter(items, onLongClickListener);
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String todoItem = etItem.getText().toString();
                /*Add item to model*/
                items.add(todoItem);
                /*Notify adapter that item has been inserted*/
                itemsAdapter.notifyItemInserted(items.size() - 1);
                etItem.setText("");
                /*Small pop up that indicates an action was done!*/
                Toast.makeText(getApplicationContext(), "Item added!", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });
    }

    /*File methods*/
    /*Note that these functions exist outside of onCreate{}*/

    /*Function to return file that stores list of items*/
    private File getDataFile() {
        return new File(getFilesDir(), "data.txt");
    }

    /*Function to load items by reading each line of the data file*/
    private void loadItems() {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading items", e);
            items = new ArrayList<>();
        }
    }

    /*Function to save items by writing them to the data file*/
    private void saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing items", e);
        }
    }
}