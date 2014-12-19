package asia.laevatein.buka.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import asia.laevatein.buka.util.ChapOrder.Chap;
import asia.laevatein.buka.util.Config.Key;

public class BukaUtil {

	private static final String CMD = "{BUKA_PARSER_EXE_PATH} {BUKA_FILE} {BUKA_OUTPUT_CHAP_DIR_PATH}";
	
	public static boolean convert(Chap chap) throws IOException {
		File inputDir = new File(Config.get(Key.INPUT_DIR_PATH));
		File bukaFile = new File(inputDir, chap.getCid() + ".buka");
		FileUtil.checkFile(bukaFile, false);
		
		File bukaParserExe = new File(Config.get(Key.BUKA_PARSER_EXE_PATH));
		File bukaOutputDir = new File(Config.get(Key.BUKA_OUTPUT_DIR_PATH));
		File bukaOutputChapDir = new File(bukaOutputDir, chap.getCid());
		String cmd = CMD.replace("{BUKA_PARSER_EXE_PATH}", bukaParserExe.getAbsolutePath());
		cmd = cmd.replace("{BUKA_FILE}", bukaFile.getAbsolutePath());
		cmd = cmd.replace("{BUKA_OUTPUT_CHAP_DIR_PATH}", bukaOutputChapDir.getAbsolutePath());
		
		Process p;  
        try {  
          //执行CMD代码,返回一个Process  
          p = Runtime.getRuntime().exec(cmd);  
          InputStream is = p.getInputStream();  
          //得到相应的控制台输出信息  
          InputStreamReader bi = new InputStreamReader(is);  
          BufferedReader br = new BufferedReader(bi);  
          String message;  
          message =  br.readLine();        
          while(message != null && !"".equals(message)){  
          //将信息输出  
            System.out.println(message);  
            message =  br.readLine();  
          }  
        } catch (IOException e) {  
          e.printStackTrace();  
        }  
		return true;
	}
}
