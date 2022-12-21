package com.example.exercise;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.exercise.models.Materia;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MateriasActivity extends AppCompatActivity {
    String cedula;
    EditText materia, profesor, salon;
    ListView listViewMaterias;
    //
    FirebaseDatabase fb_db;
    DatabaseReference db_ref;
    //firebaseAuth
    FirebaseAuth firebaseAuth;
    //
    private List<Materia> listMateria = new ArrayList<Materia>();
    ArrayAdapter<Materia> arrayAdapterMateria;
    //77-llevaar los elementos del arrayList a los input al hacer clik
    Materia materiaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materias);
        materia = findViewById(R.id.id_mate);
        profesor = findViewById(R.id.id_profe);
        salon = findViewById(R.id.id_horario);
        listViewMaterias = findViewById(R.id.id_listaMaterias);
        //autenticacion
        firebaseAuth = FirebaseAuth.getInstance();
        //inicializar firebase
        inicializarfirebase();
        listarDatos();
        //77-al dar click a un item q se muestre en los inputs
        listViewMaterias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                materiaSeleccionada=(Materia) adapterView.getItemAtPosition(i);
                materia.setText(materiaSeleccionada.getMateria());
                profesor.setText(materiaSeleccionada.getProfesor());
                salon.setText(materiaSeleccionada.getSalon());
            }
        });


    }

    private void listarDatos() {
        //traer datos del database
        String userID= firebaseAuth.getCurrentUser().getUid();
        db_ref.child("Materia").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listMateria.clear();
                for (DataSnapshot objeto : snapshot.getChildren()) {
                    Materia m = objeto.getValue(Materia.class);
                    listMateria.add(m);
                    arrayAdapterMateria = new ArrayAdapter<Materia>(MateriasActivity.this, android.R.layout.simple_list_item_1, listMateria);
                    listViewMaterias.setAdapter(arrayAdapterMateria);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void inicializarfirebase() {
        FirebaseApp.initializeApp(this);
        fb_db = FirebaseDatabase.getInstance();
        db_ref = fb_db.getReference();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_head, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //traer datos del database
        String userID= firebaseAuth.getCurrentUser().getUid();
        String mateU = materia.getText().toString();
        String profeU = profesor.getText().toString();
        String salonU = salon.getText().toString();

        switch (item.getItemId()) {
            case R.id.icon_add:
                if (mateU.equals("") || profeU.equals("")|| salonU.equals("")) {
                    validacion();
                } else {
                    Materia m = new Materia();
                    m.setMateriaID(UUID.randomUUID().toString());
                    m.setMateria(materia.getText().toString().trim());
                    m.setProfesor(profesor.getText().toString().trim());
                    m.setSalon(salon.getText().toString().trim());
                    db_ref.child("Materia").child(userID).child(m.getMateriaID()).setValue(m);
                    limpiarcajas();
                    Toast.makeText(this, "Se agrego la materia", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.icon_save:
                if (mateU.equals("") || profeU.equals("")|| salonU.equals("")) {
                    validacion();
                } else {
                    Toast.makeText(this, "Se actualizo la materia", Toast.LENGTH_SHORT).show();
                    Materia mates = new Materia();
                    mates.setMateriaID(materiaSeleccionada.getMateriaID());
                    mates.setMateria(mateU.trim());
                    mates.setProfesor(profeU.trim());
                    mates.setSalon(salonU.trim());
                    db_ref.child("Materia").child(userID).child(mates.getMateriaID()).setValue(mates);
                    limpiarcajas();
                }
                break;
            case R.id.icon_delete:
                if (mateU.equals("") || profeU.equals("")|| salonU.equals("")) {
                    validacion();
                } else {
                    Materia m1 = new Materia();
                    m1.setMateriaID(materiaSeleccionada.getMateriaID());
                    db_ref.child("Materia").child(userID).child(m1.getMateriaID()).removeValue();
                    Toast.makeText(this, "borrar", Toast.LENGTH_SHORT).show();
                    limpiarcajas();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void limpiarcajas() {
        materia.setText("");
        profesor.setText("");
        salon.setText("");
    }
    public void validacion() {
        String mateU = materia.getText().toString();
        String profeU = profesor.getText().toString();
        String salonU = salon.getText().toString();
        if (mateU.equals("")) {
            materia.setError("Requerido");

        } else if (profeU.equals("")) {
            profesor.setError("Requerido");
        }else if (salonU.equals("")) {
            salon.setError("Requerido");
        }

    }
}