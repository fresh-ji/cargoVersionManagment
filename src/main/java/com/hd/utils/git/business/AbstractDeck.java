package com.hd.utils.git.business;

import com.hd.utils.git.common.ServerResponse;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.revwalk.*;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

/**
 * 业务基类
 * @author jihang
 * @date 2017/11/21
 */

abstract class AbstractDeck {

    //public final String projectPath = "F:/mySpace/project/";
    //public final String taskPath = "F:/mySpace/task/";

    public final String projectPath = "/Users/jihang/Desktop/mySpace/project/";
    public final String taskPath = "/Users/jihang/Desktop/mySpace/task/";

    /**
     public final String projectPath = "
     * 建立名字为name的Git库，路径为cargoPath
     * @param id 库id
     * @param cargoPath 文件系统路径，不包括name，对于task来说是root
     * @return 返回码中包含刚创建时的ref
     * @throws Throwable 扔
     */
    abstract ServerResponse<String> addCargo(Long id, String cargoPath) throws Throwable;

    /**
     * 删除库，仅允许删除空项目和任务
     * @param id 库id
     * @return 返回码
     * @throws Throwable 扔
     */
    abstract ServerResponse deleteCargo(Long id) throws Throwable;

    /**
     * @param userIdentify 用户名
     * @param cargoPath 库路径，与add不同，这是全路径
     * @return clone路径及分支名
     */
    /*
    public ServerResponse<ForkInfo> forkCargo(String userIdentify, String cargoPath) throws Throwable {
        Git git = Git.open(new File(cargoPath));
        String branch = userIdentify + "@" + cargoPath;
        branch = branch.replace(":", "");
        if(git.getRepository().findRef(branch) != null) {
            return ServerResponse.createByErrorMessage("branch already exists!");
        }
        git.checkout().setName("master").call();
        git.branchCreate().setName(branch).call();

        git.checkout().setName(branch).call();
        git.commit().setCommitter(userIdentify, "email")
                .setMessage("Hi, this is " + branch + "!").call();

        ForkInfo fi = new ForkInfo();
        fi.repoUri = git.getRepository().getDirectory().getCanonicalPath();
        //System.out.println(fi.repoUri);
        fi.branchName = "refs/heads/" +  git.getRepository().getBranch();
        fi.ref = git.getRepository().exactRef(fi.branchName);
        return ServerResponse.createBySuccess(fi);
    }
    */
    /**
     * 按分支返回管道，每个分支仅返回与上一个定档版本区别
     * @param cargoPath 库路径
     * @return commit管道，最终按时间顺序排列
     */

    /*
    public ServerResponse<List<CommitInfo>> getCommitChannel(String cargoPath) throws Throwable {
        Git git = Git.open(new File(cargoPath));
        git.checkout().setName("master").call();
        RevWalk walk = new RevWalk(git.getRepository());

        List<Ref> refs = git.branchList().call();
        ArrayList<CommitInfo> channel = new ArrayList<>();

        for(Ref ref : refs) {
            //跳过master分支
            if(Objects.equals("refs/heads/master", ref.getName())) {
                continue;
            }
            CommitInfo ci = new CommitInfo();
            //填补commit
            ci.commit = walk.parseCommit(ref.getObjectId());

            Ref lastRef = RootNode.getRoot().findBranchByName(ref.getName()).getRef(0);
            //填补differs
            final List<DiffEntry> diffs = git.diff()
                    .setOldTree(prepareTreeParser(git, lastRef.getObjectId()))
                    .setNewTree(prepareTreeParser(git, ref.getObjectId()))
                    .call();
            ci.differs = new String[diffs.size()];
            for(Integer i=0;i<diffs.size();++i) {
                DiffEntry diff = diffs.get(i);
                ci.differs[i] = diff.getChangeType() + ": "
                        + (diff.getOldPath().equals(diff.getNewPath())
                        ? diff.getNewPath() : diff.getOldPath() + " -> " + diff.getNewPath());
            }
            channel.add(ci);
        }

        channel.sort(Comparator.comparing(CommitInfo::order));
        walk.dispose();
        return ServerResponse.createBySuccess(channel);
    }
    */


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
     * @param git 库
     * @param id 关键字
     * @return 抽象树指针
     */
    /*
    private AbstractTreeIterator prepareTreeParser(Git git, ObjectId id) throws Throwable {
        Repository repository = git.getRepository();
        RevWalk walk = new RevWalk(repository);
        RevCommit commit = walk.parseCommit(id);
        //把commit walk成树
        RevTree tree = walk.parseTree(commit.getTree().getId());
        CanonicalTreeParser treeParser = new CanonicalTreeParser();
        ObjectReader reader = repository.newObjectReader();
        treeParser.reset(reader, tree.getId());
        walk.dispose();
        return treeParser;
    }
    */
}