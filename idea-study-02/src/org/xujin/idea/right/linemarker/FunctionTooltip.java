package org.xujin.idea.right.linemarker;

import com.intellij.util.Function;

/**
 *
 * @author xujin
 */
public class FunctionTooltip implements Function {

    String msg = "点我快速切换至对应文件";

    public FunctionTooltip() {
    }

    public FunctionTooltip(String msg) {
        this.msg = msg;
    }


    @Override
    public Object fun(Object o) {
        return msg;
    }

}
