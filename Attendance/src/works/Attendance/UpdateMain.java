package works.Attendance;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateMain extends Activity {
	
	String name;
	int tot;
	EditText classes,total;
	//database
	SQLiteDatabase db;
	Cursor cr;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adds);
		Intent i=getIntent();
		name=i.getStringExtra("name");
		classes=(EditText)findViewById(R.id.addname);
		total=(EditText)findViewById(R.id.addtotal);
		db=SQLiteDatabase.openOrCreateDatabase("/sdcard/Attendance/database.db",null);
		try{
			cr=db.rawQuery("select total from Main where class='"+name+"'", null);
			if(cr!=null)
			{
				if(cr.moveToFirst())
				{
					do{
						tot=cr.getInt(cr.getColumnIndex("total"));
						Log.v("total", ""+tot);
					}while(cr.moveToNext());
				}
			}
			
		}catch(Exception e){
			Log.v("error",""+e.getMessage());
		}
		
		classes.setHint(name);
		total.setHint(""+tot);
		
		
	}
	
	public void save(View v)
	{
		String n,t;
		if(classes.getText().toString().equalsIgnoreCase("") || total.getText().toString().equalsIgnoreCase(""))
		{
			Toast.makeText(this,"please fill both columns",Toast.LENGTH_SHORT).show(); 
		}
		else
		{
		//database
        db=SQLiteDatabase.openOrCreateDatabase("/sdcard/Attendance/database.db",null);
		
		n=classes.getText().toString().trim();
		t=total.getText().toString().trim();
		
		try{
		db.execSQL("update Main set class='"+n+"',total="+t+" where class='"+name+"'"); 
		//ALTER TABLE orig_table_name RENAME TO tmp_table_name;
		db.execSQL("ALTER TABLE "+name+" RENAME to "+n+"");
		Log.v("up1","update Main set class='"+n+"',total="+t+" where class='"+name+"'");
		Log.v("up2","ALTER TABLE "+name+" RENAME to "+n+"");
		Toast.makeText(this,"updated successfully",100).show();
		db.close();
		this.finish();
		}
		catch (SQLException e){ 
		
				Log.v("insideif:","hello");
				//Toast.makeText(this,"Update failed\nmight be class name already exists",Toast.LENGTH_LONG).show();
		}
		
		
		}
	}
	

	public void clear(View v)
	{
		classes.setText("");
		total.setText("");
	}
	

}
