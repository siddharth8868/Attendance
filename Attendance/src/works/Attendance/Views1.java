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
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class Views1 extends Activity {
	
	EditText dataf,topic;
	ArrayList<String> datas;
	ListView lv;
	Menu menu;
	Spinner sp,datesp,classno;
	ArrayList<String> al,al2;
	ArrayAdapter<String> ad,ad2,ad3;
	String spitem;
	Button speak,ok,date,from,to;
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
		sp=(Spinner)findViewById(R.id.takespinner1);
		classno=(Spinner)findViewById(R.id.noofperiods);
		datesp=(Spinner)findViewById(R.id.takedatespinner2);
		speak=(Button)findViewById(R.id.takespeak);
		ok=(Button)findViewById(R.id.takeok);
		ok.setEnabled(true);
		ok.setText("Edit");
		date=(Button)findViewById(R.id.takedatebutton);
		from=(Button)findViewById(R.id.takefrom);
		to=(Button)findViewById(R.id.taketo);
		lv=(ListView)findViewById(R.id.takelistview);
		al=new ArrayList<String>();
		al2=new ArrayList<String>();
		datas=new ArrayList<String>();
		ad=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,al);
		ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ad2=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,al2);
		ad2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ad3=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datas);
		

		//database
        helper=new MyDBHelper(this);
        db=SQLiteDatabase.openOrCreateDatabase("/sdcard/Attendance/database.db",null);
		
		unaccess();
		

		
		sp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				spitem=(String)sp.getItemAtPosition(arg2);
				setDatespinner();
				setarraylist();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		datesp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				//spitem=(String)sp.getItemAtPosition(arg2);
				setarraylist();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
        setlist();
        setDatespinner();
        setarraylist();
	}
	
	private void unaccess() {
		// TODO Auto-generated method stub
		date.setEnabled(false);
		from.setEnabled(false);
		to.setEnabled(false);
		//speak.setEnabled(false);
		//lv.setEnabled(false);
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
		date.setEnabled(true);
		from.setEnabled(true);
		to.setEnabled(true);
		//speak.setEnabled(true);
		topic.setEnabled(true);
		classno.setEnabled(true);
		date.setTextColor(Color.BLACK);
		from.setTextColor(Color.BLACK);
		to.setTextColor(Color.BLACK);
		topic.setTextColor(Color.BLACK);
		
		//lv.setEnabled(true);
		
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub

				final String s=(String)lv.getItemAtPosition(arg2);
				dialog(s);
				return false;
			}
		});
		
		
	}

	protected void setarraylist() {
		
		String str = null;
		datas.clear();
		try{
		cr=db.rawQuery("select * from "+sp.getSelectedItem()+" where time='"+datesp.getSelectedItem()+"'", null);
		if(cr!=null)
	       {
	    	   if(cr.moveToFirst())
	    	   {
	    		   do{
	    			   Log.v("inside","1");
	    			 str=cr.getString(cr.getColumnIndex("dat"));
	    			 Log.v("inside","1");
	    			 id=cr.getInt(cr.getColumnIndex("id"));
	    			 date.setText(cr.getString(cr.getColumnIndex("date")));
	    			 from.setText(cr.getString(cr.getColumnIndex("froms")));
	    			 to.setText(cr.getString(cr.getColumnIndex("tos")));
	    			 Log.v("hehe",""+cr.getString(cr.getColumnIndex("topic")));
	    			 topic.setText(cr.getString(cr.getColumnIndex("topic")));
	    			 Log.v("inside","1");
	    			 String pts=cr.getString(cr.getColumnIndex("periods"));
	    			 Log.v("inside","1");
	    			 if(pts!=null)
	    			    classno.setSelection(Integer.parseInt(pts)-1);
	    			 else
	    				 classno.setSelection(0);
	    		   }while(cr.moveToNext());
	    	   }
	       }
		//str="hello,bye,sad";
		String [] items = null;
		if(!str.equals(""))
		{
	      items = str.split(",");
		}
		int i=0;
		
		while(i<items.length)
		{
			datas.add(items[i]);
			i++;
		}
		
		}catch (Exception e) {
			Log.v("er",""+e.getLocalizedMessage());
			Log.v("er",""+e.getMessage());
			//Toast.makeText(getApplicationContext(),e.getMessage(),100).show();
		}
		lv.setAdapter(ad3);
		Log.v("inside","2");
	}

	public void setlist() {
		
		 al.clear();
		 try{
		 cr=db.rawQuery("select * from Main", null);
	       if(cr!=null)
	       {
	    	   //Toast.makeText(getApplicationContext(),"1",100).show();
	    	   if(cr.moveToFirst())
	    	   {
	    		   do{
	    			   
	    			 al.add(cr.getString(cr.getColumnIndex("class")));
	    		   }while(cr.moveToNext());
	    		   
	    	   }
	    	   else
	    	   {
	    		   notfound();
		    	   //Toast.makeText(getApplicationContext(),"2",100).show();
	    	   }
	       }
	       sp.setAdapter(ad);
	       //set date spinner
	       
	       
		 }
		 catch (Exception e) {
			// TODO: handle exception
		}
	}
	
    private void setDatespinner() {

		 try{
			 al2.clear();
		 cr=db.rawQuery("select time from "+sp.getSelectedItem()+" order by isort", null);
	       if(cr!=null)
	       {
	    	   if(cr.moveToFirst())
	    	   {
	    		   do{
	    			   
	    			al2.add(cr.getString(cr.getColumnIndex("time")));
	    		   }while(cr.moveToNext());
	    		   
	    	   }
	    	   else
	    	   {

	    	   }
	       }
	       Collections.reverse(al2);
	      datesp.setAdapter(ad2);
	       
		 }
		 catch (Exception e) {
	    	  //Toast.makeText(getApplicationContext(),e.getMessage(),100).show();
		}
	}

	private void notfound() {
		// TODO Auto-generated method stub
    	AlertDialog.Builder dialog=
			new AlertDialog.Builder(this);
		dialog.setMessage("No classes available.\nAdd class first");
		//dialog.setTitle("Conformation Message");
		dialog.setCancelable(false);
		//Positive Button
		dialog.setPositiveButton("ok",
				new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface d,int id){
				closes();
			}
			
		});
		dialog.show();
	}
    
    public void closes() {
		// TODO Auto-generated method stub
		this.finish();
	}

	public void dialog(final String ss) {
    	
		AlertDialog.Builder dialog=
			new AlertDialog.Builder(this);
		dialog.setMessage("Do you want to delete?");
		dialog.setTitle("Conformation Message");
		dialog.setCancelable(false);
		//Positive Button
		dialog.setPositiveButton("Yes",
				new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface d,int id){
				delete(ss);
			}

			private void delete(String ss) {
				
				datas.remove(datas.indexOf(ss));
				lv.setAdapter(ad3);
				
			}
		});
		dialog.setNegativeButton("No",new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface d,int id){
			}
		});
		dialog.show();
	}
    
    
    
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

    
    
    public void ok(View v)
	{
    	Intent i = new Intent(this,Edit.class);
		String sq=(String)sp.getSelectedItem();
		Log.e("errop",sq);
		i.putExtra("class",sq);
		i.putExtra("date",date.getText());
		i.putExtra("from",from.getText());
		i.putExtra("to",to.getText());
		i.putExtra("topic",topic.getText().toString());
		i.putExtra("periods",Integer.parseInt((String)classno.getSelectedItem()));
		i.putExtra("id",id);
		startActivity(i);
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
			cr=db.rawQuery("select ssort from "+sp.getSelectedItem()+" ORDER BY ssort", null);
			if(cr!=null)
			{
				if(cr.moveToFirst())
					do {
						String pp=cr.getString(cr.getColumnIndex("ssort"));
						Log.v("main",""+pp);
						Log.v("main2",""+"update "+sp.getSelectedItem()+" set isort="+i+" where ssort='"+pp+"'");
						db.execSQL("update "+sp.getSelectedItem()+" set isort="+i+" where ssort='"+pp+"'");
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
        setDatespinner();
        setarraylist();
	}
	
	 @Override
		public boolean onCreateOptionsMenu(Menu menu) {
			menu.add("edit");
			menu.add("details");
			menu.add("share");
			this.menu=menu;
			return true;
		}
	    
	    
	    @Override
		public boolean onOptionsItemSelected(MenuItem item) {
	    	if(item.getTitle()=="edit")
	    	{
	    		Intent i = new Intent(this,Edit.class);
	    		String sq=(String)sp.getSelectedItem();
	    		Log.e("errop",sq);
	    		i.putExtra("class",sq);
	    		i.putExtra("date",date.getText());
	    		i.putExtra("from",from.getText());
	    		i.putExtra("to",to.getText());
	    		i.putExtra("topic",topic.getText().toString());
	    		i.putExtra("periods",Integer.parseInt((String)classno.getSelectedItem()));
	    		i.putExtra("id",id);
	    		startActivity(i);
	    	}
	    	else if(item.getTitle()=="details")
	    	{
	    		details();
	    	}
	    	else if(item.getTitle()=="share")
	    	{
	    		share();
	    	}
	    	
			return false;
	    }


		private void details() {
			// TODO Auto-generated method stub
			cr=db.rawQuery("select total from Main where class='"+sp.getSelectedItem()+"'", null);
			if(cr!=null)
		       {
		    	   if(cr.moveToFirst())
		    	   {
		    		   do{
		    			   
		    			 total=cr.getInt(cr.getColumnIndex("total"));
		    		   }while(cr.moveToNext());
		    	   }
		       }
			
			pre=datas.size();
			ab=total-pre;
			
			AlertDialog.Builder dialog=new AlertDialog.Builder(this);
			dialog.setMessage("class:"+sp.getSelectedItem()+"\ntotal   ="+total+"\npresent="+pre+"\nabsent ="+ab+"");
			dialog.setTitle("Summary");
			dialog.setCancelable(true);
			//Positive Button
			dialog.setPositiveButton("ok",
					new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface d,int id){		
				}
			});

			dialog.show();
		}
	    

		private void share() {
			// TODO Auto-generated method stub
			setarraylist();
			cr=db.rawQuery("select total from Main where class='"+sp.getSelectedItem()+"'", null);
			if(cr!=null)
		       {
		    	   if(cr.moveToFirst())
		    	   {
		    		   do{
		    			   
		    			 total=cr.getInt(cr.getColumnIndex("total"));
		    		   }while(cr.moveToNext());
		    	   }
		       }
			
			pre=datas.size();
			ab=total-pre;
			
			String str = "";
		    for (int i = 0;i<datas.size(); i++) {
		        str = str+datas.get(i);
		        // Do not append comma at the end of last element
		        if(i<datas.size()-1){
		            str = str+",";
		        }
		    }
		    
		    str="Class: "+sp.getSelectedItem()+" Attendence\n"+str+"\ntotal="+total+"\npresent="+pre+"\nabsent="+ab;
		    
		    	Intent intent = new Intent(Intent.ACTION_SEND);
		    	intent.setType("text/plain");
		    	intent.putExtra(Intent.EXTRA_SUBJECT,"Class: "+sp.getSelectedItem()+" Attendence");
		    	intent.putExtra(Intent.EXTRA_TEXT,str);
		    	startActivity(Intent.createChooser(intent, ""));

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
