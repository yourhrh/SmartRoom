package CollaborationMonitor;

import CollaborationMonitor.service.UBMGeneratorService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;

import room.info.Room;

public class UBMGeneratorServiceImpl implements UBMGeneratorService {

	private BundleContext bundleContext = null;
	public UBMGeneratorServiceImpl(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}
	
	@Override
	public List genCurBM(String dirPath) {
		Room room = bundleContext.getService(bundleContext.getServiceReference(Room.class));
		if(room != null)
		{
			//temp,humi,bright¼ø 
			List<Double> roomEnv = new ArrayList<Double>();
			roomEnv.add(room.getEnv().getTemp());
			roomEnv.add(room.getEnv().getHumi());
			roomEnv.add(room.getEnv().getBright());
		
			return roomEnv;
		}
		return null;
	}

	@Override
	public List getDesignedModel() {
		
		Object requestPreference = bundleContext.getService(bundleContext
				.getServiceReference("selab.sogang.persondata.Preference"));
		List prefList =  null;
		try {
			Method getPrefs = requestPreference.getClass().getDeclaredMethod("getPrefs", null);
			try {
				prefList = (List) getPrefs.invoke(requestPreference, null);
				
				System.out.println("Monitor pref SIze : " + prefList.size() );
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return prefList;
	}
}
