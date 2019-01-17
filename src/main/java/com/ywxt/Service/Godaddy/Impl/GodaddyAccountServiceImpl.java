package com.ywxt.Service.Godaddy.Impl;

import com.alibaba.fastjson.JSONArray;
import com.ywxt.Dao.Godaddy.GodaddyAccountDao;
import com.ywxt.Dao.Godaddy.Impl.GodaddyAccountDaoImpl;
import com.ywxt.Dao.Godaddy.Impl.GodaddyCertificateDaoImpl;
import com.ywxt.Dao.Godaddy.Impl.GodaddyDomainDaoImpl;
import com.ywxt.Domain.Godaddy.GodaddyAccount;
import com.ywxt.Handler.AsyncHandler;
import com.ywxt.Service.Godaddy.GodaddyAccountService;
import com.ywxt.Utils.AsyncUtils;
import com.ywxt.Utils.HttpUtils;
import com.ywxt.Utils.Parameter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GodaddyAccountServiceImpl implements GodaddyAccountService {

    // 获取总数
    public int getTotal(HashMap<String, Object> params) throws Exception {
        return new GodaddyAccountDaoImpl().getListTotal(params);
    }

    // 列表
    public List<GodaddyAccount> getList() {
        return new GodaddyAccountDaoImpl().getAccounts();
    }

    // 新增/修改
    public int saveAccount(GodaddyAccount godaddyAccount) throws Exception {
        // check key
        if (this.checkAccount(godaddyAccount.getAccessKeyId(), godaddyAccount.getAccessKeySecret())) {
            godaddyAccount.setStatus("normal");
            // update Data & 异步
            AsyncHandler handler = new AsyncHandler() {
                @Override
                public void handle() {
                    try {
                        new GodaddyServiceImpl(godaddyAccount.getAccessKeyId(), godaddyAccount.getAccessKeySecret()).freshSourceData();
                    } catch (Exception e) {
                        // 异步处理数据错误
                        System.out.println(e.getMessage());
                    }
                }
            };
            AsyncUtils.asyncWork(handler);
        } else {
            godaddyAccount.setStatus("invalid");
        }
        return new GodaddyAccountDaoImpl().saveAccount(godaddyAccount);
    }

    // 删除账号
    public boolean deleteAccount(int godaddyAccountId) {
        // update Data
        GodaddyAccount godaddyAccount = new GodaddyAccountDaoImpl().getAccount(godaddyAccountId);
        if (godaddyAccount.getStatus().equals("normal")) {
            // update Data & 异步
            AsyncHandler handler = new AsyncHandler() {
                @Override
                public void handle() {
                    // 删除ecs
                    new GodaddyDomainDaoImpl().deleteDomainByAccessId(godaddyAccount.getAccessKeyId());
                    // 删除cdn
                    new GodaddyCertificateDaoImpl().deleteCertificateByAccessId(godaddyAccount.getAccessKeyId());
                }
            };
            AsyncUtils.asyncWork(handler);
        }
        return new GodaddyAccountDaoImpl().deleteAccount(godaddyAccountId);
    }

    // 校验密钥
    public boolean checkAccount(String accessKeyId, String accessKeySecret) throws Exception {
        try {
            HashMap<String, String> inParam = new HashMap<>();
            inParam.put("limit", "1");
            String paramContext = HttpUtils.getParamContext(inParam);
            Map<String, String> headerParams = new HashMap<String, String>();
            headerParams.put("Authorization", "sso-key " + accessKeyId + ":" + accessKeySecret);
            headerParams.put("Content-type", "application/json");
            JSONArray array = JSONArray.parseArray(HttpUtils.sendConnGet(Parameter.godaddyUrl + Parameter.godaddyActions.get("GET_DOMAIN_LIST"), paramContext, headerParams));
        } catch (IOException e) {
            if (e.getMessage().equals("Unauthorized")) {
                return false;
            }
        }
        return true;
    }

}
