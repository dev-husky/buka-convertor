package asia.laevatein.buka.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import asia.laevatein.buka.model.ChapOrder.Chap;
import asia.laevatein.buka.util.FileUtil;

public class PageIndex {

	private static final Comparator<File> PIC_ORDER_COMPARATOR = new Comparator<File>() {
		public int compare(File f1, File f2) {
			return f1.getName().compareTo(f2.getName());
		}
	};
	
	private String cid;
	private List<String> picNames;

	public PageIndex(Chap chap, File chapDir) {
		cid = chap.getCid();
		picNames = new ArrayList<String>();
		List<File> pics = new ArrayList<File>(FileUtil.listFiles(chapDir, new String[]{"jpg", "png"}, false));
		Collections.sort(pics, PIC_ORDER_COMPARATOR);
		for (File pic : pics) {
			if (pic.getName().endsWith(".dat")) {
				pic.delete();
				continue;
			}
			picNames.add(pic.getName());
		}
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public List<String> getPicNames() {
		return picNames;
	}

	public void setPicNames(List<String> picNames) {
		this.picNames = picNames;
	}

}
