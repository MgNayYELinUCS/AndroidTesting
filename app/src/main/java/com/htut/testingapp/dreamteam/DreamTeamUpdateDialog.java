package com.htut.testingapp.dreamteam;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.htut.testingapp.OnSaveButtonClick;
import com.htut.testingapp.R;

public class DreamTeamUpdateDialog extends Dialog implements AdapterView.OnItemSelectedListener {

    String[] country = {"4-4-2", "4-3-3", "4-3-2", "3-4-3", "3-5-2", "3-6-1","4-5-1","5-4-1"};
    Context context;
    String formation = "";
    OnSaveButtonClick onSaveButtonClick;
    Button btnSave;

    public DreamTeamUpdateDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dreamm_team_update_dialog);
        btnSave = findViewById(R.id.btn_save);
        Spinner spin = (Spinner) findViewById(R.id.sp_formation);
        spin.setOnItemSelectedListener(this);



        ArrayAdapter aa = new ArrayAdapter(context,android.R.layout.simple_spinner_item,country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // onSaveButtonClick.onSaveClick("formation");
                dismiss();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(context, country[position].toString(), Toast.LENGTH_SHORT).show();
        formation = country[position];

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public OnSaveButtonClick getOnSaveButtonClick() {
        return onSaveButtonClick;
    }

    public void setOnSaveButtonClick(OnSaveButtonClick onSaveButtonClick) {
        this.onSaveButtonClick = onSaveButtonClick;
    }

}
