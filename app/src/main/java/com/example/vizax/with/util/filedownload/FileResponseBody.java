package com.example.vizax.with.util.filedownload;

import com.example.vizax.with.util.RxBus;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;

/**
 * 参考于官方DEMO，添加RxBus取代接口
 * Created by prj on 2016/9/26.
 */

public class FileResponseBody extends ResponseBody{

    Response originalResponse;

    public FileResponseBody(Response originalResponse){
        this.originalResponse = originalResponse;
    }

    @Override
    public MediaType contentType() {
        return originalResponse.body().contentType();
    }

    @Override
    public long contentLength() {
        return originalResponse.body().contentLength();
    }

    @Override
    public BufferedSource source() {
        return Okio.buffer(new ForwardingSource(originalResponse.body().source()) {

            long bytesReaded = 0;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {

                long bytesReaded = super.read(sink,byteCount);
                bytesReaded += bytesReaded == -1? 0:bytesReaded;

                RxBus.getDefault().post(new FileBean(bytesReaded,byteCount));
                return bytesReaded;
            }
        });
    }
}
