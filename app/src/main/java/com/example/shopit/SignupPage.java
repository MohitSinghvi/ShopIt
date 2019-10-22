package com.example.shopit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SignupPage extends AppCompatActivity {


    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);
        myDb = new DatabaseHelper(this);
    }

    public void signupUser(View view){

        TextView name= (TextView)findViewById(R.id.signup_name);
        TextView username= (TextView)findViewById(R.id.signup_username);
        TextView password1=(TextView)findViewById(R.id.signup_password1);
        TextView password2=(TextView)findViewById(R.id.signup_password2);
        String p1=password1.getText().toString();
        String p2=password2.getText().toString();
        if(!p1.equals(p2)) {
            showMessage("password mismatch","Try again"+p1+p2);
        }
        else{
//            showMessage("logging ","Logging you in");


            if (myDb.isUserRegistered(username.getText().toString(), password1.getText().toString())) {

                showMessage("Already registered","Try logging in");
            } else {
                myDb.insertUser(name.getText().toString(),username.getText().toString(), password1.getText().toString());
                showMessage("Congratulations","Sign up Successful");

            }

        }


    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void goBack(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }



}
