import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

public class jls {
    public static void main(String[] args) {
        String csvContent = produceCSVContent(args[0]);
        produceCSVFile(csvContent);
    }

    public static void produceCSVFile(String content) {
        try {
            File CSVFile = new File("output.csv");
            CSVFile.createNewFile();
        } catch (IOException e) {
            System.out.println("File creation ERROR");
        }

        try {
            FileWriter writer = new FileWriter("output.csv");
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            System.out.println("FILEWRITER error");
        }
    }

    public static String produceCSVContent(String path) {
        String outputCSV = "";
        File parent = new File(path);

        if (!parent.isDirectory()) {
            return outputCSV;
        }


        File[] children = parent.listFiles();

        for (File i : children) {
            if (i.isDirectory()) {
                outputCSV = outputCSV + produceCSVContent(path + "/" + i.getName());
            } else {
                String childName = i.getName();
                String outputLine = produceCSVLine(path, childName);
                outputCSV += outputLine + "\n";
                System.out.println(outputLine);
            }
        }

        return outputCSV;
    }

    public static String produceCSVLine(String path, String childName) {

        String outputLine = path + "/" + childName + ", " + getPackageNameFromPath(path) + ", " + childName.substring(0, childName.indexOf('.'));
        return outputLine;
    }

    public static String getPackageNameFromPath(String path) {
        return path.substring(2).replaceAll("/", ".");
    }
}
