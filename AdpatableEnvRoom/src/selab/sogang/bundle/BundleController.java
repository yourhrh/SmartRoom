package selab.sogang.bundle;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.felix.framework.Felix;
import org.apache.felix.framework.util.FelixConstants;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;

import selab.sogagn.adpatableenvroom.R;
import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;

public class BundleController {

	private String absolutePath;
	private Resources res;

	private File bundlesDir;
	private File newBundlesDir;
	private File cacheDir;

	private Properties felixProperties;
	private Felix felix;
	
	public BundleController(Activity androidContext,String absolutePath,Resources res, Object effectorParam) {
		this.absolutePath = absolutePath;
		this.res = res;
		
		//directory �뜝�룞�삕�뜝�룞�삕
		makeDir();

		//fliex �뜝�떗源띿삕�솕 context �뜝�룞�삕�뜝�룞�삕 
		makeFelix();
//		getFelix().getBundleContext().registerService(DrawService.class.getName(),new DrawUI(androidContext), null);
		
		
		/*
		getFelix().getBundleContext().registerService(
				IEffector.class.getName(), new Effector((TabView) effectorParam), null);
		*/
		/* install bundles */
		
	
	}
	
	
	public void setEffector(Object effectorParam) {
		
	}

	public void installBundles()  {
		
		
		try {
//			installAndStartBundle(R.raw.hostactivator,"hostactivator");
//			
//			/* User Behavior Model占쎈퓠占쎄퐣 �겫袁⑷퐤 野껉퀗�궢 占쎈섯占쎈선占쎌긾 */
//			Class[] classes = {Bundle.class};
//			Object[] objects = { felix.getBundle() };
//			startServiceMethod("HostActivator","hostactivator.HostActivator" ,"setFelix"
//					, classes, objects);
//			
//			
//			installAndStartBundle(R.raw.parameterobjects, "parameterobjects");
//			installAndStartBundle(R.raw.badsymptomcheckerservices, "badsymptomcheckerservices");
//			installAndStartBundle(R.raw.ubmgeneratorservice, "ubmgeneratorservice");
//			installAndStartBundle(R.raw.usabilityimproverservice, "usabilityimproverservice");
//			installAndStartBundle(R.raw.mapebundleservices, "mapebundleservices");
//			
//			
//			installAndStartBundle(R.raw.environmentsservice, "environmentsservice");
//			installAndStartBundle(R.raw.agentsservice, "agentsservice");
//			
//			installAndStartBundle(R.raw.agents, "agents");
//			installAndStartBundle(R.raw.environments, "environments");
//			
//			
//			installAndStartBundle(R.raw.configurationcomposer, "configurationcomposer");
//			installAndStartBundle(R.raw.nulladaptreasoner, "nulladaptreasonser");
//			installAndStartBundle(R.raw.collaborationmonitor, "collaborationmonitor");
//			
//			installAndStartBundle(, bundleName);
			installAndStartBundle(R.raw.room, "room");
			installAndStartBundle(R.raw.configurationcomposerservice, "configurationcomposerservice");
			installAndStartBundle(R.raw.collaborationanalyerservice, "collaborationanalyerservice");
			installAndStartBundle(R.raw.collaborationmonitorservice, "collaborationmonitorservice");
			installAndStartBundle(R.raw.configurationcomposer, "configurationcompser");
			installAndStartBundle(R.raw.collaborationanalyzer, "collaborationanalyzer");
			installAndStartBundle(R.raw.collaborationmonitor, "collaborationmonitor");
//			installAndStartBundle(R.raw.mapebundle, "mapebundle");
			
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void makeDir(){

		bundlesDir = new File(absolutePath+ "/felix/bundle");
		if(!bundlesDir.exists()){
			if(!bundlesDir.mkdirs()){
				throw new IllegalStateException("Unable to create bundlesDir dir");
			}
		}
		newBundlesDir = new File(absolutePath+"/felix/newbundle");
		if(!newBundlesDir.exists()){
			if(!newBundlesDir.mkdirs()){
				throw new IllegalStateException("Unable to create newBundleDir dir");
			}
		}
		cacheDir = new File(absolutePath+"/felix/cache");
		if (!cacheDir.exists()) {
			if (!cacheDir.mkdirs()) {
				throw new IllegalStateException("Unable to create felixcache dir");
			}
		}
	}
	//�뜝�룞�삕�뜝�룞�삕 �뜝�룞�삕移� 
	public void installAndStartBundle(int bundleId, String bundleName )throws Exception {
		InputStream is = res.openRawResource(bundleId);
		Bundle bundle = getFelix().getBundleContext()
				.installBundle(absolutePath+"felix/bundle/" + bundleName + ".jar", is);
		
		bundle.start();
		Log.i(bundleName,Integer.toString(bundle.getState()));
	}
	public synchronized void deactiveBundle(String bundleName)
	{
		Bundle bundle = getFelix().getBundleContext().getBundle(absolutePath+"felix/bundle/" + bundleName + ".jar");
		try {
			bundle.uninstall();
		} catch (BundleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void makeFelix() {
		//Properties �뜝�룞�삕�뜝�룞�삕
		felixProperties = new FelixConfig(absolutePath).getConfigProps();
				
		
		//FelixFrameWork �뜝�룞�삕�뜝�룞�삕�뜝�룞�삕 �뜝�룞�삕�뜝�룞�삕
		felix = new Felix(felixProperties);
		
		
		try {
			felix.start();

			Log.i("Felix �뜝�룞�삕�뜝�룞�삕","�뜝�룞�삕�뜝�룞�삕");
		} catch (BundleException e) {
			e.printStackTrace();
		}

	
		
	}


	public Bundle getFelix() {
		return felix;	
	}
	
	public Object startServiceMethod(String bundleSName,String serviceImplName,String methodgetName,Class[] parameterTypes,Object[] parameters) 
	{
		Object returnObject = null;
		for(Bundle b : felix.getBundleContext().getBundles())
		{
			if(b.getSymbolicName().equals(bundleSName))
			{
				for(ServiceReference<?> ref : b.getRegisteredServices())
				{
					Object service = b.getBundleContext().getService(ref);
					if(service.getClass().getName().equals(serviceImplName))
					{
						try {
							Method method = service.getClass().getDeclaredMethod(methodgetName, parameterTypes);
							try {
							returnObject = method.invoke(service, parameters);
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							}
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}



		return returnObject;
	}


}
