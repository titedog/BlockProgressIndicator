package net.titedog.bpi;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlockProgressIndicator implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("bpi");
	public static float currentBreakingProgress = 0;

	@Override
	public void onInitializeClient() {
		LOGGER.info("Initialized BPI.");
	}

	public static int getCurrentBreakingProgress() {
		return (int) (currentBreakingProgress * 15);
	}
}