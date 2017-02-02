package ua.matvienko_apps.imagecleaner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ShowResult extends AppCompatActivity implements android.view.View.OnClickListener {

    Bitmap editedImageBmp;
    ImageView arrowLeft;
    ImageView result_image;
    ImageView saveImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);

        saveImageButton = (ImageView) findViewById(R.id.saveImageButton);
        result_image = (ImageView) findViewById(R.id.resultImage);
        arrowLeft = (ImageView) findViewById(R.id.arrowLeft);

        String filename = getIntent().getStringExtra("editedImage");
        try {
            FileInputStream is = this.openFileInput(filename);
            editedImageBmp = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        result_image.setImageBitmap(editedImageBmp);

        arrowLeft.setOnClickListener(this);
        saveImageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case(R.id.arrowLeft) : {
                finish();
                break;
            }
            case(R.id.saveImageButton) : {
                Intent intent = new Intent(this, File_name.class);
                startActivity(intent);
                /*

                String file_name = "ImageClean";
                String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ImageCleaner";
                File dir = new File(file_path);
                if(!dir.exists())
                    dir.mkdirs();
                File file = new File(dir, file_name + ".png");
                if (!file.exists()) {
                    FileOutputStream out = null;
                    try {
                        out = new FileOutputStream(file);
                        editedImageBmp.compress(Bitmap.CompressFormat.PNG, 100, out);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (out != null) {
                                out.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else
                    Toast.makeText(ShowResult.this, "Image with name: [" + file_name + "] - already existed", Toast.LENGTH_SHORT).show();
                */

                break;
            }
        }
    }


}
