package com.example.exercise;

import com.google.firebase.database.FirebaseDatabase;

public class firebasePersistencia extends android.app.Application{
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
