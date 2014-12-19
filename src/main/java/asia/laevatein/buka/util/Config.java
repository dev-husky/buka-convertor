package asia.laevatein.buka.util;

import java.util.Properties;

public class Config {
	
	private static Properties properties = new Properties();
	
	public static <T> void set(Key<T> key, T value) {
		properties.put(key.getName(), value);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T get(Key<T> key) {
		return (T)properties.getProperty(key.getName(), key.getDefaultValue().toString());
	}
	
	public static class Key<T> {
		public static final Key<String> INPUT_DIR_PATH = new Key<String>("INPUT_DIR_PATH", "input");
		public static final Key<String> OUTPUT_DIR_PATH = new Key<String>("OUTPUT_DIR_PATH", "output");
		public static final Key<String> OUTPUT_CHAPORDER_FILE_PATH = new Key<String>("OUTPUT_CHAPORDER_FILE_PATH", "chaporder.dat"); 
		public static final Key<String> BUKA_PARSER_DIR_PATH = new Key<String>("BUKA_PARSER_DIR_PATH", "buka");
		public static final Key<String> BUKA_PARSER_EXE_PATH = new Key<String>("BUKA_PARSER_EXE_PATH", "buka.exe");
		public static final Key<String> BUKA_OUTPUT_DIR_PATH = new Key<String>("BUKA_OUTPUT_DIR_PATH", "buka_output");
		public static final Key<String> BUKA_CHAPORDER_FILE_PATH = new Key<String>("BUKA_CHAPORDER_FILE_PATH", "chaporder.dat");

		private String name;
		private T defaultValue;
		private Key(String name, T defaultValue) {
			this.name = name;
			this.defaultValue = defaultValue;
		}
		public String getName() {
			return name;
		}
		public T getDefaultValue() {
			return defaultValue;
		}
		@Override
		public String toString() {
			return name + " = " + get(this);
		}
		
	}
}