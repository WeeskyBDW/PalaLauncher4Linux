package net.x3f200c.pala4linux.launcherpatch;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.lang.Class;
import java.lang.String;
import net.x3f200c.pala4linux.launcherpatch.LauncherPatcher;

public class LauncherPatch {
	public static void premain(String agentArgs, Instrumentation inst) {
		patch(inst);
	}
	public static void agentmain(String agentArgs, Instrumentation inst) {
		patch(inst);
	}
	public static void patch(Instrumentation inst) {
		Class<?> routeHandlerClass = null;
		ClassLoader routeHandlerClassLoader = null;

		try {
			routeHandlerClass = Class.forName("fr.paladium.router.route.common.CommonRoute");
			routeHandlerClassLoader = routeHandlerClass.getClassLoader();
		} catch(ClassNotFoundException e) {

		}

		LauncherPatcher dt = new LauncherPatcher(routeHandlerClass.getName(), routeHandlerClassLoader);

		inst.addTransformer(dt, true);

		try {
			inst.retransformClasses(routeHandlerClass);
		} catch(UnmodifiableClassException e) {

		}
	}
}
