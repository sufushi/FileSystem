package com.rdc.project.filesystem.constant;

public class FileTypeConstant {

    /**
     * Audio
     */
    public static final int FILE_TYPE_MP3     = 1;
    public static final int FILE_TYPE_M4A     = 2;
    public static final int FILE_TYPE_WAV     = 3;
    public static final int FILE_TYPE_AMR     = 4;
    public static final int FILE_TYPE_AWB     = 5;
    public static final int FILE_TYPE_WMA     = 6;
    public static final int FILE_TYPE_OGG     = 7;
    public static final int FIRST_AUDIO_FILE_TYPE = FILE_TYPE_MP3;
    public static final int LAST_AUDIO_FILE_TYPE = FILE_TYPE_OGG;

    /**
     * MIDI
     */
    public static final int FILE_TYPE_MID     = 11;
    public static final int FILE_TYPE_SMF     = 12;
    public static final int FILE_TYPE_IMY     = 13;
    public static final int FIRST_MIDI_FILE_TYPE = FILE_TYPE_MID;
    public static final int LAST_MIDI_FILE_TYPE = FILE_TYPE_IMY;

    /**
     * Video
     */
    public static final int FILE_TYPE_MP4     = 21;
    public static final int FILE_TYPE_M4V     = 22;
    public static final int FILE_TYPE_3GPP    = 23;
    public static final int FILE_TYPE_3GPP2   = 24;
    public static final int FILE_TYPE_WMV     = 25;
    public static final int FIRST_VIDEO_FILE_TYPE = FILE_TYPE_MP4;
    public static final int LAST_VIDEO_FILE_TYPE = FILE_TYPE_WMV;

    /**
     * Image
     */
    public static final int FILE_TYPE_JPEG    = 31;
    public static final int FILE_TYPE_GIF     = 32;
    public static final int FILE_TYPE_PNG     = 33;
    public static final int FILE_TYPE_BMP     = 34;
    public static final int FILE_TYPE_WBMP    = 35;
    public static final int FIRST_IMAGE_FILE_TYPE = FILE_TYPE_JPEG;
    public static final int LAST_IMAGE_FILE_TYPE = FILE_TYPE_WBMP;

    /**
     * PlayList
     */
    public static final int FILE_TYPE_M3U     = 41;
    public static final int FILE_TYPE_PLS     = 42;
    public static final int FILE_TYPE_WPL     = 43;
    public static final int FIRST_PLAYLIST_FILE_TYPE = FILE_TYPE_M3U;
    public static final int LAST_PLAYLIST_FILE_TYPE = FILE_TYPE_WPL;

    /**
     * WPS
     */
    public static final int FILE_TYPE_DOC = 51;
    public static final int FILE_TYPE_DOCX = 52;
    public static final int FILE_TYPE_PPT = 53;
    public static final int FILE_TYPE_PPTX = 54;
    public static final int FILE_TYPE_XLS = 55;
    public static final int FILE_TYPE_XLSX = 56;
    public static final int FILE_TYPE_TXT = 57;
    public static final int FILE_TYPE_PDF = 58;
    public static final int FIRST_WPS_FILE_TYPE = FILE_TYPE_DOC;
    public static final int LAST5_WPS_FILE_TYPE = FILE_TYPE_PDF;

}
