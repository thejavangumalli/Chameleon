package in.rockerz.chameleon;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.widget.Toast;

public class Sqlitedb extends SQLiteOpenHelper {
	public static final String PROFILE="profile";
	public static final String LATITUDE="latitude";
	public static final String LONGITUDE="longitude";
	public static final String ADDRESS="address";
	public static final String LOCATION="location";
	public Context mContext;
	 

	  private static final String DATABASE_NAME = "profiles.db";
	  private static final int DATABASE_VERSION = 1;

	  // Database creation sql statement
	  private static final String DATABASE_CREATE =
              "CREATE TABLE " + "profiles" + " (" +
               PROFILE + " TEXT, " +
               LATITUDE + " DOUBLE, " +
               LONGITUDE + " DOUBLE, " +
               LOCATION + " TEXT, " +
              ADDRESS + " TEXT);";
	  
	  //private static final String DATABASE_CREATE ="create table messages(message text)";
	public Sqlitedb(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.mContext=context;
		}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
		System.out.print(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS messages");
		this.onCreate(db);
	}
	public void addProfile(String profile,double latitude,double longitude,String address,Location location){
		SQLiteDatabase db = this.getWritableDatabase();
		if(getAddress().contains(address)){
			db.execSQL("UPDATE profiles SET profile='"+profile+"' WHERE address='"+address+"'");
		}
		else if(address!=null){
		ContentValues values = new ContentValues();
	    values.put(PROFILE, profile); 
	    values.put(LATITUDE, latitude);
	    values.put(LONGITUDE, longitude);
	    values.put(LOCATION, location.toString());
	    values.put(ADDRESS, address);
	    db.insert("profiles", null, values);
	    db.close(); // Closing database connection
		}
		else{
			Toast.makeText(mContext, "Check Connection", Toast.LENGTH_LONG).show();
		}
	}
	public List<String> getProfiles(){
		List<String> storedProfiles=new ArrayList<String>();
		String selectQuery = "SELECT *  FROM " + "profiles";
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	        	storedProfiles.add(cursor.getString(0)+" at "+cursor.getString(4));
	        } while (cursor.moveToNext());
	    }
	     return storedProfiles;
	}
	public List<String> getAddress(){
		List<String> address=new ArrayList<String>();
		String selectQuery = "SELECT *  FROM " + "profiles";
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	        	address.add(cursor.getString(4));
	        } while (cursor.moveToNext());
	    }
	     return address;
	     }
	public String changeProfile(double lat,double lon){
		String selectQuery = "SELECT profile  FROM " + "profiles WHERE latitude='"+lat+"' AND longitude='"+lon+"'" ;
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);	
	    cursor.moveToFirst();
		return cursor.getString(0);
		
	}
	public List<Double> getLongitudes(){
		List<Double> longitudes=new ArrayList<Double>();
		String selectQuery = "SELECT *  FROM " + "profiles";
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	        	longitudes.add(cursor.getDouble(2));
	        } while (cursor.moveToNext());
	    }
	     return longitudes;
	     }
	public List<Double> getLatitudes(){
		List<Double> latitudes=new ArrayList<Double>();
		String selectQuery = "SELECT *  FROM " + "profiles";
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	        	latitudes.add(cursor.getDouble(1));
	        } while (cursor.moveToNext());
	    }
	     return latitudes;
	     }
	public List<String> getLocations(){
		List<String> locations=new ArrayList<String>();
		String selectQuery = "SELECT *  FROM " + "profiles";
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	        	locations.add(cursor.getString(3));
	        } while (cursor.moveToNext());
	    }
	     return locations;
	     }
}