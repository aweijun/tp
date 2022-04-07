package seedu.trackermon.commons.core;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;

public class JarTools {

    private static File getClassLocation(Class cls, boolean trmjar) {
        ClassLoader clsldr; // class loader
        URL urlobj; // url object
        String exturl; // external form of URL
        String lwrurl; // lowercase external form of URL
        File rtnfil; // return file

        if ((clsldr = cls.getClassLoader()) == null) {
            clsldr = ClassLoader.getSystemClassLoader();
        }

        if ((urlobj = clsldr.getResource(cls.getName().replace('.', '/') + ".class")) == null) {
            return null;
        }

        exturl = urlobj.toExternalForm();
        lwrurl = exturl.toLowerCase();
        while (lwrurl.startsWith("jar:") || lwrurl.startsWith("file:/")) {
            if (lwrurl.startsWith("jar:")) {
                // strip encapsulating "jar:" and "!/..." from JAR url
                if (lwrurl.indexOf("!/") != -1) {
                    exturl = exturl.substring(4, (exturl.indexOf("!/")));
                } else { // strip encapsulating "jar:"
                    exturl = exturl.substring(4);
                }
            }
            if (lwrurl.startsWith("file:/")) {
                exturl = exturl.substring(6); // strip encapsulating "file:/"
                if (!exturl.startsWith("/")) {
                    exturl = ("/" + exturl);
                }
                while (exturl.length() > 1 && exturl.charAt(1) == '/') {
                    exturl = exturl.substring(1);
                }
            }
            lwrurl = exturl.toLowerCase();
        }
        try {
            exturl = java.net.URLDecoder.decode(exturl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        rtnfil = new File(exturl);
        if (lwrurl.endsWith(".class") || (trmjar && lwrurl.endsWith(".jar"))) {
            rtnfil = rtnfil.getParentFile();
        }
        if (rtnfil.exists()) {
            rtnfil = rtnfil.getAbsoluteFile();
        }
        return rtnfil;
    }

    public static String getClassLocationString(Class cls) {
        return getClassLocation(cls, true).toPath().toString();
    }

    /**
     * Check if application is running from IntelliJ
     * @return whether application is running from IntelliJ
     */
    public static boolean runningFromJarFile() {
        String resourcePath = JarTools.class.getResource("JarTools.class").toString();
        return resourcePath.startsWith("jar:") || resourcePath.startsWith("rsrc:");
    }
}
