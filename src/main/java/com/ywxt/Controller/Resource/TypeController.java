package com.ywxt.Controller.Resource;

import com.alibaba.fastjson.JSONObject;
import com.ywxt.Annotation.NotOperationAction;
import com.ywxt.Controller.CommonController;
import com.ywxt.Domain.Resource.Type;
import com.ywxt.Service.Resource.TypeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value = "/resource/type", name = "资源类型")
public class TypeController extends CommonController {

    @Resource
    private TypeService typeService;

    @NotOperationAction
    @ResponseBody
    @RequestMapping(value = {"/info/{code}"}, name = "信息", method = RequestMethod.GET)
    public Type info(@PathVariable String code) throws Exception {
        return typeService.getType(code);
    }

    @NotOperationAction
    @ResponseBody
    @RequestMapping(value = {"/list"}, name = "列表", method = RequestMethod.GET)
    public List<Type> list(HttpServletRequest request) throws Exception {
        return typeService.getList();
    }

    @ResponseBody
    @RequestMapping(value = {"/save"}, name = "修改", method = RequestMethod.POST)
    public JSONObject save(@ModelAttribute Type type) {
        typeService.save(type);
        return this.returnObject(new HashMap<>() {{
        }});
    }


    @ResponseBody
    @RequestMapping(value = {"/delete/{id}"}, name = "删除", method = RequestMethod.GET)
    public JSONObject delete(@PathVariable Integer id) throws Exception {
        if (typeService.remove(id)) {
            return this.returnObject(new HashMap<>() {{
            }});
        }
        throw new Exception("删除失败。");
    }
}
