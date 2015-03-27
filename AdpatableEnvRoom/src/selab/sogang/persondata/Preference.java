package selab.sogang.persondata;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class Preference {
	private double lowTemp;
	private double lowHumi;
	private double lowBright;
	private double highTemp;
	private double highHumi;
	private double highBright;
	

	
	public Preference(double lowTemp,double highTemp,double lowHumi,double highHumi
			,double lowBright, double highBright) {
		// TODO Auto-generated constructor stub
		this.lowTemp = lowTemp;
		this.highTemp = highTemp;
		this.lowHumi = lowHumi;
		this.highHumi = highHumi;
		this.lowBright =lowBright;
		this.highBright = highBright;

	}
	
	public void changePref(double lowTemp,double highTemp,double lowHumi,double highHumi
			,double lowBright, double highBright)
	{
		this.lowTemp = lowTemp;
		this.highTemp = highTemp;
		this.lowHumi = lowHumi;
		this.highHumi = highHumi;
		this.lowBright =lowBright;
		this.highBright = highBright;
	}
	public List<Double> getPrefs()
	{
		Log.i("getPrefs","getPrefs");
		ArrayList<Double> prefs = new ArrayList<Double>();
		prefs.add(lowTemp);
		prefs.add(highTemp);
		prefs.add(lowHumi);
		prefs.add(highHumi);
		prefs.add(lowBright);
		prefs.add(highBright);
		Log.i("getPrefs","getPrefs Size : " + prefs.size());
		return prefs;
	}

}
