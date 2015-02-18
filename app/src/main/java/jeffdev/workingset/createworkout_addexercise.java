package jeffdev.workingset;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class createworkout_addexercise extends Activity {

    List<exerciseStorage> allvalues;
    ArrayList<exerciseStorage> selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createworkout_addexercise);

        Bundle bundleObject = getIntent().getExtras();
        if(bundleObject != null) {
            selected = (ArrayList<exerciseStorage>) bundleObject.getSerializable("exercises");
        }
        else{
            selected = new ArrayList<exerciseStorage>();
        }

        ListView listView = (ListView) findViewById(R.id.neworkoutexerciselist);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        DatabaseHandler db = new DatabaseHandler(this);


        allvalues = db.getExercise("");
        List<String> values = new ArrayList<String>();
        for(int i=0;i<allvalues.size();i++){
            values.add(allvalues.get(i).name);
        }

//        simple_list_item_1 :  Android internal layout view
//        android.R.id.text1 :  In Android internal layout view already defined text fields to show data
//        values :  User defined data array.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice, android.R.id.text1, values);

        // Assign adapter to ListView
        listView.setAdapter(adapter);

//        //listview item click listener, to add the checkmarks
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
//                CheckedTextView checkedTextView = ((CheckedTextView)view);
//                checkedTextView.setChecked(!checkedTextView.isChecked());
//            }
//        });
        // ListView Item long Click Listener
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Show description of the exercise clicked
                //with 25 textsize, and center-25down position
                Toast toast = Toast.makeText(getApplicationContext(), allvalues.get(position).description, Toast.LENGTH_LONG);
                LinearLayout toastLayout = (LinearLayout) toast.getView();
                TextView toastTV = (TextView) toastLayout.getChildAt(0);
                toastTV.setTextSize(25);
                //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 25);
                toast.show();
                return true;
            }
        });

    }

    public void submitexercises(View view){
        //selected = new ArrayList<exerciseStorage>();
        ListView listView = (ListView) findViewById(R.id.neworkoutexerciselist);
        SparseBooleanArray sparseArray = listView.getCheckedItemPositions();
        int x;
        for (int i = 0; i < sparseArray.size(); i++ )
        {
            //if its true at that spot, then i want to put my values from my corresponding
            //allvalues array into a new array to be sent back to other activity
            x = sparseArray.keyAt(i);
            selected.add(allvalues.get(x));

        }
        Intent intent = new Intent(this,CreateWorkoutPage.class);

        Bundle bundleObject = new Bundle();
        bundleObject.putSerializable("exercises",selected);

        intent.putExtras(bundleObject);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_createworkout_addexercise, menu);
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
