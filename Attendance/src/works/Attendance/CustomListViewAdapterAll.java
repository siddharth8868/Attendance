package works.Attendance;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomListViewAdapterAll extends ArrayAdapter<RowItemAll>{

	 Context context;
	
	public CustomListViewAdapterAll(Context context, int resourceId,
            List<RowItemAll> items) {
		super(context, resourceId, items);
		this.context = context;
	}
	
	private class ViewHolder {
        TextView txtdate;
        TextView txttopic;
        TextView txtperiods;
        TextView txtpeople;
        TextView txtpresent;
        TextView txtabsent;
        TextView txtpercent;
    }
	
	public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        RowItemAll rowItemAll = getItem(position);
 
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listitem_all, null);
            holder = new ViewHolder();
            holder.txtdate = (TextView) convertView.findViewById(R.id.allllistdate);
            holder.txttopic = (TextView) convertView.findViewById(R.id.alllisttopic);
            holder.txtperiods = (TextView) convertView.findViewById(R.id.alllistperiods);
            holder.txtpeople = (TextView) convertView.findViewById(R.id.alllistpeople);
            holder.txtpresent = (TextView) convertView.findViewById(R.id.alllistpresent);
            holder.txtabsent = (TextView) convertView.findViewById(R.id.alllistabsent);
            holder.txtpercent = (TextView) convertView.findViewById(R.id.alllistpercent);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
 
        holder.txtdate.setText(rowItemAll.getDate());
        holder.txttopic.setText(rowItemAll.getTopic());
        holder.txtperiods.setText(rowItemAll.getPeriods());
        holder.txtpeople.setText(rowItemAll.getPeople());
        holder.txtpresent.setText(rowItemAll.getPresent());
        holder.txtabsent.setText(rowItemAll.getAbscent());
        holder.txtpercent.setText(rowItemAll.getPercent());
 
        return convertView;
    }


}
