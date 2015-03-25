package ConfigurationComposer;

import ConfigurationComposer.service.UsabilityImprover;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;

import room.device.ActBehavior;
import room.device.Device;
import room.info.Room;

public class UsabilityImproverImpl implements UsabilityImprover {
	
	//time Threshold 이시간 내로는 preference를 맞춰줘야 한다.
	
	private final static int TIMETHRESHOLD = 10;
	
	private BundleContext bundleContext = null;
	public UsabilityImproverImpl(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}

	private ArrayList genStreatgies(ArrayList diagnosis, List curModel) {
		ArrayList strategies = new ArrayList();
		Room room = bundleContext.getService(bundleContext.getServiceReference(Room.class));
		
		String[] diffTemp = ((String) diagnosis.get(0)).split(" ");
		String[] diffHumi = ((String) diagnosis.get(1)).split(" ");
		String[] diffBright = ((String) diagnosis.get(2)).split(" ");
		//Effectable 디바이스 정보를 가져옴 
		ArrayList<DeviceInfo> deviceInfos = makeDeviceInfo(diffTemp[0],diffHumi[0],diffBright[0], room);
		
		if(diffTemp.length ==1 && diffHumi.length == 1 && diffBright.length == 1){
			System.out.println("Satisify!");
			return null;
		}
		else{
			double[] changeEnv = new double[3];
			
		}
		
		
		return strategies;
	}

	public ArrayList adapt(ArrayList diagnosis, List curModel) {
		
		
		return genStreatgies(diagnosis, curModel);
	}
	private ArrayList<DeviceInfo> makeDeviceInfo(String tempLogic,String humiLogic,String brightLogic,Room room){
		ArrayList<DeviceInfo> deviceInfo = new ArrayList<DeviceInfo>();
		
		
		
		
		
		ArrayList<Device> deviceList = room.getDevices();
		for(int deviceNum = 0; deviceNum < deviceList.size();deviceNum++){
			Device device = deviceList.get(deviceNum);
			for(ActBehavior action : device.getActions()){
				ArrayList<Double> effectDegrees = action.getEffectDegrees();
				if(tempLogic.equals("Low") && effectDegrees.get(0) > 0)
					deviceInfo.add(new DeviceInfo(deviceNum, action.getName(), effectDegrees,0));
				else if(tempLogic.equals("High") && effectDegrees.get(0) < 0)
					deviceInfo.add(new DeviceInfo(deviceNum, action.getName(), effectDegrees,0));
				else if(humiLogic.equals("Low")&&effectDegrees.get(1) > 0)
					deviceInfo.add(new DeviceInfo(deviceNum, action.getName(), effectDegrees,1));
				else if(humiLogic.equals("High")&&effectDegrees.get(1) < 0 )
					deviceInfo.add(new DeviceInfo(deviceNum, action.getName(), effectDegrees,1));
				else if(brightLogic.equals("Low")&&effectDegrees.get(2)>0)
					deviceInfo.add(new DeviceInfo(deviceNum, action.getName(), effectDegrees,2));
				else if(brightLogic.equals("High")&&effectDegrees.get(2)<0)
					deviceInfo.add(new DeviceInfo(deviceNum, action.getName(), effectDegrees,2));
			}
		}
		return deviceInfo;
	}
	
	private class DeviceInfo{
		private int deviceNum;
		private String actionName;
		private ArrayList<Double> effectDegree;
		private int factorNum;
		public DeviceInfo(int deviceNum,String actionName, ArrayList<Double> effectDegree,int factorNum) {
			// TODO Auto-generated constructor stub
			this.deviceNum = deviceNum;
			this.actionName = actionName;
			this.effectDegree = effectDegree;
			this.factorNum = factorNum;
		}
		public String getActionName() {
			return actionName;
		}
		public int getDeviceNum() {
			return deviceNum;
		}
		public ArrayList<Double> getEffectDegree() {
			return effectDegree;
		}
		public int getFactorNum() {
			return factorNum;
		}
		
	}
}

