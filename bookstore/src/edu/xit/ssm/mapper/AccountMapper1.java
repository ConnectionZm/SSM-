package edu.xit.ssm.mapper;

import java.util.List;

import edu.xit.ssm.po.Account;

public interface AccountMapper1 {
	//查出所有用户账户信息
	public List<Account> selectAccount()throws Exception;
	//根据用户id查找该用户信息
	public Account selectAccountByUid(Integer userId)throws Exception;
}
