package com.vibeit.util;

import android.content.Context;
import android.util.Log;

import java.io.*;

/**
 * @author Maria Dzyokh
 *         Writes/reads an object to/from a private local file
 */
public class LocalPersistStorage {

    /**
     * @param context
     * @param object
     * @param filename
     */
    public static void witeObjectToFile(Context context, Object object, String filename) {

        ObjectOutputStream objectOut = null;
        try {

            FileOutputStream fileOut = context.openFileOutput(filename, Context.MODE_PRIVATE);
            objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(object);
            fileOut.getFD().sync();

        } catch (IOException e) {
           // Log.e(LocalPersistStorage.class.getSimpleName(), e.getMessage(), e);
        } finally {
            if (objectOut != null) {
                try {
                    objectOut.close();
                } catch (IOException e) {
                    //Log.e(LocalPersistStorage.class.getSimpleName(), e.getMessage(), e);
                }
            }
        }
    }


    /**
     * @param context
     * @param filename
     * @return
     */
    public static Object readObjectFromFile(Context context, String filename) {

        ObjectInputStream objectIn = null;
        Object object = null;
        try {

            FileInputStream fileIn = context.getApplicationContext().openFileInput(filename);
            objectIn = new ObjectInputStream(fileIn);
            object = objectIn.readObject();

        } catch (FileNotFoundException e) {
            //Log.e(LocalPersistStorage.class.getSimpleName(), e.getMessage(), e);
        } catch (IOException e) {
           // Log.e(LocalPersistStorage.class.getSimpleName(), e.getMessage(), e);
        } catch (ClassNotFoundException e) {
           // Log.e(LocalPersistStorage.class.getSimpleName(), e.getMessage(), e);
        } finally {
            if (objectIn != null) {
                try {
                    objectIn.close();
                } catch (IOException e) {
                    //Log.e(LocalPersistStorage.class.getSimpleName(), e.getMessage(), e);
                }
            }
        }

        return object;
    }

    public static void deleteFile(Context context, String fileName) {
           File dir = context.getFilesDir();
           File file = new File(dir, fileName);
           file.delete();
    }
}
