package com.rdc.project.filesystem.utils;

import com.rdc.project.filesystem.R;

import java.util.HashMap;
import java.util.Iterator;

import static com.rdc.project.filesystem.constant.FileTypeConstant.*;

public class FileTypeUtil {

    public static String sFielExtension;
    public static final String UNKNOW_STRING = "<unkonwn>";

    static class MediaFileType {

        int fileType;
        String mimeType;

        public MediaFileType(int fileType, String mimeType) {
            this.fileType = fileType;
            this.mimeType = mimeType;
        }
    }

    private static HashMap<String, MediaFileType> sFileTypeMap = new HashMap<>();
    private static HashMap<String, Integer> sMimeTypeMap = new HashMap<>();

    public static void addFileType(String extension, int fileType, String mimeType) {
        sFileTypeMap.put(extension, new MediaFileType(fileType, mimeType));
        sMimeTypeMap.put(extension, new Integer(fileType));
    }

    static {
        addFileType("MP3", FILE_TYPE_MP3, "audio/mpeg");
        addFileType("M4A", FILE_TYPE_M4A, "audio/mp4");
        addFileType("WAV", FILE_TYPE_WAV, "audio/x-wav");
        addFileType("AMR", FILE_TYPE_AMR, "audio/amr");
        addFileType("AWB", FILE_TYPE_AWB, "audio/amr-wb");
        addFileType("WMA", FILE_TYPE_WMA, "audio/x-ms-wma");
        addFileType("OGG", FILE_TYPE_OGG, "application/ogg");

        addFileType("MID", FILE_TYPE_MID, "audio/midi");
        addFileType("XMF", FILE_TYPE_MID, "audio/midi");
        addFileType("RTTTL", FILE_TYPE_MID, "audio/midi");
        addFileType("SMF", FILE_TYPE_SMF, "audio/sp-midi");
        addFileType("IMY", FILE_TYPE_IMY, "audio/imelody");

        addFileType("MP4", FILE_TYPE_MP4, "video/mp4");
        addFileType("M4V", FILE_TYPE_M4V, "video/mp4");
        addFileType("3GP", FILE_TYPE_3GPP, "video/3gpp");
        addFileType("3GPP", FILE_TYPE_3GPP, "video/3gpp");
        addFileType("3G2", FILE_TYPE_3GPP2, "video/3gpp2");
        addFileType("3GPP2", FILE_TYPE_3GPP2, "video/3gpp2");
        addFileType("WMV", FILE_TYPE_WMV, "video/x-ms-wmv");

        addFileType("JPG", FILE_TYPE_JPEG, "image/jpeg");
        addFileType("JPEG", FILE_TYPE_JPEG, "image/jpeg");
        addFileType("GIF", FILE_TYPE_GIF, "image/gif");
        addFileType("PNG", FILE_TYPE_PNG, "image/png");
        addFileType("BMP", FILE_TYPE_BMP, "image/x-ms-bmp");
        addFileType("WBMP", FILE_TYPE_WBMP, "image/vnd.wap.wbmp");

        addFileType("M3U", FILE_TYPE_M3U, "audio/x-mpegurl");
        addFileType("PLS", FILE_TYPE_PLS, "audio/x-scpls");
        addFileType("WPL", FILE_TYPE_WPL, "application/vnd.ms-wpl");

        addFileType("DOC", FILE_TYPE_DOC, "wps/doc");
        addFileType("DOCX", FILE_TYPE_DOCX, "wps/docx");
        addFileType("PPT", FILE_TYPE_PPT, "wps/ppt");
        addFileType("PPTX", FILE_TYPE_PPTX, "wps/pptx");
        addFileType("XLS", FILE_TYPE_XLS, "wps/xls");
        addFileType("XLSX", FILE_TYPE_XLSX, "wps/xlsx");
        addFileType("TXT", FILE_TYPE_TXT, "wps/txt");
        addFileType("PDF", FILE_TYPE_PDF, "wps/pdf");

        StringBuilder builder = new StringBuilder();
        Iterator<String> iterator = sFileTypeMap.keySet().iterator();

        while (iterator.hasNext()) {
            if (builder.length() > 0) {
                builder.append(",");
            }
            builder.append(iterator.next());
        }
        sFielExtension = builder.toString();
    }

