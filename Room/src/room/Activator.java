package room;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import room.info.Room;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private Room room;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		//초기 룸 설정 및 등록
		room = new Room(26, 40, 30);
		context.registerService(Room.class,room , null);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
