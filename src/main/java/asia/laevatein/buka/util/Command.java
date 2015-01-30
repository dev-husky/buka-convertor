package asia.laevatein.buka.util;

import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class Command {

	private String[] cmd;
	private Process process;
	private List<String> output;
	
	public Command(String[] cmd) {
		this.cmd = cmd;
	}
	
	public void exec() throws IOException {
		ProcessBuilder pb = new ProcessBuilder(cmd);
		pb.redirectErrorStream(true);
		process = pb.start();
		output = IOUtils.readLines(process.getInputStream());
	}
	
	public List<String> getOutput() {
		return output;
	}
	
}
