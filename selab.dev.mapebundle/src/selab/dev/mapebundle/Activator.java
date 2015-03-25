package selab.dev.mapebundle;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import selab.dev.mapebundle.ctxmonitor.ContextMonitor; 
import selab.dev.mapebundle.ctxmonitor.IContextMonitor;

public class Activator implements BundleActivator {

	private static BundleContext context;
	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		bundleContext.registerService(IContextMonitor.class.getName(),new ContextMonitor(bundleContext), null);
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
