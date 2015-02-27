package jeffdev.workingset;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class CreateWorkoutPage extends ActionBarActivity {

    ArrayList<exerciseStorage> selected;
    Context context = this;
    boolean editing;
    EditText globalname;



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


    //need to make keyboard while editing the name, have a done button. can get this from the search code
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout_page);

        Bundle bundleObject = getIntent().getExtras();
        EditText name = (EditText) findViewById(R.id.workoutname);
        //if it doesnt equal null, then display the stuff up, otherwise maybe a textview saying to add some exercises with the plus
        //or something, maybe just blank
        if(bundleObject != null){
            selected = (ArrayList<exerciseStorage>) bundleObject.getSerializable("exercises");
            this.globalname = name;
            name.setText(bundleObject.getString("name"));


            if(bundleObject.containsKey("editworkout")){
                editing = true;
                //then lock the edittext
                name.setEnabled(false);
                name.setClickable(false);
                name.setFocusable(false);
                name.setFocusableInTouchMode(false);
            }
//            for(int i=0; i<selected.size();i++){
//                Log.d("name:",selected.get(i).name);
//            }

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
//                    DatabaseHandler db = new DatabaseHandler(context);
//                    db.test();

                }
            });

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast toast = Toast.makeText(getApplicationContext(),selected.get(position).name+" has been removed", Toast.LENGTH_LONG);
                    selected.remove(position);
                    Intent intent = new Intent(context,CreateWorkoutPage.class);

                    Bundle bundleObject = new Bundle();
                    bundleObject.putSerializable("exercises",selected);
                    bundleObject.putString("name",globalname.getText().toString());
                    if(editing == true){
                        bundleObject.putString("editworkout", "true");
                    }

                    intent.putExtras(bundleObject);
                    toast.show();
                    startActivity(intent);
//                    DatabaseHandler db = new DatabaseHandler(context);
//                    db.resettables();
                    return true;
                }
            });


        }
        else{
            selected = new ArrayList<exerciseStorage>();
        }
    }

    public void addexercise(View view){

        EditText name = (EditText) findViewById(R.id.workoutname);

        Intent intent = new Intent(this,createworkout_addexercise.class);
        Bundle bundleObject = new Bundle();
        bundleObject.putSerializable("exercises",selected);
        bundleObject.putString("name",name.getText().toString());
        if(editing == true){
            bundleObject.putString("editworkout", "true");
        }

        intent.putExtras(bundleObject);
        startActivity(intent);
    }

    public void submitworkout(View view){
        EditText nameinput = (EditText) findViewById(R.id.workoutname);
        String workoutname = nameinput.getText().toString();

        DatabaseHandler db = new DatabaseHandler(this);

        if(editing == true){
           //List<makeupStorage> originalexercises = db.getMakeup(workoutname);
            db.deleteWorkout(workoutname);
            db.addworkout(workoutname);

            for (int i = 0; i < selected.size(); i++) {
                db.addmakeup(selected.get(i).name, workoutname);
            }
                Context context = getApplicationContext();
                CharSequence text = "Workout successfully updated";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                Intent intent = new Intent(this, HomePage.class);
                startActivity(intent);


        }
        else {
            int workoutcheck = db.addworkout(workoutname);
            if (workoutcheck == 1) {
                //then its already there
                Toast toast = Toast.makeText(getApplicationContext(), "Workout name already exists...\nplease try a different one", Toast.LENGTH_LONG);
                LinearLayout toastLayout = (LinearLayout) toast.getView();
                TextView toastTV = (TextView) toastLayout.getChildAt(0);
                toastTV.setTextSize(20);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 25);
                toast.show();
                Intent intent = new Intent(this, CreateWorkoutPage.class);
                Bundle bundleObject = new Bundle();
                bundleObject.putSerializable("exercises", selected);

                intent.putExtras(bundleObject);
                startActivity(intent);
            } else {
                DatabaseHandler dbmakeup = new DatabaseHandler(this);

                for (int i = 0; i < selected.size(); i++) {
                    dbmakeup.addmakeup(selected.get(i).name, workoutname);
                    Context context = getApplicationContext();
                    CharSequence text = "New workout successfully added";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    Intent intent = new Intent(this, HomePage.class);
                    startActivity(intent);
                }
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_workout_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_help) {
            Intent intent = new Intent(this,new_workout_help.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
