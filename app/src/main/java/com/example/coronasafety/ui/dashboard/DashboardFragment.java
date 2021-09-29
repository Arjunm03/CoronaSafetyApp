package com.example.coronasafety.ui.dashboard;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.coronasafety.MainActivity;
import com.example.coronasafety.R;

public class DashboardFragment extends Fragment implements View.OnClickListener {

    Button btnSubmit;
    Switch masks, gloves, indoors, outOfState, takeOut;
    EditText txtPeople, txtArea, txtState;
    TextView txtOutput;
    MainActivity main;
    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        btnSubmit = root.findViewById(R.id.buttonSubmit);
        masks = root.findViewById(R.id.switch1);
        gloves = root.findViewById(R.id.switch2);
        indoors = root.findViewById(R.id.switch3);
        outOfState = root.findViewById(R.id.switch4);
        takeOut = root.findViewById(R.id.switch5);
        txtPeople = root.findViewById(R.id.editTextNumber1);
        txtArea = root.findViewById(R.id.editTextNumber2);
        txtState = root.findViewById(R.id.editTextText3);
        txtOutput = root.findViewById(R.id.textViewOutput);
        main = (MainActivity) getActivity();

        main.getSupportActionBar().setTitle("Event");
        btnSubmit.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View view) {

        if (TextUtils.isEmpty(txtPeople.getText()) || TextUtils.isEmpty(txtArea.getText()) || TextUtils.isEmpty(txtState.getText())) {
            txtOutput.setText("Please fill in the input!");
            return;
        }

        double safety = 100;

        String state = txtState.getText().toString().replaceAll(" ", "").toLowerCase();
        int cases = 0, rate = 0;
        for (int i = 0; i < main.getLength()-1; i++) {
            if (state.equals(main.getAbbrev(i).toLowerCase()) || state.equals(main.getName(i).replaceAll(" ", "").toLowerCase())) {
                cases = main.getCases(i);
                rate = main.getRate(i);
                break;
            }
        }
        if (cases == 0) {
            txtOutput.setText("\"" + state + "\"" + " is spelled incorrectly!");
            return;
        }
        double x = 1 - ((Math.floor(rate/ 200)) * 0.01);
        safety  = safety * x;

        if (masks.isChecked()) {
            safety = safety * 1;
        } else {
            safety = safety * 0.92;
        }
        if (gloves.isChecked()) {
            safety = safety * 1;
        } else {
            safety = safety * 0.94;
        }
        if (indoors.isChecked()) {
            safety = safety * 0.98;
        } else {
            safety = safety * 0.96;
        }
        if (outOfState.isChecked()) {
            safety = safety * 0.94;
        } else {
            safety = safety * 0.98;
        }
        if (takeOut.isChecked()) {
            safety = safety * 0.92;
        } else {
            safety = safety * 1;
        }

        int people = Integer.parseInt(txtPeople.getText().toString());
        int area = Integer.parseInt(txtArea.getText().toString());
        double rating = area / people;
        if (rating >= 36) {
            safety = safety * 1;
        } else if (rating >= 32) {
            safety = safety * 0.98;
        } else if (rating >= 28) {
            safety = safety * 0.96;
        } else if (rating >= 24) {
            safety = safety * 0.94;
        } else if (rating >= 20) {
            safety = safety * 0.92;
        } else if (rating >= 16) {
            safety = safety * 0.90;
        } else if (rating >= 12) {
            safety = safety * 0.88;
        } else if (rating >= 8) {
            safety = safety * 0.86;
        } else if (rating >= 4) {
            safety = safety * 0.84;
        } else {
            safety = safety * 0.82;
        }

        txtOutput.setText("Safety Rating: " + Integer.toString((int) Math.round(safety)) + "/100");
    }
}