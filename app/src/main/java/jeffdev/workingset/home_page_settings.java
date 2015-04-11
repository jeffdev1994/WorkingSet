package jeffdev.workingset;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;


public class home_page_settings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_settings);

        //TODO:  this doesnt work, need to use sharedpreferences instead of getprefences

        //set the home screen settings
        SharedPreferences pref1 = getPreferences(0);
        pref1.contains("screentypeblocks");
        int homepref = pref1.getInt("screentypeblocks",0);
        boolean homeprefboo;
        if(homepref == 0){
            homeprefboo = false;
        }
        else{
            homeprefboo = true;
        }

        Switch homescreen = (Switch) findViewById(R.id.log_setting);
        homescreen.setChecked(homeprefboo);

        //set the sort settings
        SharedPreferences pref2 = getPreferences(0);
        pref2.contains("logstype");
        pref2.getAll();
        boolean sortpref = pref2.getBoolean("logstype",false);

        Switch sorttype = (Switch) findViewById(R.id.log_setting);
        sorttype.setChecked(sortpref);
    }

}
