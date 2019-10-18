package org.xujin.idea.right.utils;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.DumbAwareRunnable;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.util.DisposeAwareRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xujin
 */
public class HaloIdeaUtils {
    public static final String DEFAULT_CHARSET = "UTF-8";

    public static void invokeAndWait(Project p, Runnable r) {
        invokeAndWait(p, ModalityState.defaultModalityState(), r);
    }

    public static void invokeAndWait(Project p, ModalityState state, Runnable r) {
        if (isNoBackgroundMode()) {
            r.run();
        } else {
            ApplicationManager.getApplication().invokeAndWait(DisposeAwareRunnable.create(r, p), state);
        }
    }

    public static void invokeLater(Project p, Runnable r) {
        invokeLater(p, ModalityState.defaultModalityState(), r);
    }

    public static void invokeLater(Project p, ModalityState state, Runnable r) {
        if (isNoBackgroundMode()) {
            r.run();
        } else {
            ApplicationManager.getApplication().invokeLater(DisposeAwareRunnable.create(r, p), state);
        }

    }

    public static void runWhenInitialized(final Project project, final Runnable r) {
        if (project.isDisposed()) {
            return;
        }
        if (isNoBackgroundMode()) {
            r.run();
            return;
        }
        if (!project.isInitialized()) {
            StartupManager.getInstance(project).registerPostStartupActivity(DisposeAwareRunnable.create(r, project));
            return;
        }
        runDumbAware(project, r);
    }

    public static boolean isNoBackgroundMode() {
        return (ApplicationManager.getApplication().isUnitTestMode()
                || ApplicationManager.getApplication().isHeadlessEnvironment());
    }

    public static void runDumbAware(final Project project, final Runnable r) {
        if (DumbService.isDumbAware(r)) {
            r.run();
        } else {
            DumbService.getInstance(project).runWhenSmart(DisposeAwareRunnable.create(r, project));
        }
    }

    private static List<VirtualFile> virtualFiles = new ArrayList<>();

    public static void addWaitOptimizeFile(VirtualFile virtualFile) {
        virtualFiles.add(virtualFile);
    }

    public static void doOptimize(Project project) {
        DumbService.getInstance(project).runWhenSmart((DumbAwareRunnable) () -> new WriteCommandAction(project) {
            @Override
            protected void run(@NotNull Result result) {
                for (VirtualFile virtualFile : virtualFiles) {
                    try {
                        PsiJavaFile javaFile = (PsiJavaFile) PsiManager.getInstance(project).findFile(virtualFile);
                        if (javaFile != null) {
                            CodeStyleManager.getInstance(project).reformat(javaFile);
                            JavaCodeStyleManager.getInstance(project).optimizeImports(javaFile);
                            JavaCodeStyleManager.getInstance(project).shortenClassReferences(javaFile);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                virtualFiles.clear();
            }
        }.execute());
    }
}
