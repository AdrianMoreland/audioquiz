package com.audioquiz.admin;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.audioquiz.admin.domain.IMockData;

import javax.inject.Inject;

public class AdminActivity extends AppCompatActivity {

    @Inject
    IMockData mockData;

    private Button addMockDataButton;
    private Button manageUsersButton;
    private Button viewMetricsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        addMockDataButton = findViewById(R.id.addMockDataButton);
        manageUsersButton = findViewById(R.id.manageUsersButton);
        viewMetricsButton = findViewById(R.id.viewMetricsButton);

        addMockDataButton.setOnClickListener(v -> addMockData());
        manageUsersButton.setOnClickListener(v -> manageUsers());
        viewMetricsButton.setOnClickListener(v -> viewMetrics());
    }

    private void addMockData() {
        mockData.addMockData("mockUserId", "mockData");
    }

    private void manageUsers() {
        // Implement logic to manage users
    }

    private void viewMetrics() {
        // Implement logic to view metrics
    }
}
