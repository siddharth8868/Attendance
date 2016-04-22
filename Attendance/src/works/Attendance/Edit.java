package works.Attendance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class Edit extends Activity {
	
	EditText dataf,topic;
	ArrayList<String> datas;
	ListView lv;
	Menu menu;
	ListAdapter1 adapter;
	Spinner spclass,classno,datespinner,sp;
	ArrayList<String> al,al2;
	ArrayAdapter<String> ad,ad2,ad3;
	String spitem;
	Button ok,date,from,to;
	MediaRecorder mrecorder;
	static final int check=111;
	private static final int DATE_DIALOG_ID = 0;
	private static final int FROM_TIME = 1;
	private static final int TO_TIME = 2;
	int total,pre,ab,cal=0,id;
	
	//database
	SQLiteDatabase db;
	MyDBHelper helper;
	Cursor cr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.take);
		topic=(EditText)findViewById(R.id.taketopic);
		spclass=(Spinner)findViewById(R.id.takespinner1);
		classno=(Spinner)findViewById(R.id.noofperiods);
		datespinner=(Spinner)findViewById(R.id.takedatespinner2);
		datespinner.setVisibility(View.GONE);
		ok=(Button)findViewById(R.id.takeok);
		date=(Button)findViewById(R.id.takedatebutton);
		from=(Button)findViewById(R.id.takefrom);
		to=(Button)findViewById(R.id.taketo);
		lv=(ListView)findViewById(R.id.takelistview);
		al=new ArrayList<String>();
		al2=new ArrayList<String>();
		datas=new ArrayList<String>();
		ad2=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,al2);
		ad2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ad3=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datas);
		access();
		
		Intent i =getIntent();
		date.setText(i.getStringExtra("date"));
		from.setText(i.getStringExtra("from"));
		to.setText(i.getStringExtra("to"));
		topic.setText(i.getStringExtra("topic"));
		id=i.getIntExtra("id",1);
		ArrayList<String> sid= new ArrayList<String>();
		sid.add(i.getStringExtra("class"));
		ad=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,sid);
		spclass.setAdapter(ad);
		String sts= (String) spclass.getSelectedItem();
		//database
        db=SQLiteDatabase.openOrCreateDatabase("/sdcard/Attendance/database.db",null);
        Cursor cr = db.rawQuery("select numbers from Main where class='"+ (String)spclass.getSelectedItem()+"'", null);
        al.clear();
        if(cr!=null)
        {
        	if(cr.moveToFirst())
        	{
        		do{
        			String pp= cr.getString(cr.getColumnIndex("numbers"));
        					String[] pps=pp.split(",");
        					for(int ii=0;ii<pps.length;ii++)
        					{
        						al.add(pps[ii]);
        					}
        		}while(cr.moveToNext());
        	}
        }
        
        cr=db.rawQuery("select dat from "+sts+" where id="+id,null);
        if(cr!=null)
        {
        	if(cr.moveToFirst())
        	{
        		do{
        			String pp= cr.getString(cr.getColumnIndex("dat"));
        					String[] pps=pp.split(",");
        					for(int i2=0;i2<pps.length;i2++)
        					{
        						al2.add(pps[i2]);
        					}
        		}while(cr.moveToNext());
        	}
        }
        
        adapter= new ListAdapter1(this,id,ab,al,al2);
        lv.setAdapter(adapter);

	}
	
	private void unaccess() {
		// TODO Auto-generated method stub
		ok.setEnabled(false);
		date.setEnabled(false);
		from.setEnabled(false);
		to.setEnabled(false);
		lv.setOnLongClickListener(null);
		topic.setEnabled(false);
		classno.setEnabled(false);
		
		date.setTextColor(Color.WHITE);
		from.setTextColor(Color.WHITE);
		to.setTextColor(Color.WHITE);
		topic.setTextColor(Color.WHITE);
		
	}
	
	private void access() {
		// TODO Auto-generated method stub
		ok.setEnabled(true);
		date.setEnabled(true);
		from.setEnabled(true);
		to.setEnabled(true);
		topic.setEnabled(true);
		classno.setEnabled(true);
		date.setTextColor(Color.BLACK);
		from.setTextColor(Color.BLACK);
		to.setTextColor(Color.BLACK);
		topic.setTextColor(Color.BLACK);
		
		
		
	}


   


    public void closes() {
		// TODO Auto-generated method stub
		this.finish();
	}

    
    
    /*
    public void speak(View v)
	{

		Intent i=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
    	i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
    	i.putExtra(RecognizerIntent.EXTRA_PROMPT,"speakout guy");
    	startActivityForResult(i,check);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	if(requestCode==check && resultCode==RESULT_OK)
    	{
    		ArrayList<String> al=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
    		//String s=al.get(0);
    		datas.add(al.get(0));
    		lv.setAdapter(ad3);
    	}
    	super.onActivityResult(requestCode, resultCode, data);
    }
    
    */

    
    
    public void ok(View v)
	{
    	Log.e("sgq","select total from Main where class='"+spclass.getSelectedItem()+"'");
		cr=db.rawQuery("select total from Main where class='"+spclass.getSelectedItem()+"'", null);
		if(cr!=null)
	       {
			Log.e("msq","cr not null");
	    	   if(cr.moveToFirst())
	    	   {
	    		   Log.e("msq","went into if");
	    		   do{
	    			   Log.e("msq","went into do");
	    			 total=cr.getInt(cr.getColumnIndex("total"));
	    		   }while(cr.moveToNext());
	    	   }
	       }
		
		pre=adapter.selectedItemsSize();
		ab=total-pre;
		
		AlertDialog.Builder dialog=new AlertDialog.Builder(this);
		dialog.setMessage("class:"+spclass.getSelectedItem()+"\ntotal   ="+total+"\npresent="+pre+"\nabsent ="+ab+"");
		dialog.setTitle("Summary");
		dialog.setCancelable(true);
		//Positive Button
		dialog.setPositiveButton("update",
				new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface d,int id){
					savedata();
			}

			private void savedata() {
				
				String str = "";
				ArrayList<String> ressave = new ArrayList<String>();
				ressave = adapter.getSelected();
			
				 ArrayList<Integer> intarray= new ArrayList<Integer>();
				 for(int k=0 ; k< ressave.size();k++ )
				 {
					 intarray.add(Integer.parseInt(ressave.get(k)));
				 } 
				 	Collections.sort(intarray);
					 
				    for (int i = 0;i<intarray.size(); i++) {
				        str = str+intarray.get(i);
				        // Do not append comma at the end of last element
				        if(i<intarray.size()-1){
				            str = str+",";
				        }
				    }
				
				 try{							
						 ContentValues cv = new ContentValues();
						 cv.put("dat", str);
					     cv.put("present",pre);   
						 cv.put("absent",ab);
						 cv.put("time",date.getText().toString()+"\n"+from.getText().toString()+"  "+to.getText().toString());
						 cv.put("date",date.getText().toString());
						 cv.put("froms",from.getText().toString());
						 cv.put("tos",to.getText().toString());
						 cv.put("topic",topic.getText().toString());
						 cv.put("periods",(String) classno.getSelectedItem());
						 cv.put("ssort", lasttwo());
						 cv.put("isort",1);
						 
						 //String sps=(String) sp.getSelectedItem();
						 db.update((String)spclass.getSelectedItem(), cv,"id="+id+"", new String []{});
						 makearrange();
						 unaccess();
						 Toast.makeText(getApplicationContext(),"updated successfully",Toast.LENGTH_SHORT).show();
						 close();
					  	}
					  catch (Exception e) {
					  	 Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
					  	}

			}
		});

		dialog.setNegativeButton("No",new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface d,int id){
			}
		});
		dialog.show();
		
	}
    
    public void close()
    {
    	this.finish();
    }
    
    private String lasttwo() {
		
    	String [] items;
		StringBuffer da=new StringBuffer("");

	      items =date.getText().toString().split("-");
	      Log.v("split",""+items[1]+" "+items[0]);
	      Log.v("before update","0");
	      da.append(items[2]);
	      da.append(items[1]);
	      da.append(items[0]);
		String tim=from.getText().toString();
		Log.v("date",""+da);
		Log.v("if=",""+tim);
		
		Log.v("if=",""+tim.charAt(6));
		if(tim.charAt(6)=='P')
		{
			Log.v("if","got p");
			
			da.append("1");
			Log.v("datepm",""+da);
		}
		else{
			da.append("0");
			Log.v("dateam",""+da);
		}
		Log.v("substring",""+tim.substring(0,2));
			if(tim.substring(0,2).equalsIgnoreCase("12"))
			{
				tim="00"+tim.subSequence(2,5);
			}
			else{
				tim=(String) tim.subSequence(0,5);
			}
			Log.v("final tim=",""+tim);
			da.append(tim);
			Log.v("final da=",""+da);
			da.deleteCharAt(11);
			Log.v("final =",""+da.toString());
			return da.toString();
			
		
		
	}

    
    private void makearrange() {
		
		int i=1;
		try {
			cr=db.rawQuery("select ssort from "+spclass.getSelectedItem()+" ORDER BY ssort", null);
			if(cr!=null)
			{
				if(cr.moveToFirst())
					do {
						String pp=cr.getString(cr.getColumnIndex("ssort"));
						Log.v("main",""+pp);
						Log.v("main2",""+"update "+spclass.getSelectedItem()+" set isort="+i+" where ssort='"+pp+"'");
						db.execSQL("update "+spclass.getSelectedItem()+" set isort="+i+" where ssort='"+pp+"'");
						Log.v("main3",""+pp);
						
						i++;
					} while (cr.moveToNext());
			}
		} catch (Exception e) {
			Log.v("error",""+e.getMessage());
			// TODO: handle exception
		}
		
		
	}
    
    
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    	db.close();
    }

	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		//database
        helper=new MyDBHelper(this);
        db=SQLiteDatabase.openOrCreateDatabase("/sdcard/Attendance/database.db",null);
	}
	
	 @Override
		public boolean onCreateOptionsMenu(Menu menu) {
			menu.add("edit");
			menu.add("details");
			menu.add("share");
			this.menu=menu;
			return true;
		}
	    
	    
	 


		
		
