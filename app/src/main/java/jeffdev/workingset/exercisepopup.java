package jeffdev.workingset;

import android.app.Activity;
import android.util.Log;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

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
        db.addExercise(name,description);

        List<String[]> test = db.getExercise("all");
        for(int i = 0;i<test.size();i++) {
            Log.d("reading..",i + "   " + test.get(i)[0] + " " + test.get(i)[1] );
        }
    }

}
