package com.dpplabs.project.cermatigithubuser.presentation.util;

import android.content.Context;
import android.view.ViewGroup;

public abstract class View {

    protected ViewContainer container;
    protected ViewGroup parent;
    public Context context;

    public View(ViewContainer context){
        this.container =context;
        this.parent=context.getContainerView();
        this.context=parent.getContext();
    }

    public void open(){
        container.openView(this);
    }

    public void closeSelf(){
        container.closeView();
    }

    protected abstract ViewGroup getView();

    protected abstract void start();

}
