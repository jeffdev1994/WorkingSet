package jeffdev.workingset;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class HomePage extends ActionBarActivity {

    static int screentype = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
