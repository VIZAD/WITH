package com.example.vizax.with.util;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;

/**
 * 文件读写工具类
 *
 * @author 郑韬
 */
public class FilesUtil {

    /**
     * 保存对象到磁盘
     *
     * @param path     路径
     * @param filename 文件名
     * @param obj      要保存的可序列化对象
     * @return
     */
    public static boolean saveObjectToFile(String path, String filename, Serializable obj) {
        boolean result = false;
        ObjectOutputStream oo = null;
        try {
            oo = new ObjectOutputStream(new FileOutputStream(new File(path + "/" + filename)));
            oo.writeObject(obj);
            oo.flush();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (oo != null) {
                try {
                    oo.close();
                } catch (IOException e) {
                }
            }
        }
        return result;
    }

    /**
     * 从文件中获取序列化过的对象
     *
     * @param path     路径
     * @param filename 文件名
     * @return 若不存在文件则返回空
     */
    public static Object getObjectFromFile(String path, String filename) {
        ObjectInputStream oo = null;
        try {
            File file = new File(path + "/" + filename);
            if (!file.exists()) return null;
            oo = new ObjectInputStream(new FileInputStream(file));
            try {
                return oo.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (oo != null) {
                try {
                    oo.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

    /**
     * 根据文件名称读取文件内容
     *
     * @return 文件内容
     */
    @SuppressWarnings("resource")
    public static String getStrByFileName(String path, String filename) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(path, filename);
        try {
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                InputStream in = null;// 创建输出流
                in = new FileInputStream(file);
                int filelen = in.available();
                byte[] filetext = new byte[filelen];
                in.read(filetext);
                String text = new String(filetext, 0, filetext.length, "utf-8");
                return text;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean saveStrToFileName(String path, String filename,
                                            String filestr) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(path, filename);
        if (!file.exists()) {
            try {
                file.createNewFile();
                OutputStreamWriter out = null;// 创建输入流
                try {
                    out = new OutputStreamWriter(new FileOutputStream(file),
                            "utf-8");// 获取输入流
                    out.write(filestr);// 将数据变为字符串后保存
                    if (out != null) {
                        out.close();// 关闭输出
                    }
                    return true;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    if (out != null) {
                        out.close();// 关闭输出
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            OutputStreamWriter out = null;// 创建输入流
            try {
                out = new OutputStreamWriter(new FileOutputStream(file),
                        "utf-8");// 获取输入流
                out.write(filestr);// 将数据变为字符串后保存
                if (out != null) {
                    out.close();// 关闭输出
                }
                return true;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (out != null) {
                try {
                    out.close();// 关闭输出
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    /**
     * 在指定目录下创建文件
     *
     * @param path
     * @param filename
     * @return
     */
    public static boolean createFile(String path, String filename) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(path, filename);
        if (!file.exists()) {
            try {
                file.createNewFile();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 在指定目录下删除文件
     *
     * @param path
     * @param filename
     * @return
     */
    public static boolean deleteFile(String path, String filename) {
        File file = new File(path);
        if (!file.exists()) {
            return false;
        }
        file = new File(path, filename);
        if (file.exists()) {
            file.delete();
            return true;
        }
        return false;
    }

    public static String getBytesByFile(String path) {
        // 使用InputStream从文件中读取数据，在已知文件大小的情况下，建立合适的存储字节数组
        File f = new File(path);
        byte b[];
        try {
            InputStream in = new FileInputStream(f);
            b = new byte[(int) f.length()];
            in.read(b); // 读取文件中的内容到b[]数组
            in.close();
            return new String(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取文件名称
     *
     * @param pathandname 文件路径
     * @return
     */
    public static String getFileName(String pathandname) {
        int start = pathandname.lastIndexOf("/");
        int end = pathandname.lastIndexOf(".");
        if (start != -1 && end != -1) {
            String name = pathandname.substring(start + 1);
            int eng = name.indexOf(",");
            int cha = name.indexOf("，");
            if (cha == -1 && eng == -1) {
                return name;
            }
            return null;
        } else {
            return null;
        }
    }

    /**
     * 生成路径
     *
     * @param filePath
     */
    public static void makeRootDirectory(String filePath) {
        File file = null;
        String[] paths = filePath.split("/");           //将路径拆分成一段一段的
        String strTemp = "";
        try {
            for (String strSplit :
                    paths) {
                strTemp += (strSplit + "/");
                file = new File(strTemp);
                if (!file.exists()) {
                    file.mkdir();
                }
            }
        } catch (Exception e) {

        }
    }

    /**
     * 通过bitmap 保存文件  返回文件路径
     *
     * @param bitmap   图片bmp
     * @param fileName 文件名
     * @return 文件路径
     * @throws IOException
     */
    public static String saveBitmap(Bitmap bitmap, String fileName) throws IOException {
        String url_comp = android.os.Environment
                .getExternalStorageDirectory().getPath() + "superTreasure/cacheImage/";
        String url = url_comp + fileName;
        File file = new File(url);
        if (!file.exists())
            file.createNewFile();
           /* file.delete();
        file.createNewFile();*/
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
                out.flush();
                out.close();
                return url;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 复制文件
     * @param src
     * @param dir
     * @param filename
     * @return
     */
    public static boolean copyTo(File src, File dir, String filename) {

        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fi = new FileInputStream(src);
            in = fi.getChannel();//得到对应的文件通道
            File dst;
            dst = new File(dir, filename);
            fo = new FileOutputStream(dst);
            out = fo.getChannel();//得到对应的文件通道
            in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            try {

                if (fi != null) {
                    fi.close();
                }

                if (in != null) {
                    in.close();
                }

                if (fo != null) {
                    fo.close();
                }

                if (out != null) {
                    out.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

    }
}
