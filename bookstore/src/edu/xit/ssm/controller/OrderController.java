package edu.xit.ssm.controller;


import java.io.File;





import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;









import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import edu.xit.ssm.po.Account;
import edu.xit.ssm.po.Orders;
import edu.xit.ssm.po.Users;
import edu.xit.ssm.service.AccountService;
import edu.xit.ssm.service.OrdersService;
import edu.xit.ssm.service.ShopcartService;

@Controller
public class OrderController {

	@Autowired
	private OrdersService ordersService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private ShopcartService shopcartService;
	/*@RequestMapping("/insertOrder") 
	@ResponseBody
	public String insertSelective(Orders record) throws Exception {
		if(record.getTradingType()==10){
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String str = sdf.format(date);
			record.setOrderSn(str);
			ordersService.insertSelective(record);
			return "10";
		}else if(record.getTradingType()==20){
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String str = sdf.format(date);
			record.setOrderSn(str);
			ordersService.insertSelective(record);
			//2019-4-17修改
			Account account=accountService.selectAccountByUid2(record.getUserId());
			int points=account.getPoints()-record.getPoints();
			float changMoney=(float)record.getPoints()/10;
			float balance=account.getBalance()+changMoney-record.getSubtotal();
			if(balance>=0){
				if(points>=0){
					account.setPoints(points);
				}else{
					account.setPoints(0);
				}
				account.setBalance(balance);//实现扣款
				accountService.payMoneyOrPoints(account);
				//shopcartService.deleteCart(record.getCartId());		
			}else{
				record.setTradingType(10);//未付款
				ordersService.updateOrderState(record);
				return "noMoney";
			}
			return "20";
		}else{
			return "error";
		}
		
	}*/
	@RequestMapping("/insertOrder") 
	public @ResponseBody String insertSelective(@RequestBody List<Orders>orderList) throws Exception {
//		JSONObject object=new JSONObject(orderList);
//		System.out.println(object);
		String flag="0";
		Account account=accountService.selectAccountByUid2(orderList.get(0).getUserId());
		int points=account.getPoints()-orderList.get(0).getPoints();
		float changMoney=(float)orderList.get(0).getPoints()/10;
		float allTotal=0;
		for(Orders orders:orderList){
			allTotal+=orders.getSubtotal();
		}
		float balance=account.getBalance()+changMoney-allTotal;
		int toPoints=0;
		for(Orders order:orderList){
			if(order.getTradingType()==10){
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				String str = sdf.format(date);
				order.setOrderSn(str);
				order.setTradingType(10);
//				ordersService.insertSelective(order);
//				shopcartService.deleteCart(order.getId());
				flag="1";//购买失败
				
			}
			if(order.getTradingType()==20&&balance>=0){
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				String str = sdf.format(date);
				order.setOrderSn(str);
				ordersService.insertSelective(order);
				order.setTradingType(20);
				shopcartService.deleteCart(order.getId());
				
				flag="2";//购买成功
			}else if(order.getTradingType()==20&&balance<0){
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				String str = sdf.format(date);
				order.setOrderSn(str);
				order.setTradingType(10);
//				ordersService.insertSelective(order);
//				shopcartService.deleteCart(order.getId());
				flag="3";//余额不足,购买失败
			}
			
		}
		
		if("2".equals(flag)){
			if(allTotal>=30){
				toPoints=(int) Math.ceil(((allTotal-30)/2));
			}
			account.setBalance(balance);//实现扣款
			if(points>=0){
				account.setPoints(points);
			}else{
				account.setPoints(0);
			}
			account.setPoints(toPoints);
			accountService.payMoneyOrPoints(account);
			return "20";
		}else if("1".equals(flag)){
			return "10";
		}else if("3".equals(flag)){
			return "30";
		}else{
			return "00";
		}
	}
	
	
	@RequestMapping("/insertOneOrder") 
	@ResponseBody
	public String insertOneSelective(Orders record) throws Exception {
		String flag="0";
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		record.setId(uuid);
		Account account=accountService.selectAccountByUid2(record.getUserId());
		int points=account.getPoints()-record.getPoints();
		float changMoney=(float)record.getPoints()/10;
		float balance=account.getBalance()+changMoney-record.getSubtotal();
		int toPoints=0;
		if(record.getTradingType()==10){
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String str = sdf.format(date);
			record.setOrderSn(str);
			record.setTradingType(10);
//			ordersService.insertSelective(record);
			flag="1";//购买失败
		}
		if(record.getTradingType()==20&&balance>=0){
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String str = sdf.format(date);
			record.setOrderSn(str);
			record.setTradingType(20);
			ordersService.insertSelective(record);
			flag="2";//购买成功
		}else if(record.getTradingType()==20&&balance<0){
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String str = sdf.format(date);
			record.setOrderSn(str);
			record.setTradingType(10);
//			ordersService.insertSelective(record);
			flag="3";//购买失败
		}
		if("2".equals(flag)){
			if(record.getSubtotal()>=30){
				toPoints=(int) Math.ceil(((record.getSubtotal()-30)/2));
			}
			account.setBalance(balance);//实现扣款
			if(points>=0){
				account.setPoints(points);
			}else{
				account.setPoints(0);
			}
			account.setPoints(toPoints);
			accountService.payMoneyOrPoints(account);
			return "20";
		}else if("1".equals(flag)){
			return "10";
		}else if("3".equals(flag)){
			return "30";
		}else{
			return "00";
		}
	}
			//订单列表
			@RequestMapping("/queryOrder")
			public ModelAndView queryOrder(HttpServletRequest request,HttpServletResponse response) throws Exception {
				
				ModelAndView modelAndView = new ModelAndView();
				Users user=(Users)request.getSession().getAttribute("user");
				List<Orders> order=null;
				if(user!=null){
					order=ordersService.selectOrders(user.getId());
				}
			
				modelAndView.addObject("orderList",order);
				modelAndView.setViewName("orders");
				
				return modelAndView;
			}
			
