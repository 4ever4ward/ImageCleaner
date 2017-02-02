    package ua.matvienko_apps.imagecleaner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

    public class File_name extends AppCompatActivity implements View.OnClickListener{

    Button btn_ok;
    EditText filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_name);

        filename = (EditText) findViewById(R.id.filename);
        btn_ok = (Button) findViewById(R.id.btn_ok);

        btn_ok.setOnClickListener(this);
    }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_ok: {


                    break;
                }

            }
        }
    }
