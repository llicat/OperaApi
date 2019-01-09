package com.ywxt.Service.Ali;

import com.ywxt.Domain.Ali.AliAccount;

import java.util.List;

public interface AliAccountService {

    public abstract List<AliAccount> getList();

    public abstract List<AliAccount> getList(boolean checkMoney) throws Exception;

    public abstract int saveAliAccount(AliAccount aliAccount) throws Exception;

    public abstract boolean deleteAccount(int aliAccountId);

    public abstract boolean checkAccount(String accessKeyId, String accessKeySecret) throws Exception;
}
