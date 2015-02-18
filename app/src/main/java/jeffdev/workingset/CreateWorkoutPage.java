package jeffdev.workingset;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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
//            for(int i=0; i<selected.size();i++){
//                Log.d("name:",selected.get(i).name);
//            }

            //need code to remove duplicates from it

            List<String> values = new ArrayList<String>();
            for(int i=0;i<selected.size();i++){
                values.add(selected.get(i).name);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_selectable_list_item, android.R.id.text1, values);
            ListView listView = (ListView) findViewById(R.id.workoutexerciselist);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                    Toast toast = Toast.makeText(getApplicationContext(),selected.get(position).description, Toast.LENGTH_LONG);
                    LinearLayout toastLayout = (LinearLayout) toast.getView();
                    TextView toastTV = (TextView) toastLayout.getChildAt(0);
                    toastTV.setTextSize(25);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 25);
                    toast.show();
                }
            });

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    //change this so that it removes the exercise.
                    return true;
                }
            });


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
