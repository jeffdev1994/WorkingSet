package jeffdev.workingset;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class ExercisePage extends Activity {

    List<exerciseStorage> allvalues;
    final Context context = this;
    public final static String OPTION_MESSAGE1 = "jeffdev.workingset.exercisename";
    public final static String OPTION_MESSAGE2 = "jeffdev.workingset.exercisedescription";

    //could set it up that the search button just recalls this, just need to learn to save instance thing abit
    //so that i can place what is in the search bar, back in after calling it again, cause without that
    //it just ends up blank
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_page);

        EditText searchinput = (EditText) findViewById(R.id.exercisesearch);

        //making the return button, a search button instead
        searchinput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchexercise(null);
                    return true;
                }
                return false;
            }
        });

        // Get ListView object from xml
        ListView listView = (ListView) findViewById(R.id.exerciselist);

        DatabaseHandler db = new DatabaseHandler(this);


        allvalues = db.getExercise(searchinput.getText().toString());
        List<String> values = new ArrayList<String>();
        for(int i=0;i<allvalues.size();i++){
            values.add(allvalues.get(i).name);
        }

//        simple_list_item_1 :  Android internal layout view
//        android.R.id.text1 :  In Android internal layout view already defined text fields to show data
//        values :  User defined data array.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_selectable_list_item, android.R.id.text1, values);

        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                // Show description of the exercise clicked
                //with 25 textsize, and center-25down position
                Toast toast = Toast.makeText(getApplicationContext(),allvalues.get(position).description, Toast.LENGTH_LONG);
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
                String exercisename = allvalues.get(position).name;
                String exercisedescription = allvalues.get(position).description;
                Intent intent = new Intent(context,exercisepopup_option.class);
                intent.putExtra(OPTION_MESSAGE1,exercisename);
                intent.putExtra(OPTION_MESSAGE2,exercisedescription);
                startActivity(intent);
                return true;
            }
        });
    }



    public void searchexercise(View view){
        //set a content view that has an X button to close the search basicly
        //temp is to save what was in the edittext before switching contentviews
        EditText temp = (EditText) findViewById(R.id.exercisesearch);
        String temptext = temp.getText().toString();
        setContentView(R.layout.activity_exercise_page_search);

        //put the keyboard down after searching
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        EditText searchinput = (EditText) findViewById(R.id.exercisesearch);

        searchinput.setText(temptext);

        //making the return button, a search button instead
        searchinput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchexercise(null);
                    return true;
                }
                return false;
            }
        });

        DatabaseHandler db = new DatabaseHandler(this);
        allvalues = db.getExercise(searchinput.getText().toString());
        List<String> values = new ArrayList<String>();
        for(int i=0;i<allvalues.size();i++){
            values.add(allvalues.get(i).name);
        }

        //better documentation on this is in the onCreate method
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_selectable_list_item, android.R.id.text1, values);
        ListView listView = (ListView) findViewById(R.id.exerciselist);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                Toast toast = Toast.makeText(getApplicationContext(),allvalues.get(position).description, Toast.LENGTH_LONG);
                LinearLayout toastLayout = (LinearLayout) toast.getView();
                TextView toastTV = (TextView) toastLayout.getChildAt(0);
                toastTV.setTextSize(25);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 25);
                toast.show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,int position, long id) {
                //fill this in with whatever is in the method above
                return true;
            }
        });

    }


    public void exerciseaddbutton(View view){
        Intent intent = new Intent(this,exercisepopup.class);
        startActivity(intent);
    }



    public void cancelsearch(View view){
        this.onCreate(null);
    }



//never used in real app, but have it for getting rid of the database at random times
    public void deletetables(){
        DatabaseHandler db = new DatabaseHandler(this);
        db.resettables();
        Log.d("deleting","tables are reset");
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_exercise_page, menu);
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
