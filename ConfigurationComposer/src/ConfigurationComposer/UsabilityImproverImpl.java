package ConfigurationComposer;

import ConfigurationComposer.service.UsabilityImprover;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.framework.BundleContext;

import room.device.ActBehavior;
import room.device.Device;
import room.info.Room;

public class UsabilityImproverImpl implements UsabilityImprover {
	
	//time Threshold 이시간 내로는 preference를 맞춰줘야 한다.
	
	private final static int TIMETHRESHOLD = 25;
	
	private BundleContext bundleContext = null;
	public UsabilityImproverImpl(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}

	private ArrayList genStreatgies(ArrayList diagnosis, List curModel) {
		ArrayList<ActionInfo> strategies = new ArrayList<ActionInfo>();
		Room room = bundleContext.getService(bundleContext.getServiceReference(Room.class));
		
		String[] diffTemp = ((String) diagnosis.get(0)).split(" ");
		String[] diffHumi = ((String) diagnosis.get(1)).split(" ");
		String[] diffBright = ((String) diagnosis.get(2)).split(" ");
		
		//Effectable 디바이스 정보를 가져옴 
		ArrayList<ActionInfo> deviceInfos = makeDeviceInfo(diffTemp[0],diffHumi[0],diffBright[0], room);
		
		if(diffTemp.length ==1 && diffHumi.length == 1 && diffBright.length == 1){
			System.out.println("Satisify!");
			return null;
		}
		else{
			double[] changeEnv = {0,0,0};
			
			
			
			
			for(int i=0;i<3;i++)
			{
				final int factorNum = i;
				ArrayList<ActionInfo> satiActionList = new ArrayList<UsabilityImproverImpl.ActionInfo>();
//				Stream<Object> factorStream = deviceInfos.stream().filter(new Predicate<Object>() {
//					@Override
//					public boolean test(Object t) {
//						if(((ActionInfo)t).getFactorNum() == factorNum)
//							return true;
//						return false;
//					}
//				});
				for(ActionInfo action : deviceInfos)
				{
					if(action.getFactorNum() == factorNum)
						satiActionList.add(action);
				}
		
				
				ActionInfo efficientAct = getEfficientAct(diagnosis, satiActionList, i);
				
				int j=0;
				while(j < TIMETHRESHOLD)
				{
					//못찾을경우 더이상 행동 불가능
					if(efficientAct == null){
						System.out.println("Can't Satisify!");
						break;

					}
					changeEnv[factorNum] += efficientAct.getEffectDegree();
					if(Math.abs(changeEnv[factorNum])> Double.parseDouble(((String) diagnosis.get(factorNum)).split(" ")[1])){
						System.out.println("acting gogo");
						efficientAct.setTime(j+1);
						room.getDevices().get(efficientAct.getDeviceNum()).actDevice(efficientAct.getActionName(), j+1);
						System.out.println("acting gogo");
						strategies.add(efficientAct);
						break;
					}
					
					else if(j==TIMETHRESHOLD-1){
						
						efficientAct.setTime(j+1);
						System.out.println("acting gogo");
						room.getDevices().get(efficientAct.getDeviceNum()).actDevice(efficientAct.getActionName(), j+1);
						System.out.println("acting gogo");
						strategies.add(efficientAct);
						//새로운 EffecientAct를 찾음
						satiActionList.remove(efficientAct);
						efficientAct = getEfficientAct(diagnosis, satiActionList, factorNum);
						j=-1;
					}
					j++;
					
				}
			}
		}
		drawEnv(room);
		
		return strategies;
	}

