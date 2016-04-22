package works.Attendance;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Add extends Activity {
	
	EditText name,total;
	String n,t;
	
	//database
	SQLiteDatabase db;
	Cursor cr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adds);
		name=(EditText)findViewById(R.id.addname);
		total=(EditText)findViewById(R.id.addtotal);
		
		
	}
	
	public void save(View v)
	{
		if(name.getText().toString().equalsIgnoreCase("") || total.getText().toString().equalsIgnoreCase(""))
		{
			Toast.makeText(this,"please fill both columns",Toast.LENGTH_SHORT).show(); 
		}
		else
		{
			String na=name.getText().toString();
			boolean ab=false;
				for (char c : na.toCharArray()) {
				    if (Character.isWhitespace(c)) {
				    	Toast.makeText(this,"spaces not allowed in class name",Toast.LENGTH_SHORT).show();
				    	ab=true;
				    	break;
				    }
				    
				}
			
		if(ab)
		{
			
		}
		else{
		//database
        db=SQLiteDatabase.openOrCreateDatabase("/sdcard/Attendance/database.db",null);
		
		n=name.getText().toString().trim();
		t=total.getText().toString().trim();
		
		SQLiteStatement st;
		st=db.compileStatement("insert into Main values(?,?,?)");
		st.bindString(2,n);
		st.bindLong(3,Integer.parseInt(t));
		
		try{
		st.executeInsert();
		db.execSQL("create table "+n+"(id INTEGER PRIMARY KEY AUTOINCREMENT,dat text,present integer,absent integer,time text,date text,froms text,tos text,topic text,periods text,ssort text,isort integer)");
		Toast.makeText(this,"successfully updated",Toast.LENGTH_SHORT).show(); 
		}
		catch (Exception e) {
			
			if(e.getMessage().toString().trim().equalsIgnoreCase("error code 19: constraint failed"))
			{
				Log.v("insideif:","hello");
				Toast.makeText(this,"already exist",Toast.LENGTH_SHORT).show();
			}
			else
			{
				Log.v("biscuit:",e.getMessage().toString().trim());
				Log.v("biscuit:","error code 19: constraint failed");
				//Toast.makeText(this,"",100).show();
			}
		}
		
		db.close();
		}
		
		}//else
	}
	
	public void clear(View v)
	{
		name.setText("");
		total.setText("");
	}
	
	public void next(View v)
	{

		if(name.getText().toString().equalsIgnoreCase("") || total.getText().toString().equalsIgnoreCase(""))
		{
			Toast.makeText(this,"please fill both columns",Toast.LENGTH_SHORT).show(); 
		}
		else
		{
			String na=name.getText().toString();
			boolean ab=false;
				for (char c : na.toCharArray()) {
				    if (Character.isWhitespace(c)) {
				    	Toast.makeText(this,"spaces not allowed in class name",Toast.LENGTH_SHORT).show();
				    	ab=true;
				    	break;
				    }
				    
				}
			
		if(ab)
		{
			
		}
		else{
		
				Intent i = new Intent(this,AddStudent.class);
				i.putExtra("class",name.getText().toString().trim());
				i.putExtra("total",Integer.parseInt(total.getText().toString()));
				startActivity(i);
			}
		}
	}
	


}
