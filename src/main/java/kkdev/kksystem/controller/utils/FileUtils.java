/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.controller.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author blinov_is
 */
public class FileUtils {


	// copy Directory method
	public boolean CopyDirectory(File source, File target, String SkipDir) throws IOException {
		// check first if source file exists or not
		if (!source.exists()) {
			return false;
		}
		if (source.isDirectory()) {
			if (!target.exists()) {
				target.mkdir();
			}

			File[] listFile = source.listFiles();
			for (File f : listFile) {
				File sourceFile = new File(source, f.getName());
				File outputFile = new File(target, f.getName());
                                //
                                if (f.getName().equals(SkipDir))
                                    continue;
                                //
				if (f.isDirectory()) {
					new FileUtils().CopyDirectory(sourceFile,
							outputFile,SkipDir);
				} else {
					new FileUtils().copyFile(sourceFile, outputFile);
				}
			}

		}

		return true;
	}

	// Copy file method
	public void copyFile(File input, File output) throws IOException {
		FileInputStream inputStream = new FileInputStream(input);
		// target file declaration
		FileOutputStream outputStream = new FileOutputStream(output);
		int lengthStream;
		byte[] buff = new byte[1024];
		while ((lengthStream = inputStream.read(buff)) > 0) {
			// writing to the target file contents of the source file
			outputStream.write(buff, 0, lengthStream);
		}
		outputStream.close();
		inputStream.close();
	}
       public static boolean deleteRecursive(File path) throws FileNotFoundException{
        if (!path.exists()) return true;
        
        boolean ret = true;
        if (path.isDirectory()){
            for (File f : path.listFiles()){
                ret = ret && FileUtils.deleteRecursive(f);
            }
        }
        return ret && path.delete();
    }
}
