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
	private String title;
	private List<String> picNames;

	public PageIndex(Chap chap, File chapDir) {
		cid = chap.getCid();
		if (chap.getTitle() != null && chap.getTitle().length() > 0) {
			title = chap.getTitle();
		} else {
//			switch (chap.getType()) {
//			case Chap.TYPE_EPISODE:
//				title = "第 " + chap.getIdx() + " 话";
//				break;
//			case Chap.TYPE_CHAPTER:
//				title = "第 " + chap.getIdx() + " 卷";
//				break;
//			case Chap.TYPE_LEGEND:
//				title = "番外 " + chap.getIdx() + " 话";
//				break;
//			}
			title = String.valueOf(chap.getIdx());
			chap.setTitle(title);
		}
		picNames = new ArrayList<String>();
		List<File> pics = new ArrayList<File>(FileUtil.listFiles(chapDir, new String[]{"jpg", "png"}, false));
		Collections.sort(pics, PIC_ORDER_COMPARATOR);
		for (File pic : pics) {
			picNames.add(pic.getName());
		}
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getPicNames() {
		return picNames;
	}

	public void setPicNames(List<String> picNames) {
		this.picNames = picNames;
	}
	@Override
	public boolean equals(Object o){
		if (o != null && o instanceof PageIndex) {
			if (this.cid != null) {
				return this.cid.equals(((PageIndex) o).getCid());
			}
			return ((PageIndex) o).getCid() == null;
		}
		return false;
	}
	@Override
	public int hashCode(){
		return this.cid.hashCode();
	}
}
