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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercisepopup);
    }

    public void submitexercise(View view){
        EditText nameinput = (EditText) findViewById(R.id.popupexercisename);
        String name = nameinput.getText().toString();

        EditText descriptioninput = (EditText) findViewById(R.id.popupexercisedescription);
        String description = descriptioninput.getText().toString();

        DatabaseHandler db = new DatabaseHandler(this);
        //if it the exercise name is already in the db
        int addresult = db.addExercise(name, description);
        if(addresult == 1){
            Context context = getApplicationContext();
            CharSequence text = "Exercise already available";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        //if it was succesful
        else if(addresult == 0){
            Context context = getApplicationContext();
            CharSequence text = "Exercise successfully added";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

        List<exerciseStorage> test = db.getExercise("all");
        for(int i = 0;i<test.size();i++) {
            Log.d("reading..", i + "   " + test.get(i).name + " " + test.get(i).description);
        }

        Intent intent = new Intent(this,ExercisePage.class);
        startActivity(intent);

    }

}
