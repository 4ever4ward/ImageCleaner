package ua.matvienko_apps.imagecleaner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements android.view.View.OnClickListener {

    private ImageView loadImageButton;
    private ImageView arrowRight;
    private ImageView image;
    private String MAINACTIVITY_ERROR;
    public static final int REQUEST = 1;
    private Uri selectedImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MAINACTIVITY_ERROR = "MainActivity_Error";
        image = (ImageView) findViewById(R.id.Image); // Image that selected from gallery
        loadImageButton = (ImageView) findViewById(R.id.loadImageButton);
        arrowRight = (ImageView) findViewById(R.id.arrowRight);
        //loadImageButton = (FloatingActionButton) findViewById(R.id.fab);


        loadImageButton.setOnClickListener(this);
        arrowRight.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loadImageButton: {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*"); // Select image
                startActivityForResult(intent, REQUEST);
                break;
            }

            case R.id.arrowRight: {
                try {
                    Intent intent = new Intent(this, FilterSelection.class);
                    intent.putExtra("image_URI", selectedImage.toString());
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(this, "Please choose a picture" , Toast.LENGTH_SHORT).show();
                }
                break;
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Bitmap picture = null;
        if (requestCode == REQUEST && resultCode == RESULT_OK) {
            selectedImage = data.getData();

            try {
                picture = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            image.setImageBitmap(picture);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}