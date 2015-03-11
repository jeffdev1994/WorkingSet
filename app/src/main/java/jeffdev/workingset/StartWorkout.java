package jeffdev.workingset;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


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
            Intent intent = new Intent(this,StartWorkout_choose.class);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_workout);


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
        if (id == R.id.action_settings) {
            return true;
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
            return PlaceholderFragment.newInstance(position + 1,exercises.get(position));
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
        //makeupStorage makeup;
        String Ename;
        String Wname;
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber,makeupStorage EandWnames) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            fragment.setinfo(EandWnames.Ename,EandWnames.Wname);
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        public void setinfo(String Ename, String Wname) {
            this.Ename = Ename;
            this.Wname = Wname;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_start_workout, container, false);

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
                            editText.setText("0");
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
                        editText.setText("0");
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
                        editText.setText("0");
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
                    //Log.d("exercise",Ename);
                    EditText weight = (EditText) rootView.findViewById(R.id.weight);
                    EditText reps = (EditText) rootView.findViewById(R.id.reps);

                    //add the set to the array in onCreate
                    doesSetStorage theSet = new doesSetStorage();
                    theSet.Ename = Ename;
                    theSet.Wname = Wname;
                    theSet.reps = Integer.parseInt(reps.getText().toString());
                    theSet.weight = Integer.parseInt(weight.getText().toString());
                    theSet.date = date;
                    allSetsStorage.addset(theSet);




                    //set up the new linearlayout that tells them about their set
                    LinearLayout mainlayout = (LinearLayout) rootView.findViewById(R.id.main_layout);

                    LinearLayout doneset = new LinearLayout(rootView.getContext());
                    doneset.setOrientation(LinearLayout.HORIZONTAL);
                    doneset.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                    doneset.setPadding(0,0,0,15);

                    TextView setnum = new TextView(rootView.getContext());
                    setnum.setText("set " + currentset);
                    setnum.setTextSize(25);
                    currentset++;

                    doneset.addView(setnum);

                    TextView setreps = new TextView(rootView.getContext());
                    setreps.setText(reps.getText().toString() + " x ");
                    setreps.setTextSize(25);
                    setreps.setPadding(200,0,0,0);

                    doneset.addView(setreps);

                    TextView setweight = new TextView(rootView.getContext());
                    setweight.setText(weight.getText().toString());
                    setweight.setTextSize(25);

                    doneset.addView(setweight);


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


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_finish_workout, container, false);

            //start the listeners

            Button submit = (Button) rootView.findViewById(R.id.submitButton);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<doesSetStorage> totalworkout = allSetsStorage.getlist();
                    //i have all the exercises that were made, just need to add them to the db

                }
            });
            return rootView;
        }

    }

}
