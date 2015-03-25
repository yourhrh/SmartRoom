package ConfigurationComposer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import ConfigurationComposer.service.UsabilityImprover;

public class Activator implements BundleActivator {

	private static BundleContext context;
	public static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		bundleContext.registerService(UsabilityImprover.class.getName(),new UsabilityImproverImpl(bundleContext), null);
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
