package com.example.richardhuang.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<Item> todoItems;
    ArrayAdapter<Item> aToDoAdapter;
    ListView lvItems;
    EditText etEditText;
    final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.Builder config = new Configuration.Builder(this);
        config.addModelClasses(Item.class);
        ActiveAndroid.initialize(config.create());
        setContentView(R.layout.activity_main);
        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAdapter);
        etEditText = (EditText) findViewById(R.id.etEditText);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String toFind = todoItems.get(position).toString();
                todoItems.remove(position);
                new Delete().from(Item.class).where("body = ?", toFind).execute();
                aToDoAdapter.notifyDataSetChanged();
                //writeItems();
                return true;
            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedFromList = lvItems.getItemAtPosition(position).toString();
                Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
                intent.putExtra("string", selectedFromList);
                intent.putExtra("position", position);
                startActivityForResult(intent, REQUEST_CODE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String newText = data.getExtras().getString("newText");
            String text = data.getExtras().getString("text");
            int position = data.getExtras().getInt("position", 0);
            List<Item> result = new Select().from(Item.class).where("Body = ?", text).execute();
//            String output = "";
//            for (Item item : result)
//            {
//                output += item.body + ",";
//            }
//            Log.d("TABLE", output);
            Item toUpdate = result.get(0);
            toUpdate.body = newText;
            toUpdate.save();
            todoItems.set(position, toUpdate);
            aToDoAdapter.notifyDataSetChanged();
            //writeItems();
        }
    }

    public void populateArrayItems() {
        readItems();
        aToDoAdapter = new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, todoItems);
    }

    public void onAddItem(View view) {
        Item toAdd = new Item(etEditText.getText().toString());
        toAdd.save();
        aToDoAdapter.add(toAdd);
        etEditText.setText("");
        //writeItems();
    }

    private void readItems() {
        todoItems = new ArrayList<Item>();
        List<Item> queryResults = new Select()
                .from(Item.class)
                .execute();
        for (Item item : queryResults) {
            todoItems.add(item);
        }
    }

//    private void readItems() {
//        File filesDir = getFilesDir();
//        File file = new File(filesDir, "todo.txt");
//        try {
//            todoItems = new ArrayList<String>(FileUtils.readLines(file));
//        } catch (IOException e) {
//            todoItems = new ArrayList<String>();
//        }
//    }


//    private void writeItems() {
//        File filesDir = getFilesDir();
//        File file = new File(filesDir, "todo.txt");
//        try {
//            FileUtils.writeLines(file, todoItems);
//        } catch (IOException e) {
//
//        }
//    }
}
