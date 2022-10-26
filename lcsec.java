import java.io.*;

public class lcsec {
    public static void main(String[] args) {
        produceCSVFile(args[0]);
    }

    private static boolean isLineComment(String line){
        String temp = line.trim();
        char charArray[] = temp.toCharArray();
        if(charArray[0] == '/' && (charArray[1] == '/' || charArray[1] == '*')){
            return true;
        }

        return false;
    }

    public static boolean isStartLongComment(String line){
        String temp = line.trim();
        if(temp.length() <= 2){
            return false;
        }
        char charArray[] = temp.toCharArray();
        if(charArray[0] == '/' && charArray[1] == '*'){
            return true;
        }
        return false;
    }

    private static boolean isEndLongComment(String line){
        String temp = line.trim();
        if(temp.length() <= 2){
            return false;
        }
        char charArray[] = temp.toCharArray(); 
        if(charArray[0] == '*' && charArray[1] == '/'){
            return true;
        }
        return false;
    }

    /**
     * Reads each file in the directory and subdirectories, counts the amount of times
     * fileName is mentioned
     *
     * @param fileName file name that the function will search for
     * @param path     path in which to search
     * @return if no files mention fileName 0, else amount of files mentioning fileName
     */
    public static int couplageSimpleEntreClasses(File mainFile, String path) {
        int counter = 0;
        String fileName = mainFile.getName();
        int pos = fileName.lastIndexOf(".");
        if (pos > 0) {
            fileName = fileName.substring(0, pos);
        }
        File parent = new File(path);
        File[] children = parent.listFiles();
        BufferedReader reader;
        for (File i : children) {
            if (i.isDirectory()) {
                counter += couplageSimpleEntreClasses(mainFile, path + "/" + i.getName());
            } else {
                if (!i.getName().equals(mainFile.getName())) {
                    try {
                        String line;
                        boolean found = false;

                        //checks if main file is mentioned in other file
                        reader = new BufferedReader(new FileReader(i));
                        while ((line = reader.readLine()) != null) {
                            if (line.contains(fileName) && !(isLineComment(line))) {
                                found = true;
                                break;
                            }
                            if (isStartLongComment(line)){
                               while(line != null && !isEndLongComment(line)){
                                line = reader.readLine();
                               }
                            }
                        }
                        reader.close();
                        //checks if other file is mentioned in main file
                        if (!found) {
                            String otherFileName = i.getName();
                            int index = otherFileName.lastIndexOf(".");
                            if (index > 0) {
                                otherFileName = otherFileName.substring(0, index);
                            }
                            reader = new BufferedReader(new FileReader(mainFile));
                            while ((line = reader.readLine()) != null) {
                                if (line.contains(i.getName()) && !(isLineComment(line))) {
                                    found = true;
                                    break;
                                }
                                if (isStartLongComment(line)){
                                   while(line != null && !isEndLongComment(line)){
                                    line = reader.readLine();
                                   }
                                }
                            }
                            reader.close();
                        }
                        if (found)
                            counter++;

                        found = false;
                    } catch (FileNotFoundException fnfe) {
                        System.out.println("File not found");
                        continue;
                    } catch (IOException e) {
                        System.out.println("Empty line or file");
                    }
                }
            }
        }
        return counter;
    }

    public static void produceCSVFile(String path) {
        String content = produceCSVContent(path);
        jls fileProducer = new jls();
        fileProducer.produceCSVFile(content);
    }

    public static String produceCSVContent(String path) {
        String outputCSV = "";
        File parent = new File(path);
        jls lineProducer = new jls();

        if (!parent.isDirectory()) {
            return outputCSV;
        }


        File[] children = parent.listFiles();

        for (File i : children) {
            if (i.isDirectory()) {
                outputCSV = outputCSV + produceCSVContent(path + "/" + i.getName());
            } else {
                String outputLine = lineProducer.produceCSVLine(path,i.getName()) + "," + couplageSimpleEntreClasses(i, path);
                outputCSV += outputLine + "\n";
            }
        }

        return outputCSV;
    }

}
