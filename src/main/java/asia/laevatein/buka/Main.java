package asia.laevatein.buka;

import java.io.File;
import java.io.IOException;

import asia.laevatein.buka.service.BukaConvertService;
import asia.laevatein.buka.util.Config;
import asia.laevatein.buka.util.FileUtil;
import asia.laevatein.buka.util.Log;
import asia.laevatein.buka.util.Config.Key;

public class Main {

	public static void main(String[] args) {
		try {
			parseArgs(args);
			checkConfig();
			execute();
		} catch (Exception e) {
			Log.error(e.getMessage());
		}
	}

	public static void parseArgs(String[] args) {
		Config.set(Key.INPUT_DIR_PATH, "E:\\Private\\ibuka\\2187");
		Config.set(Key.BUKA_PARSER_DIR_PATH, "D:\\Downloads\\buka_2.2");
		Config.set(Key.BUKA_PARSER_EXE_PATH, "D:\\Downloads\\buka_2.2\\buka.exe");
		Config.set(Key.BUKA_CHAPORDER_FILE_PATH, "E:\\Private\\ibuka\\2187\\chaporder.dat");

		Config.set(Key.OUTPUT_DIR_PATH, "E:\\Private\\ibuka\\2187_output");
		Config.set(Key.OUTPUT_CHAPORDER_FILE_PATH, "E:\\Private\\ibuka\\2187_output\\chaporder.dat");
		Config.set(Key.BUKA_OUTPUT_DIR_PATH, "E:\\Private\\ibuka\\2187_output\\buka_output");
		
	}
	
	public static void checkConfig() throws IOException {
		Log.info(Key.INPUT_DIR_PATH.toString());
		File inputDir = new File(Config.get(Key.INPUT_DIR_PATH));
		FileUtil.checkDir(inputDir, false);
		
		Log.info(Key.OUTPUT_DIR_PATH.toString());
		File outputDir = new File(Config.get(Key.OUTPUT_DIR_PATH));
		FileUtil.checkDir(outputDir, true);

		Log.info(Key.BUKA_PARSER_DIR_PATH.toString());
		File bukaParserDir = new File(Config.get(Key.BUKA_PARSER_DIR_PATH));
		FileUtil.checkDir(bukaParserDir, false);
		
		Log.info(Key.BUKA_PARSER_EXE_PATH.toString());
		File bukaParserExe = new File(Config.get(Key.BUKA_PARSER_EXE_PATH));
		FileUtil.checkFile(bukaParserExe, false);
		
		Log.info(Key.BUKA_OUTPUT_DIR_PATH.toString());
		File bukaOutputDir = new File(Config.get(Key.BUKA_OUTPUT_DIR_PATH));
		FileUtil.checkDir(bukaOutputDir, true);
		
		Log.info(Key.BUKA_CHAPORDER_FILE_PATH.toString());
		File chapOrderFile = new File(Config.get(Key.BUKA_CHAPORDER_FILE_PATH));
		FileUtil.checkFile(chapOrderFile, false);
	}
	
	public static void execute() throws IOException {
		BukaConvertService bukaConvertService = new BukaConvertService();
		bukaConvertService.execute();
	}
}
