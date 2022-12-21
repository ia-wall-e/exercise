package com.example.exercise;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EstudianteActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnclose, btnMater, btnEncuesta;
    TextView txtName, txtCarrera, txtTime, txtRes, txtPor;
    String cedula;
    ImageView imgUser;
    FloatingActionButton btnImg;
    //firebase
    FirebaseDatabase fb_db;
    DatabaseReference db_ref, db_ref2;
    FirebaseAuth firebaseAuth;
    //instancias de imagen
    ActivityResultLauncher<String> content;

    //datos de entrada
//    Bundle datos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudiante);
        //relacion parte grafica parte logica
        btnMater = findViewById(R.id.id_materias);
        btnEncuesta = findViewById(R.id.id_encuesta);
        btnclose = findViewById(R.id.id_btnclose);
        txtName = findViewById(R.id.id_txtname);
        txtCarrera = findViewById(R.id.id_txtcarrera);
        txtTime = findViewById(R.id.id_txttime);
        txtRes = findViewById(R.id.id_txtRes);
        txtPor = findViewById(R.id.id_txtpor2);
        imgUser=findViewById(R.id.id_img);
        btnImg=findViewById(R.id.id_btnImg);
        //eventos botones
        btnMater.setOnClickListener(this);
        btnEncuesta.setOnClickListener(this);
        btnclose.setOnClickListener(this);
        btnImg.setOnClickListener(this);
       //subida de imagen
        content=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                imgUser.setImageURI(result);
            }
        });
        //inicialize firebae databse
        inicializarFirebase();
        //autenticacion
        firebaseAuth = FirebaseAuth.getInstance();
        //traer datos del database
        String userID = firebaseAuth.getCurrentUser().getUid();
        db_ref.child("User").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String nombre = snapshot.child("name").getValue().toString();
                    String carrera = snapshot.child("carrera").getValue().toString();
                    txtName.setText(nombre);
                    txtCarrera.setText(carrera);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        db_ref2.child("Encuesta").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String eval = snapshot.child("eval").getValue().toString();
                    String time = snapshot.child("timeNow").getValue().toString();
                    txtTime.setText(time);
                    int eval2 = Integer.parseInt(eval);
                    int por = (eval2 * 100 / 8);
                    String por2 = String.valueOf(por);
                    txtPor.setText(por2 + "%");
                    if (eval2 < 4) {
                        String res = "HABILITADO";
                        txtRes.setText(res);
                    } else {
                        String res = "INHABILITADO";
                        txtRes.setText(res);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void goLogin() {
        Intent i = new Intent(this, MainActivity.class);

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    private void listarUser() {
        // databaseReference.child("user")
    }

    //metodo inicilizar firebase
    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        fb_db = FirebaseDatabase.getInstance();
        db_ref = fb_db.getReference();
        db_ref2 = fb_db.getReference();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_materias:
                Intent i_materias = new Intent(this, MateriasActivity.class);
                i_materias.putExtra("cedula", cedula);
                startActivity(i_materias);
                break;
            case R.id.id_encuesta:
                Intent i_encuesta = new Intent(this, EncuestaActivity.class);
                i_encuesta.putExtra("cedula", cedula);
                startActivity(i_encuesta);
                break;
            case R.id.id_btnclose:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(EstudianteActivity.this, "Sesion cerrada", Toast.LENGTH_SHORT).show();
                goLogin();
                break;
            case R.id.id_btnImg:
                content.launch("imgUser/*");
                Toast.makeText(EstudianteActivity.this, "Imagen", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}