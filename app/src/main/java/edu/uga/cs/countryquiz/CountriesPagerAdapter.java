package edu.uga.cs.countryquiz;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class CountriesPagerAdapter extends FragmentStateAdapter {
    public CountriesPagerAdapter (FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }
    @Override
    public Fragment createFragment (int position) {
        return QuizFragment.newInstance(position);
    }
    @Override
    public int getItemCount () {
        return QuizFragment.getNumberOfCountries();
    }

}
