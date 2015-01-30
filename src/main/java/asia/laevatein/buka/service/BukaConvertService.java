package asia.laevatein.buka.service;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import asia.laevatein.buka.model.ChapOrder;
import asia.laevatein.buka.model.ChapOrder.Chap;
import asia.laevatein.buka.util.BukaUtil;
import asia.laevatein.buka.util.Config;
import asia.laevatein.buka.util.Config.Key;
import asia.laevatein.buka.util.FileUtil;
import asia.laevatein.buka.util.Log;

import com.google.gson.Gson;

public class BukaConvertService {

	private ChapOrder bukaChapOrder;
	
	public void loadingBukaChapOrderDat() throws IOException {
		String bukaChapOrderStr = FileUtil.readFileToString(new File(Config.get(Key.BUKA_CHAPORDER_FILE_PATH)));
		bukaChapOrder = new Gson().fromJson(bukaChapOrderStr, ChapOrder.class);
		Collections.sort(bukaChapOrder.getLinks());
	}
	
	public void execute() throws IOException  {
		loadingBukaChapOrderDat();
		Log.info("[" + bukaChapOrder.getName() + "] Start converting  ...");
		File outputDir = parepareOutputDir();
		
		File outputPicDir = new File(outputDir, "pics");
		FileUtil.checkDir(outputPicDir, true);
		
		for (Chap chap : bukaChapOrder.getLinks()) {
			Log.info("[Chap " + chap.getCid() + "] Start ...");
			File chapDir = new File(outputPicDir, chap.getCid());
			if (!chapDir.exists()) {
				chapDir.mkdirs();
				BukaUtil.convert(chap, new File(Config.get(Key.INPUT_DIR_PATH)), chapDir);
			}
			BukaUtil.png2jpg(chap, chapDir);
			Log.info("[Chap " + chap.getCid() + "] Completed");
		}
		
		HtmlService htmlService = new HtmlService(outputDir, bukaChapOrder);
		htmlService.generateHtml();
		Log.info("[" + bukaChapOrder.getName() + "] Completed");
	}
	
	private File parepareOutputDir() {
		File inputDir = new File(Config.get(Key.INPUT_DIR_PATH));
		File outputDir = new File(Config.get(Key.OUTPUT_DIR_PATH), "[" + inputDir.getName() + "][" + bukaChapOrder.getName() + "]");
		FileUtil.checkDir(outputDir, true);
		return outputDir;
	}
	
}
