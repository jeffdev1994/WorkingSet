package jeffdev.workingset;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class edit_workout_choose extends ActionBarActivity {

    List<String> allvalues;
    Context context = this;

    //takes over the backkey
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(this,HomePage.class);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout_choose);

        ListView listView = (ListView) findViewById(R.id.workoutlist);

        DatabaseHandler db = new DatabaseHandler(this);
        allvalues = db.getAllWorkout();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_selectable_list_item, android.R.id.text1, allvalues);
        listView.setAdapter(adapter);

        //on click, go to the page to let them edit the exercises in the workout
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                String name = allvalues.get(position);
                Intent intent = new Intent(context,CreateWorkoutPage.class);
                //also need to send the workouts in bundle object to put in listview, just a string list
                //also put the name in the bundle object
                DatabaseHandler db = new DatabaseHandler(context);
                //shouldnt be sending a list of something different, i believe exercises so i will need to take this list
                //and get exercise list from it.
                ArrayList<makeupStorage> makeup = db.getMakeup_list(name);
                ArrayList<exerciseStorage> exercises = new ArrayList<exerciseStorage>();
                for(int i = 0;i<makeup.size();i++){
                    exercises.add(db.getSingleExercise(makeup.get(i).Ename));
                }
                Bundle bundleObject = new Bundle();
                bundleObject.putSerializable("exercises",exercises);
                bundleObject.putString("name",name);
                bundleObject.putString("editworkout", "true");

                intent.putExtras(bundleObject);

                startActivity(intent);
            }
        });

        //long click to get a choice between quickview(shows toast of the exercises in the workout) or delete
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String workoutname = allvalues.get(position);
                Intent intent = new Intent(context,workoutpopup_option.class);
                intent.putExtra("name",workoutname);
                startActivity(intent);
                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_workout_choose, menu);
        return true;
    }

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
