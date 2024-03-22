package net.x3f200c.pala4linux.launcherpatch;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.lang.Class;
import java.lang.String;
import java.lang.Exception;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import net.x3f200c.pala4linux.launcherpatch.LauncherPatcher;

public class LauncherPatch {
	public static void premain(String agentArgs, Instrumentation inst) {
		try {
			prepareCertificates();
		} catch (Exception e) {
			System.out.println("[PalaLauncher4Linux] Failed to prepare certificates. ");
		}
		patch(inst);
	}
	public static void agentmain(String agentArgs, Instrumentation inst) {
		try {
			prepareCertificates();
		} catch (Exception e) {
			System.out.println("[PalaLauncher4Linux] Failed to prepare certificates. ");
		}
		patch(inst);
	}
	public static void prepareCertificates() throws Exception {
		URL apacheMavenRepository = new URL("https://repo.maven.apache.org");

		HttpsURLConnection connection = (HttpsURLConnection) apacheMavenRepository.openConnection();
		connection.connect();
		connection.disconnect();
	}
	public static void patch(Instrumentation inst) {
		Class<?> routeHandlerClass = null;

		try {
			routeHandlerClass = Class.forName("fr.paladium.router.route.common.CommonRoute");
		} catch(ClassNotFoundException e) {

		}

		LauncherPatcher dt = new LauncherPatcher(routeHandlerClass.getName());

		inst.addTransformer(dt, true);

		try {
			inst.retransformClasses(routeHandlerClass);
		} catch(UnmodifiableClassException e) {

		}
	}
}
