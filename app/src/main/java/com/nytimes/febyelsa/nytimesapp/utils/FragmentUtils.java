package com.nytimes.febyelsa.nytimesapp.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class FragmentUtils {
    private FragmentUtils() {
        // Private constructor to hide the implicit one
    }


    public static void replaceFragment(AppCompatActivity activity, Fragment fragment, int id, boolean addToBackStack) {

        if (null == activity)
            return;

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.setCustomAnimations(0, 0);

        if (addToBackStack)
            transaction.addToBackStack(fragment.getClass().getCanonicalName());

        transaction.replace(id, fragment, fragment.getClass().getCanonicalName());
        transaction.commit();

    }
}
