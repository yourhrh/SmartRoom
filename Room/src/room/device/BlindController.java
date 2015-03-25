package room.device;

import java.util.ArrayList;

import room.info.Room;

public class BlindController extends Device {
	boolean up = true;

	public BlindController(Room room) {
		super(room);
		// TODO Auto-generated constructor stub
	}

	public void effect(int time)
	{
		ArrayList<Double> changeDegree = new ArrayList<Double>(); 
		if(up){
			changeDegree.add(0.2);
			changeDegree.add(0.0);
			changeDegree.add(0.3);
		}
		else{
			changeDegree.add(0.0);
			changeDegree.add(0.0);
			changeDegree.add(-0.3);
		}
		for(int i=0;i<time;i++)
			room.getEnv().changeEnv(changeDegree);

	}
	public void up(){
		up = true;
	}
	public void down(){
		up = false;
	}
	

}
