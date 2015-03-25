package room.device;

import java.util.ArrayList;

public interface ActBehavior{
	public void act(int time);

	public ArrayList<Double> getEffectDegrees();

	public String getName();
	
	public int getCost();
}
