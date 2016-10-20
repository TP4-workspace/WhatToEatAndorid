package com.tmaxsoft.tp4.whattoeatandroid;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View v) {

            Toast toast = Toast.makeText(getApplicationContext(), "aa", Toast.LENGTH_LONG);


            String result = RestClient.getFood();
            if (result == null) result = "Nothing";
            Log.i("Food", result);

            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setImageResource(R.drawable.server_offline);

            if (result.compareTo("gyo_jjam") == 0) {
                imageView.setImageResource(R.drawable.gyo_jjam);
            } else if (result.compareTo("real_china") == 0) {
                imageView.setImageResource(R.drawable.real_china);
            } else if (result.compareTo("sun_dai") == 0) {
                imageView.setImageResource(R.drawable.sun_dai);
            }

            toast.setView(imageView);
            toast.setGravity(Gravity.CENTER, 50, 50);
            toast.show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        findViewById(R.id.button).setOnClickListener(mClickListener);
    }


        /*
    private void loadPhoto(ImageView imageView, int width, int height) {

        ImageView tempImageView = imageView;

        AlertDialog.Builder imageDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.custom_fullimage_dialog,
                (ViewGroup) findViewById(R.id.layout_root));
        ImageView image = (ImageView) layout.findViewById(R.id.fullimage);
        image.setImageDrawable(tempImageView.getDrawable());
        imageDialog.setView(layout);
        imageDialog.setPositiveButton(
                getResources().getString(R.string.ok_button), new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        imageDialog.create();
        imageDialog.show();
    }
    */
}
