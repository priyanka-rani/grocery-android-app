package com.myapp.grocerli.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.glide.slider.library.slidertypes.DefaultSliderView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myapp.grocerli.R;
import com.myapp.grocerli.databinding.ActivityMainBinding;
import com.myapp.grocerli.ui.cart.CartActivity;
import com.myapp.grocerli.ui.login.LoginActivity;
import com.myapp.grocerli.ui.profile.ProfileActivity;
import com.myapp.grocerli.ui.order.OrderActivity;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        binding.setViewModel(mainViewModel);
        binding.setLifecycleOwner(this);
        FloatingActionButton fab = binding.fab;

        ArrayList<Integer> listImage = new ArrayList<>();
        listImage.add(R.drawable.banner1);
        listImage.add(R.drawable.banner2);
        listImage.add(R.drawable.banner3);
        listImage.add(R.drawable.banner4);
        listImage.add(R.drawable.banner5);
        listImage.add(R.drawable.banner6);
        listImage.add(R.drawable.banner7);
        listImage.add(R.drawable.banner8);
        listImage.add(R.drawable.banner9);
        listImage.add(R.drawable.banner10);
        for (int i = 0; i < listImage.size(); i++) {
            DefaultSliderView sliderView = new DefaultSliderView(this);
            // if you want show image only / without description text use DefaultSliderView instead

            // initialize SliderLayout
            sliderView.image(listImage.get(i));
            //add your extra information
            sliderView.bundle(new Bundle());
            binding.slider.addSlider(sliderView);
        }

        fab.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, CartActivity.class));
        });

        mainViewModel.getProductCategoryList().observe(this, productList -> {
            SectionsPagerAdapter sectionsPagerAdapter =
                    new SectionsPagerAdapter(MainActivity.this, getSupportFragmentManager(), productList);
            ViewPager viewPager = binding.viewPager;
            viewPager.setAdapter(sectionsPagerAdapter);
            binding.tabs.setupWithViewPager(viewPager);
        });
        binding.toolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuLogout:
                        mainViewModel.logoutUser();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        MainActivity.this.finish();
                        break;
                    case R.id.menuProfile:
                        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                        break;
                    case R.id.menuOrder:
                        startActivity(new Intent(MainActivity.this, OrderActivity.class));
                }
                return true;
            }
        });
    }
}