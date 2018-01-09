package com.hd.utils.git.business;

import com.hd.utils.git.common.ServerResponse;
import com.hd.utils.git.responsetype.ForkResult;
import com.hd.utils.git.responsetype.PushResult;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.*;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

import java.io.File;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 业务基类
 * @author jihang
 * @date 2017/11/21
 */

abstract class AbstractDeck {

    public static final String PROJECT_PATH = "F:\\mySpace\\project\\";
    public static final String TASK_PATH = "F:\\mySpace\\task\\";

    //public static final String PROJECT_PATH = "/Users/jihang/Desktop/mySpace/project/";
    //public static final String TASK_PATH = "/Users/jihang/Desktop/mySpace/task/";

    /**
     * 建立名字为name的Git库，路径为cargoPath
     * @param id 库id
     * @param cargoPath 文件系统路径，不包括name，对于task来说是root
     * @return 返回码中包含刚创建时的ref
     * @throws Throwable 扔
     */
    public static ServerResponse<String> addCargo(Long id, String cargoPath) throws Throwable {
        return ServerResponse.createByErrorMessage("This is base class method!!!");
    }

    /**
     * 删除库，仅允许删除空项目和任务
     * @param id 库id
     * @return 返回码
     * @throws Throwable 扔
     */
    public static ServerResponse deleteCargo(Long id) throws Throwable {
        return ServerResponse.createByErrorMessage("This is base class method!!!");
    }

    /**
     * fork库
     * @param user 用户名
     * @param cargoPath 库路径，与add不同，这是全路径
     * @return clone路径及分支名
     */
    public static ServerResponse<ForkResult> forkCargo(String user, String cargoPath) throws Throwable {
        Git git = Git.open(new File(cargoPath));
        String branch = user + "@" + cargoPath;
        branch = branch.replace(":", "");
        branch = branch.replace("\\", "");
        if(git.getRepository().findRef(branch) != null) {
            return ServerResponse.createByErrorMessage("branch already exists!");
        }
        git.checkout().setName("master").call();
        git.branchCreate().setName(branch).call();

        git.checkout().setName(branch).call();
        git.commit().setCommitter(user, "email")
                .setMessage("Hi, this is branch " + branch + "!").call();

        Iterable<RevCommit> gitLog = git.log().setMaxCount(1).call();

        ForkResult fi = new ForkResult();
        InetAddress address = InetAddress.getLocalHost();
        fi.serverIp = address.getHostAddress();
        fi.repoUri = git.getRepository().getDirectory().getCanonicalPath();
        fi.branchName = "refs/heads/" +  git.getRepository().getBranch();
        Integer tempId = git.getRepository().getBranch().hashCode();
        fi.branchId = Integer.toUnsignedLong(tempId);
        fi.versionName = gitLog.iterator().next().getName();
        return ServerResponse.createBySuccess(fi);
    }

    /**
     * branch新push来临
     * @param branchName 分支名
     * @param cargoPath 库路径
     * @return 新版本名
     */
    public static ServerResponse<String> pushInform(String branchName, String cargoPath) throws Throwable {
        Git git = Git.open(new File(cargoPath));
        if(git.getRepository().findRef(branchName) == null) {
            return ServerResponse.createByErrorMessage("branch not exist!!");
        }
        git.checkout().setName(branchName).call();
        Iterable<RevCommit> gitLog = git.log().setMaxCount(1).call();
        return ServerResponse.createBySuccess(gitLog.iterator().next().getName());
    }

    /**
     * 按分支返回PushResult列表
     //* @param originVersion 原始版本号
     * @param pushVersion push版本号
     * @param currentVersion 当前版本号
     * @param cargoPath 库路径
     * @return commit管道，最终按时间顺序排列
     */
    public static ServerResponse<PushResult> taskPushInfo(//String originVersion,
                                                          String pushVersion,
                                                          String currentVersion,
                                                          String cargoPath) throws Throwable {
        Git git = Git.open(new File(cargoPath));
        git.checkout().setName("master").call();

        PushResult pr = new PushResult();

        //final List<DiffEntry> dwOrigin = git.diff()
        //        .setOldTree(prepareTreeParser(git.getRepository(), originVersion))
        //        .setNewTree(prepareTreeParser(git.getRepository(), pushVersion))
        //        .call();
        //pr.differWithOrigin = new String[dwOrigin.size()];
        //for(Integer i=0;i<dwOrigin.size();++i) {
        //    DiffEntry diff = dwOrigin.get(i);
        //    pr.differWithOrigin[i] = diff.getChangeType() + " : "
        //            + (diff.getOldPath().equals(diff.getNewPath())
        //            ? diff.getNewPath() : diff.getOldPath() + " -> " + diff.getNewPath());
        //}

        final List<DiffEntry> dwCurrent = git.diff()
                .setOldTree(prepareTreeParser(git.getRepository(), currentVersion))
                .setNewTree(prepareTreeParser(git.getRepository(), pushVersion))
                .call();
        pr.differWithCurrent = new String[dwCurrent.size()];
        for(Integer i=0;i<dwCurrent.size();++i) {
            DiffEntry diff = dwCurrent.get(i);
            pr.differWithCurrent[i] = diff.getChangeType() + " : "
                    + (diff.getOldPath().equals(diff.getNewPath())
                    ? diff.getNewPath() : diff.getOldPath() + " -> " + diff.getNewPath());
        }
        return ServerResponse.createBySuccess(pr);
    }

