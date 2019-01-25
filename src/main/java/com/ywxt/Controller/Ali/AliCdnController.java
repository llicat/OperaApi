package com.ywxt.Controller.Ali;

import com.alibaba.fastjson.JSONObject;
import com.ywxt.Controller.CommonController;
import com.ywxt.Domain.Ali.AliCdn;
import com.ywxt.Domain.Ali.AliCdnTask;
import com.ywxt.Service.Ali.Impl.AliCdnServiceImpl;
import com.ywxt.Service.Impl.ParameterIgnoreServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/ali/cdn")
public class AliCdnController extends CommonController {

    // cdn域名列表
    @ResponseBody
    @RequestMapping(value = {"/list"}, method = RequestMethod.POST)
    public JSONObject cdnDomainList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int pageNumber = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
        int pageSize = request.getParameter("limit") == null ? 10 : Integer.parseInt(request.getParameter("limit"));
        HashMap<String, Object> params = new HashMap<String, Object>();
        if (!(request.getParameter("status") == null) && !(request.getParameter("status").isEmpty())) {
            if (request.getParameter("status").equals("others")) {
                String[] statusStrings = {"online", "offline"};
                params.put("domainStatus@notIn", statusStrings);
            } else {
                params.put("domainStatus", request.getParameter("status"));
            }
        }
        if (!(request.getParameter("key") == null) && !(request.getParameter("key").isEmpty())) {
            params.put("filter", request.getParameter("key"));
        }
        if (!(request.getParameter("ifMarked") == null) && !(request.getParameter("ifMarked").isEmpty())) {
            params.put("ifMarked", request.getParameter("ifMarked"));
        }
        return new AliCdnServiceImpl().getCdnDomainList(params, pageNumber, pageSize);
    }

    // 批量设置mark
    @ResponseBody
    @RequestMapping(value = {"/param/{status}"}, method = RequestMethod.POST)
    public JSONObject cdnParamSetAll(Integer[] ids, @PathVariable String status) throws Exception {
        List<Integer> list = new ArrayList<Integer>(Arrays.asList(ids));
        if (status.equals("mark")) {
            for (Integer i : list) {
                AliCdn aliCdn = new AliCdnServiceImpl().getCdn(i);
                new ParameterIgnoreServiceImpl().saveMarked(aliCdn);
            }
        } else if (status.equals("unmark")) {
            for (Integer i : list) {
                AliCdn aliCdn = new AliCdnServiceImpl().getCdn(i);
                new ParameterIgnoreServiceImpl().deleteMarked(aliCdn);
            }
        }
        return this.returnObject(new HashMap<String, Object>() {{
        }});
    }


    // 设置mark
    @ResponseBody
    @RequestMapping(value = {"/param/{status}/{id}"}, method = RequestMethod.POST)
    public JSONObject cdnParamSet(@PathVariable String status, @PathVariable Integer id) throws Exception {
        AliCdn aliCdn = new AliCdnServiceImpl().getCdn(id);
        if (status.equals("mark")) {
            new ParameterIgnoreServiceImpl().saveMarked(aliCdn);
        } else if (status.equals("unmark")) {
            new ParameterIgnoreServiceImpl().deleteMarked(aliCdn);
        }
        return this.returnObject(new HashMap<String, Object>() {{
        }});
    }

    // cdn:节点刷新&预热
    @ResponseBody
    @RequestMapping(value = {"/refresh"}, method = RequestMethod.POST)
    public Map<String, String> cdnRefreshObjectCache(String operateType, String refreshType, String content) throws Exception {
        return new AliCdnServiceImpl().refreshCdn(operateType, refreshType, content);
    }

    // cdn:节点刷新任务查看
    @ResponseBody
    @RequestMapping(value = {"/refresh/task/list"}, method = RequestMethod.POST)
    public JSONObject getCdnRefreshTaskList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int pageNumber = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
        int pageSize = request.getParameter("limit") == null ? 10 : Integer.parseInt(request.getParameter("limit"));
        HashMap<String, Object> params = new HashMap<String, Object>();
        if (!(request.getParameter("operDate") == null)) {
            String[] operateDate = request.getParameter("operDate").split(",");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            params.put("creationTime@gt", sdf.parse(operateDate[0] + " 00:00:00"));
            params.put("creationTime@lt", sdf.parse(operateDate[1] + " 23:59:59"));
        }
        if (!(request.getParameter("url") == null) && !(request.getParameter("url").isEmpty())) {
            params.put("objectPath@like", "%" + request.getParameter("url") + "%");
        }
        if (!(request.getParameter("operType") == null) && !(request.getParameter("operType").isEmpty())) {
            String operateType = request.getParameter("operType");
            switch (operateType) {
                case "url-refresh":
                    params.put("objectType", "file");
                    break;
                case "content-refresh":
                    params.put("objectType", "path");
                    break;
                case "url-warm":
                    params.put("objectType", "preload");
                    break;
                default:
                    break;
            }
        }
        params.put("orderDesc", "creationTime");
        return new AliCdnServiceImpl().getCdnRefreshTaskList(params, pageSize, pageNumber);
    }

    // cdn:节点刷新任务状态更新
    @ResponseBody
    @RequestMapping(value = {"/refresh/task/update/{id}"}, method = RequestMethod.GET)
    public AliCdnTask updateCdnRefreshTask(@PathVariable Integer id) throws Exception {
        return new AliCdnServiceImpl().updateCdnRefreshTask(id);
    }

}
