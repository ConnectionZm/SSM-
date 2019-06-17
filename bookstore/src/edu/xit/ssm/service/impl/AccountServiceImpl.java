package edu.xit.ssm.service.impl;

import java.util.ArrayList;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import edu.xit.ssm.mapper.AccountMapper;
import edu.xit.ssm.mapper.AccountMapper1;
import edu.xit.ssm.mapper.UsersMapper;
import edu.xit.ssm.po.Account;
import edu.xit.ssm.po.AccountVo;
import edu.xit.ssm.po.Users;
import edu.xit.ssm.service.AccountService;

public class AccountServiceImpl implements AccountService{
	@Autowired
	private AccountMapper1 accountMapper1;
	@Autowired
	private AccountMapper accountMapper;
	@Autowired
	private UsersMapper usersMapper;
	@Override
	public List<AccountVo> selectAccount() throws Exception {
		List<Account> accountList=accountMapper1.selectAccount();
		List<AccountVo>accountVoList=new ArrayList<AccountVo>();
		for(Account account:accountList){
			Users user=usersMapper.selectByPrimaryKey(account.getUserId());
			AccountVo accountVo=new AccountVo();
			accountVo.setId(account.getId());//设置account中的id
			accountVo.setUserId(account.getUserId());
			accountVo.setUserName(user.getUsername());
			accountVo.setTx(user.getTx());
			accountVo.setSmMoney(account.getSmMoney());
			accountVo.setBalance(account.getBalance());
			accountVo.setStatus(account.getStatus());
			accountVoList.add(accountVo);
		}
		return accountVoList;
	}
	@Override
	public AccountVo selectAccountByUid(Integer userId) throws Exception {
		Account account=accountMapper1.selectAccountByUid(userId);
		Users user=usersMapper.selectByPrimaryKey(userId);
		AccountVo accountVo=new AccountVo();
		accountVo.setId(account.getId());
		accountVo.setUserId(userId);
		accountVo.setUserName(user.getUsername());
		accountVo.setTx(user.getTx());
		accountVo.setBalance(account.getBalance());
		accountVo.setConfirmMoney(account.getConfirmMoney());
		accountVo.setPoints(account.getPoints());
		accountVo.setMsg(account.getMsg());
		return accountVo;
	}
	@Override
	public Account selectAccountByUid2(Integer userId) throws Exception {
		Account account=accountMapper1.selectAccountByUid(userId);
		return account;
	}
	@Override
	public void insert(Account record) throws Exception {
		accountMapper.insert(record);
	}
	@Override
	public int reCharge(Account record) throws Exception {
		int result=accountMapper.updateByPrimaryKeySelective(record);
		return result;
	}
	@Override
	public int sendUserWithAccount(Account record) throws Exception {
		int result=accountMapper.updateByPrimaryKeySelective(record);
		return result;
	}
	@Override
	public int payMoneyOrPoints(Account record) throws Exception {
		int result=accountMapper.updateByPrimaryKeySelective(record);
		return result;
	}
	
	
	
	

}
