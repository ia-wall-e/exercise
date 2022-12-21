package com.example.exercise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exercise.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private List<User> listUser = new ArrayList<User>();
    ArrayAdapter<User> arrayAdapterUser;
    EditText mailU, nameU, carreraU, cedulaU, passU;
    Button btnSubReg;
    TextView btnBack;
    //firebase
    FirebaseDatabase fb_db;
    DatabaseReference db_ref;
    //firebaseAuth
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mailU = findViewById(R.id.id_mail);
        nameU = findViewById(R.id.id_name);
        carreraU = findViewById(R.id.id_carrera);
        cedulaU = findViewById(R.id.id_cc);
        passU = findViewById(R.id.id_pass);
        btnSubReg = (Button) findViewById(R.id.id_submit_register);
        btnSubReg.setOnClickListener(this);
        btnBack = (TextView) findViewById(R.id.id_txtback_register);
        btnBack.setOnClickListener(this);
        //metodo personal de inicializar firebase
        inicilializarFirebase();
        //autenticacion
        firebaseAuth = FirebaseAuth.getInstance();
        //validacion de usuario
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        goHome(user);

    }

    private void goHome(FirebaseUser user) {
        if (user != null) {
            Intent intentEstudent = new Intent(RegisterActivity.this, EstudianteActivity.class);
            intentEstudent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentEstudent);
        }
    }


    //metodo inicilizar firebase
    private void inicilializarFirebase() {
        FirebaseApp.initializeApp(this);
        fb_db = FirebaseDatabase.getInstance();
        db_ref = fb_db.getReference();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_submit_register:
                String mail = mailU.getText().toString();
                String name = nameU.getText().toString();
                String carrera = carreraU.getText().toString();
                String cedula = cedulaU.getText().toString();
                String pass = passU.getText().toString();
                if (mail.equals("") || name.equals("") || carrera.equals("") || cedula.equals("")|| pass.equals("")) {
                    validacion();
                } else {
                    //Autenticacion
                    firebaseAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                               String userID= firebaseAuth.getCurrentUser().getUid();
                                //Guardar en firebase
                                User alumno = new User();
                                alumno.setUserID(userID);
                                alumno.setMail(mail);
                                alumno.setName(name);
                                alumno.setCarrera(carrera);
                                alumno.setCedula(cedula);
                                db_ref.child("User").child(alumno.getUserID()).setValue(alumno);
                                Intent intentEncuesta = new Intent(RegisterActivity.this, EncuestaActivity.class);
//                                intentEncuesta.putExtra("mail", mail);
//                                intentEncuesta.putExtra("cedula", cedula);
//                                intentEncuesta.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intentEncuesta);
                            } else {
                                Toast.makeText(RegisterActivity.this, "No se pudo crear el usuario", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
                }

                break;
            case R.id.id_txtback_register:
                Intent intentMain = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intentMain);
                break;
        }

    }



    public void validacion() {
        String mail = mailU.getText().toString();
        String name = nameU.getText().toString();
        String carrera = carreraU.getText().toString();
        String cedula = cedulaU.getText().toString();
        String pass = passU.getText().toString();
        if (mail.equals("")) {
            mailU.setError("Requerido");
        } else if (name.equals("")) {
            nameU.setError("Requerido");
        } else if (carrera.equals("")) {
            carreraU.setError("Requerido");
        } else if (cedula.equals("")) {
            cedulaU.setError("Requerido");
        }else if (pass.equals("")) {
            passU.setError("Requerido");
        }

    }
}