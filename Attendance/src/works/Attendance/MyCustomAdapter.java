package works.Attendance;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;

public class MyCustomAdapter extends ArrayAdapter<ApplicationInfo>  {

private List<ApplicationInfo> appInfoList;
private LayoutInflater mInflater;
private PackageManager pm;
ArrayList<Boolean> positionArray;
private Context ctx;
int[] visiblePosArray;
private volatile int positionCheck; 

public MyCustomAdapter(Context context, List<ApplicationInfo> myList) {
    super(context, NO_SELECTION);
    appInfoList = myList;
    ctx=context;
    mInflater =     (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    pm = context.getPackageManager();

    positionArray = new ArrayList<Boolean>(myList.size());
    for(int i =0;i<myList.size();i++){
        positionArray.add(false);
    }
}
@Override
public int getCount() {
    // TODO Auto-generated method stub
    return appInfoList.size();
}

@Override
public View getView(final int position, View convertView, ViewGroup parent) {

    View row = convertView;
    Holder holder = null;

    if(row==null){
        row = mInflater.inflate(R.layout.list_row, null); 
        //  visiblePosArray[position%visiblePosArray.length]=position;
        holder = new Holder();
        holder.tv = (TextView)row.findViewById(R.id.listrowtext);

        holder.ckbox =(CheckBox)row.findViewById(R.id.listrowcheckBox);

        row.setTag(holder);
    } else {

        holder = (Holder) convertView.getTag();
    holder.ckbox.setOnCheckedChangeListener(null);

    }

    holder.ckbox.setFocusable(false);
    holder.ckbox.setChecked(positionArray.get(position));
    holder.ckbox.setText(appInfoList.get(position).loadLabel(pm));
    holder.ckbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked ){
            System.out.println(position+"--- :)");
                positionArray.set(position, true);

            }else
                positionArray.set(position, false);
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