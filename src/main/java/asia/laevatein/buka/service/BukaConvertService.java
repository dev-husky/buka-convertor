package asia.laevatein.buka.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import asia.laevatein.buka.util.BukaUtil;
import asia.laevatein.buka.util.ChapOrder;
import asia.laevatein.buka.util.ChapOrder.Chap;
import asia.laevatein.buka.util.Config;
import asia.laevatein.buka.util.Config.Key;
import asia.laevatein.buka.util.FileUtil;
import asia.laevatein.buka.util.HtmlUtil;
import asia.laevatein.buka.util.Log;

import com.google.gson.Gson;

public class BukaConvertService {

	private ChapOrder bukaChapOrder;
	
	public void loadingBukaChapOrderDat() throws IOException {
		String bukaChapOrderStr = FileUtil.readFileToString(new File(Config.get(Key.BUKA_CHAPORDER_FILE_PATH)));
		bukaChapOrder = new Gson().fromJson(bukaChapOrderStr, ChapOrder.class);
		Collections.sort(bukaChapOrder.getLinks());
	}
	
//	public void loadingOutputChapOrderDat() throws IOException {
//		File outputChapOrderFile = new File(Config.get(Key.OUTPUT_CHAPORDER_FILE_PATH));
//		FileUtil.checkFile(outputChapOrderFile, true);
//		String outputChapOrderStr = FileUtil.readFileAsString(outputChapOrderFile);
//		if (outputChapOrderStr.length() > 0) {
//			outputChapOrder = new Gson().fromJson(outputChapOrderStr, ChapOrder.class);
//		} else {
//			outputChapOrder = new ChapOrder();
//			outputChapOrder.setName(bukaChapOrder.getName());
//			outputChapOrder.setLinks(new ArrayList<Chap>(0));
//		}
//	}
	
	public void execute() throws IOException  {
		loadingBukaChapOrderDat();
		Log.info("Start converting [" + bukaChapOrder.getName() + "] ...");
		File outputDir = parepareOutputDir();
		
		File outputPicDir = new File(outputDir, "pics");
		FileUtil.checkDir(outputPicDir, true);
		
		for (Chap chap : bukaChapOrder.getLinks()) {
			File chapDir = new File(outputPicDir, chap.getCid());
			if (chapDir.exists()) {
				continue;
			}
			chapDir.mkdirs();
			BukaUtil.convert(chap, new File(Config.get(Key.INPUT_DIR_PATH)), chapDir);
		}
		HtmlUtil.generateHtml(outputDir, bukaChapOrder);
		Log.info("Converting [" + bukaChapOrder.getName() + "] finished");
	}
	
	private File parepareOutputDir() {
		File inputDir = new File(Config.get(Key.INPUT_DIR_PATH));
		File outputDir = new File(Config.get(Key.OUTPUT_DIR_PATH), "[" + inputDir.getName() + "][" + bukaChapOrder.getName() + "]");
		FileUtil.checkDir(outputDir, true);
		return outputDir;
	}
	
}
