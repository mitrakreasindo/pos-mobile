package com.mitrakreasindo.pos.model;

/**
 * Created by hendric on 2017-05-19.
 */

public class Role
{
    private String id;
    private String name;
    private byte[] permissions;
    private int rightslevel;
    private String siteguid;
    private boolean sflag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPermissions() {
        return permissions;
    }

    public void setPermissions(byte[] permissions) {
        this.permissions = permissions;
    }

    public int getRightslevel() {
        return rightslevel;
    }

    public void setRightslevel(int rightslevel) {
        this.rightslevel = rightslevel;
    }

    public String getSiteguid() {
        return siteguid;
    }

    public void setSiteguid(String siteguid) {
        this.siteguid = siteguid;
    }

    public boolean isSflag() {
        return sflag;
    }

    public void setSflag(boolean sflag) {
        this.sflag = sflag;
    }

    @Override
    public String toString(){

        return name;
    }
}
