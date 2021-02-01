package com.cykj.pos.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class FileUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public static String[] list(String dir, String... exts) {
        try {
            URL url = getUrl(dir);
            if (url == null) {
                return null;
            }
            File d = new File(url.toURI());
            if (!d.exists()) {
                return null;
            }
            return d.list(new FileextFilter(exts));
        } catch (MalformedURLException e) {
            logger.error("", e);
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            logger.error("", e);
            throw new RuntimeException(e);
        }
    }

    public static URL getUrl(String location) throws MalformedURLException {
        if (location.startsWith("classpath:")) {
            String cp = location.substring(10);
            URL url = getClassLoader().getResource(cp);
            return url;
        }
        int idx = location.indexOf(":");
        if (idx <= 2) {
            File file = new File(location);
            return file.toURI().toURL();
        }
        return new URL(location);
    }

    private static ClassLoader getClassLoader() {
        ClassLoader cl = FileUtil.class.getClassLoader();
        if (cl == null) {
            cl = ClassLoader.getSystemClassLoader();
        }
        return cl;
    }

    static class FileextFilter implements FilenameFilter {
        private String[] exts;

        public FileextFilter(String... exts) {
            this.exts = exts;
        }

        public boolean accept(File dir, String name) {
            if ((exts == null) || (exts.length == 0)) {
                return true;
            }
            for (String ext : exts) {
                if (name.toLowerCase().endsWith("." + ext.toLowerCase())) {
                    return true;
                }
            }
            return false;
        }
    }
}
