package com.leory.commonlib.widget.morePop;

/**
 * Describe : 弹出框bean
 * Author : zhuly
 * Date : 2018-10-23
 */

public class MorePopBean {
    /**
     * 资源id
     */
    private int drawRes;
    /**
     * 标签名称
     */
    private String name;
    /**
     * 能否点击，默认true
     */
    private boolean canClick;

    public MorePopBean(String name) {
        this(0,name);
    }
    public MorePopBean(int drawRes, String name) {
        this(drawRes,name,true);
    }

    public MorePopBean(int drawRes, String name,boolean canClick){
        this.drawRes = drawRes;
        this.name = name;
        this.canClick=canClick;
    }

    public int getDrawRes() {
        return drawRes;
    }

    public void setDrawRes(int drawRes) {
        this.drawRes = drawRes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCanClick() {
        return canClick;
    }

    public void setCanClick(boolean canClick) {
        this.canClick = canClick;
    }
}
