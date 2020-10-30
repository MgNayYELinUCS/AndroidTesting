package com.htut.testingapp.dreamteam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.htut.testingapp.OnSaveButtonClick;
import com.htut.testingapp.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CreateDreamTeamActivity extends AppCompatActivity  {

    int SELECT_IMAGE = 100;
    Button btn_choose_image,btn_dream_team_update,btn_dream_team_save;
    RecyclerView rv;
    DreamTeamAdapter adapter;
    List<Player> players = new ArrayList<>();
    DreamTeamUpdateDialog teamUpdateDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_dream_team);
        btn_choose_image = findViewById(R.id.btn_choose_jersey);
        btn_dream_team_update = findViewById(R.id.btn_dream_team_update);
        btn_dream_team_save = findViewById(R.id.btn_dream_team_save);
        rv = findViewById(R.id.rv_dream_team);
        final File directory = new File(Environment.getExternalStorageDirectory() + File.separator + "MyApplication");
         teamUpdateDialog = new DreamTeamUpdateDialog(CreateDreamTeamActivity.this);

         teamUpdateDialog.setOnSaveButtonClick(new OnSaveButtonClick(){
             @Override
             public void onSaveClick(String formation) {

             }
         });
        players.add(new Player("player1"));
        players.add(new Player("player1"));
        players.add(new Player("player1"));
        players.add(new Player("player1"));
        players.add(new Player("player1"));
        players.add(new Player("player1"));
        players.add(new Player("player1"));
        players.add(new Player("player1"));
        players.add(new Player("player1"));
        players.add(new Player("player1"));
        players.add(new Player("player1"));
        settingAdapter();
        settingData(players);

        btn_choose_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setDataAndType(Uri.parse(directory.getAbsolutePath()), "image/*");
                startActivityForResult(i, SELECT_IMAGE);
            }
        });

        btn_dream_team_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teamUpdateDialog = new DreamTeamUpdateDialog(CreateDreamTeamActivity.this);
                teamUpdateDialog.show();
            }
        });
        btn_dream_team_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        Bitmap btm = viewToBitmap(rv);
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

    }
    public Bitmap viewToBitmap(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
//            ImageView imageView = (ImageView) findViewById(R.id.rv_dream_team);
//            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
           // adapter.setImageBitmap(picturePath);
        }
    }

    private void settingAdapter() {
        rv.setHasFixedSize(true);
        rv.setNestedScrollingEnabled(false);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new DreamTeamAdapter();
        rv.setAdapter(adapter);

    }
    private void settingData(List<Player> lineUp) {
        if (lineUp.size() > 0) {
            List<LineUpRow> lineUpRowList = new ArrayList<>();
            List<Player> allPlayer = lineUp;
            String formation = "5-4-1";
            String[] formationArr = formation.split("-");
            Integer[] formationIntArr = new Integer[formationArr.length];
            for (int i = 0; i < formationArr.length; i++) {
                formationIntArr[i] = Integer.parseInt(formationArr[i]);
            }
            List<Integer> formationList = new ArrayList<>();
            formationList.add(1);
            formationList.addAll(Arrays.asList(formationIntArr));
            Log.i("@@formationList",formationList.toString());
            for (int i = 0; i < formationList.size(); i++) {
                int size = 0;
                for (int k = 0; k <= i; k++) {
                    size += formationList.get(k);
                    Log.i("@@size",size+"");
                }
                int current = 0;
                for (int l = 0; l < i; l++) {
                    current += formationList.get(l);
                    Log.i("@@current",current+"");
                }
                List<Player> playerList = new ArrayList<>();
                if (i == 0) {
                    playerList.add(allPlayer.get(i));
                } else {
                    for (int j = current; j < size; j++) {
                        playerList.add(allPlayer.get(j));
                       // Log.i("@@playerList",allPlayer.get(j).getName().toString()+"");

                    }
                }
                LineUpRow lineUpRow = new LineUpRow(playerList);
                lineUpRowList.add(lineUpRow);
                // Log.i("@@lineUpRowList",lineUpRowList.toString()+"");
            }
            adapter.setDataset(lineUpRowList);
        }
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