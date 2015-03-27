package selab.sogang.persondata;

import selab.sogang.adpatableenvroom.MainActivity;

public class DrawService {
	
	public DrawService() {
		// TODO Auto-generated constructor stub
	}
	public void drawEnv(Double temp,Double humi,Double bright,Integer cost){
		
		final String envString = "Environment \n" +"Temperature : " + temp + "\nHumidity : " + humi 
				+ "\n Brightness : " + bright + "\nCost : " + cost; 
		MainActivity.mainActivity.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				MainActivity.envView.setText(envString);
			}
		});
	}

}
