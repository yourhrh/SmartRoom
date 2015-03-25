package room.info;

import java.util.ArrayList;

import room.device.BlindController;
import room.device.Device;

public class Room {
	
	Environment env;
	private Device aircon,light,stand,fan,humidifier;
	private ArrayList<Device> deviceList;
	private BlindController blindController;
	private int cost = 0;

	public Room(int temp, int humi, int bright) {
		// TODO Auto-generated constructor stub
		//env √ ±‚»≠ 
		env = new Environment(temp, humi, bright);
		initDevices();
	}

	public Environment getEnv() {
		// TODO Auto-generated method stub
		return env;
	}
	
	private void initDevices()
	{
		deviceList = new ArrayList<Device>();
		//make Aircon
		aircon = new Device(this);
		//add action
		aircon.addAction("Dehumidify",makeEffectableFactor(0, -0.5, 0), 20);
		aircon.addAction("Warm", makeEffectableFactor(0.4, 0, 0), 30);	
		aircon.addAction("Cool", makeEffectableFactor(-0.6, 0, 0), 40);
		
		
		//make Light
		light = new Device(this);
		
		light.addAction("On", makeEffectableFactor(0, 0 , 0.3), 10);
		
		//make Stand
		fan = new Device(this);
		
		fan.addAction("On", makeEffectableFactor(-0.2, 0 , 0), 3);
		
		//make humidifier
		humidifier = new Device(this);
		humidifier.addAction("On", makeEffectableFactor(0,0.4,0), 6);
		
		
		deviceList.add(aircon);
		deviceList.add(light);
		deviceList.add(fan);
		deviceList.add(humidifier);
		
		blindController = new BlindController(this);
		
	}
	private ArrayList<Double> makeEffectableFactor(double temp,double humi,double bright)
	{
		ArrayList<Double> effectableFactor = new ArrayList<Double>();
		effectableFactor.add(temp);
		effectableFactor.add(humi);
		effectableFactor.add(bright);
		return effectableFactor;
	}
	
	public Device getAircon()
	{
		return aircon;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public BlindController getBilnd() {
		return blindController;
	}
	public Device getFan() {
		return fan;
	}
	public Device getHumidifier() {
		return humidifier;
	}
	public Device getLight() {
		return light;
	}
	public Device getStand() {
		return stand;
	}

	public ArrayList<Device> getDevices() {
		// TODO Auto-generated method stub
		return deviceList;
	}

}
