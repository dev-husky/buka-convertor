package asia.laevatein.buka.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import asia.laevatein.buka.model.ChapOrder;
import asia.laevatein.buka.model.ChapOrder.Chap;
import asia.laevatein.buka.model.PageIndex;
import asia.laevatein.buka.util.FileUtil;

import com.google.gson.Gson;

public class HtmlService {
	
	private static final String PAGE_JS_PAGE_INDEX = "{pageIndex}";
	private static final String INDEX_HTML_TITLE = "{title}";

	private File baseDir;
	private File picDir;
	private File resDir;
	private File cssDir;
	private File jsDir;
	
	private ChapOrder chapOrder;
	private List<PageIndex> pageIndexList;
	
	public HtmlService(File baseDir, ChapOrder chapOrder) {
		this.chapOrder = chapOrder;
		this.baseDir = baseDir;
		this.picDir = new File(baseDir, "pics");
		this.resDir = new File(baseDir, "res");
		this.cssDir = new File(resDir, "css");
		this.jsDir = new File(resDir, "js");
		this.pageIndexList = new ArrayList<PageIndex>();
		
		FileUtil.checkDir(baseDir, false);
		FileUtil.checkDir(picDir, false);
		FileUtil.checkDir(resDir, true);
		FileUtil.checkDir(cssDir, true);
		FileUtil.checkDir(jsDir, true);
	}
	
	public void generateHtml() throws IOException {
		generatePageIndex();
		generateIndexHtml();
		generateRes();
	}
	
	private void generatePageIndex() throws IOException {
		List<Chap> chaps = chapOrder.getLinks();
		pageIndexList.clear();
		Collections.reverse(chaps);
		for (Chap chap : chaps) {
			File chapDir = new File(picDir, chap.getCid());
			FileUtil.checkDir(chapDir, false);
			pageIndexList.add(new PageIndex(chap, chapDir));
			chap.setPageIndex(pageIndexList.size() - 1);
		}
		
		Gson gson = new Gson();
		String pageIndexJsStr = FileUtil.readResourceFileAsString("/res/js/page.js");
		pageIndexJsStr = pageIndexJsStr.replace(PAGE_JS_PAGE_INDEX,  gson.toJson(pageIndexList));
		File pageIndexJsFile = new File(jsDir, "page.js");
		FileUtil.write(pageIndexJsFile, pageIndexJsStr);
		
		Collections.sort(chaps);
	}
	
	private void generateIndexHtml() throws IOException {
		String indexHtmlStr = FileUtil.readResourceFileAsString("/html/index.html");
		indexHtmlStr = indexHtmlStr.replace(INDEX_HTML_TITLE, chapOrder.getName());
		File indexHtmlFile = new File(baseDir, "index.html");
		FileUtil.write(indexHtmlFile, indexHtmlStr);
	}
	
	private void generateRes() throws IOException {
		// js file
		String jqueryFileStr = FileUtil.readResourceFileAsString("/res/js/jquery-1.10.2.min.js");
		File jqueryFile = new File(jsDir, "jquery-1.10.2.min.js");
		FileUtil.write(jqueryFile, jqueryFileStr);
		String indexJsStr = FileUtil.readResourceFileAsString("/res/js/index.js");
		File indexJsFile = new File(jsDir, "index.js");
		FileUtil.write(indexJsFile, indexJsStr);
		
		// css file
		String mainCSSFileStr = FileUtil.readResourceFileAsString("/res/css/main.css");
		File mainCSSFile = new File(cssDir, "main.css");
		FileUtil.write(mainCSSFile, mainCSSFileStr);
	}
}
