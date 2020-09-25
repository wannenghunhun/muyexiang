package com.framwork.common.utils;

import android.annotation.TargetApi;
import android.os.Environment;
import android.os.StatFs;

import com.framwork.common.GlobalContext;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/**
 *
 */

public final class AndroidFileUtil {
    
    
    /**
     * @param folderName
     * @return
     * @throws IOException
     * @see #getExternalCacheDir() 优先 SD卡 路径  #
     * @see #getCacheFile(String) 其次 APP 路径
     */
    public synchronized static File getCustomDirs(String folderName) throws IOException {
        File file;
        if(isSDCardMounted() || !isExternalStorageRemovable()) { // SD卡挂载了或者有效
            file = getExternalCacheDirFolder(folderName);
        }
        else {
            file = getCacheDir(folderName);
        }
        return file;
        
    }
    
    /**
     * @param folderName
     * @return
     * @throws IOException
     * @see #getExternalCacheDir() 优先 SD卡 路径  #
     * @see #getCacheFile(String) 其次 APP 路径
     */
    public synchronized static String getCustomFolderPath(String folderName) throws IOException {
        File file;
        if(isSDCardMounted() || !isExternalStorageRemovable()) { // SD卡挂载了或者有效
            file = getExternalCacheDirFolder(folderName);
        }
        else {
            file = getCacheDir(folderName);
        }
        if(file != null) {
            return file.getAbsolutePath();
        }
        return null;
        
    }
    
    /**
     * @param folderName
     * @return
     * @throws IOException
     * @see #getExternalCacheDir() 优先 SD卡 路径  #
     * @see #getCacheFile(String) 其次 APP 路径
     */
    public synchronized static File getCustomFile(String folderName) throws IOException {
        File file;
        if(isSDCardMounted() || !isExternalStorageRemovable()) { // SD卡挂载了或者有效
            file = getExternalCacheDirFile(folderName);
        }
        else {
            file = getCacheFile(folderName);
        }
        return file;
        
    }
    
    /**
     * @param folderName
     * @return
     * @throws IOException
     * @see #getExternalCacheDir() 优先 SD卡 路径  #
     * @see #getCacheFile(String) 其次 APP 路径
     */
    public synchronized static String getCustomFilePath(String folderName) throws IOException {
        File file;
        if(isSDCardMounted() || !isExternalStorageRemovable()) { // SD卡挂载了或者有效
            file = getExternalCacheDirFile(folderName);
        }
        else {
            file = getCacheFile(folderName);
        }
        if(file != null) {
            return file.getAbsolutePath();
        }
        return null;
        
    }
    
    
    /**
     * 文件存到sd卡
     *
     * @param name
     * @param content
     */
    public static void saveStr2FilePriorSD(String name, String content) throws IOException {
        
        File file = getExternalCacheDirFile(name);
        if(file == null) {
            file = getCacheFile(name);
        }
        writeStr2File(content, file);
    }
    
    
    /**
     * @return /data/data/{packageName}/cache/
     */
    public synchronized static String getCacheDirPath() {
        
        return GlobalContext.getContext().getCacheDir().getPath();
    }
    
    /**
     * @param uniqueName
     * @return /data/data/{packageName}/cache/xxx
     */
    public synchronized static File getCacheDir(String uniqueName) {
        return createDirectory(getCacheDirPath(), uniqueName);
    }
    
    
    /**
     * @param uniqueName
     * @return /data/data/{packageName}/cache/xxx
     */
    public synchronized static File getCacheFile(String uniqueName) throws IOException {
        return createFile(getCacheDirPath(), uniqueName);
    }
    
    
    /**
     * @return /data/data/{packageName}/files/
     */
    public synchronized static String getFilesDirPath() {
        return GlobalContext.getContext().getFilesDir().getPath();
    }
    
    /**
     * @param uniqueName
     * @return /data/data/{packageName}/files/xxx
     */
    public synchronized static File getFilesDir(String uniqueName) {
        return createDirectory(getFilesDirPath(), uniqueName);
    }
    
