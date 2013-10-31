package com.vdxp.demon_front.core;

public interface PlatformSupport {

	public String getPlatformId();

	public void initializePlatform();

	/** Hacks around libGDX issue 1640 */
	public float fixFloat(final float f);

}
