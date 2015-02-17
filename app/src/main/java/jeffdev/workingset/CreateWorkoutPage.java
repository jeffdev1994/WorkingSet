package jeffdev.workingset;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;


public class CreateWorkoutPage extends ActionBarActivity {

    ArrayList<exerciseStorage> selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout_page);

        Bundle bundleObject = getIntent().getExtras();

        //if it doesnt equal null, then display the stuff up, otherwise maybe a textview saying to add some exercises with the plus
        //or somehting, maybe just blank
        if(bundleObject != null){
            selected = (ArrayList<exerciseStorage>) bundleObject.getSerializable("exercises");


        }
        else{
            selected = new ArrayList<exerciseStorage>();
        }
    }

    public void addexercise(View view){

        Intent intent = new Intent(this,createworkout_addexercise.class);
        Bundle bundleObject = new Bundle();
        bundleObject.putSerializable("exercises",selected);

        intent.putExtras(bundleObject);
        startActivity(intent);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_create_workout_page, menu);
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
