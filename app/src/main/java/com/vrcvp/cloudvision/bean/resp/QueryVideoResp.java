package com.vrcvp.cloudvision.bean.resp;

import com.vrcvp.cloudvision.bean.VideoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询视频接口返回数据实体类（查询视频接口，视频搜索接口）
 * Created by yinglovezhuzhu@gmail.com on 2016/10/11.
 */

public class QueryVideoResp extends BaseResp<List<VideoBean>> {

    public QueryVideoResp() {
    }

    public QueryVideoResp(int httpCode, String msg) {
        super(httpCode, msg);
    }

    @Override
    public String toString() {
        return "QueryVideoResp{} " + super.toString();
    }
}
