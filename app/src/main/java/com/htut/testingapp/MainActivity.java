package com.htut.testingapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.htut.testingapp.dreamteam.CreateDreamTeamActivity;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ImageView iv_jersey;
    Button btn_jersey,btn_save,btn_p_name,btn_intent_create_dream_team;
    TextView tv_jersey_no,tv_p_name;
    //RelativeLayout tv_p_name;
    EditText ed_p_name;
    ConstraintLayout cl_jersey_preview;
    Bitmap bitmap;
    File file,f;
    CustomView customView;

    String[] country = {"0", "1", "2", "3", "4", "5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_jersey = findViewById(R.id.iv_jersey);
        btn_jersey = findViewById(R.id.btn_jersey);
        btn_save = findViewById(R.id.btn_save);
        tv_jersey_no = findViewById(R.id.tv_jersey_no);
        btn_p_name = findViewById(R.id.btn_player_name);
        tv_p_name = findViewById(R.id.tv_jersey_player_name);
        ed_p_name = findViewById(R.id.ed_player_name);
        cl_jersey_preview = findViewById(R.id.cl_jersey_save);
        customView = new CustomView(this);
        btn_intent_create_dream_team = findViewById(R.id.btn_intent_create_dream_team);


        TelephonyManager manager = (TelephonyManager)getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        String carrierName = manager.getNetworkOperatorName();
        long total = TrafficStats.getTotalRxBytes() + TrafficStats.getTotalTxBytes();
        //Toast.makeText(this,total / 1024 + " Kb", Toast.LENGTH_SHORT).show();

        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isWifiConn = false;
        boolean isMobileConn = false;
        String networkName="";
        for (Network network : connMgr.getAllNetworks()) {
            NetworkInfo networkInfo = connMgr.getNetworkInfo(network);
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                isWifiConn |= networkInfo.isConnected();
                networkName = "Wifi";
            }
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                isMobileConn |= networkInfo.isConnected();
                networkName = "Internet";
            }
        }


        cl_jersey_preview.setDrawingCacheEnabled(true);
         bitmap = cl_jersey_preview.getDrawingCache();


        Spinner spin = (Spinner) findViewById(R.id.sp_choose_team);
        spin.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

        Glide.with(this)
                .asDrawable()
                .load(R.drawable.jersey_fill)
                .into(iv_jersey);

        btn_jersey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_jersey.setColorFilter(getApplicationContext().getResources().getColor(R.color.colorPrimary));
            }
        });


        btn_p_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //tv_p_name.addView(customView);
                tv_p_name.setText(ed_p_name.getText().toString());
                tv_p_name.setVisibility(View.VISIBLE);
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    File directory = new File(Environment.getExternalStorageDirectory() + File.separator + "MyApplication");
                    if (!directory.exists()) {
                        directory.mkdirs();
                    }
                    Random generator = new Random();
                    int n = 10000;
                    n = generator.nextInt(n);
                    String name = " "+n+".jpg";

                    File pictureFile = new File(directory, name);
                    pictureFile.createNewFile();
                    Log.i("@@picpath",pictureFile.toString());
                    if(checkPermission()){
                        Bitmap btm = viewToBitmap(cl_jersey_preview);
                        FileOutputStream output = new FileOutputStream(pictureFile);
                        btm.compress(Bitmap.CompressFormat.PNG, 100, output);
                        output.close();
                        Toast.makeText(getApplicationContext(),"successfully save", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            final Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                            final Uri contentUri = Uri.fromFile(pictureFile);
                            scanIntent.setData(contentUri);
                            sendBroadcast(scanIntent);
                        } else {
                            final Intent intent = new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory()));
                            sendBroadcast(intent);
                        }

                    }else {
                        requestPermission();
                    }
                                 } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),e.getMessage().toString(), Toast.LENGTH_LONG).show();

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),e.getMessage().toString(), Toast.LENGTH_LONG).show();

                }
            }
        });

        btn_intent_create_dream_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CreateDreamTeamActivity.class));
            }
        });

    }
    public Bitmap viewToBitmap(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        if (country[position] == "0"){
            tv_jersey_no.setVisibility(View.INVISIBLE);
        }else {
            tv_jersey_no.setText(country[position]);
            tv_jersey_no.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 200){
                if (grantResults.length != 0 && grantResults[0] == 0) {
                    Toast.makeText(this, "Permission is granted, please press download again.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission denied to write your External storage", Toast.LENGTH_SHORT).show();
                }

        }

    }

    Boolean checkPermission(){
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }
    public final void requestPermission() {
        ActivityCompat.requestPermissions((Activity)this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 200);
    }

}