package CollaborationMonitor;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import CollaborationMonitor.service.UBMGeneratorService;

public class Activator implements BundleActivator {

	private static BundleContext context;
	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		bundleContext.registerService(UBMGeneratorService.class.getName(),new UBMGeneratorServiceImpl(bundleContext), null);
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
