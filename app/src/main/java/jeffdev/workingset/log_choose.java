package jeffdev.workingset;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class log_choose extends ActionBarActivity {

    Context context = this;
    ArrayList<workoutStorage> allvalues;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_choose);

        final ListView listView = (ListView) findViewById(R.id.workoutlist);

        DatabaseHandler db = new DatabaseHandler(this);
        allvalues = db.getalllogs();
        List<String> allvaluesflat = new ArrayList<String>();
        for(int i = 0; i<allvalues.size();i++){
            allvaluesflat.add(allvalues.get(i).name + ": " + allvalues.get(i).date);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_selectable_list_item, android.R.id.text1, allvaluesflat);
        listView.setAdapter(adapter);

        //on click, go to the page to let them edit the exercises in the workout
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                workoutStorage workout = allvalues.get(position);
                //Intent intent = new Intent(context,log.class);

                //Log.d("name", workout.name);

                Bundle bundleObject = new Bundle();
                bundleObject.putSerializable("workout",workout);

                //intent.putExtras(bundleObject);

                //startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int position1 = position;
                AlertDialog.Builder ask = new AlertDialog.Builder(context);
                ask.setPositiveButton("Delete log", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        workoutStorage workout = allvalues.get(position1);
                        DatabaseHandler db = new DatabaseHandler(context);
                        db.deletelog(workout);
                        Intent intent = new Intent(context,log_choose.class);
                        startActivity(intent);
                        return;
                    }
                });
                ask.create();
                ask.show();

                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log_choose, menu);
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
