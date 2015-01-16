package asia.laevatein.buka.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

import asia.laevatein.buka.model.ChapOrder;
import asia.laevatein.buka.model.ChapOrder.Chap;
import asia.laevatein.buka.model.PageIndex;
import asia.laevatein.buka.util.FileUtil;

import com.google.gson.Gson;

public class HtmlService {
	
	private static final String PARAM_JS_PAGE_INDEX = "{pageIndex}";
	private static final String PARAM_JS_CHAP_ORDER = "{chapOrder}";

	private File baseDir;
	private File picDir;
	private File resDir;
	private File cssDir;
	private File jsDir;
	private File imgDir;
	
	private ChapOrder chapOrder;
	private List<PageIndex> pageIndexList;
	
	public HtmlService(File baseDir, ChapOrder chapOrder) {
		this.chapOrder = chapOrder;
		this.baseDir = baseDir;
		this.picDir = new File(baseDir, "pics");
		this.resDir = new File(baseDir, "res");
		this.cssDir = new File(resDir, "css");
		this.jsDir = new File(resDir, "js");
		this.imgDir = new File(resDir, "img");
		this.pageIndexList = new ArrayList<PageIndex>();
		
		FileUtil.checkDir(baseDir, false);
		FileUtil.checkDir(picDir, false);
		FileUtil.checkDir(resDir, true);
		FileUtil.checkDir(cssDir, true);
		FileUtil.checkDir(jsDir, true);
		FileUtil.checkDir(imgDir, true);
	}
	
	public void generateHtml() throws IOException {
		generateRes();
		generateParam();
		generateIndexHtml();
	}
	
	private void generateParam() throws IOException {
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
		String pageIndexJsStr = FileUtil.readResourceFileAsString("/res/js/param.js");
		pageIndexJsStr = pageIndexJsStr.replace(PARAM_JS_PAGE_INDEX,  gson.toJson(pageIndexList));
		pageIndexJsStr = pageIndexJsStr.replace(PARAM_JS_CHAP_ORDER,  gson.toJson(chapOrder));
		File pageIndexJsFile = new File(jsDir, "param.js");
		FileUtil.write(pageIndexJsFile, pageIndexJsStr);
		
		Collections.sort(chaps);
	}
	
	private void generateIndexHtml() throws IOException {
		String indexHtmlStr = FileUtil.readResourceFileAsString("/html/index.html");
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
		
		// img file
		byte[] bgFileByte = FileUtil.readResourceFileToByteArray("/res/img/bg.png");
		File bgFile = new File(imgDir, "bg.png");
		FileUtil.writeByteArrayToFile(bgFile, bgFileByte);
		
		// download logo
		String coverUrl = chapOrder.getLogo();
		String coverFileName = "cover" + coverUrl.substring(coverUrl.lastIndexOf("."));
		HttpClient client = new HttpClient();
		HttpMethod method = new GetMethod(coverUrl);
		client.executeMethod(method);
		File coverFile = new File(imgDir, coverFileName);
		FileUtil.writeByteArrayToFile(coverFile, method.getResponseBody());
		chapOrder.setLogo("res/img/" + coverFileName);
	}
	
}
