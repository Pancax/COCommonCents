package com.pancaxrzcosola.cocommoncents;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.reimaginebanking.api.nessieandroidsdk.requestclients.NessieClient;

public class MainActivity extends AppCompatActivity {

    static NessieClient nessieAPI = NessieClient.getInstance("37e93a473684febe1afbe773dbe31637");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}