    /**
     * 查看以上channel中的单个文件具体更改情况
     * @param fileName 文件名
     * @param branchName 分支名
     * @param cargoPath 库名
     * @return 该分支对该文件修改前后
     */
    /*
    public ServerResponse<ChangeInfo> checkChange(String fileName, String branchName, String cargoPath) throws Throwable {
        Git git = Git.open(new File(cargoPath));
        git.checkout().setName("master").call();
        Ref ref = git.getRepository().findRef(branchName);
        ServerResponse<String> sr1 = prepareContent(git, fileName, ref);
        Ref lastRef = RootNode.getRoot().findBranchByName(branchName).getRef(0);
        ServerResponse<String> sr2 = prepareContent(git, fileName, lastRef);

        ChangeInfo ci = new ChangeInfo();
        ci.newContent = sr1.getData();
        ci.oldContent = sr2.getData();
        return ServerResponse.createBySuccess(ci);
    }
    */

    /**
     * Git库合并指定commit所属分支
     * @param branchName 分支名
     * @param cargoPath 库名
     */
    /*
    public ServerResponse<MergeInfo> mergeCargoByBranch(String branchName, String cargoPath) throws Throwable {
        Git git = Git.open(new File(cargoPath));
        RevWalk walk = new RevWalk(git.getRepository());
        Ref ref = git.getRepository().findRef(branchName);
        RevCommit commit = walk.parseCommit(ref.getObjectId());
        git.merge().include(commit).call();

        MergeInfo mi = new MergeInfo();
        mi.branchNewRef = ref;
        mi.taskNewRef = git.getRepository().exactRef("refs/heads/master");
        return ServerResponse.createBySuccess(mi);
    }
    */

    /**
     * 查看库状态
     * @param git 库
     */
    /*
    private void statusCargo(Git git) throws Throwable {
        Status status = git.status().call();
        Set<String> conflicting = status.getConflicting();
        for(String conflict : conflicting) {
            System.out.println("Conflicting: " + conflict);
        }

        Map<String, StageState> conflictingStageState = status.getConflictingStageState();
        for(Map.Entry<String, StageState> entry : conflictingStageState.entrySet()) {
            System.out.println("ConflictingState: " + entry);
        }

        Set<String> added = status.getAdded();
        for(String add : added) {
            System.out.println("Added: " + add);
        }

        Set<String> changed = status.getChanged();
        for(String change : changed) {
            System.out.println("Change: " + change);
        }

        Set<String> missing = status.getMissing();
        for(String miss : missing) {
            System.out.println("Missing: " + miss);
        }

        Set<String> modified = status.getModified();
        for(String modify : modified) {
            System.out.println("Modification: " + modify);
        }

        Set<String> removed = status.getRemoved();
        for(String remove : removed) {
            System.out.println("Removed: " + remove);
        }

        Set<String> uncommittedChanges = status.getUncommittedChanges();
        for(String uncommitted : uncommittedChanges) {
            System.out.println("Uncommitted: " + uncommitted);
        }

        Set<String> untracked = status.getUntracked();
        for(String untrack : untracked) {
            System.out.println("Untracked: " + untrack);
        }

        Set<String> untrackedFolders = status.getUntrackedFolders();
        for(String untrack : untrackedFolders) {
            System.out.println("Untracked Folder: " + untrack);
        }
    }
    */

    /**
     * 根据hash找commit
     * @param git 库
     * @param fileName 文件名
     * @param ref 关键字
     * @return RevCommit
     */
    /*
    private ServerResponse<String> prepareContent(Git git, String fileName, Ref ref) throws Throwable {
        RevWalk walk = new RevWalk(git.getRepository());
        RevCommit commit = walk.parseCommit(ref.getObjectId());
        RevTree tree = commit.getTree();
        TreeWalk treeWalk = new TreeWalk(git.getRepository());
        treeWalk.addTree(tree);
        treeWalk.setRecursive(true);
        treeWalk.setFilter(PathFilter.create(fileName));
        if (!treeWalk.next()) {
            return ServerResponse.createBySuccess(null);
        }
        ObjectId objectId = treeWalk.getObjectId(0);
        ObjectLoader loader = git.getRepository().open(objectId);
        OutputStream os = new ByteArrayOutputStream();
        loader.copyTo(os);
        return ServerResponse.createBySuccess(os.toString());
    }
    */

    /**
     * 根据ref建立树
     * @param repository 库
     * @param version 版本号
     * @return 抽象树指针
     */
    private static AbstractTreeIterator prepareTreeParser(Repository repository,
                                                          String version) throws Throwable {
        RevWalk walk = new RevWalk(repository);
        ObjectId objId = repository.resolve(version);
        RevCommit commit = walk.parseCommit(objId);
        //把commit walk成树
        RevTree tree = walk.parseTree(commit.getTree().getId());
        CanonicalTreeParser treeParser = new CanonicalTreeParser();
        ObjectReader reader = repository.newObjectReader();
        treeParser.reset(reader, tree.getId());
        walk.dispose();
        return treeParser;
    }
}