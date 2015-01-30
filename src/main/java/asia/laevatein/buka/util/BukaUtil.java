package asia.laevatein.buka.util;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import asia.laevatein.buka.model.ChapOrder.Chap;
import asia.laevatein.buka.util.Config.Key;

public class BukaUtil {

	public static void convert(Chap chap, File inputDir, File outputDir) throws IOException {
		File bukaResource = new File(inputDir, chap.getCid() + ".buka");
		if (!bukaResource.exists()) {
			// chap不是buka文件，可能是文件夹
			bukaResource = new File(inputDir, chap.getCid());
			if (!bukaResource.exists() || !bukaResource.isDirectory()) {
				// chap也不是文件夹
				throw new RuntimeException("Buka resource file not found");
			}
		}
		
		Log.info("[Chap " + chap.getCid() + "] Found resource : " + bukaResource.getAbsolutePath());
		File bukaParserExe = new File(Config.get(Key.BUKA_PARSER_EXE_PATH));
		
		File tmpDir = new File(outputDir, "tmp");
		tmpDir.mkdirs();
		
		String[] cmd = new String[]{
				bukaParserExe.getAbsolutePath(),
				bukaResource.getAbsolutePath(),
				tmpDir.getAbsolutePath()
		};
		Command command = new Command(cmd);
		command.exec();
		
		Collection<File> pngFiles = FileUtil.listFiles(outputDir, new String[]{"png"}, true);
		for (File pngFile : pngFiles) {
			FileUtil.moveFileToDirectory(pngFile, outputDir, false);
		}
		for (File tmp : outputDir.listFiles()) {
			if (tmp.isDirectory()) {
				FileUtil.forceDelete(tmp);
			}
		}
		Log.info("[Chap " + chap.getCid() + "] Got " + FileUtil.listFiles(outputDir, new String[]{"png"}, false).size() + " PNG files");
	}
	

	public static void png2jpg(Chap chap, File outputDir) throws IOException {
		File bukaPng2jpgExe = new File(Config.get(Key.BUKA_PNG2JPG_EXE_PATH));
		String[] cmd = new String[] {
				bukaPng2jpgExe.getAbsolutePath(),
				outputDir.getAbsolutePath()
		};
		Command command = new Command(cmd);
		command.exec();
		Log.info("[Chap " + chap.getCid() + "] Got " + FileUtil.listFiles(outputDir, new String[]{"jpg"}, false).size() + " JPG files");
	}
}
