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
		//Service�� ������ ��� �Ǿ� ���� ������ ���� ��� ��ϵǾ� ������ Change
		if(servicePref == null ){
			felixContext.registerService(Preference.class,new Preference(lowTemp, highTemp,
					lowHumi, highHumi, lowBright, highBright), null);
		}
		else
		{
			servicePref.changePref(lowTemp, highTemp, lowHumi, highHumi, lowBright, highBright);
		}
			
	}
	//���ο� Preference�� �Բ� ���� 
	public void startAdaptation(double lowTemp,double highTemp,double lowHumi,double highHumi
			,double lowBright, double highBright)
	{
		setPreference(lowTemp, highTemp, lowHumi, highHumi, lowBright, highBright);
		startAdaptation();
	}
	//���� Preference���� ����
	public void startAdaptation()
	{
		// TODO Start MapeBundle
		
	}
	

}
