package asia.laevatein.buka;

import java.io.File;
import java.io.IOException;

import asia.laevatein.buka.service.BukaConvertService;
import asia.laevatein.buka.util.Config;
import asia.laevatein.buka.util.Config.Key;
import asia.laevatein.buka.util.FileUtil;
import asia.laevatein.buka.util.Log;

public class Main {

	public static final String PARAM_FLAG_CHAPORDER_DAT = "-c";
	public static final String PARAM_FLAG_BUKA_PARSER = "-b";
	public static final String PARAM_FLAG_OUTPUT_DIR = "-o";
	
	public static void main(String[] args) {
		try {
			parseArgs(args);
			checkConfig();
			execute();
		} catch (Exception e) {
			Log.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void parseArgs(String[] args) {
		if (args == null || args.length < 6) {
			throw new RuntimeException("Missing parameters.");
		}
		for (int i = 0; i < args.length; i++) {
			if (PARAM_FLAG_CHAPORDER_DAT.equals(args[i])) {
				if (i + 1 > args.length - 1) {
					throw new RuntimeException("Invalid parameters.");
				}
				File chapOrderFile = new File(args[++i]);
				Config.set(Key.BUKA_CHAPORDER_FILE_PATH, chapOrderFile.getAbsolutePath());
				Config.set(Key.INPUT_DIR_PATH, chapOrderFile.getParentFile().getAbsolutePath());
			} else if (PARAM_FLAG_BUKA_PARSER.equals(args[i])) {
				if (i + 1 > args.length - 1) {
					throw new RuntimeException("Invalid parameters.");
				}
				File bukaPaserDir = new File(args[++i]);
				File bukaExe = new File(bukaPaserDir, "buka.exe");
				File png2jpgExe = new File(bukaPaserDir, "png2jpg.exe");
				Config.set(Key.BUKA_PARSER_EXE_PATH, bukaExe.getAbsolutePath());
				Config.set(Key.BUKA_PNG2JPG_EXE_PATH, png2jpgExe.getAbsolutePath());
			} else if (PARAM_FLAG_OUTPUT_DIR.equals(args[i])) {
				if (i + 1 > args.length - 1) {
					throw new RuntimeException("Invalid parameters.");
				}
				Config.set(Key.OUTPUT_DIR_PATH, args[++i]);
			}
		}
			
	}
	
	public static void checkConfig() throws IOException {
		Log.info(Key.INPUT_DIR_PATH.toString());
		File inputDir = new File(Config.get(Key.INPUT_DIR_PATH));
		FileUtil.checkDir(inputDir, false);
		
		Log.info(Key.OUTPUT_DIR_PATH.toString());
		File outputDir = new File(Config.get(Key.OUTPUT_DIR_PATH));
		FileUtil.checkDir(outputDir, true);

		Log.info(Key.BUKA_PARSER_EXE_PATH.toString());
		File bukaParserExe = new File(Config.get(Key.BUKA_PARSER_EXE_PATH));
		FileUtil.checkFile(bukaParserExe, false);
		
		Log.info(Key.BUKA_PNG2JPG_EXE_PATH.toString());
		File bukaPng2jpgExe = new File(Config.get(Key.BUKA_PNG2JPG_EXE_PATH));
		FileUtil.checkFile(bukaPng2jpgExe, false);
		
		Log.info(Key.BUKA_CHAPORDER_FILE_PATH.toString());
		File chapOrderFile = new File(Config.get(Key.BUKA_CHAPORDER_FILE_PATH));
		FileUtil.checkFile(chapOrderFile, false);
	}
	
	public static void execute() throws IOException {
		BukaConvertService bukaConvertService = new BukaConvertService();
		bukaConvertService.execute();
	}
}
