package org.xujin.idea.right.linemarker;

import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.codeInsight.daemon.LineMarkerProvider;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.openapi.util.IconLoader;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.PsiClassImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

public class HaloLineMarker implements LineMarkerProvider {

    @Nullable
    @Override
    public LineMarkerInfo getLineMarkerInfo(@NotNull PsiElement psiElement) {
        LineMarkerInfo lineMarkerInfo= null;
        try {
            lineMarkerInfo = null;
            String anno="org.springframework.boot.autoconfigure.SpringBootApplication";
            if(!judgeHaveAnnotation(psiElement,anno)){
               return lineMarkerInfo;
            }
            PsiClassImpl field = ((PsiClassImpl) psiElement);
            PsiAnnotation psiAnnotation = field.getAnnotation(anno);
            lineMarkerInfo = new LineMarkerInfo<>(psiAnnotation, psiAnnotation.getTextRange(), IconLoader.findIcon("/icons/right/HaloBasic.png"),
                    new FunctionTooltip("快速导航"),
                    new AppMgmtNavigationHandler(),
                    GutterIconRenderer.Alignment.LEFT);
            //根据不同的行标记分配不同的行标记处理器
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lineMarkerInfo;
    }

    @Override
    public void collectSlowLineMarkers(@NotNull List<PsiElement> list, @NotNull Collection<LineMarkerInfo> collection) {
    }

    private boolean judgeHaveAnnotation(@NotNull PsiElement psiElement, String anno) {
        if (psiElement instanceof PsiClass) {
            PsiClassImpl field = ((PsiClassImpl) psiElement);
            PsiAnnotation psiAnnotation = field.getAnnotation(anno);
            if (null != psiAnnotation) {
                return true;
            }
            return false;
        }
        return false;
    }

}
