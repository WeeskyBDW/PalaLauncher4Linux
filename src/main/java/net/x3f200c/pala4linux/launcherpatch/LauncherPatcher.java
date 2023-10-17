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
	private final String targetClassName;

	public LauncherPatcher(String className, ClassLoader classLoader) {
        this.targetClassName = className;
    }

	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> redefinedClass, ProtectionDomain protectionDomain, byte[] classBuffer) {
		byte[] bytecode = classBuffer;

		String finalTargetClassName = this.targetClassName.replaceAll("\\.", "/");
        if (!className.equals(finalTargetClassName)) {
            return bytecode;
        }

		if (className.equals(finalTargetClassName)) {
			try {
				ClassPool cp = ClassPool.getDefault();
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
