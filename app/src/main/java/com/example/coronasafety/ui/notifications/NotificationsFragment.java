package com.example.coronasafety.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.coronasafety.MainActivity;
import com.example.coronasafety.R;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment implements View.OnClickListener {

    Button btnSwitch;
    TextView txt1, txt2;
    MainActivity main;
    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        btnSwitch = root.findViewById(R.id.button3);
        txt1 = root.findViewById(R.id.textView1);
        txt2 = root.findViewById(R.id.textView2);
        TextView txt3 = root.findViewById(R.id.textView3);
        main = (MainActivity) getActivity();

        main.getSupportActionBar().setTitle("Data");

        txt3.setText("US Total: " + main.getCases(52) + ", " + main.getRate(52));


        for (int i = 0; i < main.getLength() - 1; i++) {
            if (i < main.getLength() / 2) {
                txt1.append(main.getAbbrev(i) + ": " + main.getCases(i) + ", " + main.getRate(i));
                txt1.append("\n");
            } else {
                txt2.append(main.getAbbrev(i) + ": " + main.getCases(i) + ", " + main.getRate(i));
                txt2.append("\n");
            }
        }
        btnSwitch.setOnClickListener(this);

        return root;
    }

    @Override

    public void onClick(View view) {
        if (btnSwitch.getText().toString().equals("Alphabetical")) {

            btnSwitch.setText("Cases");
            txt1.setText("");
            txt2.setText("");

            ArrayList<Integer> list = new ArrayList<>();
            for (int i = 0; i < main.getLength() - 1; i++) {
                int max = 0;
                int maxNum = 0;
                for (int j = 0; j < main.getLength() - 1; j++) {
                    if (!list.contains(j) && main.getCases(j) > maxNum) {
                        max = j;
                        maxNum = main.getCases(j);
                    }
                }
                list.add(max);
                if (i < main.getLength() / 2) {
                    txt1.append(main.getAbbrev(max) + ": " + main.getCases(max) + ", " + main.getRate(max));
                    txt1.append("\n");
                } else {
                    txt2.append(main.getAbbrev(max) + ": " + main.getCases(max) + ", " + main.getRate(max));
                    txt2.append("\n");
                }
            }
        } else if (btnSwitch.getText().toString().equals("Cases")) {

            btnSwitch.setText("Rates");
            txt1.setText("");
            txt2.setText("");

            ArrayList<Integer> list = new ArrayList<Integer>();
            for (int i = 0; i < main.getLength() - 1; i++) {
                int max = 0;
                int maxNum = 0;
                for (int j = 0; j < main.getLength() - 1; j++) {
                    if (!list.contains(j) && main.getRate(j) > maxNum) {
                        max = j;
                        maxNum = main.getRate(j);
                    }
                }
                list.add(max);
                if (i < main.getLength() / 2) {
                    txt1.append(main.getAbbrev(max) + ": " + main.getCases(max) + ", " + main.getRate(max));
                    txt1.append("\n");
                } else {
                    txt2.append(main.getAbbrev(max) + ": " + main.getCases(max) + ", " + main.getRate(max));
                    txt2.append("\n");
                }
            }
        } else if (btnSwitch.getText().toString().equals("Rates")) {

            btnSwitch.setText("Alphabetical");
            txt1.setText("");
            txt2.setText("");

            for (int i = 0; i < main.getLength() - 1; i++) {
                if (i < main.getLength() / 2) {
                    txt1.append(main.getAbbrev(i) + ": " + main.getCases(i) + ", " + main.getRate(i));
                    txt1.append("\n");
                } else {
                    txt2.append(main.getAbbrev(i) + ": " + main.getCases(i) + ", " + main.getRate(i));
                    txt2.append("\n");
                }
            }
        }
    }
}
