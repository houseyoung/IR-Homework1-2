import java.io.*;

/**
 * WriteFile
 *
 * @author: yangch
 * @time: 2015/11/19 17:23
 */
public class WriteFile {
    /**
     * 写入内容至TXT文件(若文件已存在，则覆盖原有内容)
     * @param outputPath
     * @param outputFileName
     * @param outputContext
     */
    public static void writeToTxt(String outputPath, String outputFileName, String outputContext) {
            String str = new String();

            try {
                File path = new File(outputPath);
                File file = new File(outputPath + outputFileName);

                if(!path.exists()){
                    path.mkdirs();
                }

                if (!file.exists()) {
                    file.createNewFile();
                }

                //将新插入的内容存到str中
                str += outputContext;

                BufferedWriter output = new BufferedWriter(new FileWriter(file));
                output.write(str);
                output.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    /**
     * 向原有TXT文件中插入内容
     * @param outputPath
     * @param outputFileName
     * @param outputContext
     */
    public static void contentToTxt(String outputPath, String outputFileName, String outputContext) {
        //原文件内容
        String existStr = new String();
        //插入后的内容
        String newStr = new String();

        try {
            File path = new File(outputPath);
            File file = new File(outputPath + outputFileName);

            if(!path.exists()){
                path.mkdirs();
            }

            if (!file.exists()) {
                file.createNewFile();
            }

            BufferedReader input = new BufferedReader(new FileReader(file));
            //把现有的内容一行行读取出来，存到newStr中
            while ((existStr = input.readLine()) != null) {
                newStr += existStr + "\n";
            }
            input.close();

            //将新插入的内容存到newStr中
            newStr += outputContext;

            BufferedWriter output = new BufferedWriter(new FileWriter(file));
            output.write(newStr);
            output.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}
