package works.Attendance;

import java.io.File;
import java.util.ArrayList;import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class AttendanceActivity extends Activity {
    /** Called when the activity is first created. */
	
	private static final int version=1;
	private static final String versionname="added";
	SQLiteDatabase db;
	Cursor cr;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        createDb();
    }
    
    private void createDb() {
		
    	File direct = new File(Environment.getExternalStorageDirectory() + "/Attendance");

        Log.v("hello","created1");
        if(!direct.exists())
         {Log.v("hello","created2");
             if(direct.mkdir()) 
               {
                //directory is created;
            	 Log.v("hello","created3");
               }

         }
        db=SQLiteDatabase.openOrCreateDatabase("/sdcard/Attendance/database.db",null);
        db.execSQL("create table if not exists "
				+"Main"
				+"(id INTEGER PRIMARY KEY AUTOINCREMENT,class text unique,total INTEGER,numbers text)");
        db.execSQL("create table if not exists "
				+"Version"
				+"(version INTEGER PRIMARY KEY AUTOINCREMENT,name text unique)");
        
        try{

        	cr=db.rawQuery("select version from Version",null);
        	 
	       if(cr.getCount()!=0)
	       { 
	    	   Log.v("hello","notnull");
	    	   if(cr.moveToFirst())
	    	   {
	    			 int v=cr.getInt(cr.getColumnIndex("version"));
	    			  if(v!=version){
	    				  switch (v) {
	  					case 1:
	  						  db.execSQL("update Version set version="+(v++)+",name='"+versionname+"' where version="+v+"");
		    				  performupdate1();
		    				  
	  						break;
	  					case 2:
	  						  db.execSQL("update Version set version="+(v++)+",name='"+versionname+"' where version="+v+"");
		    				  //performupdate2();
		    				  
	  						break;
	  					}
	    				  
	    			  }
	    	   }
	       }
	       else{
	    	   //NO NEED TO PERFORM UPDATE ITS FREASH
	    	   Log.v("hello","null");
	    	   db.execSQL("insert into Version values("+version+",'"+versionname+"')");
	    	   Log.v("tt","version added");
	    	   //FOR THIS TIME ONLY FROM NEXT TIME NO
	    	   performupdate1();
	       }

		 }
		 catch (Exception e) {
			 Log.v("tt",""+e.getMessage());
		}
		
        
        
	}

	private void performupdate1() {
		// TODO Auto-generated method stub
		Log.v("error","perform update");
		Toast.makeText(this,"software updated version="+version,100).show();
		ArrayList<String> alal=new ArrayList<String>();
		 try{
		 cr=db.rawQuery("select * from Main", null);
	       if(cr!=null)
	       {
	    	   if(cr.moveToFirst())
	    	   {
	    		   do{
	    			 alal.add(cr.getString(cr.getColumnIndex("class")));
	    		   }while(cr.moveToNext());
	    	   }
	       }
		 }
		 catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
		try {
			Log.v("error",""+alal.get(1));
			if(alal!=null)
			{
				for(int i=0;i<alal.size();i++)
				{
					String ap=alal.get(i);
				db.execSQL("alter table "+ap+" add column date text");
				db.execSQL("alter table "+ap+" add column froms text");
				db.execSQL("alter table "+ap+" add column tos text");
				db.execSQL("alter table "+ap+" add column topic text");
				db.execSQL("alter table "+ap+" add column periods text");
				db.execSQL("alter table "+ap+" add column ssort text");
				db.execSQL("alter table "+ap+" add column isort integer");
				Log.v("error",""+"inside");
				editdata(ap);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.v("error","error in altering tables");
		}
		
		
	}

	private void editdata(String ap) {
		// TODO Auto-generated method stub
		try {
			cr=db.rawQuery("select time from "+ap,null);
			if(cr!=null)
			{
				if(cr.moveToFirst())
				{
					do {
						String time=cr.getString(cr.getColumnIndex("time"));
						String [] items = null;
						StringBuffer sb=new StringBuffer("");
						if(!time.equals(""))
						{
					      items = time.split("  ");
					      Log.v("split",""+items[1]+" "+items[0]);
					      Log.v("before update","0");
					      
					      String [] it;

						      it =items[0].split("-");
						     Log.v("split",""+it[2]+" "+it[1]+""+it[0]);
						     // Log.v("before update","0");
						      sb.append(it[2]);
						      sb.append(it[1]);
						      sb.append(it[0]);
						      Log.v("split",""+sb.toString());
					      
					      
					      Log.v("before update","1");
					      Log.v("before update","2");
					      sb.append(items[1]);
					      Log.v("before update","3");
					      String ss=sb.toString();
					      Log.v("before update","4");
					      db.execSQL("update "+ap+" set date='"+items[0]+"',froms='"+items[1]+"',ssort='"+ss+"' where time='"+time+"'");
					      Log.v("after update","");
						}
					} while (cr.moveToNext());
				}
				makearrange(ap);
			}
		} catch (Exception e) {
			Log.v("editdate error",""+e.getMessage());
			// TODO: handle exception
		}
	}
	
	private void makearrange(String ap) {
		
		int i=1;
		try {
			cr=db.rawQuery("select ssort from "+ap+" ORDER BY ssort", null);
			if(cr!=null)
			{
				if(cr.moveToFirst())
					do {
						String pp=cr.getString(cr.getColumnIndex("ssort"));
						Log.v("main",""+pp);
						Log.v("main2",""+"update "+ap+" set isort="+i+" where ssort='"+pp+"'");
						db.execSQL("update "+ap+" set isort="+i+" where ssort='"+pp+"'");
						Log.v("main3",""+pp);
						
						i++;
					} while (cr.moveToNext());
			}
		} catch (Exception e) {
			Log.v("makearrange error",""+e.getMessage());
			// TODO: handle exception
		}
		
		
	}
	
	

	public void add(View v)
    {
    	Intent i=new Intent(this,Add.class);
    	startActivity(i);
    	
    }
    
    public void delete(View v)
    {
    	Intent i=new Intent(this,Delete.class);
    	startActivity(i);
    }
    
    public void take(View v)
    {
    	Intent i=new Intent(this,Take1.class);
    	startActivity(i);
    }
    
    public void view(View v)
    {
    	Intent i=new Intent(this,Views1.class);
    	startActivity(i);
    }
    
    public void analysis(View v)
    {
    	Intent i=new Intent(this,Analysis.class);
    	startActivity(i);
    }
    
    public void help(View v)
    {
    	Intent i=new Intent(this,Help.class);
    	startActivity(i);
    }
    
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    	db.close();
    }
}