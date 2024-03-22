package net.x3f200c.pala4linux.launcherpatch;

import java.lang.instrument.ClassFileTransformer;
import java.io.IOException;
import java.security.ProtectionDomain;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.CannotCompileException;

class LauncherPatcher implements ClassFileTransformer {
	private final String routeTargetClassName;

	public LauncherPatcher(String routerClassName) {
		this.routeTargetClassName = routerClassName;
    }

	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> redefinedClass, ProtectionDomain protectionDomain, byte[] classBuffer) {
		byte[] bytecode = classBuffer;

		ClassPool cp = ClassPool.getDefault();

		String finalRouterClassName = this.routeTargetClassName.replaceAll("\\.", "/");

		if (className.equals(finalRouterClassName)) {
			try {
				CtClass crClass = cp.get("fr.paladium.router.route.common.CommonRoute");
				CtMethod osGetterMethod = crClass.getDeclaredMethod("handleGetOS");

				osGetterMethod.setBody("{ return fr.paladium.router.handler.CefRouteHandler.create().success(\"os\", \"windows\"); }");

				bytecode = crClass.toBytecode();
				crClass.detach();
			} catch(NotFoundException | CannotCompileException | IOException e) {

			}
		}

		return bytecode;
	}
}
