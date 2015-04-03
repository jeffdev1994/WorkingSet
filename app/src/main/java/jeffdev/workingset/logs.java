package jeffdev.workingset;

import android.content.Intent;
import android.content.SharedPreferences;
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

    boolean sorted = false;
    workoutStorage workout;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs);

        SharedPreferences settings = getPreferences(0);
        sorted = settings.getBoolean("logstype",false);

        workout = (workoutStorage) getIntent().getExtras().getSerializable("workout");


        DatabaseHandler db = new DatabaseHandler(this);

        List<doesSetStorage> sets;

        LinearLayout mainlayout = (LinearLayout) this.findViewById(R.id.main);

        if(!sorted){
            sets = db.getsets(workout);
            TextView header = (TextView) this.findViewById(R.id.header);
            header.setText("Sorted by set");
        }
        else{
            sets = db.getsetsordered(workout);
            TextView header = (TextView) this.findViewById(R.id.header);
            header.setText("Sorted by exercise");
        }



        for(int i = 0; i<sets.size();i++) {
            doesSetStorage set = sets.get(i);

            LinearLayout displayset = new LinearLayout(this);
            displayset.setOrientation(LinearLayout.HORIZONTAL);
            displayset.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            displayset.setPadding(0, 10, 0, 0);
            if(sorted) {
                displayset.setPadding(0, 0, 0, 0);
            }

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

            //if there are notes, then display them
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

            //if its sorted by exercise, were not at the last one, and the next set exercise isnt the same as the one were on
            //then add in an extra bit of space
            if(sorted && i != (sets.size()-1)){
                if(!set.Ename.equals(sets.get(i+1).Ename)) {
                    LinearLayout displaynote = new LinearLayout(this);
                    displaynote.setOrientation(LinearLayout.HORIZONTAL);
                    displaynote.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    displaynote.setPadding(0, 0, 0, 5);

                    TextView note = new TextView(this);
                    note.setText("\t");
                    note.setTextSize(10);

                    displaynote.addView(note);
                    mainlayout.addView(displaynote);
                }

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
        if (id == R.id.action_sort) {
            //flip the value of sorted
            boolean sorted1;
            if(sorted){
                sorted = false;
            }
            else{
                sorted = true;
            }
            workoutStorage workout1 = workout;
            Intent intent = new Intent(this,logs.class);

            Bundle bundleObject = new Bundle();
            bundleObject.putSerializable("workout",workout1);

            SharedPreferences settings = getPreferences(0);
            SharedPreferences.Editor sortsettings =  settings.edit();
            sortsettings.putBoolean("logstype", sorted);
            sortsettings.commit();

            intent.putExtras(bundleObject);

            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
