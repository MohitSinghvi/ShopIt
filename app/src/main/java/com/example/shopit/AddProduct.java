package com.example.shopit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

public class AddProduct extends AppCompatActivity {

    ImageView prod_img;
    Bitmap imageBitmap;
    DatabaseHelper mydb;
    public static final int CAMERA_REQUEST=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        prod_img=(ImageView)findViewById(R.id.uploaded_prod_img);
        mydb=new DatabaseHelper(this);

    }
    public void OpenCamera(View view){
//        Intent intent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent,CAMERA_REQUEST);
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAMERA_REQUEST);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CAMERA_REQUEST){
//            Bitmap bitmap=(Bitmap)data.getExtras().get("data");
//            prod_img.setImageBitmap(bitmap);
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            prod_img.setImageBitmap(imageBitmap);
        }

    }

    public void AddProduct(View view){
        TextView prod_name = findViewById(R.id.prod_name);
        TextView prod_brand = findViewById(R.id.prod_brand);
        TextView prod_category = findViewById(R.id.prod_category);
        TextView prod_description = findViewById(R.id.prod_description);
        TextView prod_price = findViewById(R.id.prod_price);

//        prod_img
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        byte[]image=stream.toByteArray();
        if(mydb.insertProduct(prod_name.getText().toString(),prod_brand.getText().toString(),prod_category.getText().toString(),prod_description.getText().toString(),image,Integer.parseInt(prod_price.getText().toString()))){

            showMessage("Success","Product Added Successfully");
        }
        else{
            showMessage("failed","cannot add");
        }



    }
    public void showMessage(String title, String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }







}
