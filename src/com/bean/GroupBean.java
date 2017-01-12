package com.bean;

/**
 * Created by recycle on 12/15 0015.
 */
public class GroupBean {
    private int idGroup;
    private String groupName;
    private int groupScheduled;

    public int getGroupScheduled() {
        return groupScheduled;
    }

    public void setGroupScheduled(int groupScheduled) {
        this.groupScheduled = groupScheduled;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }
}
