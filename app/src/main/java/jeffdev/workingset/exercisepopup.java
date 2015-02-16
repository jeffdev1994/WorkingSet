package jeffdev.workingset;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
//        Log.d("type",intent.getStringExtra(exercisepopup_option.OPTION_MESSAGE1));
        //put the passed on info into the fields.
        try{
            String name = intent.getStringExtra(exercisepopup_option.OPTION_MESSAGE1);
            this.updatingname = name;
            String description = intent.getStringExtra(exercisepopup_option.OPTION_MESSAGE2);
            EditText namefield = (EditText) findViewById(R.id.popupexercisename);
            namefield.setText(name);
            EditText descriptionfield = (EditText) findViewById(R.id.popupexercisedescription);
            descriptionfield.setText(description);
            update_flag = 1;
        }catch(NullPointerException e){
            //do nothing, if it doesnt have extra, then let it be
        }
    }

    public void submitexercise(View view) {
        if (update_flag == 0) {
            EditText nameinput = (EditText) findViewById(R.id.popupexercisename);
            String name = nameinput.getText().toString();

            EditText descriptioninput = (EditText) findViewById(R.id.popupexercisedescription);
            String description = descriptioninput.getText().toString();

            DatabaseHandler db = new DatabaseHandler(this);
            //if it the exercise name is already in the db
            int addresult = db.addExercise(name, description);
            if (addresult == 1) {
                Context context = getApplicationContext();
                CharSequence text = "Exercise already available";
                int duration = Toast.LENGTH_SHORT;

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
            db.updateExercise(this.updatingname, name, description);
            //display the toast
            Context context = getApplicationContext();
            CharSequence text = "Exercise successfully updated";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
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
