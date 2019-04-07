package com.fcc.common.tool;

import java.io.File;

/**
 * @Description: 文件工具类
 * @Author: J.W
 * @Date: 下午3:38 17/11/17
 */
public class FileTools {

    public static final String POINT = ".";

    /**
     * 获取文件扩展名
     *
     * @param fileName 文件名
     * @return
     */
    public static String getFileExt(String fileName) {
        if (StringTools.isBlank(fileName)) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(POINT) + 1);
    }

    /**
     * 先根遍历序递归删除文件夹
     *
     * @param dirFile 要被删除的文件或者目录
     * @return 删除成功返回true, 否则返回false
     */
    public static boolean deleteFile(File dirFile) {
        // 如果dir对应的文件不存在，则退出
        if (!dirFile.exists()) {
            return false;
        }

        if (dirFile.isFile()) {
            return dirFile.delete();
        } else {

            for (File file : dirFile.listFiles()) {
                deleteFile(file);
            }
        }
        return dirFile.delete();
    }

}
