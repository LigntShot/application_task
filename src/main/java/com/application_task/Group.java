package com.application_task;

/**
 * Class, describing a social network group with a <b>gid</b> and an array of <b>uids</b>
 * @author Shablonov Dennis
 * @version 0.1
 */
public class Group {
    int gid;
    int[] uids;

    public Group(int gid, int[] uids) {
        this.gid = gid;
        this.uids = uids;
    }
}
