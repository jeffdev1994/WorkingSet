package jeffdev.workingset;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class workoutpopup_option extends Activity {

    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workoutpopup_option);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
    }

    public void QV(View view){
        DatabaseHandler db = new DatabaseHandler(this);
        List<makeupStorage> exercises = db.getMakeup(name);
        String singleline = exercises.get(0).Ename + "\n";
        for(int i = 1; i<exercises.size();i++){
            singleline+= exercises.get(i).Ename + "\n";
        }
        singleline = singleline.substring(0,singleline.length()-1);


        Toast toast = Toast.makeText(this,singleline, Toast.LENGTH_LONG);
        LinearLayout toastLayout = (LinearLayout) toast.getView();
        TextView toastTV = (TextView) toastLayout.getChildAt(0);
        toastTV.setTextSize(25);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 25);
        toast.show();

        Intent intent = new Intent(this,edit_workout_choose.class);
        startActivity(intent);

    }


    public void delete(View view){
        DatabaseHandler db = new DatabaseHandler(this);
        if(db.deleteWorkout(name) == 0){
            Context context = this;
            CharSequence text = "Workout successfully deleted";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        Intent intent = new Intent(this,edit_workout_choose.class);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_workoutpopup_option, menu);
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
