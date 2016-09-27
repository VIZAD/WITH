package com.example.vizax.with.util.filedownload;

import java.io.File;

/**
 * RxBus监听的bean类
 * Created by prj on 2016/9/26.
 */

public class FileBean {

    long progress;
    long total;

    public FileBean(long progress,long total){
        this.progress = progress;
        this.total = total;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
