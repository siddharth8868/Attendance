package works.Attendance;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class AddStudent extends Activity{
	
	String classes;
	int total;
	ListView lv;
	TextView tval,tclass;
	ArrayList<String> datas;
	ArrayAdapter<String> ad,ad2;
	SQLiteDatabase db;
	EditText ed;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addstudents);
		Intent i=getIntent();
		ed = (EditText) findViewById(R.id.addstudentstext);
		classes=i.getStringExtra("class");
		total =  i.getIntExtra("total",0); 
		datas=new ArrayList<String>();
		tval= (TextView)findViewById(R.id.addstudenttotal11);
		tclass = (TextView)findViewById(R.id.addstudentclassname11);
		tval.setText(""+total);
		tclass.setText(classes);
		
		lv=(ListView)findViewById(R.id.addstudentlistView1);
		lv.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		lv.setStackFromBottom(true);
		ad2=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datas);
		lv.setAdapter(ad2);
		
		
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
		
		if(datas.size()==total)
		{
			Toast.makeText(this, "cannot add more than "+total,300).show();
		}
		else{
			if(ed.getText().toString().equalsIgnoreCase(""))
    		{
    		}
    		else
    		{
    			datas.add(ed.getText().toString().trim());
        		lv.setAdapter(ad2);
    			ed.setText("");
    		}
		}
	}
	
	public void save(View v)
	{
		String str = "";
		if(datas.size()<total)
		{
			Toast.makeText(this, "should add "+total+"numbers\n you added only "+datas.size(),300).show();
		}
		else
		{
			
		
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
		    db=SQLiteDatabase.openOrCreateDatabase("/sdcard/Attendance/database.db",null);
			st=db.compileStatement("insert into Main values(?,?,?,?)");
			st.bindString(2,classes);
			st.bindLong(3,total);
			st.bindString(4,str);
			try{
				st.executeInsert();
				db.execSQL("create table "+classes+"(id INTEGER PRIMARY KEY AUTOINCREMENT,dat text,present integer,absent integer,time text,date text,froms text,tos text,topic text,periods text,ssort text,isort integer)");
				Toast.makeText(this,"added successfully",300).show();
				this.finish();
				
			}
			catch (Exception e) {
				// TODO: handle exception
			}
		    
		    
		}
		    
		    
	}
	
	
	}
