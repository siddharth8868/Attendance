package works.Attendance;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Repeated {
	
	static ArrayList<String> al;
	static SQLiteDatabase db;
	static Cursor cr;
	
	Repeated(){
		db = SQLiteDatabase.openOrCreateDatabase("/sdcard/Attendance/database.db",null);
	}
	
	public static ArrayList<String> setlist() { 
		 al=new ArrayList<String>();
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
	    		   
	    	   }
	       }
	       return al;
		 }
		 catch (Exception e) {
			// TODO: handle exception
			 return al;
		}
	}

}
