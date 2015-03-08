import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Vector;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class FileFunctions {

	/*
	 * get the file data in String[] line by line.
	 */
	static public Vector<String> readFileLineByLine(final String filename)
			throws IOException {
		Vector<String> lines = new Vector<>();
		String encoding = "UTF-8";
		try (Scanner sc = new Scanner(new File(filename), encoding)) {
			while (sc.hasNextLine()) {
				lines.add(sc.nextLine());
			}
			if (sc.ioException() != null) {
				throw sc.ioException();
			}
		}
		return lines;
	}

	/*
	 * Writing data to any file
	 */
	public static void WriteToFile(String fileName, String data, Boolean appendExistingFile)
			{
		
		FileWriter fstream = null;
		BufferedWriter out = null;
		try {
			fstream = new FileWriter(fileName, appendExistingFile);
			out = new BufferedWriter(fstream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			out.write(data);
			out.newLine();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Merge the contents of File.
	 */
	public static void MergeFiles(Map<String, File> filedata, File mergedFile) {

		File[] files = new File[filedata.size()];
		int i = 0;
		for (Entry<String, File> entry : filedata.entrySet()) {
			files[i++] = entry.getValue();
		}

		FileWriter fstream = null;
		BufferedWriter out = null;
		try {
			fstream = new FileWriter(mergedFile, false);
			out = new BufferedWriter(fstream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		for (File f : files) {

			if (!f.getName().equals(mergedFile.getName())) {
				System.out.println("merging: " + f.getName());

				FileInputStream fis;
				try {
					fis = new FileInputStream(f);
					BufferedReader in = new BufferedReader(
							new InputStreamReader(fis));

					String aLine;
					while ((aLine = in.readLine()) != null) {
						out.write(aLine);
						out.newLine();
					}

					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static final String fWHITESPACE_AND_QUOTES = " \t\r\n\",.";
	private static final String IGNORECHARS[] = { " ", "\t", "\r", "\n", "\\",
			".", "," };

	/*
	 * get the words from the file with their count 
	 */
	static Map<String, Integer> countWords(Vector<String> lines) {
		ConcurrentHashMap<String, Integer> dict = new ConcurrentHashMap<>();
		String token = null;
		for (int i = 0; i < lines.size(); i++) {
			StringTokenizer parser = new StringTokenizer(lines.get(i),
					fWHITESPACE_AND_QUOTES, true);
			while (parser.hasMoreTokens()) {
				token = parser.nextToken().toLowerCase();
				if (!dict.containsKey(token)) {
					dict.put(token, 1);
				} else {
					dict.put(token, dict.get(token).intValue() + 1);
				}

			}

		}
		return dict;
	}

	static void printWordCount(Map<String, Integer> dictionary, String fileName) {
		Map<String, Integer> treeMap = new TreeMap<String, Integer>(dictionary);

		Boolean isNewFile=false;
		for (java.util.Map.Entry<String, Integer> entry : treeMap.entrySet()) {
			String key = entry.getKey().toString();
			if (!Arrays.asList(IGNORECHARS).contains(key)) {
				Integer value = entry.getValue();
				WriteToFile(fileName, key + " " + value, isNewFile);
				isNewFile=true;
			}

		}
	}

	static private float getMedian(float arr[]) {
		Arrays.sort(arr);
		int len = arr.length;
		Boolean islengthEven = len % 2 == 0 ? true : false;
		if (len == 1) {
			return (arr[len - 1]);
		} else if (islengthEven) {
			float val = (float) (arr[len / 2] + arr[len / 2 - 1]) / 2;
			return val;
		} else {
			return (arr[len / 2]);
		}
	}

	static void findRunningMedian(Vector<String> lines, String outputFile) throws IOException {
		float median[] = new float[lines.size()];

		for (int i = 0; i < lines.size(); i++) {
			int count = 0;
			StringTokenizer parser = new StringTokenizer(lines.get(i),
					fWHITESPACE_AND_QUOTES, true);
			while (parser.hasMoreTokens()) {
				String key = parser.nextToken();
				if (!Arrays.asList(IGNORECHARS).contains(key)) {
					count++;
				}
			}
			median[i] = (float) count;

			float runningMedian = getMedian(Arrays.copyOfRange(median, 0, i + 1));
			WriteToFile(outputFile, String.valueOf(runningMedian), i==0?false:true);

		}
	}
	

	/**
	 * Get all file names from directory
	 */
	static TreeMap<String, File> getAllFiles(final String directory) {
		Map<String, File> hashmap = new HashMap<>();
		File folder = new File(directory);

		for (File file : folder.listFiles()) {
			hashmap.put(file.getName().toLowerCase(), file);
		}
		return (new TreeMap<String, File>(hashmap));

	}
}
