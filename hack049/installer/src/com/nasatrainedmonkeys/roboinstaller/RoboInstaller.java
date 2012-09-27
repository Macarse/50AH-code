package com.nasatrainedmonkeys.roboinstaller;

import android.content.Context;
import android.util.Log;
import java.io.*;

public class RoboInstaller {
    Context ctx;

    public RoboInstaller(Context ctx) {
        this.ctx = ctx;
    }

    private static final String TAG = "RoboInstaller";

    public void installScalaLibs() {
        /*
         *  1. Make /system writeable
         *  2. Install files into application directory
         *  3. Change permissions
         *  4. Make links to ScalaLibs to /system/etc/permissions
         *  5. Make /system read-only
         */

        try {
            makeWritable();
            installFiles();
            makeLinks();
        } catch(Exception e) {
            throw new RuntimeException(e);
        } finally {
            makeReadOnly();
        }
    }

    private  static void makeWritable() {
        Log.d(TAG, "Make /system writable");
        sudo("mount -oremount,rw /system");
    }
    private  static void makeReadOnly() {
        Log.d(TAG, "Make /system read-only");
        sudo("mount -oremount,ro /system");
    }

    private static int[] resources = {
        R.raw.android_support_v4_dex,
        R.raw.android_support_v4_dex_desc,
        R.raw.guice_3_0_no_aop_dex,
        R.raw.guice_3_0_no_aop_dex_desc,
        R.raw.roboguice_2_0_dex,
        R.raw.roboguice_2_0_dex_desc,
        R.raw.javax_inject_dex,
        R.raw.javax_inject_dex_desc
    };

    private void installFiles() throws IOException {
        for (int resid: resources)
            installFile(resid);
    }
    private void makeLinks() {
        for (int resid: resources) {
            File path = fileForResource(resid);

            if (path.getName().endsWith("_desc.xml")) // descriptor
                sudo("ln -sf %s /system/etc/permissions/%s", path.getAbsolutePath(), path.getName());
        }
    }

    private static Runtime runtime = Runtime.getRuntime();
    private static void sudo(String cmdFormat, Object... args){
        String cmd = String.format(cmdFormat, args);
        try {
            Process proc = runtime.exec(new String[] {"su", "-c", cmd});
            int res = proc.waitFor();
            if (res != 0)
                throw new RuntimeException(String.format("Execution of cmd '%s' failed with exit code %d", cmd, res));
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Takes the resource with the given name and installs it into the files dir
     * @param resid
     */
    private void installFile(int resid) throws IOException {
        File targetFile = fileForResource(resid);
        Log.i(TAG, "Extracting file to "+targetFile.getAbsolutePath());
        FileOutputStream fos = new FileOutputStream(targetFile);
        InputStream is = ctx.getResources().openRawResource(resid);

        byte[] buffer = new byte[65000];

        while (is.available() > 0) {
            int read = is.read(buffer);
            fos.write(buffer, 0, read);
        }
        is.close();
        fos.close();

        sudo("chmod a+r "+targetFile.getAbsolutePath());
    }
    private File fileForResource(int resid) {
        String namePart = lastPart(ctx.getResources().getResourceName(resid));
        if (namePart.endsWith("_desc"))
            namePart = namePart + ".xml";
        else
            namePart = namePart + ".jar";

        return new File(ctx.getFilesDir(), namePart);
    }
    private String lastPart(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }
}
