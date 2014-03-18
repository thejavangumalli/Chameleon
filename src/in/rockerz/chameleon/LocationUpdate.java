package in.rockerz.chameleon;

import java.util.List;

import android.content.Context;
import android.location.Location;
import android.media.AudioManager;
import android.os.AsyncTask;

public class LocationUpdate extends AsyncTask<Location,String,String> {
	private Context mContext;
	LocationUpdate(Context context){
		this.mContext=context;
		System.out.println("CONTEXT"+context);
	}
	@Override
	protected String doInBackground(Location... arg0) {
		Location[] location=arg0;
		final AudioManager mode = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);		
		Sqlitedb sq=new Sqlitedb(mContext);
		List<Double> latitudes=sq.getLatitudes();
		List<Double> longitudes=sq.getLongitudes();
		for(int i=0;i<latitudes.size();i++){
			double lat=latitudes.get(i);
			double lon=longitudes.get(i);
			System.out.println("\nLAT"+lat);
			System.out.println("\nLON"+lon);
			int distance=(int) (Math.sqrt(Math.pow(lat - location[0].getLatitude(), 2) + Math.pow(lon - location[0].getLongitude(), 2)) / (0.000008998719243599958));
			System.out.println("\nDISTANCE"+distance);
			if(distance<150){
				System.out.println("INSIDE FOR");
				if(sq.changeProfile(lat,lon).equalsIgnoreCase("General")){
					System.out.println("General");
					mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
					}
					else if(sq.changeProfile(lat,lon).equalsIgnoreCase("Vibrate")){
						System.out.println("VIBRATE");
						mode.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
						}
					else if(sq.changeProfile(lat,lon).equalsIgnoreCase("Silent")){
						System.out.println("SILENT");
						mode.setRingerMode(AudioManager.RINGER_MODE_SILENT);
						}
			}
		}
		return null;
		
		
	}

}
