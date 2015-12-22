package com.example.saylani_it.cameraintents;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.VideoView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends ActionBarActivity {
    ImageView imageView;
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.image);
        videoView = (VideoView) findViewById(R.id.video);

//        this is for picking Image from Gallery or file
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent,0);

        //For capturing Image from Camera
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent,1);

        //For Video From Camera
//        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//        startActivityForResult(intent, 2);
        //For Selecting Video from Gallery
   //     Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
    //    startActivityForResult(intent, 3);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;
        if (requestCode == 0 && resultCode == RESULT_OK && data != null) {

            try {
                //this is for picking Image from Gallery or file
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            bitmap = data.getExtras().getParcelable("data");
            try {
                //write the captured image-file to SD Card

                File file = new File(Environment.getExternalStorageDirectory(), "new.png");
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, fileOutputStream);
                fileOutputStream.close();


            } catch (Exception e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(bitmap);
        }
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            //For Videos from Camera
            videoView.setVideoURI(data.getData());
            videoView.start();
        }
        if (requestCode == 3 && resultCode == RESULT_OK && data != null) {
            videoView.setVideoURI(data.getData());
            videoView.start();

        }
    }
}
