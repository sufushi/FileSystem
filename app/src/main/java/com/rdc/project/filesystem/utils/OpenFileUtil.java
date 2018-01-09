package com.rdc.project.filesystem.utils;

import android.content.Intent;
import android.net.Uri;

import java.io.File;

public class OpenFileUtil {

    /**
     * 打开HTML
     * @param path
     * @return
     */
    public static Intent getHtmlFileIntent(String path) {
        File file = new File(path);
        Uri uri = Uri.parse(file.toString()).buildUpon().encodedAuthority("com.android.htmlfileprovider")
                .scheme("content").encodedPath(file.toString()).build();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(uri, "text/html");
        return intent;
    }

    /**
     * 打开图片
     * @param path
     * @return
     */
    public static Intent getImageFileIntent(String path) {
        File file = new File(path);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "image/*");
        return intent;
    }

    /**
     * 打开PDF
     * @param Path
     * @return
     */
    public static Intent getPdfFileIntent(String Path)
    {
        File file = new File(Path);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }

    /**
     * 打开TXT
     * @param Path
     * @return
     */
    public static Intent getTextFileIntent(String Path)
    {
        File file = new File(Path);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "text/plain");
        return intent;
    }

    /**
     * 代开音频
     * @param Path
     * @return
     */
    public static Intent getAudioFileIntent(String Path)
    {
        File file = new File(Path);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "audio/*");
        return intent;
    }

    /**
     * 打开视频
     * @param Path
     * @return
     */
    public static Intent getVideoFileIntent(String Path)
    {
        File file = new File(Path);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "video/*");
        return intent;
    }

    /**
     * 打开CHM
     * @param Path
     * @return
     */
    public static Intent getChmFileIntent(String Path)
    {
        File file = new File(Path);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/x-chm");
        return intent;
    }

    /**
     * 打开Word
     * @param Path
     * @return
     */
    public static Intent getWordFileIntent(String Path)
    {
        File file = new File(Path);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }

    /**
     * 打开Excel
     * @param Path
     * @return
     */
    public static Intent getExcelFileIntent(String Path)
    {
        File file = new File(Path);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }

    /**
     * 打开PPT
     * @param Path
     * @return
     */
    public static Intent getPPTFileIntent(String Path)
    {
        File file = new File(Path);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;
    }

    /**
     * 打开APK
     * @param Path
     * @return
     */
    public static Intent getApkFileIntent(String Path)
    {
        File file = new File(Path);
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        return intent;
    }

    public static Intent openFile(String fileName) {
        if (FileTypeUtil.isWpsFileType(fileName)) {
            if (fileName.endsWith(".doc") || fileName.endsWith(".docx")) {
                return getWordFileIntent(fileName);
            } else if (fileName.endsWith(".ppt") || fileName.endsWith(".pptx")) {
                return getPPTFileIntent(fileName);
            } else if (fileName.endsWith(".xls") || fileName.endsWith(".xlsx")) {
                return getExcelFileIntent(fileName);
            } else if (fileName.endsWith(".txt")) {
                return getTextFileIntent(fileName);
            } else if (fileName.endsWith(".pdf")) {
                return getPdfFileIntent(fileName);
            } else {
                return null;
            }
        } else if (FileTypeUtil.isImageFileType(fileName)) {
            return getImageFileIntent(fileName);
        } else if (FileTypeUtil.isVideoFileType(fileName)) {
            return getVideoFileIntent(fileName);
        } else if (FileTypeUtil.isAudioFileType(fileName)) {
            return getAudioFileIntent(fileName);
        } else if (fileName.endsWith(".html")) {
            return getHtmlFileIntent(fileName);
        } else {
            return null;
        }
    }

}
