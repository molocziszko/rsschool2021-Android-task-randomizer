package com.rsschool.android2021;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements OnOutputSender, OnInputSender {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openFirstFragment(0);
    }

    /**
     *
     * @param previousNumber    the random generated number from previous button click or 0 if app starts
     */
    private void openFirstFragment(int previousNumber) {
        final Fragment firstFragment = FirstFragment.newInstance(previousNumber);
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction
                .replace(R.id.container, firstFragment)
                .commit();
    }

    /**
     *
     * @param min   value that serves as a start point of the random generated range
     * @param max   value that serves as an end point of the random generated range
     */
    private void openSecondFragment(int min, int max) {
        final Fragment secondFragment = SecondFragment.newInstance(min, max);
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction
                .replace(R.id.container, secondFragment, TAG)
                .commit();
    }

    @Override
    public void sendOutput(int data) {
        openFirstFragment(data);
    }

    @Override
    public void sendInput(int firstInput, int secondInput) {
        openSecondFragment(firstInput, secondInput);
    }
}
