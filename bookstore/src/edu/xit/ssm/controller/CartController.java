package edu.xit.ssm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.xit.ssm.po.Account;
import edu.xit.ssm.po.Shopcart;
import edu.xit.ssm.po.ShopcartVo;
import edu.xit.ssm.po.Users;
import edu.xit.ssm.service.AccountService;
import edu.xit.ssm.service.ShopcartService;

@Controller
public class CartController {
	
	@Autowired
	private ShopcartService shopcartService;
	@Autowired
	private AccountService accountService;
	@RequestMapping("/insertToCart")
	@ResponseBody
	public String insertToCart(Shopcart shopcart) throws Exception{
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		shopcart.setId(uuid);
		shopcartService.insertToCart(shopcart);
		return "ok";
	}
	
		//购物车列表
		@RequestMapping("/queryCart")
		public ModelAndView queryCart(HttpServletRequest request,HttpServletResponse response) throws Exception {
			
			ModelAndView modelAndView = new ModelAndView();
			Users user=(Users)request.getSession().getAttribute("user");
			List<Shopcart> shopcartList=shopcartService.selectCarts(user.getId());
			Account account=accountService.selectAccountByUid2(user.getId());
			List<ShopcartVo>shopCartVoList=new ArrayList<ShopcartVo>();
			ShopcartVo shopCartVo=null;
			for(Shopcart shopCart:shopcartList){
				shopCartVo=new ShopcartVo();
				shopCartVo.setId(shopCart.getId());
				shopCartVo.setUserId(shopCart.getUserId());
				shopCartVo.setBookId(shopCart.getBookId());
				shopCartVo.setBookName(shopCart.getBookName());
				shopCartVo.setRob(shopCart.getRob());
				shopCartVo.setNumber(shopCart.getNumber());
				shopCartVo.setSubtotal(shopCart.getSubtotal());
				shopCartVo.setPrice(shopCart.getPrice());
				shopCartVo.setPic(shopCart.getPic());
				//shopCartVo.setPoints(account.getPoints());
				shopCartVoList.add(shopCartVo);
			}
			modelAndView.addObject("shopcartList",shopCartVoList);
			modelAndView.addObject("points",account.getPoints());
			modelAndView.setViewName("cart");
			
			return modelAndView;
		}
		
		//通过id删除购物车(只能删除一条)
		@RequestMapping("/deleteCart")
		public String deleteCart(String id) throws Exception{
			
			shopcartService.deleteCart(id);
			
			return "redirect:queryCart.action";
		}
		
		//通过id删除购物车(只能删除一条)ajax异步调用
		@RequestMapping("/deleteCart1")
		public String deleteCart1(String id,HttpServletRequest req) throws Exception{
					
			shopcartService.deleteCart(id);		
			return "ok";
		}
		
		//通过id删除购物车(可删除多条)
		@RequestMapping("/deleteAllCart")
		@ResponseBody
		public String deleteAllCart(Shopcart shopcart) throws Exception{
					
			String id=shopcart.getBookName();
			String [] id1=id.split(",");
			for(int i=0;i<id1.length;i++){
				shopcartService.deleteCart(id1[i]);
			}
			return "ok";
		}
	
}
