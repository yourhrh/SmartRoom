package room.device;

import java.util.ArrayList;
import java.util.HashMap;

import room.info.Room;

public class Device {
	protected Room room;
	protected HashMap<String, ActBehavior> actMap;
 	public Device(Room room) {
		// TODO Auto-generated constructor stub
		this.room = room;
		actMap = new HashMap<String, ActBehavior>();
	}
 	public void addAction(String actName,  ArrayList<Double> effectDegree,
					  int charge){
 		
 		actMap.put(actName, new DeviceOn(actName,room, effectDegree,charge));
 	}
 	public void actDevice(String actName,int time)
 	{
 		ActBehavior act = actMap.get(actName);
 		if(act == null)
 			System.out.println(actName + " not found!");
 		else
 			act.act(time);
 	}
	public ArrayList<ActBehavior> getActions() {
		// TODO Auto-generated method stub
		return new ArrayList<ActBehavior>(actMap.values());
	}

}
