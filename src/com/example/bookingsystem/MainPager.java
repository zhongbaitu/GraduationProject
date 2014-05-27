package com.example.bookingsystem;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import com.bookingSystem.adapter.MyPagerAdapter;
import com.bookingSystem.effect.ZoomOutPageTransformer;

public class MainPager extends Activity {

	private ViewPager viewPager;
	private List<View> list;
	private View mainView; // 主界面
	private View commentView; // 评论/畅谈 界面
	private View bookView; // 查看自己的预约界面
	public static ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_viewpager);
		init();
		bind();
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		// Get the SearchView and set the searchable configuration
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		final SearchView searchView = (SearchView) menu.findItem(
				R.id.action_search).getActionView();
		// Assumes current activity is the searchable activity
		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(getComponentName()));
		searchView.setIconifiedByDefault(false); // Do not iconify the widget;
													// expand it by default
		searchView.setSubmitButtonEnabled(true);
		searchView.setOnSearchClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Intent intent=new Intent(MainPager.this,
				// SearchResultsActivity.class);
				// startActivity(intent);

			}
		});
		searchView.setIconifiedByDefault(true);

		searchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				searchView.setIconified(true);
				return true;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				System.out.println("onQueryTextChange");
				return true;
			}
		});

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			startActivity(new Intent(this, Location.class));
			break;
		}
		return true;
	}

	@SuppressLint("NewApi")
	private void init() {
		list = new ArrayList<View>();
		viewPager = (ViewPager) this.findViewById(R.id.main_viewpager);
		mainView = getLayoutInflater().inflate(R.layout.main, null);
		commentView = getLayoutInflater()
				.inflate(R.layout.comment_layout, null);
		bookView = getLayoutInflater().inflate(R.layout.order, null);
		list.add(commentView);
		list.add(mainView);
		list.add(bookView);

		new Main(this, mainView);
		new Comment(this, commentView);
		new Order(this,bookView);

		actionBar = getActionBar();
		actionBar.setBackgroundDrawable(this.getResources().getDrawable(
				R.drawable.actionbar_background));
		actionBar.setTitle("未知");
		actionBar.setHomeButtonEnabled(true);
		actionBar.setIcon(R.drawable.ic_action_place);

	}

	private void bind() {
		viewPager.setAdapter(new MyPagerAdapter(list));
		viewPager.setCurrentItem(1);
		viewPager.setPageTransformer(false, new ZoomOutPageTransformer());
	}

}