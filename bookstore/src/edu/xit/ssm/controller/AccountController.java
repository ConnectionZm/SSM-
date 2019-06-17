package edu.xit.ssm.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.xit.ssm.po.Account;
import edu.xit.ssm.po.AccountVo;
import edu.xit.ssm.po.Comment;
import edu.xit.ssm.po.CommentVo;
import edu.xit.ssm.po.Users;
import edu.xit.ssm.service.AccountService;
import edu.xit.ssm.service.CommentService;
import edu.xit.ssm.service.UsersService;

@Controller
public class AccountController {
	@Autowired
	private AccountService accountService;

	// 查询所有账户信息
	@RequestMapping("/selectAccount")
	public ModelAndView selectAccount(HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		List<AccountVo> accountVoList = accountService.selectAccount();
		modelAndView.addObject("accountVoList", accountVoList);
		modelAndView.setViewName("accountmanager");
		return modelAndView;
	}

	// 根据用户id查询该用户的账户信息
	@RequestMapping("/selectAccountByUid")
	public ModelAndView selectAccountByUid(HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		Users user = (Users) request.getSession().getAttribute("user");
		AccountVo accountVo = accountService.selectAccountByUid(user.getId());
		accountVo.setRemark(accountVo.getMsg());
		modelAndView.addObject("accountVo", accountVo);
		modelAndView.setViewName("userAccount");
		return modelAndView;
	}

	// 用户充值提交
	@RequestMapping("/updateAccount")
	public ModelAndView updateAccount(AccountVo accountVo) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		Account account = new Account();
		Account qryAccount = accountService.selectAccountByUid2(accountVo.getUserId());
		account.setId(accountVo.getId());
		if ("1".equals(qryAccount.getStatus())) {// 已确认充值
			account.setSmMoney(accountVo.getSmMoney());
		} else if ("0".equals(qryAccount.getStatus())) {// 未充值
			float smMoney = qryAccount.getSmMoney() + accountVo.getSmMoney();
			account.setSmMoney(smMoney);
		}
		account.setStatus("0");//修改状态为未处理，管理员就可以操作
		int result = accountService.reCharge(account);
		if (result > 0) {
			accountVo.setRemark("提交充值成功!等待商家审核");
		} else {
			accountVo.setRemark("提交充值失败!");
		}
		accountVo.setTx(accountVo.getTx());
		modelAndView.addObject("accountVo", accountVo);
		modelAndView.setViewName("userAccount");
		return modelAndView;
	}
	//管理员给用户充值
	@RequestMapping("/confirmRecharge")
	public @ResponseBody String confirmRecharge(AccountVo accountVo)throws Exception{
		Account account=new Account();
		account.setId(accountVo.getId());
		account.setUserId(accountVo.getUserId());
		account.setConfirmMoney(accountVo.getSmMoney());
		account.setBalance(accountVo.getSmMoney()+accountVo.getBalance());
		account.setSmMoney(0f);
		account.setPoints(accountVo.getPoints());
		account.setStatus("1");
		int result=accountService.reCharge(account);
		if(result>0){
			return "success";
		}else{
			return "failer";
		}
	}
	
	//管理员通知用户账户信息
	@RequestMapping("/sendUserWithAccount")
	public @ResponseBody String sendUserWithAccount(Account account)throws Exception{
		int result=accountService.sendUserWithAccount(account);
		if(result>0){
			return "success";
		}else{
			return "failer";
		}
	}
	
}
