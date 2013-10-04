package framework.utills;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: Yan
 * Date: 04/10/13
 * Time: 15:19
 */
public class FileUtils {

    public static final void writeObjectToFile(Serializable obj, String filename) {
        try {
            //use buffering
            OutputStream file = new FileOutputStream(filename);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);
            try {
                output.writeObject(obj);
            } finally {
                output.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static final Object readObjectFromFile(String filename) {

        Object ret = null;
        try {
            //use buffering
            InputStream file = new FileInputStream(filename);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);
            try {
                ret = input.readObject();
            } finally {
                input.close();
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return ret;
    }
}
