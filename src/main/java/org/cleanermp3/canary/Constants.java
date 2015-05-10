package org.cleanermp3.canary;

import java.util.regex.Pattern;

/**
 * Created by daichi on 7/5/15.
 */
public interface Constants {

    String CANARY_PROPS_FILE = "canary.properties";

    String USER_HOME = System.getenv("user.home");
    String DEFAULT_MUSIC_DIR = USER_HOME + System.getenv("file.separator") + "Music";

    String MUSIC_DIR_TAG = "music.directory";
    String USER_HOME_TAG = "user.home";
    String MUSIC_DEST_TAG = "music.dest";

    String MP3_MEDIA_TYPE = "audio/mpeg";

    String LOG4J_LOGGER = "canaryLogger";

    String EMPTY= "";
    String EXCEPTION_DEFAULT_MSG = "Exception Faced";

    enum TagContents {
        TITLE,
        ARTIST,
        ALBUM;
    }

    Pattern URL_PATTERN = Pattern.compile("((?! -)[A-Za-z0-9-]{1,63}(?<!-)\\.)+[A-Za-z]{2,6}");
    Pattern BRACKET_PATTERN = Pattern.compile("[({<]\\s*[)}>]|\\[\\s*\\]");
    Pattern TRAILING_PUNCT_PATTERN = Pattern.compile("-\\s*$");
    Pattern TRACK_NUMBER_PATTERN = Pattern.compile("^\\d+\\.?");

}
