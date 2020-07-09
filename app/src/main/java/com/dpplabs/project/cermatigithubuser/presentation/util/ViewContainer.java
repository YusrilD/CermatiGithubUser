package com.dpplabs.project.cermatigithubuser.presentation.util;

import android.view.ViewGroup;
import com.dpplabs.project.cermatigithubuser.net.util.Function;

public class ViewContainer {

    public ViewGroup getContainerView() {
        return container;
    }

    private ViewGroup container;

    private Function onClose;
    private Function onOpen;

    public ViewContainer(ViewGroup container){
        this.container=container;
    }

    public ViewContainer setOnCloseListener(Function onClose){
        this.onClose=onClose;
        return this;
    }

    public ViewContainer setOnOpenListener(Function onOpen){
        this.onOpen=onOpen;
        return this;
    }

    protected void openView(View view){
        closeView();
        container.addView(view.getView(), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.start();
        if(onOpen!=null) onOpen.run();
    }

    public void closeView(){
        container.removeAllViews();
        if(onClose!=null) onClose.run();
    }

    public boolean viewOpened(){
        return container.getChildCount()!=0;
    }

}
