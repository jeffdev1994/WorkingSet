package jeffdev.workingset;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


public class logs extends ActionBarActivity {

    //takes over the backkey
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(this, log_choose.class);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

        //need to decide if i want the view to come up in the order they did sets, or all the same sets together and so on
    //maybe provide an option for either set?
    //do the in order of added first
    //TODO: add in a button or menu item to switch between different types of view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs);

        workoutStorage workout = (workoutStorage) getIntent().getExtras().getSerializable("workout");
        DatabaseHandler db = new DatabaseHandler(this);
        List<doesSetStorage> sets = db.getsets(workout);

        //if i want to keep them all ordered nicely, then need to do this and compare them with other list
        //List<makeupStorage> makeuplist = db.getMakeup(workout.name);

        LinearLayout mainlayout = (LinearLayout) this.findViewById(R.id.main);

        for(int i = 0; i<sets.size();i++) {
            doesSetStorage set = sets.get(i);

            LinearLayout displayset = new LinearLayout(this);
            displayset.setOrientation(LinearLayout.HORIZONTAL);
            displayset.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            displayset.setPadding(0, 10, 0, 0);

            TextView exercisename = new TextView(this);
            exercisename.setText(set.Ename);
            exercisename.setTextSize(25);
            exercisename.setTextColor(Color.BLACK);
            //conversion calculation for using dp
            int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 230, getResources().getDisplayMetrics());
            exercisename.setMaxWidth(px);

            displayset.addView(exercisename);

            TextView setreps = new TextView(this);
            setreps.setText(set.reps + " x ");
            setreps.setTextSize(25);
            setreps.setPadding(150, 0, 0, 0);
            setreps.setTextColor(Color.BLACK);

            displayset.addView(setreps);

            TextView setweight = new TextView(this);
            setweight.setText(set.weight+ "");
            setweight.setTextSize(25);
            setweight.setTextColor(Color.BLACK);

            displayset.addView(setweight);

            mainlayout.addView(displayset);

            if(!set.notes.equals("")){
                LinearLayout displaynote = new LinearLayout(this);
                displaynote.setOrientation(LinearLayout.HORIZONTAL);
                displaynote.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                displaynote.setPadding(0, 0, 0, 5);

                TextView note = new TextView(this);
                note.setText("\tNote: " + set.notes);
                note.setTextSize(15);

                displaynote.addView(note);
                mainlayout.addView(displaynote);
            }



        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_logs, menu);
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
