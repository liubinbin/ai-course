package cn.liubinbin.ai;

import java.io.*;

/**
 * Created by bin on 2019/12/16.
 *
 * @Description: TODO
 */
public class OneHot {

    private final static String LINE_SEPARATOR = " ";
    private final static String ATTR_SEPARATOR = ",";
    private final static String CLASS_SEPARATOR = ",";

    public static String attr2onehot(String attribute, String[] candidates) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < candidates.length; i++) {
            if (i != 0) {
                stringBuilder.append(",");
            }
            if (attribute.equalsIgnoreCase(candidates[i])) {
                stringBuilder.append("1");
            } else {
                stringBuilder.append("0");
            }
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) throws IOException {
        String srcDataPath = "C:\\Users\\viruser.v-desktop\\Desktop\\AI 作业\\german.data";
        String targetDataPath = "C:\\Users\\viruser.v-desktop\\Desktop\\AI 作业\\german.data.onehot";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(srcDataPath)));
             FileWriter fileWriter = new FileWriter(new File(targetDataPath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                StringBuilder stringBuilder = new StringBuilder();
                String[] lineSplit = line.split(LINE_SEPARATOR);

                stringBuilder.append(attr2onehot(lineSplit[0], new String[]{"A11", "A12", "A13", "A14"})).append(ATTR_SEPARATOR);
                stringBuilder.append(lineSplit[1]).append(ATTR_SEPARATOR);
                stringBuilder.append(attr2onehot(lineSplit[2], new String[]{"A30", "A31", "A32", "A33", "A34"})).append(ATTR_SEPARATOR);
                stringBuilder.append(attr2onehot(lineSplit[3], new String[]{"A40", "A41", "A42", "A43", "A44", "A45", "A46", "A47", "A48", "A49", "A410"})).append(ATTR_SEPARATOR);
                stringBuilder.append(lineSplit[4]).append(ATTR_SEPARATOR);
                stringBuilder.append(attr2onehot(lineSplit[5], new String[]{"A61", "A62", "A63", "A64", "A65"})).append(ATTR_SEPARATOR);
                stringBuilder.append(attr2onehot(lineSplit[6], new String[]{"A71", "A72", "A73", "A74", "A75"})).append(ATTR_SEPARATOR);
                stringBuilder.append(lineSplit[7]).append(ATTR_SEPARATOR);
                stringBuilder.append(attr2onehot(lineSplit[8], new String[]{"A91", "A92", "A93", "A94", "A95"})).append(ATTR_SEPARATOR);
                stringBuilder.append(attr2onehot(lineSplit[9], new String[]{"A101", "A102", "A103"})).append(ATTR_SEPARATOR);
                stringBuilder.append(lineSplit[10]).append(ATTR_SEPARATOR);
                stringBuilder.append(attr2onehot(lineSplit[11], new String[]{"A121", "A122", "A123", "A124"})).append(ATTR_SEPARATOR);
                stringBuilder.append(lineSplit[12]).append(ATTR_SEPARATOR);
                stringBuilder.append(attr2onehot(lineSplit[13], new String[]{"A141", "A142", "A143"})).append(ATTR_SEPARATOR);
                stringBuilder.append(attr2onehot(lineSplit[14], new String[]{"A151", "A152", "A153"})).append(ATTR_SEPARATOR);
                stringBuilder.append(lineSplit[15]).append(ATTR_SEPARATOR);
                stringBuilder.append(attr2onehot(lineSplit[16], new String[]{"A171", "A172", "A173", "A174"})).append(ATTR_SEPARATOR);
                stringBuilder.append(lineSplit[17]).append(ATTR_SEPARATOR);
                stringBuilder.append(attr2onehot(lineSplit[18], new String[]{"A191", "A192"})).append(ATTR_SEPARATOR);
                stringBuilder.append(attr2onehot(lineSplit[19], new String[]{"A201", "A202"})).append(CLASS_SEPARATOR);
                stringBuilder.append(lineSplit[20]);

                fileWriter.write(stringBuilder.toString() + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
