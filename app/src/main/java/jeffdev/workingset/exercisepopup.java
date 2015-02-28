package jeffdev.workingset;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;



public class exercisepopup extends Activity {
    //if it equals 1, then im updating, if 0 then new exercise
    int update_flag = 0;
    String updatingname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_exercisepopup);
        Intent intent = getIntent();
        //put the passed on info into the fields.
        if(intent.getStringExtra(exercisepopup_option.OPTION_MESSAGE1) != null) {
            String name = intent.getStringExtra(exercisepopup_option.OPTION_MESSAGE1);
            this.updatingname = name;
            String description = intent.getStringExtra(exercisepopup_option.OPTION_MESSAGE2);
            EditText namefield = (EditText) findViewById(R.id.popupexercisename);
            namefield.setText(name);
            EditText descriptionfield = (EditText) findViewById(R.id.popupexercisedescription);
            descriptionfield.setText(description);
            update_flag = 1;
        }
    }

    public void submitexercise(View view) {
        if (update_flag == 0) {
            EditText nameinput = (EditText) findViewById(R.id.popupexercisename);
            String name = nameinput.getText().toString();

            //get rid of leading and trailing spaces from workoutname
            while(name.startsWith(" ")){
                name = name.substring(1);
            }
            while(name.endsWith(" ")){
                name = name.substring(0,name.length()-1);
            }

            EditText descriptioninput = (EditText) findViewById(R.id.popupexercisedescription);
            String description = descriptioninput.getText().toString();

            DatabaseHandler db = new DatabaseHandler(this);
            //if it the exercise name is already in the db
            int addresult = db.addExercise(name, description);
            if (addresult == 1) {
                Context context = getApplicationContext();
                CharSequence text = "Exercise already available";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
            //if it was succesful
            else if (addresult == 0) {
                Context context = getApplicationContext();
                CharSequence text = "Exercise successfully added";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
        else if(update_flag == 1){
            EditText nameinput = (EditText) findViewById(R.id.popupexercisename);
            String name = nameinput.getText().toString();

            EditText descriptioninput = (EditText) findViewById(R.id.popupexercisedescription);
            String description = descriptioninput.getText().toString();

            DatabaseHandler db = new DatabaseHandler(this);
            //if it the exercise name is already in the db
            int failed = db.updateExercise(this.updatingname, name, description);
            //if it worked
            if(failed == 0) {
                //display the toast
                Context context = getApplicationContext();
                CharSequence text = "Exercise successfully updated";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
            //if it failed
            else if(failed == 1){
                Intent intent = new Intent(this,exercisepopup.class);
                intent.putExtra("jeffdev.workingset.exercisename",updatingname);
                intent.putExtra("jeffdev.workingset.exercisedescription",description);

                CharSequence text = "Exercise name already exists, please try another name";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(this, text, duration);
                toast.show();

                startActivity(intent);
                return;


            }
        }
        else{
            Context context = getApplicationContext();
            CharSequence text = "Error: something went wrong, nothing happened";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }


        Intent intent = new Intent(this, ExercisePage.class);
        startActivity(intent);

    }


}
