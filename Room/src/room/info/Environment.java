package room.info;

import java.util.ArrayList;

public class Environment {
	private double temp,humi,bright;

	public Environment(double temp, double humi, double bright) {
		// TODO Auto-generated constructor stub
		this.temp = temp;
		this.humi = humi;
		this.bright = bright;
		
		
	}
	public boolean equals(Environment env) {
	
		if(env == null)
			return false;
		else if(temp == env.getTemp() && humi == env.getHumi() && bright == env.getBright() )
			return true;
		else
			return false;
			
	}
	public double getBright() {
		return bright;
	}
	public double getHumi() {
		return humi;
	}
	public double getTemp() {
		return temp;
	}
	public void changeEnv(ArrayList<Double> effect)
	{
		temp += effect.get(0);
		humi += effect.get(1);
		bright += effect.get(2);
	}
	

}
