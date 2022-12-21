package com.example.exercise;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exercise.models.Encuesta;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class EncuestaActivity extends AppCompatActivity implements View.OnClickListener {

    private CheckBox ckbox1;
    private CheckBox ckbox2;
    private CheckBox ckbox3;
    private CheckBox ckbox4;
    private CheckBox ckbox5;
    private CheckBox ckbox6;
    private CheckBox ckbox7;
    private CheckBox ckbox8;
    private Button btnsub;
    private TextView btnback, txtMail;
    private String timeNow;
    private String mail;
    private String cedula;


    //firebase
    FirebaseDatabase fb_db;
    DatabaseReference db_ref;
    FirebaseAuth firebaseAuth;
    //datos de register
//    Bundle datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta);
        //conexion de elementos del view
        ckbox1 = (CheckBox) findViewById(R.id.ckbox1);
        ckbox2 = (CheckBox) findViewById(R.id.ckBox2);
        ckbox3 = (CheckBox) findViewById(R.id.ckBox3);
        ckbox4 = (CheckBox) findViewById(R.id.ckBox4);
        ckbox5 = (CheckBox) findViewById(R.id.ckBox5);
        ckbox6 = (CheckBox) findViewById(R.id.ckBox6);
        ckbox7 = (CheckBox) findViewById(R.id.ckBox7);
        ckbox8 = (CheckBox) findViewById(R.id.ckBox8);
        btnsub = (Button) findViewById(R.id.subEncuesta);
        btnback = (TextView) findViewById(R.id.id_txtback_encuesta);
        txtMail=findViewById(R.id.id_mailEncuesta);
        btnsub.setOnClickListener(this);
        btnback.setOnClickListener(this);
        //metodo personal de inicializar firebase
        inicilializarFirebase();
        //autenticacion
        firebaseAuth = FirebaseAuth.getInstance();

    }

    //metodo inicilizar firebase
    private void inicilializarFirebase() {
        FirebaseApp.initializeApp(this);
        fb_db = FirebaseDatabase.getInstance();
        db_ref = fb_db.getReference();
    }

    @Override
    public void onClick(View view) {
        // Toast.makeText(this, "boton oprimido", Toast.LENGTH_SHORT).show();
        switch (view.getId()) {
            case R.id.subEncuesta:
                int ck1, ck2, ck3, ck4, ck5, ck6, ck7, ck8;
                if (ckbox1.isChecked()) {
                    ck1 = 1;
                } else {
                    ck1 = 0;
                }
                if (ckbox2.isChecked()) {
                    ck2 = 1;
                } else {
                    ck2 = 0;
                }
                if (ckbox3.isChecked()) {
                    ck3 = 1;
                } else {
                    ck3 = 0;
                }
                if (ckbox4.isChecked()) {
                    ck4 = 1;
                } else {
                    ck4 = 0;
                }
                if (ckbox5.isChecked()) {
                    ck5 = 1;
                } else {
                    ck5 = 0;
                }
                if (ckbox6.isChecked()) {
                    ck6 = 1;
                } else {
                    ck6 = 0;
                }
                if (ckbox7.isChecked()) {
                    ck7 = 1;
                } else {
                    ck7 = 0;
                }
                if (ckbox8.isChecked()) {
                    ck8 = 1;
                } else {
                    ck8 = 0;
                }
                int Eval = ck1 + ck2 + ck3 + ck4 + ck5 + ck6 + ck7 + ck8;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                timeNow = simpleDateFormat.format(new Date());
                String userID= firebaseAuth.getCurrentUser().getUid();
                Encuesta encuesta = new Encuesta();
                encuesta.setUserID(userID);
                encuesta.setCedula(cedula);
                encuesta.setMail(mail);
                encuesta.setEval(Eval);
                encuesta.setTimeNow(timeNow);
                db_ref.child("Encuesta").child(encuesta.getUserID()).setValue(encuesta);
                Intent intentEstudiante = new Intent(EncuestaActivity.this, EstudianteActivity.class);
                startActivity(intentEstudiante);
                break;
            case R.id.id_txtback_encuesta:
                Intent intentMain = new Intent(EncuestaActivity.this, MainActivity.class);
                startActivity(intentMain);
                break;
            default:
        }
    }

}