package com.kustov.webproject.command;

public class CommandPair {

    private DispatchType dispatchType;
    private String page;

    public CommandPair(DispatchType dispatchType, String page) {
        this.dispatchType = dispatchType;
        this.page = page;
    }

    public DispatchType getDispatchType() {
        return dispatchType;
    }

    public void setDispatchType(DispatchType dispatchType) {
        this.dispatchType = dispatchType;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public enum DispatchType{
        REDIRECT, FORWARD
    }
}