public void datedialog(View v){
			
			showDialog(DATE_DIALOG_ID);
		}
		public void fromtimedialog(View v){
			
			showDialog(FROM_TIME);
		}
		public void totimedialog(View v){
	
			showDialog(TO_TIME);
		}
		
		@Override
		protected Dialog onCreateDialog(int id, Bundle args) {
			// TODO Auto-generated method stub
			Calendar c = Calendar.getInstance();
			switch (id) {
			case DATE_DIALOG_ID:
			   return new DatePickerDialog(this, 
					   datePickerListener, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
			case FROM_TIME:
				return new TimePickerDialog(this, 
                        fromTimePickerListener, c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),false);
			case TO_TIME:
				return new TimePickerDialog(this, 
                        toTimePickerListener, c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),false);
			}
			return null;
		}
		
		
		
		private DatePickerDialog.OnDateSetListener datePickerListener 
        = new DatePickerDialog.OnDateSetListener() {
			
			@Override
			public void onDateSet(DatePicker view, int y, int m,int d) {
				// TODO Auto-generated method stub
				if(m<10)
				{
					date.setText(d+"-0"+m+"-"+y);
				}
				else{
				date.setText(d+"-"+m+"-"+y);
				}
				
			}
		};
		
		
		private TimePickerDialog.OnTimeSetListener fromTimePickerListener = 
	            new TimePickerDialog.OnTimeSetListener() {
					
					@Override
					public void onTimeSet(TimePicker view, int h, int m) {
						// TODO Auto-generated method stub
						from.setText(updateTime(h, m));
					}
				};
				
		private TimePickerDialog.OnTimeSetListener toTimePickerListener = 
	            new TimePickerDialog.OnTimeSetListener() {
					
					@Override
					public void onTimeSet(TimePicker view, int h, int m) {
						// TODO Auto-generated method stub
						to.setText(updateTime(h, m));
					}
				};
		
public String updateTime(int hours, int mins) {
String timeSet = "",aTime = null;
if (hours > 12) {
	hours-= 12;
timeSet = "PM";
} else if (hours == 0) {
hours += 12;
timeSet = "AM";
} else if (hours == 12)
timeSet = "PM";
else
timeSet = "AM";
String minutes = "";
if (mins < 10)
minutes = "0" + mins;
else
minutes = String.valueOf(mins);	
// Append in a StringBuilder
if(hours<10)
{
	aTime = new StringBuilder().append("0").append(hours).append(':').append(minutes).append(" ").append(timeSet).toString();
}
else{
aTime = new StringBuilder().append(hours).append(':').append(minutes).append(" ").append(timeSet).toString();
}
return aTime;
	    
}

}
