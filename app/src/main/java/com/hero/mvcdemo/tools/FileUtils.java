package com.hero.mvcdemo.tools;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Android文件目录 :https://blog.csdn.net/wzhworld/article/details/83718336#1%E3%80%81%E5%86%85%E9%83%A8%E5%AD%98%E5%82%A8
 *
 *
 * context.getExternalCacheDir()  、 context.getCacheDir()
 * 相同点：
 * 1、相同点：都可以做app缓存目录。
 * 2、app卸载后，两个目录下的数据都会被清空。
 * 不同点：
 * 1、目录的路径不同。前者的目录存在外部SD卡上的。后者的目录存在app的内部存储上。
 * 2、前者的路径在手机里可以直接看到。后者的路径需要root以后，用Root Explorer 文件管理器才能看到。
 * <p>
 * mkdir 只创建文件夹  如果没有根目录则不创建
 * mkdirs  会创建整个目录 如果路径中文件夹没有则会一路创建
 *
 * Context.getExternalFilesDir()方法可以获取到   SDCard/Android/data/youPackageName/files/ 目录，一般放一些长时间保存的数据
 * </pre>
 */
public class FileUtils {
    private static final String TAG = FileUtils.class.getSimpleName();

    /**
     * 如果路径文件未创建  则创建出来
     *
     * @param filePath
     */
    public static String makeFileCreate(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }

