package edu.xit.ssm.service;

import java.util.List;

import edu.xit.ssm.po.Account;
import edu.xit.ssm.po.AccountVo;

public interface AccountService {
	//查出所有用户账户信息
	public List<AccountVo> selectAccount()throws Exception;
	//根据用户id查找账户包装类信息
	public AccountVo selectAccountByUid(Integer userId)throws Exception;
	//根据用户id查找账户信息
	public Account selectAccountByUid2(Integer userId)throws Exception;
	//用户注册账号时初始化账户余额和积分
	public void insert(Account record)throws Exception;
	//用户充值
	public int reCharge(Account record)throws Exception;
	//通知用户
	public int sendUserWithAccount(Account record)throws Exception;
	//扣款或者扣除积分
	public int payMoneyOrPoints(Account record)throws Exception;
}
