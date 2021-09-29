package com.example.coronasafety.ui.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.coronasafety.MainActivity;
import com.example.coronasafety.R;

public class HomeFragment extends Fragment implements View.OnClickListener {

    Button btnStart;
    EditText txtStart, txtEnd, txtMiddle;
    TextView txtOutput, txtOutput2;
    MainActivity main;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        btnStart = root.findViewById(R.id.buttonStart);
        txtStart = root.findViewById(R.id.editTextStart);
        txtEnd = root.findViewById(R.id.editTextEnd);
        txtMiddle = root.findViewById(R.id.editTextMiddle);
        txtOutput = root.findViewById(R.id.textViewOutput);
        txtOutput2 = root.findViewById(R.id.textViewOutput2);
        main = (MainActivity) getActivity();

        main.getSupportActionBar().setTitle("Roadtrip");
        btnStart.setOnClickListener(this);

        return root;

    }

    @Override
    public void onClick(View view) {
        if (TextUtils.isEmpty(txtStart.getText()) || TextUtils.isEmpty(txtEnd.getText())) {
            txtOutput.setText("Please fill in the input!");
            txtOutput2.setText("");
            return;
        }

        String start = txtStart.getText().toString().replaceAll(" ", "").toLowerCase();
        String end = txtEnd.getText().toString().replaceAll(" ", "").toLowerCase();
        String[] middle = txtMiddle.getText().toString().replaceAll(" ", "").toLowerCase().split(",");

        int[][] numberInfo = new int[middle.length + 2][2];
        String[] stateNames = new String[middle.length + 2];

        for (int i = 0; i < main.getLength()-1; i++) {
            if (start.equals(main.getAbbrev(i).toLowerCase()) || start.equals(main.getName(i).replaceAll(" ", "").toLowerCase())) {
                numberInfo[0][0] = main.getCases(i);
                numberInfo[0][1] = main.getRate(i);
                stateNames[0] = main.getName(i);
            }
            if (end.equals(main.getAbbrev(i).toLowerCase()) || end.equals(main.getName(i).replaceAll(" ", "").toLowerCase())) {
                numberInfo[1][0] = main.getCases(i);
                numberInfo[1][1] = main.getRate(i);
                stateNames[1] = main.getName(i);
            }
            for (int j = 0; j < middle.length; j++) {
                if (middle[j].equals(main.getAbbrev(i).toLowerCase()) || middle[j].equals(main.getName(i).replaceAll(" ", "").toLowerCase())) {
                    numberInfo[j + 2][0] = main.getCases(i);
                    numberInfo[j + 2][1] = main.getRate(i);
                    stateNames[j + 2] = main.getName(i);
                }
            }
        }

        String spelling = "";
        if (numberInfo[0][0] == 0) {
            spelling += start + ", ";
        }
        if (numberInfo[1][0] == 0) {
            spelling += end + ", ";
        }
        for (int i = 2; i < numberInfo.length; i++) {
            if (numberInfo[i][0] == 0 && !middle[i - 2].equals("")) {
                spelling += middle[i - 2] + ", ";
            }
        }
        if (!spelling.equals("") && spelling.length() > 2) {
            txtOutput.setText("The following are spelled incorrectly: " + spelling.substring(0, spelling.length() - 2) + "!");
            txtOutput2.setText("");
            return;
        }


        double safety = 100;
        String danger = "";

        for (int i = 0; i < numberInfo.length; i++) {

            double x = 1 - ((Math.floor(numberInfo[i][1]/ 200)) * 0.01);
            if (x <= 0.93) danger += stateNames[i] + ", ";
            safety  = safety * x;
            if(i == numberInfo.length-1) safety = safety * (1 + (0.01 * (i-1)));

        }

        txtOutput.setText("Safety Rating: " + (int) Math.round(safety) + "/100");

        if (safety > 90) {
            txtOutput2.setText("Your journey is safe!");
        } else if (safety > 80) {
            if (!danger.equals(""))
                txtOutput2.setText("You're journey is safe for the most part but be a little careful in these states: " + danger.substring(0, danger.length() - 2) + "!");
            else
                txtOutput2.setText("You're journey is for the most part safe but still be a little careful!");
        } else if (safety > 70) {
            if (!danger.equals(""))
                txtOutput2.setText("You should be careful and be very careful while in these states: " + danger.substring(0, danger.length() - 2) + "!");
            else
                txtOutput2.setText("You should be careful while following this route!");
        } else if (safety > 60) {
            if (!danger.equals(""))
                txtOutput2.setText("This is a risky route and the following states are pretty bad: " + danger.substring(0, danger.length() - 2) + "!");
            else
                txtOutput2.setText("This is a risky route and extreme caution should be taken!");
        } else if (safety > 50) {
            if (!danger.equals(""))
                txtOutput2.setText("If possible try to take another route. If taking this route be very careful in these states: " + danger.substring(0, danger.length() - 2) + "!");
            else
                txtOutput2.setText("Be very careful or take another route if possible!");
        } else {
            if (!danger.equals(""))
                txtOutput2.setText("Don't take this route and try to find another one. These states are really bad: " + danger.substring(0, danger.length() - 2) + "!");
            else txtOutput2.setText("YDon't take this route and try to find another one!");
        }
    }
}


