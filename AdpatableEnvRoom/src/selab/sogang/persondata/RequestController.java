package selab.sogang.persondata;

import org.osgi.framework.BundleContext;
import selab.sogang.bundle.BundleController;

public class RequestController {
	private BundleContext felixContext;
	
	
	
	
	public RequestController(BundleController bundleController) {
		// TODO Auto-generated constructor stub
		this.felixContext = bundleController.getFelix().getBundleContext();
	}
	
	public void setPreference(double lowTemp,double highTemp,double lowHumi,double highHumi
			,double lowBright, double highBright)
	{
	
		Preference servicePref = felixContext.getService(felixContext
				.getServiceReference(Preference.class));
		//Service가 이전에 등록 되어 있지 않으면 새로 등록 등록되어 있으면 Change
		if(servicePref == null ){
			felixContext.registerService(Preference.class,new Preference(lowTemp, highTemp,
					lowHumi, highHumi, lowBright, highBright), null);
		}
		else
		{
			servicePref.changePref(lowTemp, highTemp, lowHumi, highHumi, lowBright, highBright);
		}
			
	}
	//새로운 Preference와 함꼐 시작 
	public void startAdaptation(double lowTemp,double highTemp,double lowHumi,double highHumi
			,double lowBright, double highBright)
	{
		setPreference(lowTemp, highTemp, lowHumi, highHumi, lowBright, highBright);
		startAdaptation();
	}
	//현재 Preference에서 시작
	public void startAdaptation()
	{
		// TODO Start MapeBundle
		
	}
	

}