			/*@RequestMapping("/queryOrderAear")
			@ResponseBody
			public List<Orders> queryOrderAear(HttpServletRequest request,HttpServletResponse response) throws Exception {
				
				
				Users user=(Users)request.getSession().getAttribute("user");
				List<Orders> orderList=ordersService.selectOrders(user.getId());
				
				
				return orderList;
			}*/
			//订单列表-后台
			@RequestMapping("/queryOrder1")
			public ModelAndView queryOrder1() throws Exception {
				
				ModelAndView modelAndView = new ModelAndView();
				List<Orders> order=ordersService.selectOrders1();
				modelAndView.addObject("orderList",order);
				modelAndView.setViewName("ordersmanager");
				
				return modelAndView;
			}
			
			//更新订单状态
			@RequestMapping("/updateOrderState")
			public String updateOrderState(Orders order) throws Exception {

				ordersService.updateOrderState(order);
				return "redirect:queryOrder";
			}
			
			//更新订单状态-后台
			@RequestMapping("/updateOrderState1")
			public String updateOrderState1(Orders order) throws Exception {

				ordersService.updateOrderState(order);
				return "redirect:queryOrder1";
			}
			
			//通过id删除订单(只能删除一条)
			@RequestMapping("/deleteOrder")
			public String deleteOrder(String id) throws Exception{
				
				ordersService.deleteOrder(id);
				
				return "redirect:queryOrder.action";
			}
			
			//通过id删除订单(只能删除一条)
			@RequestMapping("/deleteOrder1")
			public String deleteOrder1(String id) throws Exception{
				
				ordersService.deleteOrder(id);
				
				return "redirect:queryOrder1.action";
			}
			
			
			 /** 
		     * 方法二上传文件，一次一张 
		     */  
			@RequestMapping("/onefile2")  
			public String oneFileUpload2(HttpServletRequest request,  
			        HttpServletResponse response) throws Exception {  
				String p2=request.getRequestURI();
				System.out.println(p2);
			    CommonsMultipartResolver cmr = new CommonsMultipartResolver(  
			            request.getServletContext());  
			    if (cmr.isMultipart(request)) {  
			        MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) (request);  
			        Iterator<String> files = mRequest.getFileNames();  
			        while (files.hasNext()) {  
			            MultipartFile mFile = mRequest.getFile(files.next());  
			            if (mFile != null) {  
			                String fileName = UUID.randomUUID()  
			                        + mFile.getOriginalFilename();  
			                String path = "C:/Users/Administrator/Desktop/Hplus/WebContent/images/book/" + fileName;  
			  
			                File localFile = new File(path);  
			                mFile.transferTo(localFile);  
			                request.setAttribute("fileUrl", path);  
			            }  
			        }  
			    }  
			    return "fileUpload";  
			}  
			
			
			 /** 
		     * 方法一上传文件 
		     */  
		    @RequestMapping("/onefile")  
		    public String oneFileUpload(  
		            @RequestParam("file") CommonsMultipartFile file,  
		            HttpServletRequest request, ModelMap model) {  
		  
		        // 获得原始文件名  
		        String fileName = file.getOriginalFilename();  
		        System.out.println("原始文件名:" + fileName);  
		  
		        // 新文件名  
		        String newFileName = UUID.randomUUID() + fileName;  
		  
		        // 获得项目的路径  
		        ServletContext sc = request.getSession().getServletContext();  
		        // 上传位置  
		        String path = sc.getRealPath("/images/book") + "/"; // 设定文件保存的目录  
		  
		        File f = new File(path);  
		        if (!f.exists())  
		            f.mkdirs();  
		        if (!file.isEmpty()) {  
		            try {  
		                FileOutputStream fos = new FileOutputStream(path + newFileName);  
		                InputStream in = file.getInputStream();  
		                int b = 0;  
		                while ((b = in.read()) != -1) {  
		                    fos.write(b);  
		                }  
		                fos.close();  
		                in.close();  
		            } catch (Exception e) {  
		                e.printStackTrace();  
		            }  
		        }  
		        
		         
		  
		        System.out.println("上传图片到:" + path + newFileName);  
		        // 保存文件地址，用于JSP页面回显  
		        model.addAttribute("fileUrl", path + newFileName);  
		        return "fileUpload";  
		    } 
	
}
