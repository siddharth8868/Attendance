package works.Attendance;

import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.widget.TextView;

public class Help extends Activity {
	
	TextToSpeech tts;
	TextView tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		tv=(TextView)findViewById(R.id.helptextView);
		
		tts=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
			
				@Override
				public void onInit(int status) {
					// TODO Auto-generated method stub
					if(status!=TextToSpeech.ERROR)
					{
						tts.setLanguage(Locale.CHINA);
					}
				}
			});
		
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
		  @Override
		  public void run() {
		    //Do something after 100ms
				//Toast.makeText(getApplicationContext(),"hurry",100).show();
				String s=getApplicationContext().getString(R.string.helps);
		    	tts.speak(s, TextToSpeech.QUEUE_FLUSH,null);

		  }
		}, 3000);
	}
	
	@Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	if(tts!=null)
    	{
    	  tts.stop();
    	  tts.shutdown();
    	}
    	super.onPause();
    }

}
