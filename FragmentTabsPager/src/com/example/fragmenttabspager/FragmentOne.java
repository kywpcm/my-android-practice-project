package com.example.fragmenttabspager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentOne extends Fragment {

	private View wordGroupView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		wordGroupView = inflater.inflate(R.layout.word_group_list, container, false);
		return wordGroupView;
	}
}
