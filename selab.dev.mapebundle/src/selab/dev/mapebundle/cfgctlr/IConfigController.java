package selab.dev.mapebundle.cfgctlr;

import java.util.ArrayList;
import java.util.List;
import org.osgi.framework.BundleContext;

public interface IConfigController {
	public void reconfigure(BundleContext bundleContext, ArrayList strategies,List curUBM);
}
