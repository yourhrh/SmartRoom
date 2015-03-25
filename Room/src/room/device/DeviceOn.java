package room.device;

import java.util.ArrayList;

import room.info.Room;

public class DeviceOn implements ActBehavior{
	Room myRoom = null;
	ArrayList<Double> effectDegrees = null;
	int cost = 0;
	String name;
	
	public DeviceOn(String name,Room room, ArrayList<Double> effectDegree,
			  int charge) {
		// TODO Auto-generated constructor stub
		this.myRoom = room;
		this.effectDegrees = effectDegree;
		this.cost = charge;
		this.name = name;
	}
	
	

	@Override
	public void act(int time) {
		// TODO Auto-generated method stub
		for(int i=0;i<time;i++)
		{
			//비용 추가
			myRoom.setCost(myRoom.getCost()+cost);
			myRoom.getEnv().changeEnv(effectDegrees);
		}
	}
	public ArrayList<Double> getEffectDegrees() {
		return effectDegrees;
	}



	@Override
	public String getName() {
		// TODO Auto-generated method stub
		
		return name;
	}
	@Override
	public int getCost() {
		return cost;
	}

	
}


