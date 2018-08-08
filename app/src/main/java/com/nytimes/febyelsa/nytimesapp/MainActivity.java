package com.nytimes.febyelsa.nytimesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nytimes.febyelsa.nytimesapp.ui.fragment.ArticleListFragment;
import com.nytimes.febyelsa.nytimesapp.utils.FragmentUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentUtils.replaceFragment(this, ArticleListFragment.newInstance(), R.id.fragContainer, false);

    }
}
