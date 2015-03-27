package selab.dev.mapebundle.ctxmonitor;

import CollaborationMonitor.service.UBMGeneratorService;

import java.util.List;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import selab.dev.mapebundle.adaptreasoner.AdaptationReasoner;

public class ContextMonitor implements IContextMonitor {

	private boolean isRunning = false;
	private BundleContext bundleContext;

	public ContextMonitor(BundleContext bundleContext) {

		activate();
		this.bundleContext = bundleContext;
		System.out.println("ContextMonitor: start");	
		Monitoring();
	}
	public void Monitoring() {
		
		System.out.println("ContextMonitor: Monitoring");
				
		List currModel = null;
		List designedModel = null;
		
////////////////////////////////Change/////////////////////////////////////
		ServiceReference ref = bundleContext.getServiceReference(UBMGeneratorService.class.getName());
		if (ref != null)
		{
			System.out.println("MAPE Bundle Find OK!");
			UBMGeneratorService ubmGenerator = (UBMGeneratorService) bundleContext.getService(ref);
			designedModel = ubmGenerator.getDesignedModel();
			currModel = ubmGenerator.genCurBM("/data/");
			bundleContext.ungetService(ref);
		}
////////////////////////////////Change/////////////////////////////////////
		
		if(currModel != null) {
			// metaData -> designModel : SharedPrefs 
			AdaptationReasoner.getInstance().reason(bundleContext, designedModel, currModel);
		}
	}
	public void activate() {
		isRunning = true;
	}

	public void deActivate() {
		isRunning = false;
	}

}