    /**
     * 如果路径文件夹未创建  则创建出来
     *
     * @param filePath
     * @return
     */
    public static String makeFileDirCreate(String filePath) {
        try {
            File dir = new File(filePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }

    /**
     * 生成 图片文件   在sd卡
     *
     * @param dirName sd卡 根目录文件名
     */
    public static File createImageFile(String dirName) {
        File appDir = null;
        try {
            appDir = new File(Environment.getExternalStorageDirectory(), dirName);
            if (!appDir.exists()) {
                appDir.mkdir();
            }
        } catch (Exception e) {

        }
        String fileNmae = System.currentTimeMillis() + ".jpg";
        return new File(appDir, fileNmae);
    }

    /**
     * 生成 图片文件    在缓存目录中
     */
    public static File createImageFile(Context mContext, String dirName) {
        File appDir = null;
        try {
            appDir = new File(mContext.getCacheDir().getPath(), dirName);
            if (!appDir.exists()) {
                appDir.mkdir();
            }
        } catch (Exception e) {

        }
        String fileNmae = System.currentTimeMillis() + ".jpg";
        return new File(appDir, fileNmae);
    }

    /**
     * 在SD卡上创建一个文件夹
     */
    public static String createDirFile(String saveDir) {
        String savePath = "";
        try {
            // 下载位置
            File downloadFile = new File(Environment.getExternalStorageDirectory(), saveDir);
            if (!downloadFile.mkdirs()) {
                downloadFile.createNewFile();
            }
            savePath = downloadFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return savePath;
    }

    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();
    }

    /**
     * 生成 APP内部文件夹
     */
    public static File createAppDirFile(Context mContext, String dirName) {
        File appDir = null;
        try {
            appDir = new File(mContext.getExternalCacheDir().getPath(), dirName);
            if (!appDir.exists()) {
                appDir.mkdir();
            }
        } catch (Exception e) {
        }
        return appDir;
    }

    //在文件夹下  创建文件夹
    public static File createDirFile(String dirPath, String dirName) {
        File appDir = null;
        try {
            appDir = new File(dirPath, dirName);
            if (!appDir.exists()) {
                appDir.mkdir();
            }
        } catch (Exception e) {
        }
        return appDir;
    }

    /**
     * 生成 APP内部文件夹
     * /data/data/包名/cache
     */
    public static String createAppDirFilePath(Context mContext, String dirName) {
        File appDir = null;
        String filePath = "";
        try {
            appDir = new File(mContext.getCacheDir().getPath(), dirName);
            if (!appDir.exists()) {
                appDir.mkdir();
            }
            filePath = appDir.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }

    /**
     * 生成 APP内部文件夹   非缓存
     * /data/data/包名/files
     *
     * @param mContext
     * @param dirName
     * @return
     */
    public static String createAppFilesDirPath(Context mContext, String dirName) {
        File appDir = null;
        String filePath = "";
        try {
            appDir = new File(mContext.getFilesDir().getPath(), dirName);
            if (!appDir.exists()) {
                appDir.mkdir();
            }
            filePath = appDir.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }

    /**
     * 对文件重命名
     *
     * @param filePath 文件的路径     reName  新文件名  不含后缀
     */
    public static String chageFileName(String filePath, String reName) {
        try {
            File file = new File(filePath);
            //前面路径必须一样才能修改成功
            String path = filePath.substring(0, filePath.lastIndexOf("/") + 1) + reName + filePath.substring(filePath.lastIndexOf("."), filePath.length());
            File newFile = new File(path);
            file.renameTo(newFile);
            return path;
        } catch (Exception e) {
            e.printStackTrace();
            return filePath;
        }
    }

    /**
     * oldPath: 图片缓存的路径
     * newPath: 图片缓存copy的路径
     */
    public static void copyFile(String oldPath, String newPath) {
        try {
            int byteRead;
            File oldFile = new File(oldPath);
            if (oldFile.exists()) {
                InputStream inStream = new FileInputStream(oldPath);
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                while ((byteRead = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteRead);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制文件操作出错");
            e.printStackTrace();
        }
    }

    //判断文件是否存在
    public static boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    //判断网络文件大小
    public static int getHttpfileLength(String fileUrl) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(fileUrl).openConnection();
            int contentLength = connection.getContentLength();
            return contentLength;
        } catch (Exception e) {
            return 0;
        }
    }

    //把文件插入系统图库
    public static void saveImageToGallery(Context context, File file, String title) {
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), title, null);
            //        没有立刻显示在图库中，而我们需要立刻更新系统图库以便让用户可以立刻查看到这张图片。更新系统图库的方法
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //把文件插入系统图库
    public static void saveImageToGallery(Context context, String fileAbsolutePath, String title) {
        // 其次把文件插入到系统图库
        try {
            //调用系统提供的插入图库的方法
            //title、description参数只是插入数据库中的字段，真实的图片名称系统会自动分配
            //MediaStore.Images.Media.insertImage(getContentResolver(), "image path", "title", "description");
            //此处插入图库  图库会多一个图片
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    fileAbsolutePath, title, null);
            // 最后通知图库更新
//        没有立刻显示在图库中，而我们需要立刻更新系统图库以便让用户可以立刻查看到这张图片。更新系统图库的方法
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + fileAbsolutePath)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //把文件插入系统图库
    public static void saveImageToGallery(final Context context, final String fileAbsolutePath, final boolean isInsertImage) {
        // 其次把文件插入到系统图库
        //新建线程  防止UI卡顿
        new Thread() {
            public void run() {
                //调用系统提供的插入图库的方法
                //title、description参数只是插入数据库中的字段，真实的图片名称系统会自动分配
                //MediaStore.Images.Media.insertImage(getContentResolver(), "image path", "title", "description");
                try {
                    //此处插入图库  图库会多一个图片
                    if (isInsertImage) {
                        MediaStore.Images.Media.insertImage(context.getContentResolver(),
                                fileAbsolutePath, "", null);
                    }
                    // 最后通知图库更新
//        没有立刻显示在图库中，而我们需要立刻更新系统图库以便让用户可以立刻查看到这张图片。更新系统图库的方法
                    context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + fileAbsolutePath)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 向文件里面追加类容
     *
     * @param absPath
     * @param content
     */
    public static void appendInfo2File(String absPath, String content) {
        FileWriter fileWrite = null;
        try {

            File fileContent = new File(absPath);
            File parentFile = fileContent.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }

            if (!fileContent.exists()) {
                fileContent.createNewFile();
            }

            fileWrite = new FileWriter(fileContent, true);
            fileWrite.write(content);
            fileWrite.write("\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fileWrite) {
                    fileWrite.flush();
                    fileWrite.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //删除文件
    public static void delFile(String fileAbsolutePath) {
        try {
            File file = new File(fileAbsolutePath);
            if (file.isFile()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //删除文件
    public static void delFile(File file) {
        try {
            if (file.isFile()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //删除文件夹和文件夹里面的文件
    public static void deleteDir(String fileAbsolutePath) {
        File dir = new File(fileAbsolutePath);
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;

        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDir(file.getAbsolutePath()); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }

    /**
     * 删除文件  无论是文件还是文件夹都删除
     *
     * @param fileAbsolutePath
     */
    public static void deleteFileOrDir(String fileAbsolutePath) {
        delFile(fileAbsolutePath);
        deleteDir(fileAbsolutePath);
    }

    /**
     * 以网络文件地址获取md5值 + 最后一个/之后的字符，做文件名，在本地生成文件
     *
     * @param isSDPath 是否是在sd路径  不是则在app缓存文件中
     */
    /*public static File createFlieFromHttp(String fileUrl, String destFileDir, boolean isSDPath, Context context) {
        //文件名 为md5值 + 文件最后一个/后的字符串 ------保证一个url对应一个文件名--------已下载过不用下载
        File file = null;
        try {
            String fileName = Md5Helper.StringtoMD5Final(fileUrl) + getFileNameFromUrl(fileUrl);
            if (isSDPath) {
                file = new File(FileUtils.createDirFile(destFileDir), fileName);
            } else {
                file = new File(FileUtils.createAppDirFilePath(context, destFileDir), fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }*/

    /**
     * @param url
     * @return 从下载连接中解析出文件名
     */
    public static String getFileNameFromUrl(String url) {
        if (url != null) {
            return url.substring(url.lastIndexOf("/") + 1);
        }
        return "";
    }

    /**
     * 往本地写入文件的方法
     *
     * @param fileUrl
     * @param destFileDir
     * @param bytes
     * @param isSDPath    是否是在sd卡创建
     * @throws Exception
     */
  /*  public static void savaFileToNative(String fileUrl, String destFileDir, boolean isSDPath, Context context, byte[] bytes) throws Exception {
        File flieFromHttp = createFlieFromHttp(fileUrl, destFileDir, isSDPath, context);
        //这里就不要用openFileOutput了,那个是往手机内存中写数据的
        FileOutputStream output = new FileOutputStream(flieFromHttp);
        output.write(bytes);
        //将bytes写入到输出流中
        output.close();
        //关闭输出流
    }*/

    /**
     * 图片插入系统相册        仍有缺陷  在picture 中生成  损坏的  图片
     *
     * @param fileName
     * @param context
     */
    public static void insertImage(final String fileName, Context context) {
        // Toast.makeText(this, "插入图片", Toast.LENGTH_LONG).show();
        try {
            insertImageW(context.getContentResolver(), fileName, new File(fileName).getName(),
                    new File(fileName).getName());
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(new File(fileName));
            intent.setData(uri);
            context.sendBroadcast(intent);
            MediaScannerConnection.scanFile(context, new String[]{fileName}, new String[]{"image/jpeg"},
                    new MediaScannerConnection.MediaScannerConnectionClient() {
                        @Override
                        public void onMediaScannerConnected() {

                        }

                        @Override
                        public void onScanCompleted(String path, Uri uri) {

                        }
                    });
//            File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//            FileUtils.deleteFile(new File(externalStoragePublicDirectory , FileUtils.getFileNameFromUrl(fileName)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String insertImageW(ContentResolver cr, String imagePath, String name, String description)
            throws FileNotFoundException {
        // Check if file exists with a FileInputStream
        FileInputStream stream = new FileInputStream(imagePath);
        try {
            Bitmap bm = BitmapFactory.decodeFile(imagePath);
            String ret = insertImageT(cr, bm, name, description);
            bm.recycle();
            return ret;
        } finally {
            try {
                stream.close();
            } catch (Exception e) {
            }
        }
    }

    private static String insertImageT(ContentResolver cr, Bitmap source, String title, String description) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, title);
        values.put(MediaStore.Images.Media.DESCRIPTION, description);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

        Uri url = null;
        String stringUrl = null; /* value to be returned */

        try {
            String CONTENT_AUTHORITY_SLASH = "content://" + "media" + "/";
            Uri uri = Uri.parse(CONTENT_AUTHORITY_SLASH + "external" + "/images/media");
            url = cr.insert(uri, values);
        } catch (Exception e) {
            if (url != null) {
                cr.delete(url, null, null);
                url = null;
            }
        }

        if (url != null) {
            stringUrl = url.toString();
        }
        return stringUrl;
    }

    /**
     * 从文件夹中获取文件  列表
     *
     * @param fileDir
     * @return
     */
    public static List<File> getDirInFile(File fileDir) {
        if (fileDir == null || !fileDir.exists()) {
            return new ArrayList<>();
        }
        File[] fileArray = fileDir.listFiles();
        if (fileArray == null) {
            return new ArrayList<>();
        }
        List<File> fileList = new ArrayList<>();
        for (File f : fileArray) {
            if (f != null && f.isFile()) {
                fileList.add(f);
            } else {
                fileList.addAll(getDirInFile(f));
            }
        }
        return fileList;
    }

    /**
     * 查询文件夹中所有文件路径  包括子文件夹
     *
     * @param fileDirPath
     * @return
     */
    public static List<String> getDirInFile(String fileDirPath) {
        List<String> fileList = new ArrayList<>();
        if (EmptyJudgeUtils.stringIsEmpty(fileDirPath)) {
            return fileList;
        }
        File fileDir = new File(fileDirPath);
        if (!fileDir.exists()) {
            return fileList;
        }
        File[] fileArray = fileDir.listFiles();
        if (fileArray == null || fileArray.length == 0) {
            return fileList;
        }
        for (File f : fileArray) {
            if (f != null && f.isFile()) {
                fileList.add(f.getAbsolutePath());
            } else {
                fileList.addAll(getDirInFile(f.getAbsolutePath()));
            }
        }
        return fileList;
    }

    /**
     * 读取本地 asset 文件
     *
     * @param fileName
     * @return
     */
    public static String getAssetFlieString(Context context,String fileName) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 读取本地 RAW 文件
     *
     * @param resId
     * @return
     */
    public static String getRawFlieString(Context context,int resId) {
        try {
            InputStream is = context.getResources().openRawResource(resId);
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuffer buffer = new StringBuffer("");
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int getResIdFromName(Context context,String fileName, String fileType) {
        int resId = 0;
        try {
            resId = context.getResources().getIdentifier(fileName, fileType, context.getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resId;
    }

    /**
     * 截取文件名  不含后缀
     *
     * @param pathFile
     * @return
     */
    public static String getFileNameFromPath(String pathFile) {
        String fileName = "";
        if (TextUtils.isEmpty(pathFile)) {
            return fileName;
        }
        int lastIndexOf = pathFile.lastIndexOf("/");
        int lastIndexOfDot = pathFile.lastIndexOf(".");
        //是文件   最后一个“.”比最后一个"/"靠后
        if (lastIndexOf < lastIndexOfDot) {
            fileName = pathFile.substring(lastIndexOf + 1, lastIndexOfDot);
        } else {
            //是文件夹
            String[] dir = pathFile.split("/+");
            int lastNameIndex = dir.length - 1;
            fileName = dir[lastNameIndex];
        }

        return fileName;
    }

    /**
     * 读取文件中的文本内容
     *
     * @param filePath
     * @return
     */
    public static String read(String filePath) {

        FileInputStream fis = null;
        String result = null;

        try {

            File file = new File(filePath);

            fis = new FileInputStream(file);

            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();

            result = new String(buffer, "UTF-8");

        } catch (Exception ex) {

            ex.printStackTrace();

            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}