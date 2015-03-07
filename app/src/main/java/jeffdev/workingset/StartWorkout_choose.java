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


public class StartWorkout_choose extends ActionBarActivity {

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
        setContentView(R.layout.activity_start_workout_choose);

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
                Intent intent = new Intent(context,StartWorkout.class);
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

                intent.putExtras(bundleObject);

                startActivity(intent);
            }
        });

        //long click to get a choice between quickview(shows toast of the exercises in the workout) or delete
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String workoutname = allvalues.get(position);
                QV(workoutname);
                return true;
            }
        });
    }

    public void QV(String name){
        DatabaseHandler db = new DatabaseHandler(this);
        List<makeupStorage> exercises = db.getMakeup(name);
        String singleline = exercises.get(0).Ename + "\n";
        for(int i = 1; i<exercises.size();i++){
            singleline+= exercises.get(i).Ename + "\n";
        }
        singleline = singleline.substring(0,singleline.length()-1);


        Toast toast = Toast.makeText(this,singleline, Toast.LENGTH_LONG);
        LinearLayout toastLayout = (LinearLayout) toast.getView();
        TextView toastTV = (TextView) toastLayout.getChildAt(0);
        toastTV.setTextSize(25);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 25);
        toast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_workout_choose, menu);
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
