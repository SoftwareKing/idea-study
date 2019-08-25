package org.xujin.idea.right;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.DumbAwareRunnable;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.idea.maven.project.MavenProjectsManager;
import org.xujin.idea.right.model.NewRightContext;
import org.xujin.idea.right.model.NewRightModel;
import org.xujin.idea.right.utils.HaloIdeaUtils;
import org.xujin.idea.right.utils.PisFileByFreeMarkerUtil;


/**
 * 右键创建Basic Action
 * @author xujin
 */
public class NewBasicExeAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {

        Project project = e.getProject();
        /**
         * 从Action中得到一个虚拟文件
         */
        VirtualFile virtualFile = e.getData(DataKeys.VIRTUAL_FILE);
        if (!virtualFile.isDirectory()) {
            virtualFile = virtualFile.getParent();
        }
        Module module = ModuleUtil.findModuleForFile(virtualFile, project);

        String moduleRootPath = ModuleRootManager.getInstance(module).getContentRoots()[0].getPath();
        String actionDir = virtualFile.getPath();
        String str = StringUtils.substringAfter(actionDir, moduleRootPath + "/src/main/java/");
        //获取右键后的路径
        String basePackage = StringUtils.replace(str, "/", ".");
        NewRightContext.clearAllSet();
        NewRightContext.setSelectedPackage(basePackage);
        BasicActionOpenDialog dialog = new BasicActionOpenDialog(project, module);
        if (!dialog.showAndGet()) {
            return;
        }

        DumbService.getInstance(project).runWhenSmart((DumbAwareRunnable) () -> new WriteCommandAction(project) {
            @Override
            protected void run(@NotNull Result result) {
                switch (NewRightContext.getClassType()) {
                    case HaloConstant.COMBOX_CONTROLLER:
                        createByFtl(project, moduleRootPath,"Controller.java.ftl");
                        break;
                }
                MavenProjectsManager manager = MavenProjectsManager.getInstance(project);
                //解决依赖
                manager.forceUpdateAllProjectsOrFindAllAvailablePomFiles();
                //优化生成的所有Java类
                HaloIdeaUtils.doOptimize(project);

            }
        }.execute());

    }


    /**
     * 通过ftl创建 Controller
     * @param project
     * @param moduleRootPath
     */
    private void createByFtl(Project project, String moduleRootPath, String  ftlName) {
        NewRightModel newRightModel= NewRightContext.copyToNewRightModel();
        try {
            PisFileByFreeMarkerUtil.createFile(project, newRightModel, moduleRootPath,ftlName);
            NewRightContext.clearAllSet();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
