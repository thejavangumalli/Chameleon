package in.rockerz.chameleon;

import java.util.List;

import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnTouchListener;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity{
	final CharSequence[] SoundProfile={"General","Vibrate","Silent"};
	String profile;
	Sqlitedb sq=new Sqlitedb(MainActivity.this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	@Override
	protected void onStart(){
		super.onStart();
		GPSTracker gps=new GPSTracker(MainActivity.this);
		updateMe();
		//Intent i=new Intent(this,GPSTracker.class);
		//startService(i);
		LocationUpdate lu=new LocationUpdate(MainActivity.this);
		setAlarm();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.location:
	    	/*if(!gps.canGetLocation)
			{
				gps.showSettingsAlert();
			}*/
	    	Toast.makeText(getApplicationContext(), "LOCATION", Toast.LENGTH_SHORT).show();
	    	AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this)
	    	.setTitle("Choose a Profile")
	    	.setSingleChoiceItems(SoundProfile, -1, new DialogInterface.OnClickListener() {
	    	@Override
	    	public void onClick(DialogInterface dialog, int which) {
	    	Toast.makeText(getApplicationContext(),
	    	"The selected profile is "+SoundProfile[which], Toast.LENGTH_LONG).show();
	    	profile=SoundProfile[which].toString();
	    	GPSTracker gps=new GPSTracker(MainActivity.this);
	    	String address=gps.getAddress(gps.getLatitude(), gps.getLongitude());
	    	Toast.makeText(MainActivity.this, address+gps.getLatitude()+gps.getLongitude(), Toast.LENGTH_LONG).show();
	    	sq.addProfile(SoundProfile[which].toString(),gps.getLatitude(),gps.getLongitude(),address,gps.getLocation());
	    	updateMe();
	    	//dismissing the dialog when the user makes a selection.
	    	dialog.dismiss();
	    	}
	    	});
	    	builder.show();
	      break;
	   // case R.id.time:
	     // break;

	    default:
	      break;
	    }
	    return true;
	  }
	public void updateMe(){
		String prof1 = null;
		List<String> prof=sq.getProfiles();
		TableLayout table=(TableLayout) findViewById(R.id.table);
		if(prof.size()==0)
		{  
			table.removeAllViews();
			TableRow tr = new TableRow(this);
		    tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		    TextView tv = new TextView(this);
		    tv.setText("Add New Entry");
		    tv.setTextSize(30);
		    tr.addView(tv);
		    table.addView(tr, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		}
		else{
			table.removeAllViews();
        for(int i=0;i<prof.size();i++){
        	prof1=prof.get(i);
        	System.out.println("profile"+prof1);
        	TableRow tr = new TableRow(this);
		    tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		    TextView tv = new TextView(this);
		    tv.setText(prof1);
		    tv.setTextSize(15);
		    tr.addView(tv);
		    table.addView(tr, i);
        }
	}
}
	public void setAlarm(){
		Log.d("ALARM","IN ALARM");
		//LocationUpdate lu=new LocationUpdate(MainActivity.this);
		//GPSTracker gps=new GPSTracker(MainActivity.this);
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		Intent i = new Intent(this, GPSTracker.class);
		PendingIntent pi = PendingIntent.getService(this, 0, i,PendingIntent.FLAG_UPDATE_CURRENT);
		am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,System.currentTimeMillis(), 10000, pi);
	}
}