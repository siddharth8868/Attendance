package works.Attendance;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Delete extends Activity{
	
	ListView lv;
	ArrayList<String> al;
	ArrayAdapter<String> ad;
	int id;
	//database
	SQLiteDatabase db;
	Cursor cr;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.delete);
		lv=(ListView)findViewById(R.id.dellistView1);
		al=new ArrayList<String>();
		ad=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,al);
		
		//database
        db=SQLiteDatabase.openOrCreateDatabase("/sdcard/Attendance/database.db",null);
		
        //setting clicklistener for ListView
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				
				final String s=(String)lv.getItemAtPosition(arg2);
				dialog(s);
	
			}
		});
        setlist();
      
	}


	public void setlist() {
		
		 al.clear();
	     db=SQLiteDatabase.openOrCreateDatabase("/sdcard/Attendance/database.db",null);
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
	       lv.setAdapter(ad);
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


	private void closes() {
		// TODO Auto-generated method stub
		this.finish();
	}
	

	public void delete(String s) {
		
		
		//Toast.makeText(this,"id="+id,100).show();
		
		cr=db.rawQuery("select id from Main where class='"+s+"'", null);
	       if(cr!=null)
	       {
	    	   if(cr.moveToFirst())
	    	   {
	    		   do{
	    			   id=cr.getInt(cr.getColumnIndex("id"));
	    		   }while(cr.moveToNext());
	    	   }
	       }
		
		try{
			db.delete("Main","class=?",new String[]{s});
			String up="update Main set id=id-1 where id>"+id;
			db.execSQL(up);
		
		db.execSQL("DROP TABLE IF EXISTS " + s);
		setlist();
		}
		catch (Exception e) {
			Toast.makeText(this,""+e.getMessage(), Toast.LENGTH_SHORT).show();
		}
		
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
	});
	dialog.setNegativeButton("No",new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface d,int id){
		}
	});
	dialog.setNeutralButton("Update",new OnClickListener() {
		
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			Intent i=new Intent(getApplicationContext(),UpdateMain.class);
			i.putExtra("name",ss);
			startActivity(i);
		}

		
	});
	dialog.show();
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
		setlist();
		 db=SQLiteDatabase.openOrCreateDatabase("/sdcard/Attendance/database.db",null);
	}
	
}