	public ArrayList adapt(ArrayList diagnosis, List curModel) {
		
		return genStreatgies(diagnosis, curModel);
	}
	private ActionInfo getEfficientAct(ArrayList diagnosis, List<ActionInfo> actionList,int factorNum){
		ActionInfo efficientAct = null;
		if(((String) diagnosis.get(factorNum)).split(" ")[0].equals("Low"))
		{
//			 efficientAct = factorStream.filter(info->info.getEffectDegree()<0)
//				.min(Comparator.comparing(info -> info.getEffectDegree()/info.getCost())).get();
			
			System.out.println("in Low if actionList size: " +actionList.size());
			for(ActionInfo action : actionList){
				System.out.println("actionName : " + action.getActionName());
				if(efficientAct == null && action.getEffectDegree()>0)
				{
					
					System.out.println("in for first if: " + action.getEffectDegree());
					efficientAct = action;
					
				}
				
				else if(efficientAct != null && efficientAct.getEffectDegree()/efficientAct.getCost() < action.getEffectDegree()/action.getCost())
				{
					System.out.println("in for second if: " + action.getEffectDegree());
					efficientAct = action;
				}
			}
			
		}
		else if(((String) diagnosis.get(factorNum)).split(" ")[0].equals("High"))
		{
			System.out.println("in High if");
//			efficientAct = factorStream.filter(info->info.getEffectDegree()>0)
//					.max(Comparator.comparing(info -> info.getEffectDegree()/info.getCost())).get();
			for(ActionInfo action : actionList){
				if(efficientAct == null && action.getEffectDegree()<0)
				{
					System.out.println("in for first if: " + action.getEffectDegree());
					efficientAct = action;
				}
					
				else if(efficientAct != null && efficientAct.getEffectDegree()/efficientAct.getCost() > action.getEffectDegree()/action.getCost())
				{
					System.out.println("in for second if: " + action.getEffectDegree());
					efficientAct = action;
				}
			}
		}
		
		return efficientAct;
	}
	private ArrayList<ActionInfo> makeDeviceInfo(String tempLogic,String humiLogic,String brightLogic,Room room){
		ArrayList<ActionInfo> deviceInfo = new ArrayList<ActionInfo>();
		
		ArrayList<Device> deviceList = room.getDevices();
		for(int deviceNum = 0; deviceNum < deviceList.size();deviceNum++){
			Device device = deviceList.get(deviceNum);
			for(ActBehavior action : device.getActions()){
				ArrayList<Double> effectDegrees = action.getEffectDegrees();
				if(tempLogic.equals("Low") && effectDegrees.get(0) > 0)
					deviceInfo.add(new ActionInfo(deviceNum, action.getName(), effectDegrees.get(0),0,action.getCost()));
				if(tempLogic.equals("High") && effectDegrees.get(0) < 0)
					deviceInfo.add(new ActionInfo(deviceNum, action.getName(), effectDegrees.get(0),0,action.getCost()));
				if(humiLogic.equals("Low")&&effectDegrees.get(1) > 0)
					deviceInfo.add(new ActionInfo(deviceNum, action.getName(), effectDegrees.get(1),1,action.getCost()));
				if(humiLogic.equals("High")&&effectDegrees.get(1) < 0 )
					deviceInfo.add(new ActionInfo(deviceNum, action.getName(), effectDegrees.get(1),1,action.getCost()));
				if(brightLogic.equals("Low")&&effectDegrees.get(2)>0)
					deviceInfo.add(new ActionInfo(deviceNum, action.getName(), effectDegrees.get(2),2,action.getCost()));
				if(brightLogic.equals("High")&&effectDegrees.get(2)<0)
					deviceInfo.add(new ActionInfo(deviceNum, action.getName(), effectDegrees.get(2),2,action.getCost()));
			}
		}
		return deviceInfo;
	}
	private void drawEnv(Room room){
		Object drawService = bundleContext.getService(bundleContext.getServiceReference
				("selab.sogang.persondata.DrawService"));
		try {
			Method drawEnv = drawService.getClass().getDeclaredMethod("drawEnv", Double.class,Double.class,Double.class,Integer.class);
			try {
				drawEnv.invoke(drawService, room.getEnv().getTemp(),room.getEnv().getHumi(),room.getEnv().getBright(),room.getCost());
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private class ActionInfo{
		private int deviceNum;
		private String actionName;
		private double effectDegree;
		private int factorNum;
		private int cost;
		private int time =0;
		public ActionInfo(int deviceNum,String actionName, double effectDegree,int factorNum,int cost) {
			// TODO Auto-generated constructor stub
			this.deviceNum = deviceNum;
			this.actionName = actionName;
			this.effectDegree = effectDegree;
			this.factorNum = factorNum;
			this.cost = cost;
		}
		public String getActionName() {
			return actionName;
		}
		public int getDeviceNum() {
			return deviceNum;
		}
		public double getEffectDegree() {
			return effectDegree;
		}
		public int getFactorNum() {
			return factorNum;
		}
		public int getCost(){
			return cost;
		}
		@Override
		public boolean equals(Object obj) {
			// TODO Auto-generated method stub
			ActionInfo info = (ActionInfo) obj;
			if(info.getActionName().equals(actionName)&&info.getCost()==cost&&info.getEffectDegree()==effectDegree&&info.getFactorNum() == factorNum
					&&info.getDeviceNum() == deviceNum)
				return true;
			return false;
		}
		public int getTime() {
			return time;
		}
		public void setTime(int time) {
			this.time = time;
		}
	}
	
}

