package works.Attendance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
//import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
//import android.widget.Toast;
import android.widget.ToggleButton;

public class Analysis extends Activity {
	
	int highid,lowid,lightval,periodval;
	Spinner sp1,sp2,sp3;
	ListView lv;
	EditText individual,percents;
	ToggleButton tbutton;
	List<RowItem> rowitem;
	List<RowItem> rowItems;
	List<RowItemAll> rowItemsAll;
	ArrayList<String> al1,al2,al3;
	ArrayAdapter<String> ad1,ad2,ad3;
	CustomListViewAdapter lvadapter;
	//database
		SQLiteDatabase db;
		Cursor cr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.analysis);
		
		lv=(ListView)findViewById(R.id.analistview);
		individual=(EditText)findViewById(R.id.anaedittext);
		percents=(EditText)findViewById(R.id.anapercentsedittext);
		tbutton = (ToggleButton) findViewById(R.id.toggleButton1);
		sp1=(Spinner)findViewById(R.id.anaclassspinner);
		sp2=(Spinner)findViewById(R.id.anafromspinner);
		sp3=(Spinner)findViewById(R.id.anatospinner);
		rowItems = new ArrayList<RowItem>();
		al1=new ArrayList<String>();
		al2=new ArrayList<String>();
		al3=new ArrayList<String>();
		ad1=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,al1);
		ad1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ad2=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,al2);
		ad2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ad3=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,al3);
		ad3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		lvadapter=new CustomListViewAdapter(getApplicationContext(), R.layout.list_item, rowitem);
		
		
		//database
        db=SQLiteDatabase.openOrCreateDatabase("/sdcard/Attendance/database.db",null);
        
        individual.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				setListView();
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
        
		sp1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Log.v("hello","sp1");
				//Toast.makeText(getApplicationContext(),"sp1",100).show();
				rowItems.clear();
				CustomListViewAdapter adapter = new CustomListViewAdapter(getApplicationContext(),
		                R.layout.list_item, rowItems);
		        lv.setAdapter(adapter);

				setDatespinner();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		sp2.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Log.v("hello","sp2");
				//Toast.makeText(getApplicationContext(),"sp2",100).show();
				setListView();

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		sp3.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Log.v("hello","sp3");
				//Toast.makeText(getApplicationContext(),"sp3",100).show();
				setListView();

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Log.v("hello","beforemaincall");
		//Toast.makeText(getApplicationContext(),"beforemaincall",100).show();
		setList();
		Log.v("hello","aftermaincall");
		//Toast.makeText(getApplicationContext(),"aftermaincall",100).show();

		
	}
	
	public void setList()
	{
		Log.v("hello","setlist-start");
		//Toast.makeText(getApplicationContext(),"setlist-start",100).show();
		 al1.clear();
		 try{
		 cr=db.rawQuery("select * from Main", null);
	       if(cr!=null)
	       {
	    	   //Toast.makeText(getApplicationContext(),"1",100).show();
	    	   if(cr.moveToFirst())
	    	   {
	    		   do{
	    			   
	    			 al1.add(cr.getString(cr.getColumnIndex("class")));
	    		   }while(cr.moveToNext());
	    		   
	    	   }
	    	   else
	    	   {
	    		   notfound();
		    	   //Toast.makeText(getApplicationContext(),"2",100).show();
	    	   }
	       }
	       sp1.setAdapter(ad1);
			Log.v("hello","setlist-stop");
			//Toast.makeText(getApplicationContext(),"setlist stop",100).show();
		 }
		 catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add("Details");
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		String it=item.getTitle().toString();
		if(it.equalsIgnoreCase("Details"))
		{
			if(!tbutton.isChecked())
			{
				detailsall();
			}
			else{
				detailsspecfic();
			}
		}
		return true;
	}
	
	private void detailsall() {
		
		AlertDialog.Builder dialog=new AlertDialog.Builder(this);
		int count=lv.getCount(),days=0;
		RowItemAll ri;
		if(count!=0)
		{
		int percent=0;
		for(int i=0;i<count;i++)
		{
			ri= rowItemsAll.get(i);
			days=days+Integer.parseInt(ri.getPeriods());
			percent=percent + Integer.parseInt(ri.getPercent())*Integer.parseInt(ri.getPeriods());
		}
		percent=percent/days;
		
    	
		dialog.setMessage("Total classes= "+days+"\nPresent % ="+percent);
		//dialog.setTitle("Conformation Message");
		dialog.setCancelable(false);
		//Positive Button
		dialog.setPositiveButton("ok",
				new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface d,int id){
			}
			
		});
		}
		else
		{
			dialog.setMessage("No classes available");
			dialog.setCancelable(false);
			//Positive Button
			dialog.setPositiveButton("ok",
					new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface d,int id){
				}
				
			});
		}
		dialog.show();
		
	}

	private void detailsspecfic() {
		// TODO Auto-generated method stub
		int percent=0;
		AlertDialog.Builder dialog=new AlertDialog.Builder(this);
		int count=lv.getCount();
		if(count!=0)
		{
		
		percent=(lightval*100/periodval);
		
    	
		dialog.setMessage("Total classes= "+periodval+"\nPresent="+lightval+"\nPercent % ="+percent);
		dialog.setTitle("Roll no:"+individual.getText().toString().trim());
		dialog.setCancelable(false);
		//Positive Button
		dialog.setPositiveButton("ok",
				new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface d,int id){
			}
			
		});
		}
		else
		{
			dialog.setMessage("No classes available");
			dialog.setCancelable(false);
			//Positive Button
			dialog.setPositiveButton("ok",
					new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface d,int id){
				}
				
			});
			percent=0;
		}
		dialog.show();
	}
	
	public void percentsspecific()
	{
		long percent=0;
		int days=lv.getCount();
		if(days!=0)
		{		
		percent=((long)lightval*100/(long)periodval);
		percents.setText(""+percent+"%");
		}
		else{
			percents.setText("");

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
	 
	 private void setDatespinner() {

		 Log.v("hello","datesetstart");
		 //Toast.makeText(getApplicationContext(),"datesetstart",100).show();
		 try{
			 al2.clear();
			 al3.clear();
		 cr=db.rawQuery("select time from "+sp1.getSelectedItem()+" order by isort", null);
	       if(cr!=null)
	       {
	    	   if(cr.moveToFirst())
	    	   {
	    		   do{
	    			   
	    			al2.add(cr.getString(cr.getColumnIndex("time")));
	    			al3.add(cr.getString(cr.getColumnIndex("time")));
	    		   }while(cr.moveToNext());
	    		   
	    	   }
	    	   else
	    	   {

	    	   }
	       }
	       Collections.reverse(al2);
	      sp2.setAdapter(ad2);
	      sp3.setAdapter(ad3);
	      Log.v("hello","datesetstop");
	      //Toast.makeText(getApplicationContext(),"datesetstop",100).show();
		 }
		 catch (Exception e) {
	    	  //Toast.makeText(getApplicationContext(),e.getMessage(),100).show();
		}
		
		 
	}
	 
 
/*	 
 public static final String[] titles = new String[] { "Strawberry",
         "Banana", "Orange", "Mixed" };

 public static final String[] descriptions = new String[] {
         "It is an aggregate accessory fruit",
         "It is the largest herbaceous flowering plant", "Citrus Fruit",
         "Mixed Fruits" };

 public static final Integer[] images = { R.drawable.ic_launcher,
         R.drawable.red1, R.drawable.ic_launcher, R.drawable.red1};

	 
	 private void setListView1() {
		 rowItems.clear();
	        for (int i = 0; i < titles.length; i++) {
	            RowItem item = new RowItem(images[i], titles[i], descriptions[i]);
	            rowItems.add(item);
	        }
	 
	        //lv= (ListView) findViewById(R.id.analistview);
	        CustomListViewAdapter adapter = new CustomListViewAdapter(this,
	                R.layout.list_item, rowItems);
	        lv.setAdapter(adapter);
	 }  */

	private void setListView() {

		try{
		 cr=db.rawQuery("select isort from "+sp1.getSelectedItem()+" where time='"+sp2.getSelectedItem()+"' or time='"+sp3.getSelectedItem()+"'", null);
  	   Log.v("hel1",""+cr.getCount());
	       if(cr!=null)
	       {
	    	   
	    	   if(cr.moveToFirst())
	    	   {
	    			highid=cr.getInt(cr.getColumnIndex("isort"));
	    			if(cr.moveToNext())
	    			{
	    			lowid=cr.getInt(cr.getColumnIndex("isort"));
	    			}
	    			else
	    			{
	    				lowid=highid;
	    			}
	    	   }
	    	   else
	    	   {

	    	   }
	    	   if(highid<lowid)
	    	   {
	    		   int t=highid;
	    		   highid=lowid;
	    		   lowid=t;
	    	   }
	    	   
	       }
		 }
		 catch (Exception e) {
	    	 // Toast.makeText(getApplicationContext(),e.getMessage(),100).show();
		}

		
		try{
			 cr=db.rawQuery("select * from "+sp1.getSelectedItem()+" where isort between "+lowid+" and "+highid+" order by isort", null);
	  	 Log.v("get",""+cr.getCount());
		     	if(tbutton.isChecked())
		     	{
		     		Log.v("going to","doSpecific method");
		     		doSpecific();
		     		
		     	}
		     	else{
		     		Log.v("going to","doAll method");
		     		doAll();
		     	}
			 }
			 catch (Exception e) {
				 Log.v("error",""+e.getMessage());
		    	 // Toast.makeText(getApplicationContext(),e.getMessage(),100).show();
			}
		
		
		Log.v("hello","setlistviewstop");
		
	}
	
	private void doAll() {
		// TODO Auto-generated method stub
		int total = 100,pre,abs;
		try{
		Cursor cr1=db.rawQuery("select total from main where class='"+sp1.getSelectedItem()+"'", null);

		  if(cr1!=null)
	       {
	    	   if(cr1.moveToFirst())
	    	   {
	    		   do{
	    			  total=cr1.getInt(cr1.getColumnIndex("total"));
	    		   }while(cr1.moveToNext());
	    	   }
	       }
		 // Log.v("class total",""+total);
		
		  if(cr!=null)
	       {
	    	   rowItemsAll = new ArrayList<RowItemAll>();
	    	   if(cr.moveToFirst())
	    	   {
	    		   do{
	    			   
		    			String date,topic,periods,people,present = null,absent = null,percent;
		    			
		    			date=cr.getString(4);
		    			topic=cr.getString(8);
		    			periods=cr.getString(9);
		    			if(periods==null)
		    			{
		    				periods="1";
		    			}
		    			people=cr.getString(cr.getColumnIndex("dat"));
		    			pre=cr.getInt(cr.getColumnIndex("present"));
		    			abs=cr.getInt(cr.getColumnIndex("absent"));

		    			percent=""+((pre*100/total));
		    			present=""+pre;
		    			absent=""+abs;
		    			
		    				Log.v("date",date);
		    				Log.v("people",people);
		    				Log.v("present",present);
		    				Log.v("absent",absent);
		    				Log.v("percent",percent);	
		    				RowItemAll item = new RowItemAll(date,topic,periods,people,present,absent,percent);
		    				rowItemsAll.add(item);
		    			
		    		   }while(cr.moveToNext());
	    	   }

	       }
		  CustomListViewAdapterAll adapter = new CustomListViewAdapterAll(this,
	                R.layout.listitem_all, rowItemsAll);
	        lv.setAdapter(adapter);
		  
		}catch (Exception e) {
			Log.v("error",e.getMessage());
		}
	       
	}

	private void doSpecific() {
		// TODO Auto-generated method stub
		percents.setText("");
		lightval=0;
		periodval=0;
		try{
		  if(cr!=null)
	       {
	    	   rowItems = new ArrayList<RowItem>();
	    	   Log.v("lightsss","1");
	    	   if(cr.moveToFirst())
	    	   {
	    		   do{
	    			   Log.v("lightsss","2");
		    			String data,time,periods;
		    			int light = R.drawable.red1;
		    			 Log.v("lightsss","2.2");
		    			data=cr.getString(1);
		    			 Log.v("lightsss","2.3");
		    			time=cr.getString(4);
		    			 Log.v("lightsss","2.4");
		    			periods=cr.getString(9);
		    			 Log.v("lightsss1","\n "+data+"\n "+time+"\n "+periods);
		    			if(periods==null)
		    			{
		    				periods="1";
		    			}
		    			periodval=periodval+Integer.parseInt(periods);
		    			String [] items = null;
		    			
		    			Log.v("lightsss",""+3);
		    			Log.v("lightsss",""+data);
		    				if(!data.equalsIgnoreCase(""))
		    				{
		    					Log.v("lightsss","4");
		    					items = data.split(",");
		    				
		    				int i=0;
		    				
		    				while(i<items.length)
		    				{
		    					Log.v("lightsss","5");
		    					if(individual.getText().toString().trim().equalsIgnoreCase(items[i]))
		    					{
		    						light=R.drawable.green1;
		    						lightval=lightval+Integer.parseInt(periods);
		    						break;
		    					}
		    					i++;
		    				}
		    			  }
		    				Log.v("light",""+light);
		    				Log.v("time",time);
		    				Log.v("des"," ");
		    				RowItem item = new RowItem(light,time,time);
		    				Log.v("item","comp");
		    				rowItems.add(item);
		    			
		    		   }while(cr.moveToNext());
	    	   }

	       }
	       //lv.setAdapter(lvadapter);
	      
	       CustomListViewAdapter adapter = new CustomListViewAdapter(this,
	                R.layout.list_item, rowItems);
	        lv.setAdapter(adapter);
	        percentsspecific();
		}
		catch (Exception e) {
			// TODO: handle exception
			Log.v("null error",""+e.getMessage());
		}
	}

	public void toggle(View v)
	{
		if(!tbutton.isChecked())
		{
			individual.setVisibility(View.INVISIBLE);
			percents.setVisibility(View.INVISIBLE);
		}
		else
		{
			individual.setVisibility(View.VISIBLE);
			percents.setVisibility(View.VISIBLE);
			individual.setText("");
			percents.setText("");
		}
		setListView();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		db.close();
	}
	
	
	 
}
