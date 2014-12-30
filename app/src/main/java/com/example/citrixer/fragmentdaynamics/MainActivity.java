package com.example.citrixer.fragmentdaynamics;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements MyListFragment.OnItemSelectedListener {

    private final String TAG = "Fragment-Sample";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate() [MainActivity]");

        setContentView(R.layout.activity_main);

        if (isPortrait()) { // In the portrait mode
            Log.i(TAG, "Portrait Mode");
            MyListFragment listFragment = new MyListFragment();

            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                            R.animator.card_flip_left_in, R.animator.card_flip_left_out)
                    .replace(R.id.listcontainer, listFragment)
                    .commit();
        } else {
            Log.i(TAG, "Landscape Mode");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, getClass().getSimpleName() + ":entered onResume() [MainActivity]");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onRssItemSelected(String link) {

        if (isPortrait()) { // In the portrait mode

            DetailFragment detailFragment = new DetailFragment();
            detailFragment.setText(link);
            getFragmentManager()
                    .beginTransaction()
                    // Replace the default fragment animations with animator resources representing
                    // rotations when switching to the back of the card, as well as animator
                    // resources representing rotations when flipping back to the front (e.g. when
                    // the system Back button is pressed).
                    .setCustomAnimations(
                            R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                            R.animator.card_flip_left_in, R.animator.card_flip_left_out)
                    .replace(R.id.listcontainer, detailFragment)
                    // Add this transaction to the back stack, allowing users to press Back
                    // to get to the previous Fragment.
                    .addToBackStack(null)
                    // Commit the transaction.
                    .commit();

            Toast.makeText(getApplicationContext(), "Show Time!", Toast.LENGTH_SHORT).show();
        } else {
            DetailFragment fragment = (DetailFragment) getFragmentManager().findFragmentById(R.id.detailFragment);
            if (fragment != null && fragment.isInLayout()) {
                fragment.updateText(link);
            }
        }
    }

    boolean isPortrait() {
        return !getResources().getBoolean(R.bool.dual_pane);
    }

    @Override
    public void onBackPressed() {

        // initialize variables
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        // check to see if stack is empty
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
            ft.commit();
            Toast.makeText(getApplicationContext(), "Pop Back!", Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
    }
}
