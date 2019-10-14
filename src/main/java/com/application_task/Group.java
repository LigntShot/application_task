package com.application_task;

/**
 * Class, describing a social network group with a <b>gid</b> and an array of <b>uids</b>
 * @author Shablonov Dennis
 * @version 0.1
 */
class Group {
    /** Group ID field */
    int gid;
    /** User IDs field */
    int[] uids;

    /**
     * Constructor for this class
     * @param gid - Group ID
     * @param uids Array of User IDs
     */
    Group(int gid, int[] uids) {
        this.gid = gid;
        this.uids = uids;
    }
}
