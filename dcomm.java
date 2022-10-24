import java.io.*;

public class dcomm {
    public static void main(String[] args) {
        dcommLOC(args[0]);
    }

    public static float[] dcommLOC(String path) {
        int[] directoryValues = directoryParser(path);
        float density = 0;
        if (directoryValues[1] != 0)
            density = directoryValues[0] / directoryValues[1];
        int loc = directoryValues[1]-directoryValues[0];

        System.out.println(path+", comments density: "+ density+", Lines of code: "+loc);
        return new float[]{density,loc};
    }

    public static int[] directoryParser(String path) {
        float density = 0;
        int[] linesAndComments = new int[2];
        File parent = new File(path);

        if (!parent.isDirectory()) {
            linesAndComments = fileParser(path);
            return linesAndComments;
        }
        File[] children = parent.listFiles();
        for (File i : children) {
            if (i.isDirectory()) {
                int[] subdirectoryValues = directoryParser(path + "/" + i.getName());
                linesAndComments[0] += subdirectoryValues[0];
                linesAndComments[1] += subdirectoryValues[1];
            } else {
                int[] fileValues = fileParser(path);
                linesAndComments[0] += fileValues[0];
                linesAndComments[1] += fileValues[1];
            }
        }
        return linesAndComments;
    }

    public static int[] fileParser(String file) {
        if (!file.endsWith(".java")) {
            System.out.println("Not a java file.");
            return new int[]{0, 0};
        }
        int nloc = 0;
        int cloc = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            boolean multilineComment = false;
            String line;
            while ((line = reader.readLine()) != null) {
                if (!"".equals(line.trim())) {
                    nloc++;
                    if (line.trim().endsWith("*/")) {
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
                    if (line.trim().startsWith("//")) {
                        cloc++;
                    }
                    if (line.trim().startsWith("/*")) {
                        cloc++;
                        multilineComment = true;
                        if (line.trim().endsWith("*/")) {
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
            density = cloc/nloc;
        int loc = nloc-cloc;

        System.out.println(file+", comments density: "+ density+", Lines of code: "+loc);
        return new int[]{cloc, nloc};
    }
}