package com.example.shopit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayMessageActivity {


//        setContentView(R.layout.activity_display_message);
//
//        Intent intent =getIntent();
//        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
//
//        TextView textView = findViewById(R.id.textView);
//        textView.setText(message);


    public void showMessage(Context context, String title, String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
