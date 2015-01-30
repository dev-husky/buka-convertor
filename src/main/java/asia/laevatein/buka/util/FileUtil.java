package asia.laevatein.buka.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import asia.laevatein.buka.Main;

public class FileUtil extends FileUtils{

	public static void checkFile(File file, boolean create) throws IOException {
		if (!file.exists()) {
			if (create) {
				file.createNewFile();
				Log.info("Create file: " + file.getAbsolutePath());
			} else {
				throw new RuntimeException("Check file failed: \"" + file.getAbsolutePath() + "\" not found.");
			}
		}
		if (!file.isFile()) {
			throw new RuntimeException("Check file failed: \"" + file.getAbsolutePath() + "\" is not a file.");
		}
	}
	
	public static void checkDir(File dir, boolean create) {
		if (!dir.exists()) {
			if (create) {
				dir.mkdirs();
				Log.info("Create directory: " + dir.getAbsolutePath());
			} else {
				throw new RuntimeException("Check directory failed: \"" + dir.getAbsolutePath() + "\" not found.");
			}
		}
		if (!dir.isDirectory()) {
			throw new RuntimeException("Check directory failed: \"" + dir.getAbsolutePath() + "\" is not a directory.");
		}
	}
	
	public static String readResourceFileAsString(String resource) throws IOException {
		return IOUtils.toString(Main.class.getResourceAsStream(resource), "UTF-8");
	}
	
	public static byte[] readResourceFileToByteArray(String resource) throws IOException {
		return IOUtils.toByteArray(Main.class.getResourceAsStream(resource));
	}
	
}
