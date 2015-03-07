package jeffdev.workingset;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class HomePage extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_home_page, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
