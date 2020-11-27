package starterserver;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		inria.smarttools.componentsmanager.World.getInstance().setWorldFile(
				"resources:starterserver/resources/peerReview.world");
		/*inria.smarttools.componentsmanager.World.getInstance().setWorldFile(
				"resources:starterserver/resources/processClaim.world");*/
		inria.smarttools.componentsmanager.World.getInstance().loadWorld();
		final Bundle[] bundles = context.getBundles();
		for (final Bundle bundle : bundles) {
			if (bundle.getSymbolicName().equals("componentsManager")) {
				bundle.start();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
