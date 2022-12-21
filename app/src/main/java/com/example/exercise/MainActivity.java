package com.example.exercise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnLogin, btnRegister;
    private EditText mail, pass;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mail = findViewById(R.id.id_inputEmail);
        pass = findViewById(R.id.id_inputPass);
        btnLogin = (Button) findViewById(R.id.id_btnLogin);
        btnLogin.setOnClickListener(this);
        btnRegister = (Button) findViewById(R.id.id_btnRegister);
        btnRegister.setOnClickListener(this);
        //autenticacion
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        goHome(user);
    }

    private void goHome(FirebaseUser user) {
        if (user != null) {
            Intent intentEstudent = new Intent(MainActivity.this, EstudianteActivity.class);
            intentEstudent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentEstudent);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_btnLogin:
                String mailu = mail.getText().toString();
                String passu = pass.getText().toString();
                if (mailu.equals("") || passu.equals("")) {
                    validacion();
                } else {
                    firebaseAuth.signInWithEmailAndPassword(mailu, passu).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                goStudent();

                            } else {
                                Toast.makeText(MainActivity.this, "Se ha presentado un error al loguearse, o la cuenta no existe", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                break;
            case R.id.id_btnRegister:
                Intent intentRegister = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intentRegister);
                break;

        }

    }

    private void goStudent() {
        Intent i = new Intent(MainActivity.this, EstudianteActivity.class);
//        i.putExtra("mail", mail.getText().toString());
//        i.putExtra("cedula", cedula.getText().toString());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    public void validacion() {
        String mailU = mail.getText().toString();
        String passU = pass.getText().toString();
        if (mailU.equals("")) {
            mail.setError("Requerido");

        } else if (passU.equals("")) {
            pass.setError("Requerido");
        }

    }
}