/*

Old Way

if (numberInfo[i][0] > 400000) {
                safety = safety * 0.93;
            } else if (numberInfo[i][0] > 350000) {
                safety = safety * 0.935;
            } else if (numberInfo[i][0] > 300000) {
                safety = safety * 0.94;
            } else if (numberInfo[i][0] > 250000) {
                safety = safety * 0.945;
            } else if (numberInfo[i][0] > 200000) {
                safety = safety * 0.95;
            } else if (numberInfo[i][0] > 150000) {
                safety = safety * 0.955;
            } else if (numberInfo[i][0] > 100000) {
                safety = safety * 0.96;
            } else if (numberInfo[i][0] > 50000) {
                safety = safety * 0.965;
            } else if (numberInfo[i][0] > 40000) {
                safety = safety * 0.97;
            } else if (numberInfo[i][0] > 30000) {
                safety = safety * 0.975;
            } else if (numberInfo[i][0] > 20000) {
                safety = safety * 0.98;
            } else if (numberInfo[i][0] > 10000) {
                safety = safety * 0.985;
            } else if (numberInfo[i][0] > 5000) {
                safety = safety * 0.99;
            } else if (numberInfo[i][0] > 0) {
                safety = safety * 0.995;
            }


            if (numberInfo[i][1] > 2600) {
                safety = safety * 0.93;
                danger += stateNames[i] + ", ";
            } else if (numberInfo[i][1] > 2400) {
                safety = safety * 0.935;
                danger += stateNames[i] + ", ";
            } else if (numberInfo[i][1] > 2200) {
                safety = safety * 0.94;
                danger += stateNames[i] + ", ";
            } else if (numberInfo[i][1] > 2000) {
                safety = safety * 0.945;
                danger += stateNames[i] + ", ";
            } else if (numberInfo[i][1] > 1800) {
                safety = safety * 0.95;
                danger += stateNames[i] + ", ";
            } else if (numberInfo[i][1] > 1600) {
                safety = safety * 0.955;
                danger += stateNames[i] + ", ";
            } else if (numberInfo[i][1] > 1400) {
                safety = safety * 0.96;
                danger += stateNames[i] + ", ";
            } else if (numberInfo[i][1] > 1200) {
                safety = safety * 0.965;
                danger += stateNames[i] + ", ";
            } else if (numberInfo[i][1] > 1000) {
                safety = safety * 0.97;
                danger += stateNames[i] + ", ";
            } else if (numberInfo[i][1] > 800) {
                safety = safety * 0.975;
            } else if (numberInfo[i][1] > 600) {
                safety = safety * 0.98;
            } else if (numberInfo[i][1] > 400) {
                safety = safety * 0.985;
            } else if (numberInfo[i][1] > 200) {
                safety = safety * 0.99;
            } else if (numberInfo[i][1] > 0) {
                safety = safety * 0.995;
            }*/