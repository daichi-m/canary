package org.cleanermp3.canary;

/**
 * Created by daichi on 7/5/15.
 */
public interface Constants {

    String CANARY_PROPS_FILE = "canary.properties";

    String USER_HOME = System.getenv("user.home");
    String DEFAULT_MUSIC_DIR = USER_HOME + System.getenv("file.separator") + "Music";

    String MUSIC_DIR_TAG = "music.directory";
    String USER_HOME_TAG = "user.home";

}
