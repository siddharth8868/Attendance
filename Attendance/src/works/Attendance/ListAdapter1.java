package works.Attendance;

import java.util.ArrayList;
import java.util.List;

import works.Attendance.ListAdapter.Holder;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class ListAdapter1 extends ArrayAdapter<String> {

ArrayList<String> selectedstring;
ArrayList<Boolean> positionArray;
private LayoutInflater mInflater;

public ListAdapter1(Context context, int resource, int textViewResourceId,List<String> objects ,ArrayList<String> selected) {
		super(context, resource, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		selectedstring =new ArrayList<String>();
		selectedstring = selected;
		positionArray = new ArrayList<Boolean>(objects.size());
	    for(int i =0;i<objects.size();i++){
	        positionArray.add(false);
	    }
	}

public int selectedItemsSize(){
	return selectedstring.size();
}

public ArrayList<String>  getSelected(){
	return selectedstring;
}

@Override
public View getView(final int position, View convertView, ViewGroup parent) {

    View row = convertView; 
	Holder holder = null;

    if (row == null) {
    	  row = mInflater.inflate(R.layout.list_row, null); 
    	holder = new Holder();
        holder.tv = (TextView)row.findViewById(R.id.listrowtext);

        holder.ckbox =(CheckBox)row.findViewById(R.id.listrowcheckBox);

        row.setTag(holder);

    }
    else{
    	holder = (Holder) convertView.getTag();
        holder.ckbox.setOnCheckedChangeListener(null);
    }

    //Item p = getItem(position);

    final String s=getItem(position);   
    int cnt=0;
    for(int i=0;i<selectedItemsSize();i++)
    {
    	if(s.equals(selectedstring.get(i)))
    	{
    		cnt=1;
    		break;
    	}
    }
    
    
    	holder.tv.setText(s);
    	if(cnt==1)
        {
        	//holder.ckbox.setChecked(true);
        	positionArray.set(position,true);
        }
    	holder.ckbox.setChecked(positionArray.get(position));
    	holder.ckbox.setFocusable(false); 
    	holder.ckbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					selectedstring.add(s);
					positionArray.set(position, true);
					for(int i=0;i<selectedItemsSize();i++)
					{
						Log.e("values",selectedstring.get(i));
					}
                }else{
                	selectedstring.remove(s);
                	positionArray.set(position, false);
                	for(int i=0;i<selectedItemsSize();i++)
					{
						Log.e("values",selectedstring.get(i));
					}
                }
			}
		});

    return row;
}

static class Holder
{
    TextView tv;
    CheckBox ckbox;

}

}