    /**
     * @param uniqueName
     * @return /data/data/{packageName}/files/xxx
     */
    public synchronized static File getFilesFile(String uniqueName) throws IOException {
        return createFile(getFilesDirPath(), uniqueName);
    }
    
    
    /**
     * 判断SD卡是否被挂载
     *
     * @return
     */
    public static boolean isSDCardMounted() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }
    
    
    public static File getExternalCacheDirFile(String file) throws IOException {
        return createFile(getExternalCacheDir().getAbsolutePath(), file);
    }
    
    public static String getExternalCacheDirFilePath(String file) throws IOException {
        return getFilePath(getExternalCacheDir().getAbsolutePath(), file);
    }
    
    public static File getExternalCacheDirFolder(String file) throws IOException {
        return createDirectory(getExternalCacheDir().getAbsolutePath(), file);
    }
    
    public static String getExternalCacheDirFolderPath(String file) throws IOException {
        return getFilePath(getExternalCacheDir().getAbsolutePath(), file);
    }
    
    /***
     *
     * @return SDCard/Android/data/{pm}/cache/
     */
    public static File getExternalCacheDir() {
        File file = null;
        if(OSUtils.hasFroyo_8()) {
            file = GlobalContext.getContext().getExternalCacheDir();
        }
        if(file == null) {
            file = createExternalCacheDirPath();
        }
        return file;
    }
    
    /***
     *
     * @return SDCard/Android/data/{pm}/cache/
     */
    public static String getExternalCacheDirPath() {
        File file = getExternalCacheDir();
        if(file != null) {
            return file.getAbsolutePath();
        }
        return "";
    }
    
    
    /**
     * 有则返回，无则自己创建
     * <p>
     * /**
     *
     * @return /Android/data/{packageName}/cache/
     */
    public static File createExternalCacheDirPath() {
        String path = StringUtil.join(File.separator
                , Environment.getExternalStorageDirectory().getPath()
                , "Android"
                , "data"
                , AppUtil.getPackageName()
                , "cache" + File.separator
        );
        return new File(path);
    }
    
    /**
     * Check if external storage is built-in or removable.
     *
     * @return True if external storage is removable (like an SD card), false
     * otherwise.
     */
    @TargetApi(9)
    public static boolean isExternalStorageRemovable() {
        if(OSUtils.hasGingerbread_9()) {
            return Environment.isExternalStorageRemovable();
        }
        return true;
    }
    
    /**
     * 获取sd卡的大小
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public static long getSDAllSizeKB() {
        if(isSDCardMounted()) {
            // get path of sdcard
            File path = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(path.getPath());
            // get single block size(Byte)
            long blockSize = sf.getBlockSize();
            // 获取所有数据块数
            long allBlocks = sf.getBlockCount();
            // 返回SD卡大小
            return (allBlocks * blockSize) / 1024; // KB
        }
        return 0;
    }
    
    /**
     * 获取sd卡可用大小
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public static long getSDAvalibleSizeKB() {
        if(isSDCardMounted()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(path.getPath());
            long blockSize = sf.getBlockSize();
            long avaliableSize = sf.getAvailableBlocks();
            return (avaliableSize * blockSize) / 1024;// KB
        }
        return 0;
    }
    
    /**
     * 刷新图库
     *
     * @param file 文件
     */
    public static void refreshGallery(File file) {
        BroadcastUtil.refreshGallery(file);
    }
    
    /**
     * 刷新图库
     *
     * @param filePath 文件路径
     */
    public static void refreshGallery(String filePath) {
        BroadcastUtil.refreshGallery(filePath);
    }
    
    
    /**
     * @param path directory path
     * @param name file name
     * @return really path for this file
     */
    public static String getFilePath(String path, String name) {
        return FileUtil.getFilePath(path, name);
    }
    
    
    /**
     * 创建目录
     *
     * @param path
     * @return
     */
    public static File createDirectory(String path) {
        return FileUtil.createDirectory(path);
    }
    
    
    /**
     * 创建目录
     *
     * @param path
     * @return
     */
    public static File createDirectory(String path, String name) {
        return createDirectory(getFilePath(path, name));
    }
    
    
    /**
     * 创建文件
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static File createFile(String path) throws IOException {
        return FileUtil.createFile(path);
    }
    
    /**
     * 创建文件
     *
     * @param dir
     * @param fileName
     * @return
     * @throws IOException
     */
    public static File createFile(File dir, String fileName) throws IOException {
        return FileUtil.createFile(dir, fileName);
    }
    
    
    /**
     * 创建文件
     *
     * @param dirPath
     * @param fileName
     * @return
     * @throws IOException
     */
    public static File createFile(String dirPath, String fileName) throws IOException {
        return FileUtil.createFile(dirPath, fileName);
    }
    
    
    /**
     * @param object obj
     * @param path   directory
     * @param name   file name
     */
    public synchronized static <T extends Serializable> void saveObj2File(T object, String path, String name) {
        FileUtil.saveObj2File(object, path, name);
        
    }
    
    @SuppressWarnings("unchecked")
    public synchronized static <T> T readObjFromFile(String path, String name) {
        return FileUtil.readObjFromFile(path, name);
    }
    
    
    /**
     * 移动文件
     *
     * @param srcFileName 源文件完整路径
     * @param desFileName 目的目录完整路径
     * @return 文件移动成功返回true，否则返回false
     */
    public static boolean moveFile(String srcFileName, String desFileName) {
        return FileUtil.moveFile(srcFileName, desFileName);
    }
    
    /**
     * 删除包含该文件夹在内的所有文件
     *
     * @param file
     */
    public static void deleteFilesAll(File file) {
        FileUtil.deleteFilesAll(file);
    }
    
    /**
     * 删除该文件夹内所有文件，不包含该文件夹
     *
     * @param path
     */
    public static void deleteFiles(String path) {
        deleteFilesAll(new File(path));
    }
    
    /**
     * 删除单个文件
     *
     * @param path
     */
    public static void deleteFile(String path) {
        FileUtil.deleteFile(path);
    }
    
    /**
     * @param path
     * @return true if file is exits,otherwise false
     */
    public static boolean isFileExists(String path) {
        return FileUtil.isFileExists(path);
    }
    
    public static boolean isFileExists(File file) {
        
        return FileUtil.isFileExists(file);
    }
    
    
    /**
     * 从文件中读取数据
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public static String readStrFromFile(String fileName) throws IOException {
        return FileUtil.readStrFromFile(fileName);
    }

    /**
     * 保存数据到文件中
     *
     * @param content
     * @param file
     * @throws IOException
     */
    public static void writeStr2File(String content, File file) throws IOException {
        FileUtil.writeStr2File(content, file);
    }
    
    public static long getLastModified(String fileName) {
        return getLastModified(fileName);
    }
    
    public static long getLastModified(File file) {
        return getLastModified(file);
    }
    
    
}
