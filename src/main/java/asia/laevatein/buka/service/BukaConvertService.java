package asia.laevatein.buka.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import asia.laevatein.buka.util.BukaUtil;
import asia.laevatein.buka.util.ChapOrder;
import asia.laevatein.buka.util.ChapOrder.Chap;
import asia.laevatein.buka.util.Config;
import asia.laevatein.buka.util.Config.Key;
import asia.laevatein.buka.util.FileUtil;

import com.google.gson.Gson;

public class BukaConvertService {

	private ChapOrder bukaChapOrder;
	private ChapOrder outputChapOrder;
	
	public void loadingBukaChapOrderDat() throws IOException {
		String bukaChapOrderStr = FileUtil.readFileAsString(new File(Config.get(Key.BUKA_CHAPORDER_FILE_PATH)));
		bukaChapOrder = new Gson().fromJson(bukaChapOrderStr, ChapOrder.class);
	}
	
	public void loadingOutputChapOrderDat() throws IOException {
		File outputChapOrderFile = new File(Config.get(Key.OUTPUT_CHAPORDER_FILE_PATH));
		FileUtil.checkFile(outputChapOrderFile, true);
		String outputChapOrderStr = FileUtil.readFileAsString(outputChapOrderFile);
		if (outputChapOrderStr.length() > 0) {
			outputChapOrder = new Gson().fromJson(outputChapOrderStr, ChapOrder.class);
		} else {
			outputChapOrder = new ChapOrder();
			outputChapOrder.setName(bukaChapOrder.getName());
			outputChapOrder.setLinks(new ArrayList<Chap>(0));
		}
	}
	
	public void execute() throws IOException  {
		loadingBukaChapOrderDat();
		loadingOutputChapOrderDat();
		
		File outputDir = new File(Config.get(Key.OUTPUT_DIR_PATH));
		File comicDir = new File(outputDir, bukaChapOrder.getName());
		FileUtil.checkDir(comicDir, true);
		
		for (Chap chap : bukaChapOrder.getLinks()) {
			if (!outputChapOrder.getLinks().contains(chap)) {
				BukaUtil.convert(chap);
				break;
			}
		}
	}
	
	
}
