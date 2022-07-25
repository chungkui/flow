
package org.snaker.api.common;


import cn.hutool.extra.cglib.CglibUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Response<A> implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String successCode = "200";
    private String code;
    private String msg;
    private A data;

    public static <T> Response<T> success() {
        return restResult(null, successCode, null);
    }

    public static <S, T> Response<T> success(S data, Class<T> targetClass) {
        return restResult(CglibUtil.copy(data,targetClass), successCode, null);
    }

    public static <S, T> Response<ResPage<T>> page(IPage<S> page, Class<T> targetClass) {
        List<S> data = page.getRecords();
        List<T> res = new ArrayList<>();
        ResPage<T> xyPage = new ResPage<>();
        if (CollectionUtils.isNotEmpty(data)) {

            res = CglibUtil.copyList(data,()->{
                try {
                    return targetClass.newInstance();
                } catch (Exception e) {}
                return null;
            });
        }
        xyPage.setNowPage(page.getCurrent());
        xyPage.setPageSize(page.getSize());
        xyPage.setCount(page.getTotal());
        xyPage.setData(res);
        return restResult(xyPage, successCode, "ok");
    }

    public static <S, T> Response<ResPage<T>> page(IPage<S> page, Class<T> targetClass, CopyCallback<S, T> copyCallback) {
        List<S> data = page.getRecords();
        List<T> res = new ArrayList<>();
        ResPage<T> resPage = new ResPage<>();
        if (CollectionUtils.isNotEmpty(data)) {
            for (S s : data) {
                T t = CglibUtil.copy(s, targetClass);
                copyCallback.callback(s, t);
                res.add(t);
            }
        }
        resPage.setNowPage(page.getCurrent());
        resPage.setPageSize(page.getSize());
        resPage.setCount(page.getTotal());
        resPage.setData(res);
        return restResult(resPage, successCode, "ok");
    }

    public interface CopyCallback<S, T> {
        /**
         * 功能描述: <br>
         * 〈拷贝功能复制字段特殊逻辑处理回调函数〉
         *
         * @param s
         * @param t
         * @return void
         * @throws
         * @see [相关类/方法](可选)
         * @since [产品/模块版本](可选)
         */
        void callback(S s, T t);
    }

    public static <T> Response<T> success(T data) {
        return restResult(data, successCode, null);
    }

    public static <T> Response<T> success(T data, String msg) {
        return restResult(data, "0", msg);
    }

    public static <T> Response<T> failed() {
        return restResult(null, "500", null);
    }

    public static <T> Response<T> failed(String msg) {
        return restResult(null, "500", msg);
    }

    public static <T> Response<T> failed(Exception exception) {
        return restResult(null, "500", exception.getMessage());
    }

    public static <T> Response<T> failed(T data) {
        return restResult(data, "500", null);
    }

    public static <T> Response<T> failed(T data, String msg) {
        return restResult(data, "500", msg);
    }

    public static <T> Response<T> failed(String code, String msg) {
        return restResult(null, code, msg);
    }

    public static <T> Response<T> restResult(T data, String code, String msg) {
        Response<T> apiResult = new Response<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public A getData() {
        return data;
    }

    public void setData(A data) {
        this.data = data;
    }
}
