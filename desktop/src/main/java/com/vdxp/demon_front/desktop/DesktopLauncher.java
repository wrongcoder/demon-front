package com.vdxp.demon_front.desktop;

import java.io.File;
import java.net.URLDecoder;

public class DesktopLauncher {

	public static void main(final String[] args) throws Exception {
		if (Runtime.getRuntime().maxMemory() >= 256 * 1024 * 1024) {
			DesktopDriver.main(args);
		} else {
			final String javaHome = System.getProperty("java.home");
			final File javaBin = new File(new File(javaHome), "bin");

			final String jarUrl = DesktopLauncher.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			final String jarPath = URLDecoder.decode(jarUrl, "UTF-8");
			final File jarFile = new File(jarPath);

			final File home;
			if (jarFile.isFile()) {
				home = jarFile.getParentFile();
			} else {
				throw new IllegalStateException("No JAR");
			}

			final File javaw = new File(javaBin, "javaw.exe");
			final File java = new File(javaBin, "java");

			final ProcessBuilder processBuilder = new ProcessBuilder(
					javaw.exists() ? javaw.getPath() : java.getPath(),
					"-Xmx384m",
					"-jar",
					jarPath
			);
			processBuilder.directory(home);

			final Process process = processBuilder.start();
			System.exit(process.waitFor());
		}
	}

}
