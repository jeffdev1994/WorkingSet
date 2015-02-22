package jeffdev.workingset;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Jeff on 2015-02-15.
 */
public class exercisepopup_option extends Activity{

    String name;
    String description;

    public final static String OPTION_MESSAGE1 = "jeffdev.workingset.exercisename";
    public final static String OPTION_MESSAGE2 = "jeffdev.workingset.exercisedescription";

    //takes over the backkey
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(this, ExercisePage.class);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercisepopup_option);
        Intent intent = getIntent();
        this.name = intent.getStringExtra(ExercisePage.OPTION_MESSAGE1);
        this.description = intent.getStringExtra(ExercisePage.OPTION_MESSAGE2);
    }

    public void update(View view){
        Intent intent = new Intent(this,exercisepopup.class);
        intent.putExtra(OPTION_MESSAGE1,name);
        intent.putExtra(OPTION_MESSAGE2,description);
        startActivity(intent);
    }

    public void delete(View view){
        DatabaseHandler db = new DatabaseHandler(this);
        db.deleteExercise(name);

        Context context = getApplicationContext();
        CharSequence text = "Exercise successfully deleted";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        Intent intent = new Intent(this,ExercisePage.class);
        startActivity(intent);
    }
}

