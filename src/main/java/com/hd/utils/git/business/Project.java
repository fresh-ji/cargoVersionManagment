package com.hd.utils.git.business;

import com.hd.utils.git.common.ServerResponse;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.File;

/**
 * 项目类
 * @author jihang
 * @date 2017/11/21
 */

public class Project extends AbstractDeck {

    public static ServerResponse<String> addCargo(Long id, String cargoPath) throws Throwable {
        //建文件夹
        String name = Long.toString(id);
        String masterPath = cargoPath + name;
        File file = new File(masterPath);
        if (file.exists()) {
            return ServerResponse.createByErrorMessage(name + " already exists!!");
        }
        //建库
        Git git = Git.init().setDirectory(file).call();
        //提交第一个commit
        git.commit().setCommitter("master", "email")
                .setMessage("Hi, this is " + name + "!").call();
        Iterable<RevCommit> gitLog = git.log().setMaxCount(1).call();
        return ServerResponse.createBySuccess(gitLog.iterator().next().getName());
    }

    public static ServerResponse deleteCargo(Long id) throws Throwable {
        return ServerResponse.createByErrorMessage("function developing.....");
    }

}