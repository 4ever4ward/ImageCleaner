package ua.matvienko_apps.imagecleaner;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class FilterSelection extends AppCompatActivity
        implements android.view.View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    ProgressDialog progressDialog;
    ImageView arrowLeft;
    ImageView arrowRight;
    TextView text_matrixSize;
    TabHost tabs;
    Filters filters;
    Uri image_Uri;
    Bitmap picture;
    int matrix_size;
    Bitmap editedImage;
    SeekBar medianSeekBar;
    TextView medianMatrixSizeText;
    CheckBox enableMedianFilter;
    int medianMatrixSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_selection);

        tabs = (TabHost) findViewById(R.id.tabHost);
        arrowLeft = (ImageView) findViewById(R.id.arrowLeft);
        arrowRight = (ImageView) findViewById(R.id.arrowRight);
        medianSeekBar = (SeekBar) findViewById(R.id.MedianSeekBar);
        text_matrixSize = (TextView) findViewById(R.id.MatrixSize);
        medianMatrixSizeText = (TextView) findViewById(R.id.medianMatrixSize);
        enableMedianFilter = (CheckBox) findViewById(R.id.enableMedianFilter);

        filters = new Filters();

        medianSeekBar.setEnabled(false);
        text_matrixSize.setVisibility(View.GONE);
        medianMatrixSizeText.setVisibility(View.GONE);

        progressDialog = new ProgressDialog(this,R.style.MyTheme);
        progressDialog.setProgressStyle(android.R.style.Widget_DeviceDefault_ProgressBar_Large);
        progressDialog.setCancelable(true);

        /**Parse path to image from intent and take image-bitmap from this path*/
        Bundle extras = getIntent().getExtras();
        image_Uri = Uri.parse(extras.getString("image_URI")); //parse Uri from intent

        picture = null;
        try {
            picture = MediaStore.Images.Media.getBitmap(getContentResolver(), image_Uri); //find Bitmap by Uri and save it in picture
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**---------------------------------------------------------------------*/

        /**-----Create tabs, named each of them and select layout for it--------*/
        tabs.setup();
        TabHost.TabSpec spec;

        spec = tabs.newTabSpec("tag1");
        spec.setContent(R.id.MedianLayout);
        spec.setIndicator("Median Filter");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("tag2");
        spec.setContent(R.id.SmoothLayout);
        spec.setIndicator("Smooth Filter");
        tabs.addTab(spec);
        /**---------------------------------------------------------------------*/

        arrowLeft.setOnClickListener(this);
        arrowRight.setOnClickListener(this);
        enableMedianFilter.setOnClickListener(this);
        medianSeekBar.setOnSeekBarChangeListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.arrowLeft): {
                progressDialog.dismiss();
                finish();
                break;
            }
            case (R.id.arrowRight): {
                FilterTask filterTask = new FilterTask();
                if (enableMedianFilter.isChecked()) {
                    filterTask.execute();
                } else
                    Toast.makeText(FilterSelection.this, "Please use at least one filter", Toast.LENGTH_SHORT).show();
                break;
            }
            case (R.id.enableMedianFilter): {
                if (enableMedianFilter.isChecked()) {
                    medianSeekBar.setEnabled(true);
                    medianMatrixSizeText.setVisibility(View.VISIBLE);
                    text_matrixSize.setVisibility(View.VISIBLE);
                } else {
                    medianSeekBar.setEnabled(false);
                    medianMatrixSizeText.setVisibility(View.GONE);
                    text_matrixSize.setVisibility(View.GONE);
                }
                break;
            }
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.MedianSeekBar: {
                if (progress > 3) {
                    progress = (progress / 2) * 2;
                    progress++;
                    seekBar.setProgress(progress);
                } else {
                    progress = 3;
                    seekBar.setProgress(progress);
                }
                medianMatrixSizeText.setText(progress + " x " + progress);
                medianMatrixSize = progress;
            }
        }
    }

    class FilterTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {
            editedImage = filters.MedianFilter(picture, matrix_size);

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            matrix_size = medianSeekBar.getProgress();
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            try {
                //Write file
                String filename = "bitmap.png";
                FileOutputStream stream = FilterSelection.this.openFileOutput(filename, Context.MODE_PRIVATE);
                editedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);

                //Cleanup
                stream.close();
                editedImage.recycle();

                //Pop intent
                Intent intent = new Intent(FilterSelection.this, ShowResult.class);
                intent.putExtra("editedImage", filename);
                startActivity(intent);

            } catch (Exception e) {
                e.printStackTrace();
            }
            progressDialog.dismiss();
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
