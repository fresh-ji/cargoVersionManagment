package com.hd.utils.git.responsetype;

/**
 * 作为taskPushInfo返回
 * @author jihang
 * @date 2017/12/12
 */

public class PushResult {
    public String branchName;
    public String[] differWithOrigin;
    public String[] differWithCurrent;
}