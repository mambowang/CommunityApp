package com.jis.coommunity.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.jis.coommunity.R;


public class SportListAdapter extends BaseAdapter {

	private Activity activity;
	private int length;
	private boolean list_check[] = {false,false,true,false,true,false,true,false,false,false,false,false};
	private String name[] = {"All","American football","Badminton","Bandy","Baseball","Basketball","Bodybuilding","Boxing","Chess","Cricket","Curling","Field hockey"};
	private static LayoutInflater inflater=null;

	public SportListAdapter(Activity a) {
		activity = a;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	public int getCount() {
		return this.name.length;
	}
	public Object getItem(int position) {
		return this.name.length;
	}
	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if(convertView == null)	vi = inflater.inflate(R.layout.element_sportlist, null);
		TextView title = (TextView)vi.findViewById(R.id.title);
		final ImageView check = (ImageView) vi.findViewById(R.id.checkBox);
		if(list_check[position])check.setVisibility(View.VISIBLE);
		else check.setVisibility(View.INVISIBLE);
		title.setText(name[position]);
		return vi;
	}
	public void check(int position)
	{
		if(position==0) {
			Boolean flag = (list_check[0]) ? false : true;
			for (int i = 0; i < getCount(); i++)
				list_check[i] = flag;
		}
		else {list_check[position] = (list_check[position])?false:true;list_check[0] = false;}
	}
}