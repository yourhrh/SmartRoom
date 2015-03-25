package CollaborationAnalyzer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import CollaborationAnalyzer.service.BadSymptomService;

public class Activator implements BundleActivator {

	private static BundleContext context;
	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		bundleContext.registerService(BadSymptomService.class.getName(), new BadSymptomServiceImpl(bundleContext), null);
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
