package com.github.mrstampy.esp.openbci.subscription;

import java.util.EventListener;

public interface OpenBCIEventListener extends EventListener {

	void dataEventPerformed(OpenBCIEvent event);
}
