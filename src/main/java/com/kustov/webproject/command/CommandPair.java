package com.kustov.webproject.command;


/**
 * The Class CommandPair.
 */
public class CommandPair {

    /**
     * The dispatch type.
     */
    private DispatchType dispatchType;

    /**
     * The page.
     */
    private String page;

    /**
     * Instantiates a new command pair.
     *
     * @param dispatchType the dispatch type
     * @param page         the page
     */
    public CommandPair(DispatchType dispatchType, String page) {
        this.dispatchType = dispatchType;
        this.page = page;
    }

    /**
     * Gets the dispatch type.
     *
     * @return the dispatch type
     */
    public DispatchType getDispatchType() {
        return dispatchType;
    }

    /**
     * Sets the dispatch type.
     *
     * @param dispatchType the new dispatch type
     */
    public void setDispatchType(DispatchType dispatchType) {
        this.dispatchType = dispatchType;
    }

    /**
     * Gets the page.
     *
     * @return the page
     */
    public String getPage() {
        return page;
    }

    /**
     * Sets the page.
     *
     * @param page the new page
     */
    public void setPage(String page) {
        this.page = page;
    }

    /**
     * The Enum DispatchType.
     */
    public enum DispatchType {

        /**
         * The redirect.
         */
        REDIRECT,
        /**
         * The forward.
         */
        FORWARD
    }
}
