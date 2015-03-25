package room.test;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.junit.Test;

import room.device.ActBehavior;
import room.device.Device;
import room.device.DeviceOn;
import room.info.Environment;
import room.info.Room;

public class RoomTest {
	Room testRoom;
	@Test
	public void test() {
		testRoom = new Room(26,40,30);
		assertTrue(testRoom.getEnv().equals(new Environment(26, 40, 30)));
		testRoom.getAircon().actDevice("Dehumidify", 3);
		assertTrue(testRoom.getEnv().equals(new Environment(26, 38.5, 30)));
		testRoom.getBilnd().effect(3);
//		System.out.println(testRoom.getEnv().getTemp()+" "+testRoom.getEnv().getHumi()+" "+testRoom.getEnv().getBright());
//		assertTrue(testRoom.getEnv().equals(new Environment(26.6, 38.5, 30.9)));
//		assertEquals(65, testRoom.getCost());
		planningTest();
	}
	private void planningTest()
	{
		ArrayList<String> diagonosis = new ArrayList<String>();
		diagonosis.add("Low 5");
		diagonosis.add("high 10");
		diagonosis.add("satisify");
		ArrayList<PlanningInfo> planInfo = new ArrayList<RoomTest.PlanningInfo>();
		String[] diffTemp = diagonosis.get(0).split(" ");
		String[] diffHumi = diagonosis.get(1).split(" ");
		String[] diffBright = diagonosis.get(2).split(" ");
		
		
		
		
		ArrayList<Device> deviceList = testRoom.getDevices();
		for(int deviceNum = 0; deviceNum < deviceList.size();deviceNum++){
			Device device = deviceList.get(deviceNum);
			for(ActBehavior action : device.getActions()){
				ArrayList<Double> effectDegrees = action.getEffectDegrees();
				if(diffTemp[0].equals("Low") && effectDegrees.get(0) > 0)
					planInfo.add(new PlanningInfo(deviceNum, action.getName(), effectDegrees,0));
				else if(diffTemp[0].equals("High") && effectDegrees.get(0) < 0)
					planInfo.add(new PlanningInfo(deviceNum, action.getName(), effectDegrees,0));
				else if(diffHumi[0].equals("Low")&&effectDegrees.get(1) > 0)
					planInfo.add(new PlanningInfo(deviceNum, action.getName(), effectDegrees,1));
				else if(diffHumi[0].equals("High")&&effectDegrees.get(1) < 0 )
					planInfo.add(new PlanningInfo(deviceNum, action.getName(), effectDegrees,1));
				else if(diffBright[0].equals("Low")&&effectDegrees.get(2)>0)
					planInfo.add(new PlanningInfo(deviceNum, action.getName(), effectDegrees,2));
				else if(diffBright[0].equals("High")&&effectDegrees.get(2)<0)
					planInfo.add(new PlanningInfo(deviceNum, action.getName(), effectDegrees,2));
			}
		}
		assertTrue(planInfo.size()>0);
		assertTrue(planInfo.stream().map(info -> info.getActionName()).anyMatch((s) -> s.equals("Warm")));
		
			
	}
	private class PlanningInfo{
		private int deviceNum;
		private String actionName;
		private ArrayList<Double> effectDegree;
		private int factorNum;
		public PlanningInfo(int deviceNum,String actionName, ArrayList<Double> effectDegree,int factorNum) {
			// TODO Auto-generated constructor stub
			this.deviceNum = deviceNum;
			this.actionName = actionName;
			this.effectDegree = effectDegree;
			this.factorNum = factorNum;
		}
		public String getActionName() {
			return actionName;
		}
		
	}

}
