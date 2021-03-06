package com.hd.utils.git.business;

import com.hd.utils.git.common.ServerResponse;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.File;

/**
 * 任务类
 * @author jihang
 * @date 2017/11/21
 */

public class Task extends AbstractDeck {

    public static ServerResponse<String> addCargo(Long id, String cargoPath) throws Throwable {
        //建root文件夹
        String name = Long.toString(id);
        String rootPath = cargoPath + name;
        File rootFile = new File(rootPath);
        if (rootFile.exists()) {
            return ServerResponse.createByErrorMessage(name
                    + " already exists on root side!!");
        }
        //建库
        Git rootGit = Git.init().setDirectory(rootFile).call();
        //提交第一个commit
        rootGit.commit().setCommitter("root", "email")
                .setMessage("Hi, this is " + name + " on root side!")
                .call();
        //建slave文件夹
        String masterPath = TASK_PATH + name;
        File masterFile = new File(masterPath);
        if (masterFile.exists()) {
            return ServerResponse.createByErrorMessage(name
                    + " already exists on master side!!");
        }
        //克隆库
        String uri = rootGit.getRepository().getDirectory()
                .getCanonicalPath();
        Git masterGit = Git.cloneRepository().setURI(uri)
                .setDirectory(masterFile).call();
        //提交第一个commit
        masterGit.commit().setCommitter("master", "email")
                .setMessage("Hi, this is " + name + " on master side!")
                .call();
        Iterable<RevCommit> gitLog = masterGit.log().setMaxCount(1).call();
        return ServerResponse.createBySuccess(gitLog.iterator().next().getName());
    }

    public static ServerResponse deleteCargo(Long id) throws Throwable {
        return ServerResponse.createByErrorMessage("function developing.....");
    }


    //public ServerResponse pushTask(String taskPath, String rootPath) throws Throwable {
    //    Git git = Git.open(new File(taskPath));
    //    git.checkout().setName("master").call();
    //    git.add().addFilepattern(".").call();
    //    git.commit().setMessage(git.toString() + " commit!!");
    //    git.push().call();

    //    Git rootGit = Git.open(new File(rootPath));
    //    rootGit.checkout().setName("master").call();
    //    Ref ref = rootGit.getRepository().findRef("master");
    //    rootGit.merge().include(ref).call();

    //    return ServerResponse.createBySuccess();
    //}

}