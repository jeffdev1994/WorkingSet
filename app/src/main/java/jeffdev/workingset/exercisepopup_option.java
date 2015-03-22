package jeffdev.workingset;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Jeff on 2015-02-15.
 */
public class exercisepopup_option extends Activity{

    String name;
    String description;
    Context context = this;

    public final static String OPTION_MESSAGE1 = "jeffdev.workingset.exercisename";
    public final static String OPTION_MESSAGE2 = "jeffdev.workingset.exercisedescription";


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
        DatabaseHandler dbcheck = new DatabaseHandler(this);
        final List<String> check = dbcheck.getExerciseUsed(name);
        final int size = check.size();
        if(size > 0){
            AlertDialog.Builder ask = new AlertDialog.Builder(this);
            ask.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for(int i = 0; i < size; i++ ) {
                        DatabaseHandler db = new DatabaseHandler(context);
                        db.deleteWorkout(check.get(i));
                    }

                    DatabaseHandler db1 = new DatabaseHandler(context);
                    db1.deleteExercise(name);



//                    Context context = getApplicationContext();
                    CharSequence text = "Exercise successfully deleted";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    Intent intent = new Intent(context,ExercisePage.class);
                    startActivity(intent);
                    return;
                }
            });
            ask.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(context, ExercisePage.class);
                    startActivity(intent);
                    return;
                }
            });
            ask.setMessage("This Exercise is used in some of your workouts\ndeleting it will result in deleting the associated workouts as well");
            ask.create();
            ask.show();
        }
        else{
            DatabaseHandler db1 = new DatabaseHandler(context);
            db1.deleteExercise(name);

            CharSequence text = "Exercise successfully deleted";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            Intent intent = new Intent(context,ExercisePage.class);
            startActivity(intent);
            return;
        }
    }
}

