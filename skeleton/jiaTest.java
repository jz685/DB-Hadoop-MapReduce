import java.io.*; // DataInput/DataOuput
import java.nio.file.Files;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class jiaTest{
	
	public static void main(String[] args) throws IOException {
		String FILE_NAME = "/home/local/CUCS/jz685/Documents/Ass4/skeleton/kmeans_test2/points/points1.txt";
		List<String> lines = readSmallTextFile(FILE_NAME);
		System.out.println(lines.get(1) + "Lengh is: " + String.valueOf(lines.get(1).length()));
	}

	public static List<String> readSmallTextFile(String aFileName) throws IOException {
    	Path path = Paths.get(aFileName);
    	return Files.readAllLines(path, StandardCharsets.UTF_8);
	}
}
