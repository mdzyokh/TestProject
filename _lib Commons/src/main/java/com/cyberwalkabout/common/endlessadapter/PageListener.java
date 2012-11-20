package com.cyberwalkabout.common.endlessadapter;

import com.cyberwalkabout.common.adapter.Page;

/**
 * @author Maria Dzyokh
 */
public interface PageListener<T> {

    void onNewPage(PageInfo<T> page);
}
