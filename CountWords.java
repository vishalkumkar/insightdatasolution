import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Vector;

public class CountWords {

	public static void main(String[] args) throws IOException {

		final String OUTPUT_FILENAME = System.getProperty("user.dir")
				+ File.separatorChar + "wc_output" + File.separatorChar
				+ "wc_result.txt";
		final String INPUT_FILENAME = System.getProperty("user.dir")
				+ File.separatorChar + "wc_input" + File.separatorChar
				+ "input.txt";

		Vector<String> lines = FileFunctions.readFileLineByLine(INPUT_FILENAME);
		Map<String, Integer> dictionary = FileFunctions.countWords(lines);
		FileFunctions.printWordCount(dictionary, OUTPUT_FILENAME);

	}

}
