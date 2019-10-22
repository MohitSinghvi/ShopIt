package com.example.shopit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginPage extends AppCompatActivity {

//    DatabaseHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
//        myDB = new DatabaseHelper(this);


    }
    public void goBack(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public void loginUser(View view){

        DisplayMessageActivity disp=new DisplayMessageActivity();

        DatabaseHelper myDb = new DatabaseHelper(this);
        TextView email = findViewById(R.id.login_email);
        TextView password = findViewById(R.id.login_password);

//        Session.endsession(this);

        if(myDb.isUserRegistered(email.getText().toString(),password.getText().toString())){
            Session.endsession(this);
            Session.setuser(this,email.getText().toString(),password.getText().toString());
            goBack(view);

        }
        else{
            disp.showMessage(this,"Invalid Credentials","Please try again!");
        }





    }


}
