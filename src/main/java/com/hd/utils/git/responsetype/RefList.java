package com.hd.utils.git.responsetype;

import org.eclipse.jgit.lib.Ref;

import java.io.*;
import java.util.LinkedList;

/**
 * @author jihang
 * @date 2018/01/06
 */

public class RefList implements Serializable {

    private LinkedList<Ref> refList;

    public LinkedList<Ref> getRefList() {
        return refList;
    }

    public void setRefList(LinkedList<Ref> refList) {
        this.refList = refList;
    }

    public RefList(Ref ref) {
        refList = new LinkedList<>();
        refList.push(ref);
    }

    public void addRef(Ref ref) {
        refList.push(ref);
    }
}
