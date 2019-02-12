package com.bw.fresco_lx;

import android.net.Uri;

/**
 * Created by 1607c王晴
 * date 2019/2/12
 * Describe:
 */
public interface Callback<T> {
    void onSuccess(Uri uri, T result);

    void onFailure(Uri uri, Throwable throwable);

    void onCancel(Uri uri);
}
