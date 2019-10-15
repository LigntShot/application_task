package com.application_task;

/**
 * Класс, описывающий группу соц. сети с помощью<b>gid</b> и массива <b>uids</b>
 * @author Шаблонов Денис
 * @version 1.0
 */
class Group {
    /** Поле ИД Группы*/
    private int gid;
    /** Поле ИДшников пользователей*/
    int[] uids;

    /**
     * Конструктор
     * @param gid - ИД Группы
     * @param uids Массив ИДшников пользователей
     */
    Group(int gid, int[] uids) {
        this.gid = gid;
        this.uids = uids;
    }
}
