package com.example.coronasafety;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    public static String[][] data = {
            {"AK", "Alaska", "0", "731,545"},
            {"AL", "Alabama", "0", "4,903,185"},
            {"AR", "Arkansas", "0", "3,017,825"},
            {"AZ", "Arizona", "0", "7,278,717"},
            {"CA", "California", "0", "39,512,223"},
            {"CO", "Colorado", "0", "5,758,736"},
            {"CT", "Connecticut", "0", "3,565,287"},
            {"DC", "Washington DC", "0", "705,749"},
            {"DE", "Delaware", "0", "973,764"},
            {"FL", "Florida", "0", "21,477,737"},
            {"GA", "Georgia", "0", "10,617,423"},
            {"HI", "Hawaii", "0", "1,415,872"},
            {"IA", "Iowa", "0", "3,155,070"},
            {"ID", "Idaho", "0", "1,787,065"},
            {"IL", "Illinois", "0", "12,671,821"},
            {"IN", "Indiana", "0", "6,732,219"},
            {"KS", "Kansas", "0", "2,913,314"},
            {"KY", "Kentucky", "0", "4,467,673"},
            {"LA", "Louisiana", "0", "4,648,794"},
            {"MA", "Massachusetts", "0", "6,949,503"},
            {"MD", "Maryland", "0", "6,045,680"},
            {"ME", "Maine", "0", "1,344,212"},
            {"MI", "Michigan", "0", "9,986,857"},
            {"MN", "Minnesota", "0", "5,639,632"},
            {"MO", "Missouri", "0", "6,137,428"},
            {"MS", "Mississippi", "0", "2,976,149"},
            {"MT", "Montana", "0", "1,068,778"},
            {"NC", "North Carolina", "0", "10,488,084"},
            {"ND", "North Dakota", "0", "762,062"},
            {"NE", "Nebraska", "0", "1,934,408"},
            {"NH", "New Hampshire", "0", "1,359,711"},
            {"NJ", "New Jersey", "0", "8,882,190"},
            {"NM", "New Mexico", "0", "2,096,829"},
            {"NV", "Nevada", "0", "3,080,156"},
            {"NY", "New York", "0", "19,453,561"},
            {"OH", "Ohio", "0", "11,689,100"},
            {"OK", "Oklahoma", "0", "3,956,971"},
            {"OR", "Oregon", "0", "4,217,737"},
            {"PA", "Pennsylvania", "0", "12,801,989"},
            {"RI", "Rhode Island", "0", "1,059,361"},
            {"SC", "South Carolina", "0", "5,148,714"},
            {"SD", "South Dakota", "0", "884,659"},
            {"TN", "Tennessee", "0", "6,833,174"},
            {"TX", "Texas", "0", "28,995,881"},
            {"UT", "Utah", "0", "3,205,958"},
            {"VA", "Virginia", "0", "8,535,519"},
            {"VI", "Virgin Islands", "0", "106,977"},
            {"VT", "Vermont", "0", "623,989"},
            {"WA", "Washington", "0", "7,614,893"},
            {"WI", "Wisconsin", "0", "5,822,434"},
            {"WV", "West Virginia", "0", "1,792,147"},
            {"WY", "Wyoming", "0", "578,759"},
            {"US", "America", "0", "321,418,820"}
    };

    public MainActivity() throws IOException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_roadtrip, R.id.navigation_event, R.id.navigation_data).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        GetData process = new GetData();
        process.execute();
    }


    public String getAbbrev(int i) {
        return data[i][0];
    }

    public String getName(int i) {
        return data[i][1];
    }

    public int getCases(int i) {
        return Integer.parseInt(data[i][2]);
    }

    public int getRate(int i) {
        return (int) Math.round(((Integer.parseInt(data[i][2])) / ((double) Integer.parseInt(data[i][3].replaceAll(",", "")))) * 100000);
    }

    public int getLength() {
        return data.length;
    }

}