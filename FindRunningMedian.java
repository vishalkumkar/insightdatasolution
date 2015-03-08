import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Vector;

public class FindRunningMedian {

	public static void main(String[] args) throws IOException {
		
		final String MERGEDFILE_NAME =  System.getProperty("user.dir") + "\\wc_input\\mergedfile.txt";
		final String OUTPUT_FILENAME = System.getProperty("user.dir") + "\\wc_output\\med_result.txt";

		Map<String, File> filedata = FileFunctions.getAllFiles(System.getProperty("user.dir") + "\\wc_input");
		FileFunctions.MergeFiles(filedata, new File(MERGEDFILE_NAME));

		Vector<String> linebyline = FileFunctions
				.readFileLineByLine(MERGEDFILE_NAME);
		FileFunctions.findRunningMedian(linebyline, OUTPUT_FILENAME);

	}

}
