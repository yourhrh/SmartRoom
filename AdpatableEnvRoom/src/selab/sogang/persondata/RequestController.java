package selab.sogang.persondata;

import org.osgi.framework.BundleContext;

import selab.sogagn.adpatableenvroom.R;
import selab.sogang.adpatableenvroom.MainActivity;
import selab.sogang.bundle.BundleController;

public class RequestController {
	private BundleContext felixContext;
	private BundleController bundleController;
	private boolean startBundle = false;
	
	
	private boolean prefState = false;
	
	public RequestController(BundleController bundleController) {
		// TODO Auto-generated constructor stub
		this.bundleController = bundleController;
		this.felixContext = bundleController.getFelix().getBundleContext();
		
	}
	
	public void setPreference(double lowTemp,double highTemp,double lowHumi,double highHumi
			,double lowBright, double highBright)
	{
	
		
		//Service�� ������ ��� �Ǿ� ���� ������ ���� ��� ��ϵǾ� ������ Change
		if(prefState == false ){
			felixContext.registerService(Preference.class,new Preference(lowTemp, highTemp,
					lowHumi, highHumi, lowBright, highBright), null);
			
			prefState = true;
			
		}
		else
		{
			Preference servicePref = felixContext.getService(felixContext
					.getServiceReference(Preference.class));
			servicePref.changePref(lowTemp, highTemp, lowHumi, highHumi, lowBright, highBright);
		}
		drawPreference(lowTemp, highTemp, lowHumi, highHumi, lowBright, highBright);
			
	}
	
	//���ο� Preference�� �Բ� ���� 
	public void startAdaptation(double lowTemp,double highTemp,double lowHumi,double highHumi
			,double lowBright, double highBright)
	{
		setPreference(lowTemp, highTemp, lowHumi, highHumi, lowBright, highBright);
		startAdaptation();
	}
	//���� Preference���� ����
	public void startAdaptation(){
		// TODO Start MapeBundle
//		felixContext.getService(felixContext.getService(arg0))
		try {
			if(startBundle == true)
				bundleController.deactiveBundle("mapebundle");
			bundleController.installAndStartBundle(R.raw.mapebundle, "mapebundle");
			startBundle = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void drawPreference(double lowTemp,double highTemp,double lowHumi,double highHumi
			,double lowBright, double highBright){
		
		String prefString = "Preference : \n" + "Temperature : " + lowTemp + " ~ " + highTemp +"\n" 
				+ "Humidity : " + lowHumi + " ~ " + highHumi + "\nBrightness : " + lowBright + " ~ " + highBright;
		MainActivity.preferenceView.setText(prefString);
	}
	

}
