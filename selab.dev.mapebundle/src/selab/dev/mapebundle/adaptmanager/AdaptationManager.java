package selab.dev.mapebundle.adaptmanager;

import ConfigurationComposer.service.UsabilityImprover;

import java.util.ArrayList;
import java.util.List;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import selab.dev.mapebundle.cfgctlr.ConfigController;

public class AdaptationManager implements IAdaptationManager {

	private static AdaptationManager instance;

	static {
		instance = new AdaptationManager();
	}

	public static AdaptationManager getInstance() {
		if(instance == null) {
			return new AdaptationManager();
		} else {
			return instance;
		}
	}


	//dependence code
	public void adapt(BundleContext bundleContext, ArrayList diagnosis,List currModel){		
		System.out.println("diagnosis:"+diagnosis);
		System.out.println("currModel:"+currModel);
		
		ArrayList streatgies = null;
		
////////////////////////////////Change/////////////////////////////////////
		ServiceReference ref = bundleContext.getServiceReference(UsabilityImprover.class.getName());
		if (ref != null)
		{
			System.out.println("Improver Bundle Find OK!");
			UsabilityImprover usabilityService = (UsabilityImprover) bundleContext.getService(ref);
			streatgies = usabilityService.adapt(diagnosis, currModel);
			bundleContext.ungetService(ref);
		}
////////////////////////////////Change/////////////////////////////////////
		
		if(streatgies != null)
			ConfigController.getInstance().reconfigure(bundleContext, streatgies, currModel);
	}

}
