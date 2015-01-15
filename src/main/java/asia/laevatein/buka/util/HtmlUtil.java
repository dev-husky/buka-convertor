package asia.laevatein.buka.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import asia.laevatein.buka.model.ChapOrder;
import asia.laevatein.buka.model.PageIndex;
import asia.laevatein.buka.model.ChapOrder.Chap;

import com.google.gson.Gson;

public class HtmlUtil {

	private static final String INDEX_TITLE = "{title}";
	private static final String INDEX_CHAP_ORDER = "{chapOrder}";
	
	private static final String CHAP_PAGES = "{pages}";
	
	private static final String PAGE_JS_PAGE_INDEX = "{pageIndex}";
	
	
	public static void generateHtml(File outputDir, ChapOrder bukaChapOrder) throws IOException {
		generatePageIndex(outputDir, bukaChapOrder);
		generateIndex(outputDir, bukaChapOrder);
		generateRes(outputDir);
	}
	
	public static void generatePageIndex(File outputDir, ChapOrder bukaChapOrder) {
		// pic folder
		File picDir = new File(outputDir, "pic");
		FileUtil.checkDir(picDir, true);
		
		// js folder
		
		List<PageIndex> pageIndexList = new ArrayList<PageIndex>();
		List<Chap> chaps = bukaChapOrder.getLinks();
		Collections.reverse(chaps);
		for (Chap chap : chaps) {
			File chapDir = new File(picDir, chap.getCid());
			FileUtil.checkDir(chapDir, true);
			pageIndexList.add(new PageIndex(chap, chapDir));
		}
		
	}
	
	public static void generateChap(File outputDir, List<String> pageIndex) throws IOException {
		Gson gson = new Gson();
		String chapHtmlStr = FileUtil.readResourceFileAsString("/html/chap.html");
		chapHtmlStr = chapHtmlStr.replace(CHAP_PAGES,  gson.toJson(pageIndex));
		File chapHtmlFile = new File(outputDir, "chap.html");
		FileUtil.write(chapHtmlFile, chapHtmlStr);
	}
	
	private static void generateIndex(File outputDir, ChapOrder bukaChapOrder) throws IOException {
		Gson gson = new Gson();
		String indexHtmlStr = FileUtil.readResourceFileAsString("/html/index.html");
		indexHtmlStr = indexHtmlStr.replace(INDEX_TITLE, bukaChapOrder.getName());
		indexHtmlStr = indexHtmlStr.replace(INDEX_CHAP_ORDER, gson.toJson(bukaChapOrder));
		File indexHtmlFile = new File(outputDir, "index.html");
		FileUtil.write(indexHtmlFile, indexHtmlStr);
	}
	
	private static void generateRes(File outputDir) throws IOException {
		// res folder
		File resDir = new File(outputDir, "res");
		FileUtil.checkDir(resDir, true);
		
		// js folder
		File jsDir = new File(resDir, "js");
		FileUtil.checkDir(jsDir, true);
		
		// css folder
		File cssDir = new File(resDir, "css");
		FileUtil.checkDir(cssDir, true);
		
		// js file
		String jqueryFileStr = FileUtil.readResourceFileAsString("/res/js/jquery-1.10.2.min.js");
		File jqueryFile = new File(jsDir, "jquery-1.10.2.min.js");
		FileUtil.write(jqueryFile, jqueryFileStr);
		String chapJsStr = FileUtil.readResourceFileAsString("/res/js/chap.js");
		File chapJsFile = new File(jsDir, "chap.js");
		FileUtil.write(chapJsFile, chapJsStr);
		
		// css file
		String mainCSSFileStr = FileUtil.readResourceFileAsString("/res/css/main.css");
		File mainCSSFile = new File(cssDir, "main.css");
		FileUtil.write(mainCSSFile, mainCSSFileStr);
	}
}
