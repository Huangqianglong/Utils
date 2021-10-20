package com.hql.netlib.miniprogram;

import com.hql.netlib.result.BaseResultBean;

/**
 * @author ly-huangql
 * <br /> Create time : 2020/12/14
 * <br /> Description : 查询时序需要拉起小程序加油的结果bean
 */
public class WeatherResultBean extends BaseResultBean {
    /**
     * 油量是否不足 :1不足0充足
     */
public final static String RESULT_NEED_REPLENISH = "1";

    /**
     * msg : 操作成功
     * result : 1
     * rows : {"isOilInsufficient":"1","reqId":"174d7c37-bd73-439a-8ac9-43e71cf7dc29","sceneId":"TM-001"}
     */

    private String msg;
    private String result;
    private RowsBean rows;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public RowsBean getRows() {
        return rows;
    }

    public void setRows(RowsBean rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * isOilInsufficient : 1
         * reqId : 174d7c37-bd73-439a-8ac9-43e71cf7dc29
         * sceneId : TM-001
         */

        private String isOilInsufficient;
        private String reqId;
        private String sceneId;

        public String getIsOilInsufficient() {
            return isOilInsufficient;
        }

        public void setIsOilInsufficient(String isOilInsufficient) {
            this.isOilInsufficient = isOilInsufficient;
        }

        public String getReqId() {
            return reqId;
        }

        public void setReqId(String reqId) {
            this.reqId = reqId;
        }

        public String getSceneId() {
            return sceneId;
        }

        public void setSceneId(String sceneId) {
            this.sceneId = sceneId;
        }
    }
}
