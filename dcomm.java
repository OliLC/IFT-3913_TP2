import java.io.*;

public class dcomm {
    public static void main(String[] args) {
        dcommLOC(args[0]);
    }

    public static void dcommLOC(String path) {
        try {
            File output = new File("dcom_loc.csv");
            output.createNewFile();
        } catch (IOException e) {
            System.out.println("File creation error");
        }
        try {
            FileWriter csvWriter = new FileWriter("dcom_loc.csv");
            csvWriter.write("file,dcom,loc\n");
            directoryParser(path, csvWriter);
            csvWriter.close();
        } catch (IOException e) {
            System.out.println("File Writer error");
        }
    }

    public static void directoryParser(String path, FileWriter csvWriter) {
        File parent = new File(path);
        File[] children = parent.listFiles();
        for (File i : children) {
            if (i.isDirectory()) {
                directoryParser(path + "/" + i.getName(), csvWriter);
            } else {
                String filePath = path + "/" + i.getName();
                if (!filePath.endsWith(".java")) {
                    continue;
                }
                int[] fileValues = fileParser(filePath);
                float density = 0;
                if (fileValues[1] != 0)
                    density = fileValues[0] / (float) fileValues[1];
                try {
                    csvWriter.write(filePath + "," + density + "," + (fileValues[1] - fileValues[0]) + "\n");
                } catch (IOException e) {
                    System.out.println("File Writer Error");
                }
            }
        }
    }

    public static int[] fileParser(String file) {
        int nloc = 0;
        int cloc = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            boolean multilineComment = false;
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmedLine = line.trim();
                if (!"".equals(trimmedLine)) {
                    nloc++;
                    if (trimmedLine.endsWith("*/")) {
                        if (multilineComment) {
                            cloc++;
                            multilineComment = false;
                            continue;
                        }
                    }
                    if (multilineComment) {
                        cloc++;
                        continue;
                    }
                    if (trimmedLine.startsWith("//")) {
                        cloc++;
                    }
                    if (trimmedLine.startsWith("/*")) {
                        cloc++;
                        multilineComment = true;
                        if (trimmedLine.endsWith("*/")) {
                            multilineComment = false;
                        }
                    }
                }
            }
            reader.close();
        } catch (FileNotFoundException fnfe) {
            System.out.println("File not found");
            return new int[]{0, 0};
        } catch (IOException e) {
            System.out.println("Empty line or file");
            return new int[]{0, 0};
        }
        float density = 0;
        if (nloc != 0)
            density = (float) cloc / nloc;
        int loc = nloc - cloc;
        System.out.println(file + ", comments density: " + density + ", Lines of code: " + loc);
        return new int[]{cloc, nloc};
    }
}