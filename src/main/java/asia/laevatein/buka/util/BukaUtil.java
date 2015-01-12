package asia.laevatein.buka.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.io.IOUtils;

import asia.laevatein.buka.Main;
import asia.laevatein.buka.util.ChapOrder.Chap;
import asia.laevatein.buka.util.Config.Key;

import com.google.gson.Gson;

public class BukaUtil {

	private static final String CMD = "{BUKA_PARSER_EXE_PATH} {BUKA_FILE} {BUKA_OUTPUT_CHAP_DIR_PATH}";
	//private static final String INDEX_JS = "index[{CID}] = {CID_INDEX};";
	private static final String[] VIEW_FILE_EXT = new String[]{"view"};
	//private static final String[] PIC_FILE_EXT = new String[]{"png", "jpg", "bmp"};
	private static final Comparator<File> PIC_ORDER_COMPARATOR = new Comparator<File>() {
		public int compare(File f1, File f2) {
			return f1.getName().compareTo(f2.getName());
		}
	};
	
	
	public static boolean convert(Chap chap, File inputDir, File outputDir) throws IOException {
		Log.info("Converting Chap: " + chap.getCid());
		File bukaResource = new File(inputDir, chap.getCid() + ".buka");
		if (!bukaResource.exists()) {
			// chap不是buka文件，可能是文件夹
			bukaResource = new File(inputDir, chap.getCid());
			if (!bukaResource.exists() || !bukaResource.isDirectory()) {
				// chap也不是文件夹
				throw new RuntimeException("Buka resource file not found");
			}
		}
		List<String> pageIndex = new ArrayList<String>();
//		if (bukaResource.isDirectory()) {
//			Log.info("Found resource directory: " + bukaResource.getAbsolutePath());
//			List<File> pics = new ArrayList<File>(FileUtil.listFiles(bukaResource, VIEW_FILE_EXT, false));
//			Collections.sort(pics, PIC_ORDER_COMPARATOR);
//			for (File pic : pics) {
//				File newFile = new File(outputDir, removeViewExt(pic.getName()));
//				FileUtil.copyFile(pic, newFile);
//				pageIndex.add(newFile.getName());
//			}
//			
//		} else {
			Log.info("Found resource : " + bukaResource.getAbsolutePath());
			File bukaParserExe = new File(Config.get(Key.BUKA_PARSER_EXE_PATH));
			String cmd = CMD.replace("{BUKA_PARSER_EXE_PATH}", bukaParserExe.getAbsolutePath())
					.replace("{BUKA_FILE}", bukaResource.getAbsolutePath())
					.replace("{BUKA_OUTPUT_CHAP_DIR_PATH}", outputDir.getAbsolutePath());
			
	        try {  
	        	Process p = Runtime.getRuntime().exec(cmd);  
	        	InputStream is = p.getErrorStream(); 
	        	while (is.read() != -1) {
	        	}
	        } catch (IOException e) {  
	          e.printStackTrace();  
	        }
	        List<File> pics = new ArrayList<File>(FileUtil.listFiles(outputDir, null, false));
			Collections.sort(pics, PIC_ORDER_COMPARATOR);
			for (File pic : pics) {
				if (pic.getName().endsWith(".dat")) {
					pic.delete();
					continue;
				}
				pageIndex.add(pic.getName());
			}
//		}
		HtmlUtil.generateChap(outputDir, pageIndex);
		
		
////		File bukaOutputDir = new File(Config.get(Key.BUKA_OUTPUT_DIR_PATH));
////		File bukaOutputChapDir = new File(bukaOutputDir, chap.getCid());
		
//		cmd = cmd.replace("{BUKA_OUTPUT_CHAP_DIR_PATH}", bukaOutputChapDir.getAbsolutePath());
//		
		
		return true;
	}
	
	private static String removeViewExt(String oldFileName) {
		if (!oldFileName.endsWith("." + VIEW_FILE_EXT[0])) {
			throw new RuntimeException("Not a view file: " + oldFileName);
		}
		return oldFileName.substring(0, oldFileName.indexOf("." + VIEW_FILE_EXT[0]));
	}
}
