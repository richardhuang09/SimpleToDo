package com.example.richardhuang.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        position = getIntent().getIntExtra("position", 0);
        String text = getIntent().getStringExtra("string");
        EditText mtEdit = (EditText) findViewById(R.id.mtEdit);
        mtEdit.append(text);
        mtEdit.requestFocus();

    }

    public void onSave(View view) {
        EditText mtEdit = (EditText) findViewById(R.id.mtEdit);
        String newText = mtEdit.getText().toString();
        Intent data = new Intent();
        data.putExtra("newText", newText);
        data.putExtra("position", position);
        setResult(RESULT_OK, data);
        finish();
    }

}
