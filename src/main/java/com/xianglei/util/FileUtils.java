package com.xianglei.util;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.xianglei.constant.OsDefaultEnum;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Xianglei
 * @Company:
 * @Date: 2020/6/21 23:14
 * @Version 1.0
 * 文件操作工具类
 */
public class FileUtils {

    public static final long HOURS24 = 24 * 60 * 60 * 1000L;

    /**
     * 获取文件列表
     *
     * @param strPath 路径
     * @param bl      是否获取全部文件，true获取全部，false只获取路径下的文件，不包括路径下子文件夹的文件
     * @return 返回文件列表
     */
    public static List<File> getFileList(String strPath, boolean bl) {
        List<File> fileList = new ArrayList<File>();
        File dir = new File(strPath);
        // 该文件目录下文件全部放入数组
        File[] files = dir.listFiles();
        // 因为System Volume Information文件夹是无法访问的，所以需要判断不为空
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                // 判断是文件还是文件夹，如果是，是否获取全部文件
                if (files[i].isDirectory() && bl) {
                    // 获取文件绝对路径
                    getFileList(files[i].getAbsolutePath(), bl);
                    // 判断是文件还是文件夹，如果是文件夹，不添加到文件列表
                } else if (files[i].isDirectory()) {
                    continue;
                } else {
                    fileList.add(files[i]);
                }
            }
        }
        return fileList;
    }

    /**
     * 获取文件夹列表
     *
     * @param strPath 路径
     * @return
     */
    public static List<File> getFolderList(String strPath) {
        List<File> folderList = new ArrayList<File>();
        File dir = new File(strPath);
        // 该文件目录下文件全部放入数组
        File[] files = dir.listFiles();
        // 因为System Volume Information文件夹是无法访问的，所以需要判断不为空
        if (files != null) {
            for (int i = 0, l = files.length; i < l; i++) {
                // 如果是文件夹，就添加入列表
                if (files[i].isDirectory()) {
                    // 递归放在folderList之前，这样是为了输出时能先输出最深层的文件夹
                    getFolderList(files[i].getPath());
                    folderList.add(files[i]);
                }
            }
        }
        return folderList;
    }

    /**
     * 获取路径下的文件名列表，文件名包括文件名扩展名
     *
     * @param strPath 路径
     * @param bl      是否获取全部文件名列表，true获取全部，包括子文件夹下的文件，false只获取路径下的文件，不包括文件夹
     * @return 返回字符串类型的文件名数组
     */
    public static String[] getFileName(String strPath, boolean bl) {

        FileUtils fileUtil = new FileUtils();
        List<File> fileList = getFileList(strPath, bl);
        String[] fileName = new String[fileList.size()];
        for (int i = 0; i < fileList.size(); i++) {
            fileName[i] = fileList.get(i).getName();
        }
        return fileName;
    }

    /**
     * 获取路径下的文件的全路径，包括文件名扩展名
     *
     * @param strPath 路径
     * @param bl      是否获取全部文件全路径，true获取全部，包括子文件夹下的文件，false只获取路径下的文件，不包括文件夹
     * @return 返回字符串类型的文件名全路径数组
     */
    public String[] getFileAllPath(String strPath, boolean bl) {
        FileUtils fileUtil = new FileUtils();
        List<File> fileList = getFileList(strPath, bl);
        String[] fileName = new String[fileList.size()];
        for (int i = 0; i < fileList.size(); i++) {
            fileName[i] = fileList.get(i).getAbsolutePath();
        }
        return fileName;
    }

    /**
     * 创建目录
     *
     * @param destDirName 目标目录名
     * @return 目录创建成功返回true，否则返回false
     */
    public static boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {
            return false;
        }
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        // 创建单个目录
        if (dir.mkdirs()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除文件
     *
     * @param filePathAndName 文件路径及名称 如c:/fpn.txt
     */
    public static void delFile(String filePathAndName) {
        try {
            String filePath = filePathAndName;
            filePath = filePath.toString();
            java.io.File myDelFile = new java.io.File(filePath);
            myDelFile.delete();
        } catch (Exception e) {
            System.out.println("There was an error deleting the file!");
            e.printStackTrace();
        }
    }

    /**
     * 读取到字节数组0
     *
     * @param filePath 路径
     * @return
     * @throws IOException
     */
    @SuppressWarnings("resource")
    public static byte[] readToByteArray0(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        long fileSize = file.length();
        if (fileSize > Integer.MAX_VALUE) {
            // file too big 文件太大
            System.out.println("file too big...");
            return null;
        }
        FileInputStream fi = new FileInputStream(file);
        byte[] buffer = new byte[(int) fileSize];
        int offset = 0;
        int numRead = 0;
        while (offset < buffer.length && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
            offset += numRead;
        }
        // 确保所有数据均被读取
        if (offset != buffer.length) {
            // Could not completely read file 无法完全读取文件
            throw new IOException("Could not completely read file " + file.getName());
        }
        fi.close();
        return buffer;
    }

    /**
     * 读取到字节数组1
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static byte[] readToByteArray1(String filePath) throws IOException {
        File f = new File(filePath);
        if (!f.exists()) {
            throw new FileNotFoundException(filePath);
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(f));
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bos.close();
        }
    }

    /**
     * 读取到字节数组2
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static byte[] readToByteArray2(String filePath) throws IOException {
        File f = new File(filePath);
        if (!f.exists()) {
            throw new FileNotFoundException(filePath);
        }

        FileChannel channel = null;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {
                // do nothing
                // System.out.println("reading");
            }
            return byteBuffer.array();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取到字节数组3</br>
     * <p>
     * Mapped File way MappedByteBuffer 可以在处理大文件时，提升性能
     *
     * @param
     * @return
     * @throws IOException
     */
    public static byte[] readToByteArray3(String filePath) throws IOException {
        FileChannel fc = null;
        RandomAccessFile rf = null;
        try {
            rf = new RandomAccessFile(filePath, "r");
            fc = rf.getChannel();
            MappedByteBuffer byteBuffer = fc.map(MapMode.READ_ONLY, 0, fc.size()).load();
            // System.out.println(byteBuffer.isLoaded());
            byte[] result = new byte[(int) fc.size()];
            if (byteBuffer.remaining() > 0) {
                // System.out.println("remain");
                byteBuffer.get(result, 0, byteBuffer.remaining());
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                rf.close();
                fc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取文件内容
     *
     * @param filePath   文件全路径
     * @param methodType 读取字节的方法<br />
     *                   有四个方法，范围0-3<br />
     *                   其中3 可以在处理大文件时，提升性能
     * @return
     */
    public static String getFileContent(String filePath, int methodType) {
        String str = null;
        if (methodType < 0 || methodType > 3) {
            methodType = 0;
        }
        byte[] b;
        try {
            switch (methodType) {
                case 0:
                    b = readToByteArray0(filePath);
                    break;
                case 1:
                    b = readToByteArray1(filePath);
                    break;
                case 2:
                    b = readToByteArray2(filePath);
                    break;
                case 3:
                    b = readToByteArray3(filePath);
                    break;
                default:
                    b = readToByteArray0(filePath);
            }
            str = new String(b, "utf-8");
        } catch (IOException e) {
            System.out.println("Error reading process!");
            e.printStackTrace();
        }
        return str;
    }


    /**
     * 字符串写入到文件 24 小时内日志在同一个文件
     *
     * @param content  字符串内容
     * @param filePath 路径
     * @param append   true是往文件后追加，false是覆盖
     */
    public static void writeContentToFile(String content, String filePath, boolean append) throws RuntimeException{
        // 获取最后一个分割线的下表
        int lastIndexOf = filePath.lastIndexOf(File.separator);
        String tempPath = filePath.substring(0, lastIndexOf);
        List<File> fileList = getFileList(tempPath, false);
        for (File file : fileList) {
            String absolutePath = file.getAbsolutePath();
            String fileCreateTime = getFileCreateTime(absolutePath);
            // 当前时间
            DateTime createTime = DateUtil.parse(fileCreateTime);
            long oldFileTime = createTime.toTimestamp().getTime();
            DateTime nowDateTime = DateTime.now();
            long newFileTime = nowDateTime.toTimestamp().getTime();
            if (newFileTime - oldFileTime <= HOURS24) {
                filePath = absolutePath;
                break;
            }
        }
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream out = new FileOutputStream(file, append);
            out.write(content.getBytes("utf-8"));// 注意需要转换对应的字符集
            out.flush();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException("File's content writing failed!",e);
        }
    }

    /**
     * 获取文件后缀名
     *
     * @param str 文件全名或者文件路径，文件路径请包括文件全名
     * @return
     */
    public static String getFileSuffix(String str) {
        return str.substring(str.lastIndexOf(".") + 1);
    }

    /**
     * 获取文件后缀名
     *
     * @param file
     * @return
     */
    public static String getFileSuffix(File file) {
        String str = file.getAbsolutePath();
        return str.substring(str.lastIndexOf(".") + 1);
    }

    /**
     * 获取文件的创建时间
     *
     * @param filePath
     * @return
     */
    public static String getFileCreateTime(String filePath) {
        String startTime = null;
        String suffix = getFileSuffix(filePath);
        try {
            Process p = Runtime.getRuntime().exec("cmd /C dir " + filePath + "/tc");
            InputStream input = p.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.endsWith(suffix)) {
                    startTime = line.substring(0, 17);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return startTime;
    }

    /**
     * 获取文件的创建时间
     *
     * @param file
     * @return
     */
    public static String getFileCreateTime(File file) {
        String startTime = null;
        String suffix = getFileSuffix(file);
        try {
            Process p = Runtime.getRuntime().exec("cmd /C dir " + file.getAbsolutePath() + "/tc");
            InputStream input = p.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.endsWith(suffix)) {
                    startTime = line.substring(0, 17);
                    startTime = startTime.replace("/", "");
                    startTime = startTime.replace(":", "");
                    startTime = startTime.replace(" ", "");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return startTime;
    }

    /**
     * 获取文件的最后访问时间，注：jdk环境需要在1.7或之上
     *
     * @param filePath 文件路径
     * @return
     */
    public static String getFileAccessTime(String filePath) {
        String time = null;
        Path p = Paths.get(filePath);
        try {
            // 获取文件的属性
            BasicFileAttributes att = Files.readAttributes(p, BasicFileAttributes.class);
            // 创建时间
            /// time = att.creationTime().toMillis() + "";
            // 修改时间
            /// time = att.lastModifiedTime().toMillis() + "";
            // 访问时间
            time = att.lastAccessTime().toMillis() + "";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 弹出jdk自带资源管理窗口，用于选择路径
     *
     * @return 选择的路径
     */
    public static String getSelectPath() {
        String selectPath = null;
        JFileChooser chooser = new JFileChooser();
        // 设置只能选择目录
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            selectPath = chooser.getSelectedFile().getPath();
        }
        return selectPath;
    }

    /**
     * 根据系统获取默认/设置的路径并且创建
     *
     * @param storeFilePath
     * @return
     */
    public static String getOsInfoAndMkdir(String storeFilePath) throws RuntimeException {
        // 获取操作系统名称
        String osName = SystemRecognizeUtils.getOSName().toLowerCase();
        if (osName.startsWith(OsDefaultEnum.WINDOWS.getInfo())) {
            if (StringUtils.isEmpty(storeFilePath) || StringUtils.equals("", storeFilePath)) {
                storeFilePath = OsDefaultEnum.WINDOWS.getPath();
            }
        } else {
            if (StringUtils.isEmpty(storeFilePath) || StringUtils.equals("", storeFilePath)) {
                storeFilePath = OsDefaultEnum.LINUX.getPath();
            }
        }
        // 根据路径判断是否存在  不存在就创建该存储路径
        boolean dir = FileUtils.createDir(storeFilePath);
        return storeFilePath;
    }
}
