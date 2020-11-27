package p2pTinyWfMS;

import p2pTinyWfMS.P2pTinyWfMSContainer;
import inria.smarttools.core.component.Container;
import inria.smarttools.core.component.STGenericActivator;

public class Activator extends STGenericActivator {

	public Activator() {
		resourceFileName = "/p2pTinyWfMS/resources/p2pTinyWfMS.cdml";
		bundleName = "p2pTinyWfMS";
	}

	@Override
	public Container createComponent(final String componentId) {
		final P2pTinyWfMSContainer container = new P2pTinyWfMSContainer(
				componentId, resourceFileName);
		registerContainer(container);
		return container;
	}

}
