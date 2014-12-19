package asia.laevatein.buka.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileUtil {

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
	
	public static String readFileAsString(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		StringBuilder sb = new StringBuilder();
		char[] chars = new char[4096];
		int read = -1;
		while ((read = br.read(chars)) > 0) {
			sb.append(chars, 0, read);
		}
		br.close();
		return sb.toString();
	}
	
}
