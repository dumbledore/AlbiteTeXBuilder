/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package albitetexbuilder;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author albus
 */
public class Main {

    /**
     * @param args the command line arguments
     */
public static void main(final String[] args) {
        if (args.length == 1) {
            String language = args[0];

            List<String> text = new Vector<String>();

            try {
                // read text from Notepad UTF-8 file
                InputStream in = new FileInputStream(language + ".txt");
                try {
                    BufferedReader bufin =
                            new BufferedReader(
                            new InputStreamReader(in, "UTF-8"));
                    String s;
                    while ((s = bufin.readLine()) != null) {
                        // remove formatting character added by Notepad
                        s = s.replaceAll("\ufffe", "");
                        text.add(s);
                    }
                } finally {
                    in.close();
                }

                // write it for easy reading in J2ME
                OutputStream out = new FileOutputStream(language + ".tex");
                DataOutputStream dout = new DataOutputStream(out);
                try {
                    // first item is the number of strings
                    dout.writeShort(text.size());
                    // then the string themselves
                    for (String s : text) {
                        dout.writeUTF(s);
                    }
                } finally {
                    dout.close();
                }
            } catch (Exception e) {
                System.err.println("TextConverter: " + e);
            }
        } else {
            System.err.println("syntax: albtex <language-code>");
        }
    }
}