    private static boolean isAudioFileType(int fileType) {
        return ((fileType >= FIRST_AUDIO_FILE_TYPE && fileType <= LAST_AUDIO_FILE_TYPE)
        || (fileType >= FIRST_MIDI_FILE_TYPE && fileType <= LAST_MIDI_FILE_TYPE));
    }

    private static boolean isVideoFileType(int fileType) {
        return fileType >= FIRST_VIDEO_FILE_TYPE && fileType <= LAST_VIDEO_FILE_TYPE;
    }

    private static boolean isImageFileType(int fileType) {
        return fileType >= FIRST_IMAGE_FILE_TYPE && fileType <= LAST_IMAGE_FILE_TYPE;
    }

    private static boolean isPlayListFileType(int fileType) {
        return fileType >= FIRST_PLAYLIST_FILE_TYPE && fileType <= LAST_PLAYLIST_FILE_TYPE;
    }

    private static boolean isWpsFileType(int fileType) {
        return fileType >= FIRST_WPS_FILE_TYPE && fileType <= LAST5_WPS_FILE_TYPE;
    }

    public static MediaFileType getFileType(String path) {
        int index = path.lastIndexOf(".");
        if (index < 0) {
            return null;
        }
        return sFileTypeMap.get(path.substring(index + 1).toUpperCase());
    }

    public static boolean isAudioFileType(String path) {
        MediaFileType mediaFileType = getFileType(path);
        if (mediaFileType != null) {
            return isAudioFileType(mediaFileType.fileType);
        }
        return false;
    }

    public static boolean isVideoFileType(String path) {
        MediaFileType mediaFileType = getFileType(path);
        if (mediaFileType != null) {
            return isVideoFileType(mediaFileType.fileType);
        }
        return false;
    }

    public static boolean isImageFileType(String path) {
        MediaFileType mediaFileType = getFileType(path);
        if (mediaFileType != null) {
            return isImageFileType(mediaFileType.fileType);
        }
        return false;
    }

    public static boolean isPlayListFileType(String path) {
        MediaFileType mediaFileType = getFileType(path);
        if (mediaFileType != null) {
            return isPlayListFileType(mediaFileType.fileType);
        }
        return false;
    }

    public static boolean isWpsFileType(String path) {
        MediaFileType mediaFileType = getFileType(path);
        if (mediaFileType != null) {
            return isWpsFileType(mediaFileType.fileType);
        }
        return false;
    }

    public static int getFileTypeByMimeType(String mimeType) {
        Integer value = sMimeTypeMap.get(mimeType);
        return value == null ? 0 : value.intValue();
    }

    public static int getTypeResId(String fileName) {
        int resId;
        if (FileTypeUtil.isWpsFileType(fileName)) {
            if (fileName.endsWith(".doc") || fileName.endsWith(".docx")) {
                resId = R.drawable.ic_word;
            } else if (fileName.endsWith(".ppt") || fileName.endsWith(".pptx")) {
                resId = R.drawable.ic_ppt;
            } else if (fileName.endsWith(".xls") || fileName.endsWith(".xlsx")) {
                resId = R.drawable.ic_excel;
            } else if (fileName.endsWith(".txt")) {
                resId = R.drawable.ic_txt;
            } else if (fileName.endsWith(".pdf")) {
                resId = R.drawable.ic_pdf;
            } else {
                resId = R.drawable.ic_file;
            }
        } else if (FileTypeUtil.isImageFileType(fileName)) {
            resId = R.drawable.ic_picture;
        } else if (FileTypeUtil.isVideoFileType(fileName)) {
            resId = R.drawable.ic_video;
        } else if (FileTypeUtil.isAudioFileType(fileName)) {
            resId = R.drawable.ic_music;
        } else if (fileName.endsWith(".zip") || fileName.endsWith(".rar")) {
            resId = R.drawable.ic_zip;
        } else if (fileName.endsWith(".apk")) {
            resId = R.drawable.ic_apk;
        } else if (fileName.endsWith(".html")){
            resId =  R.drawable.ic_html;
        } else {
            resId = R.drawable.ic_file;
        }
        return resId;
    }
}
