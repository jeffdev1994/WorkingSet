package jeffdev.workingset;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class StartWorkout extends ActionBarActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;


    //takes over the backkey
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder ask = new AlertDialog.Builder(this);

            ask.setPositiveButton("Leave", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getApplicationContext(),StartWorkout_choose.class);
                    startActivity(intent);
                    return;
                }
            });
            ask.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            ask.setMessage("If you leave now, your workout will not be saved!");
            ask.create();
            ask.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_workout);
//        getActionBar().hide();


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        //String workoutname = bundle.getString("name");
        ArrayList<makeupStorage> exercises = (ArrayList<makeupStorage>) bundle.getSerializable("exercises");
        //int numExercises = exercises.size();



        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),exercises);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_workout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_help) {
            Intent intent = new Intent(this,startworkout_help.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        ArrayList<makeupStorage> exercises;


        public SectionsPagerAdapter(FragmentManager fm, ArrayList<makeupStorage> exercises) {
            super(fm);
            this.exercises = exercises;
        }


        @Override
        public Fragment getItem(int position) {
            //this will be where i put the finished workout tab.
            if((position ) == exercises.size()){
                //return finishfragment.newInstance();
                return finishFragment.newInstance(position + 1);
            }
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
            String description = db.getSingleExercise(exercises.get(position).Ename).description;
            return PlaceholderFragment.newInstance(position + 1,exercises.get(position),description);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return exercises.size()+1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if((position ) == exercises.size()){
                return "Finished";
            }
            //Locale l = Locale.getDefault();
            return exercises.get(position).Ename;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        int currentset = 1;
        int deletebuttonID = 0;
        //makeupStorage makeup;
        String Ename;
        String Edescription;
        String Wname;
        List<String> donesets = new ArrayList<String>();
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        View rootView;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber,makeupStorage EandWnames, String Edescription) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            fragment.setinfo(EandWnames.Ename,EandWnames.Wname, Edescription);
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        public void setinfo(String Ename, String Wname, String Edescription) {
            this.Ename = Ename;
            this.Wname = Wname;
            this.Edescription = Edescription;
        }

//        @Override
//        public void onPause(){
//            super.onPause();
//            ViewGroup mainlayout = (LinearLayout) rootView.findViewById(R.id.main_layout);
//            if(mainlayout.getChildAt(10) != null) {
//                mainlayout.removeViewAt(10);
//            }
//        }
        //this is used when coming back, it solves the problem of losing my sets after going to far
        @Override
        public void onResume() {
            super.onResume();
            //removes them first, since when you turn off screen, it doesnt get rid of it automatically
            LinearLayout stuff = (LinearLayout) rootView.findViewById(R.id.sets_section);
            stuff.removeAllViewsInLayout();
            stuff.invalidate();

            if(currentset > 1){
            int j = 1;
            int deletebuttonID2 = 0;
            for(int i = 0; i < donesets.size(); i+=2) {
                if(!donesets.get(i).equals("")) {
                    LinearLayout mainlayout = (LinearLayout) rootView.findViewById(R.id.sets_section);

                    LinearLayout doneset = new LinearLayout(rootView.getContext());
                    doneset.setOrientation(LinearLayout.HORIZONTAL);
                    doneset.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    doneset.setPadding(0, 0, 0, 15);

                    TextView setnum = new TextView(rootView.getContext());
                    setnum.setText("\tset " + j);
                    setnum.setTextSize(25);
                    j++;

                    doneset.addView(setnum);

                    TextView setreps = new TextView(rootView.getContext());
                    setreps.setText(donesets.get(i));
                    setreps.setTextSize(25);
                    setreps.setPadding(200, 0, 0, 0);

                    doneset.addView(setreps);

                    TextView setweight = new TextView(rootView.getContext());
                    setweight.setText(donesets.get(i + 1) + "\t\t\t\t\t");
                    setweight.setTextSize(25);

                    doneset.addView(setweight);

                    ImageButton deletebutton = new ImageButton(rootView.getContext());
                    deletebutton.setId(deletebuttonID2);
                    //ViewGroup.LayoutParams params1 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.MATCH_PARENT)
                    deletebutton.setLayoutParams(new ViewGroup.LayoutParams(95,ViewGroup.LayoutParams.MATCH_PARENT));
                    deletebutton.setPadding(100,20,100,0);
                    deletebutton.setBackground(getResources().getDrawable(R.drawable.deletebutton));
                    deletebutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int id = v.getId();
                            allSetsStorage.deletesingle(Ename,donesets.get((id*2)+1),donesets.get(id*2).substring(0,donesets.get(id*2).length()-3));
                            currentset--;
                            donesets.set((id*2) + 1, "");
                            donesets.set(id*2, "") ;
                            onResume();
                            return;

                        }});

                    doneset.addView(deletebutton);


                    mainlayout.addView(doneset);
                }
                deletebuttonID2++;
            }



            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_start_workout, container, false);
            TextView displaydescription = (TextView) rootView.findViewById(R.id.description);
            displaydescription.setText(Edescription);

            //start the listeners
            ImageButton repsup = (ImageButton) rootView.findViewById(R.id.repsup);
            repsup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText editText = (EditText) rootView.findViewById(R.id.reps);
                    if(!editText.getText().toString().equals("")) {
                        Integer upone = Integer.parseInt(editText.getText().toString());
                        if (upone >= 0) {
                            upone += 1;
                            editText.setText(upone.toString());
                        } else {
                            editText.setText("0");
                        }
                        }
                        else{
                            editText.setText("1");
                        }
                }
            });


            ImageButton repsdown = (ImageButton) rootView.findViewById(R.id.repsdown);
            repsdown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText editText = (EditText) rootView.findViewById(R.id.reps);
                    if(!editText.getText().toString().equals("")) {
                        Integer downone = Integer.parseInt(editText.getText().toString());
                        if (downone >= 1) {
                            downone -= 1;
                            editText.setText(downone.toString());
                        } else {
                            editText.setText("0");
                        }
                    }
                    else{
                        editText.setText("0");
                    }
                }
            });

            ImageButton weightup = (ImageButton) rootView.findViewById(R.id.weightup);
            weightup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText editText = (EditText) rootView.findViewById(R.id.weight);
                    if (!editText.getText().toString().equals("")) {
                        Integer upone = Integer.parseInt(editText.getText().toString());
                        if (upone >= 0) {
                            upone += 1;
                            editText.setText(upone.toString());
                        } else {
                            editText.setText("0");
                        }
                    } else {
                        editText.setText("1");
                    }
                }
            });


            ImageButton weightdown = (ImageButton) rootView.findViewById(R.id.weightdown);
            weightdown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText editText = (EditText) rootView.findViewById(R.id.weight);
                    if(!editText.getText().toString().equals("")) {
                        Integer downone = Integer.parseInt(editText.getText().toString());
                        if (downone >= 1) {
                            downone -= 1;
                            editText.setText(downone.toString());
                        } else {
                            editText.setText("0");
                        }
                    }
                    else{
                        editText.setText("0");
                    }
                }
            });


            ImageButton weightdoubleup = (ImageButton) rootView.findViewById(R.id.weightdoubleup);
            weightdoubleup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText editText = (EditText) rootView.findViewById(R.id.weight);
                    if(!editText.getText().toString().equals("")) {
                        Integer upone = Integer.parseInt(editText.getText().toString());
                        if (upone >= 0) {
                            upone += 10;
                            editText.setText(upone.toString());
                        } else {
                            editText.setText("0");
                        }
                    }
                    else{
                        editText.setText("10");
                    }
                }
            });

            ImageButton weightdoubledown = (ImageButton) rootView.findViewById(R.id.weightdoubledown);
            weightdoubledown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText editText = (EditText) rootView.findViewById(R.id.weight);
                    if(!editText.getText().toString().equals("")) {
                        Integer downone = Integer.parseInt(editText.getText().toString());
                        if (downone >= 11) {
                            downone -= 10;
                            editText.setText(downone.toString());
                        } else {
                            editText.setText("0");
                        }
                    }
                    else{
                        editText.setText("0");
                    }
                }
            });

            ImageButton addset = (ImageButton) rootView.findViewById(R.id.addset);
            addset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO: need to get rid of the focus from the edit texts after pressing add, or just put down the keyboard
                    //Log.d("exercise",Ename);
                    EditText weight = (EditText) rootView.findViewById(R.id.weight);
                    EditText reps = (EditText) rootView.findViewById(R.id.reps);
                    EditText notes = (EditText) rootView.findViewById(R.id.notes);


                    if(weight.getText().toString().equals("")){
                        Toast toast = Toast.makeText(rootView.getContext(),"please enter a value for weight", Toast.LENGTH_SHORT);
                        LinearLayout toastLayout = (LinearLayout) toast.getView();
                        TextView toastTV = (TextView) toastLayout.getChildAt(0);
                        toastTV.setTextSize(20);
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 25);
                        toast.show();
                        return;
                    }

                    if(reps.getText().toString().equals("")){
                        Toast toast = Toast.makeText(rootView.getContext(),"please enter a value for reps", Toast.LENGTH_SHORT);
                        LinearLayout toastLayout = (LinearLayout) toast.getView();
                        TextView toastTV = (TextView) toastLayout.getChildAt(0);
                        toastTV.setTextSize(20);
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 25);
                        toast.show();
                        return;
                    }

                    //add the set to the array in onCreate
                    doesSetStorage theSet = new doesSetStorage();
                    theSet.Ename = Ename;
                    theSet.Wname = Wname;
                    theSet.notes = notes.getText().toString();
                    theSet.reps = Integer.parseInt(reps.getText().toString());
                    theSet.weight = Integer.parseInt(weight.getText().toString());
                    theSet.date = date;
                    allSetsStorage.addset(theSet);


                    //set notes back to empty, cuz it wil be on a per set basis, instead of per workout
                    notes.setText("");



                    //set up the new linearlayout that tells them about their set
                    LinearLayout mainlayout = (LinearLayout) rootView.findViewById(R.id.sets_section);

                    LinearLayout doneset = new LinearLayout(rootView.getContext());
                    doneset.setOrientation(LinearLayout.HORIZONTAL);
                    doneset.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                    doneset.setPadding(0,0,0,15);

                    TextView setnum = new TextView(rootView.getContext());
                    setnum.setText("\tset " + currentset);
                    setnum.setTextSize(25);
                    currentset++;

                    doneset.addView(setnum);

                    TextView setreps = new TextView(rootView.getContext());
                    setreps.setText(reps.getText().toString() + " x ");
                    setreps.setTextSize(25);
                    setreps.setPadding(200,0,0,0);

                    doneset.addView(setreps);

                    TextView setweight = new TextView(rootView.getContext());
                    setweight.setText(weight.getText().toString() + "\t\t\t\t\t");
                    setweight.setTextSize(25);

                    doneset.addView(setweight);

                    ImageButton deletebutton = new ImageButton(rootView.getContext());
                    deletebutton.setId(deletebuttonID);
                    deletebuttonID++;
                    deletebutton.setLayoutParams(new ViewGroup.LayoutParams(95,ViewGroup.LayoutParams.MATCH_PARENT));
                    deletebutton.setPadding(100,20,100,0);
                    deletebutton.setBackground(getResources().getDrawable(R.drawable.deletebutton));
                    deletebutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            currentset--;
                            int id = v.getId();
                            allSetsStorage.deletesingle(Ename,donesets.get((id*2)+1),donesets.get(id*2).substring(0,donesets.get(id*2).length()-3));
                            donesets.set((id*2) + 1, "");
                            donesets.set(id*2, "") ;

                            onResume();

                        }});

                    doneset.addView(deletebutton);

                    //this is for recreating it onResume
                    donesets.add(reps.getText().toString() + " x ");
                    donesets.add(weight.getText().toString());




                    mainlayout.addView(doneset);

                }
            });


            //end the listeners

            return rootView;
        }




    }


    /**
     * A placeholder fragment containing a simple view.
     */
    //TODO: incorporate this into the other fragment, because when i go to this tab, it loses the memory of the other ones.
    //most importantly the sets
    public static class finishFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static finishFragment newInstance(int sectionNumber) {
            finishFragment fragment = new finishFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public finishFragment() {
        }

        public void askuser(final View rootView){
            AlertDialog.Builder ask = new AlertDialog.Builder(rootView.getContext());

            ask.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DatabaseHandler db = new DatabaseHandler(rootView.getContext());
                    ArrayList<doesSetStorage> totalworkout = allSetsStorage.getlist();

                    if(totalworkout.size() != 0) {
                        for (int i = 0; i < totalworkout.size(); i++) {
                            db.addset(totalworkout.get(i));
                        }
                        int newWorkout = db.addCompletedWorkout(totalworkout.get(0).Wname,totalworkout.get(0).date);
                        if(newWorkout == 1){
                            Toast toast = Toast.makeText(rootView.getContext(),"you already did this workout today!\nthese exercises will be combined with earlier workout", Toast.LENGTH_LONG);
                            LinearLayout toastLayout = (LinearLayout) toast.getView();
                            TextView toastTV = (TextView) toastLayout.getChildAt(0);
                            toastTV.setTextSize(20);
                            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                            toast.show();
                            //Toast.makeText(rootView.getContext(),"you already did this workout today!\nthese exercises will be combined with earlier workout",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(rootView.getContext(),"Workout successfully completed",Toast.LENGTH_LONG).show();
                        }
                        allSetsStorage.deleteall();
                        Intent intent = new Intent(rootView.getContext(),HomePage.class);
                        startActivity(intent);


                    }else{
                        Toast toast = Toast.makeText(rootView.getContext(),"you didnt do any sets! Try harder", Toast.LENGTH_SHORT);
                        LinearLayout toastLayout = (LinearLayout) toast.getView();
                        TextView toastTV = (TextView) toastLayout.getChildAt(0);
                        toastTV.setTextSize(20);
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 25);
                        toast.show();
                    }
                    return;
                }
            });
            ask.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            ask.setMessage("Great work today!\nSure your done?");
            ask.create();
            ask.show();
            return;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_finish_workout, container, false);

            //start the listeners

            Button submit = (Button) rootView.findViewById(R.id.submitButton);
            //send it to a confirmation dialog
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    askuser(rootView);

                }
            });
            return rootView;
        }

    }

}
