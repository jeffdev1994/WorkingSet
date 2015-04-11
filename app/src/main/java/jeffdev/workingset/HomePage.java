package jeffdev.workingset;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class HomePage extends ActionBarActivity {

    static int screentype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //will find the value, and if there isnt one, it will return 0.
        //so default is the buttons, if changed, it should stay as blocks
        SharedPreferences settings = getPreferences(0);
        screentype = settings.getInt("screentypeblocks",0);

        if(screentype == 0) {
            setContentView(R.layout.activity_home_page);
        }
        else if(screentype == 1){
            setContentView(R.layout.activity_home_page_blocks);
        }
    }

    public void gotoexercises(View view){
        Intent intent = new Intent(this, ExercisePage.class);
        startActivity(intent);
    }

    public void gotocreateworkout(View view){
        Intent intent = new Intent(this, CreateWorkoutPage.class);
        startActivity(intent);
    }

    public void gotoeditworkout(View view){
        Intent intent = new Intent(this,edit_workout_choose.class);
        startActivity(intent);
    }

    public void gotostartworkout(View view){
        Intent intent = new Intent(this,StartWorkout_choose.class);
        startActivity(intent);
    }

    public void gotologs(View view){
        Intent intent = new Intent(this, log_choose.class);
        startActivity(intent);
    }

    public void gotostats(View view){
//        Intent intent = new Intent(this, home_page_settings.class);
//        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(screentype == 0) {
            getMenuInflater().inflate(R.menu.menu_home_page, menu);
        }
        else if(screentype == 1){
            getMenuInflater().inflate(R.menu.menu_home_page_blocks, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_switch_screens) {
            if(screentype == 1) {
                setContentView(R.layout.activity_home_page);
                this.supportInvalidateOptionsMenu();
                screentype = 0;
            }
            else if(screentype == 0){
                setContentView(R.layout.activity_home_page_blocks);
                this.supportInvalidateOptionsMenu();
                screentype = 1;
            }
        }

        else if(id == R.id.action_help ){
            Intent intent = new Intent(this,homepage_help.class);
            startActivity(intent);

        }
//        else if(id == R.id.action_settings){
//            Intent intent = new Intent(this, home_page_settings.class);
//            startActivity(intent);
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop(){
        super.onStop();
        SharedPreferences settings = getPreferences(0);
        SharedPreferences.Editor screentypesettings =  settings.edit();
        screentypesettings.putInt("screentypeblocks", screentype);

        screentypesettings.commit();

    }

}
