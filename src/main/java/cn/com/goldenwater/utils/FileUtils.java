//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.com.goldenwater.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public final class FileUtils {
    private static final Log logger = LogFactory.getLog(FileUtils.class);

    private FileUtils() {
    }

    public static ArrayList executeArray(String path, String seperator) {
        if (path == null || "".equals(path.trim())) {
            System.err.println("path is not exist");
            System.exit(0);
        }

        ArrayList list = new ArrayList();
        BufferedReader intxt = null;

        try {
            intxt = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
            String line = null;

            while((line = intxt.readLine()) != null) {
                if (!line.startsWith("#") && !line.startsWith("//") && !"".equals(line.trim())) {
                    list.add(line.split(seperator));
                }
            }
        } catch (IOException var13) {
            logger.error("executeArray(String, String)", var13);
            System.err.println("fail in reading parameter file");
            var13.printStackTrace();
        } finally {
            try {
                intxt.close();
            } catch (Exception var12) {
                logger.warn("executeArray(String, String) - exception ignored", var12);
            }

        }

        return list;
    }

    public static ArrayList executeList(String path) {
        if (path == null || "".equals(path.trim())) {
            System.err.println("path is not exist");
            System.exit(0);
        }

        ArrayList list = new ArrayList();
        BufferedReader intxt = null;
        if (!(new File(path)).exists()) {
            logger.warn(path + " 没有找到！");
            return list;
        } else {
            try {
                intxt = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
                String line = null;

                while((line = intxt.readLine()) != null) {
                    if (!line.startsWith("#") && !line.startsWith("//") && !"".equals(line.trim())) {
                        list.add(line);
                    }
                }
            } catch (IOException var12) {
                logger.error("executeList(String)", var12);
                var12.printStackTrace();
                System.err.println("fail in reading parameter file" + path);
            } finally {
                try {
                    intxt.close();
                } catch (Exception var11) {
                    logger.warn("executeList(String) - exception ignored", var11);
                }

            }

            return list;
        }
    }

    public static String executeString(String path) {
        if (path == null || "".equals(path.trim())) {
            System.err.println("path is not exist");
            System.exit(0);
        }

        StringBuffer list = new StringBuffer();
        BufferedReader intxt = null;
        if (!(new File(path)).exists()) {
            logger.warn(path + " 没有找到！");
            return null;
        } else {
            try {
                intxt = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
                String line = null;

                while((line = intxt.readLine()) != null) {
                    if (!line.startsWith("#") && !line.startsWith("//") && !"".equals(line.trim())) {
                        list.append(line).append("\n\r");
                    }
                }
            } catch (IOException var12) {
                logger.error("executeList(String)", var12);
                var12.printStackTrace();
                System.err.println("fail in reading parameter file" + path);
            } finally {
                try {
                    intxt.close();
                } catch (Exception var11) {
                    logger.warn("executeList(String) - exception ignored", var11);
                }

            }

            return list.toString();
        }
    }

    public static String appendSeparator(String path) {
        return path.endsWith(File.separator) ? path : path + File.separator;
    }

    public static boolean writeFile(String path, String content) {
        if (content == null) {
            content = "";
        }

        BufferedWriter output = null;

        boolean var4;
        try {
            logger.info(DateUtils.Calendar2Str(Calendar.getInstance(), "yyyy-MM-dd HH:mm:ss") + "----------->path: " + path);
            File file = new File(path);
            if (!file.exists() && !file.createNewFile()) {
                var4 = false;
                return var4;
            }

            output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            output.write(content);
            var4 = true;
            return var4;
        } catch (Exception var15) {
            logger.error("FileUtils.writeFile(String, String)", var15);
            var4 = false;
        } finally {
            try {
                output.close();
            } catch (Exception var14) {
                ;
            }

        }

        return var4;
    }

    public static void appendFile(String path, String content) {
        if (exist(path) && null != content) {
            try {
                RandomAccessFile randomFile = new RandomAccessFile(path, "rw");
                long fileLength = randomFile.length();
                randomFile.seek(fileLength);
                randomFile.writeBytes(content);
                randomFile.close();
            } catch (IOException var5) {
                var5.printStackTrace();
            }
        }

    }

    public static void copyFile(String sourceFile, String destFile) throws IOException {
        org.apache.commons.io.FileUtils.copyFile(new File(sourceFile), new File(destFile));
    }

    public static boolean exist(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static boolean deleteFile(String path) {
        if (path != null && !"".equals(path.trim())) {
            File file = new File(path);
            if (file.exists()) {
                if (file.isDirectory()) {
                    try {
                        org.apache.commons.io.FileUtils.deleteDirectory(file);
                    } catch (IOException var3) {
                        logger.error("FileUtils.deleteFile(......)", var3);
                        return false;
                    }
                } else {
                    file.delete();
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public static boolean mkFolder(String path) {
        try {
            File folder = new File(path);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            return true;
        } catch (Exception var2) {
            logger.error("FileUtils.mkFolder(......)", var2);
            return false;
        }
    }

    public static boolean createFolder(String path) {
        try {
            deleteFile(path);
            File folder = new File(path);
            folder.mkdirs();
            return true;
        } catch (Exception var2) {
            logger.error("FileUtils.createFolder(......)", var2);
            return false;
        }
    }

    public static void writeToDisk(InputStream ins, String path, String fileName) {
        try {
            if (!exist(path)) {
                File folder = new File(path);
                folder.mkdirs();
            }

            OutputStream bos = new FileOutputStream(path + fileName);
            byte[] buffer = new byte[4096];

            int bytesRead;
            while((bytesRead = ins.read(buffer, 0, 4096)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }

            bos.close();
        } catch (FileNotFoundException var6) {
            var6.printStackTrace();
        } catch (IOException var7) {
            var7.printStackTrace();
        }

    }

    public static void clearPath(String path, long timeAgo) {
        File folder = new File(path);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            File temp = null;

            for(int i = 0; i < files.length; ++i) {
                temp = files[i];
                if (timeAgo > temp.lastModified()) {
                    deleteFile(temp.getPath());
                    logger.debug("clearPath：" + temp.getPath());
                }
            }
        }

    }

    /**
     *
     * @param path  磁盘JSON文件路径
     * @return      String字符串
     */
    public static String readJsonToString(String path){
        String res = null;
        File file = new File(path);
        if(file.exists()){
            res = readFileToString(file);
        }
        return res;
    }


    private String concatList(List list) {
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < list.size(); ++i) {
            sb.append(list.get(i)).append("\r\n");
        }

        return sb.toString();
    }

    private static String readFileToString(File file){
        Scanner scanner = null;
        StringBuilder buffer = new StringBuilder();
        try {
            scanner = new Scanner(file, "utf-8");
            while (scanner.hasNextLine()) {
                buffer.append(scanner.nextLine());
            }
        } catch (Exception e) {

        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return buffer.toString();
    }

}
