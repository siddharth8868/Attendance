package works.Attendance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class Take extends Activity {
	
	EditText dataf,ed2,topic;
	ArrayList<String> datas;
	ListView lv;
	Spinner sp,datesp,classno;
	ArrayList<String> al;
	ArrayAdapter<String> ad,ad2;
	String spitem;
	Button speak,date,from,to;
	MediaRecorder mrecorder;
	static final int check=111;
	private static final int DATE_DIALOG_ID = 0;
	private static final int FROM_TIME = 1;
	private static final int TO_TIME = 2;
	int total,pre,ab,cal=0;
	
	//database
	SQLiteDatabase db;
	Cursor cr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.take);
		topic=(EditText)findViewById(R.id.taketopic);
		sp=(Spinner)findViewById(R.id.takespinner1);
		datesp=(Spinner)findViewById(R.id.takedatespinner2);
		classno=(Spinner)findViewById(R.id.noofperiods);
		speak=(Button)findViewById(R.id.takespeak);
		date=(Button)findViewById(R.id.takedatebutton);
		from=(Button)findViewById(R.id.takefrom);
		to=(Button)findViewById(R.id.taketo);
		lv=(ListView)findViewById(R.id.takelistview);
		lv.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		lv.setStackFromBottom(true);
		al=new ArrayList<String>();
		datas=new ArrayList<String>();
		
		ad=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,al);
		ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ad2=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datas);
		lv.setAdapter(ad2);
		
		datesp.setVisibility(View.GONE);
		
		assign();
		
		//database
        db=SQLiteDatabase.openOrCreateDatabase("/sdcard/Attendance/database.db",null);
		
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub

				final String s = (String) lv.getItemAtPosition(arg2);
				dialog(s);
				return false;
			}
		});
		
		sp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				spitem=(String)sp.getItemAtPosition(arg2);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
        setlist();        
        
	}
	
	 @SuppressLint("SimpleDateFormat")
	private void assign() {
		 
 		 java.util.Date d=new java.util.Date();
		 SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy"); 
 		 date.setText(df.format(d));
 		df = new SimpleDateFormat("hh:mm a");
 		from.setText(df.format(d));
 		to.setText(df.format(d));
		
	}

	@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			//menu.add("edit");
			return true;
		}
	    
	    
	    @Override
		public boolean onOptionsItemSelected(MenuItem item) {
	    	if(item.getTitle()=="edit")
	    	{
	    		//Intent i=new Intent(getApplicationContext(),Correction.class);
	    		
	    	}
			return false;
	    }
	    
	    

	
	
	public void setlist() {
		
	       
		 al.clear();
		 try{
		 cr=db.rawQuery("select * from Main", null);
	       if(cr!=null)
	       {
	    	   if(cr.moveToFirst())
	    	   {
	    		   do{
	    			 al.add(cr.getString(cr.getColumnIndex("class")));
	    		   }while(cr.moveToNext());
	    	   }
	    	   else
	    	   {
	    		   notfound();
	    	   }
	       }
	       sp.setAdapter(ad);
		 }
		 catch (Exception e) {
			// TODO: handle exception
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

	public void speak(View v)
	{

		Intent i=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
    	i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
    	i.putExtra(RecognizerIntent.EXTRA_PROMPT,"speakout guy");
    	startActivityForResult(i,check);
    }
    
    @SuppressWarnings("unused")
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	if(requestCode==check && resultCode==RESULT_OK)
    	{
    		ArrayList<String> al=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
    		//String s=al.get(0);
    		try
    		{
    		Integer in=Integer.parseInt(al.get(0));
    		}
    		catch(Exception e){
    			Toast.makeText(getApplicationContext(),"sorry, it is not an integer",200);
    		}
    		datas.add(al.get(0));
    		lv.setAdapter(ad2);
    	}
    	super.onActivityResult(requestCode, resultCode, data);
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
		    		lv.setAdapter(ad2);
					
				}
    		});
    		dialog.setNegativeButton("No",new DialogInterface.OnClickListener() {
    			@Override
    			public void onClick(DialogInterface d,int id){
    			}
    		});
    		dialog.show();
    	}
    
    	public void enter(View v)
    	{
    		if(ed2.getText().toString().equalsIgnoreCase(""))
    		{
    		}
    		else
    		{
    			datas.add(ed2.getText().toString().trim());
        		lv.setAdapter(ad2);
    			ed2.setText("");
    		}
    	}
    	
    	public void ok(View v)
    	{
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
    		dialog.setPositiveButton("save",
    				new DialogInterface.OnClickListener(){
    			@Override
    			public void onClick(DialogInterface d,int id){
    					savedata();
				}

				private void savedata() {
					
					 String str = "";
					 ArrayList<Integer> intarray= new ArrayList<Integer>();
					 for(int k=0 ; k< datas.size();k++ )
					 {
						 intarray.add(Integer.parseInt(datas.get(k)));
					 } 
					 	Collections.sort(intarray);
						 
					    for (int i = 0;i<intarray.size(); i++) {
					        str = str+intarray.get(i);
					        // Do not append comma at the end of last element
					        if(i<intarray.size()-1){
					            str = str+",";
					        }
					    }
					       
					SQLiteStatement st;
					
					st=db.compileStatement("insert into "+sp.getSelectedItem()+" values(?,?,?,?,?,?,?,?,?,?,?,?)");
					st.bindString(2,str);
					st.bindLong(3,pre);
					st.bindLong(4,ab);
					st.bindString(5,date.getText().toString()+"\n"+from.getText().toString()+"  "+to.getText().toString());
					//st.bindString(5,date.getText().toString()+"  "+from.getText().toString()+"  "+to.getText().toString());
					st.bindString(6,date.getText().toString());
					st.bindString(7,from.getText().toString());
					st.bindString(8,to.getText().toString());
					st.bindString(9,topic.getText().toString());
					st.bindString(10,(String) classno.getSelectedItem());
					st.bindString(11,lasttwo());
					st.bindLong(12, 1);
					try{
						st.executeInsert();
						makearrange();
						closes();
					}
					catch (Exception e) {
						// TODO: handle exception
					}
					
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
				
				
    		});

    		dialog.setNegativeButton("No",new DialogInterface.OnClickListener() {
    			@Override
    			public void onClick(DialogInterface d,int id){
    			}
    		});
    		dialog.show();
    	
    		
    	}
    	
    	
		@SuppressLint("SimpleDateFormat")
		public String dateConverter()
    	{
    		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy  HH:mm:ss"); 
    		java.util.Date date=new java.util.Date();
			//Toast.makeText(getApplicationContext(), dateFormat.format(date), 100).show();
			return dateFormat.format(date);
    		
    	}

		protected void closes() {
			// TODO Auto-generated method stub
			this.finish();
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
	        db=SQLiteDatabase.openOrCreateDatabase("/sdcard/Attendance/database.db",null);
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


