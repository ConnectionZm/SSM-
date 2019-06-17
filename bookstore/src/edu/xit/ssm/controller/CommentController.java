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

import edu.xit.ssm.po.Comment;
import edu.xit.ssm.po.CommentVo;
import edu.xit.ssm.po.Users;
import edu.xit.ssm.service.CommentService;
import edu.xit.ssm.service.UsersService;

@Controller
public class CommentController {
	@Autowired
	private CommentService CommentService;
//	@Autowired
//	private UsersService usersService;

	//查询所有用户评论
	@RequestMapping("/selectCommentsAll")
	public @ResponseBody ModelAndView selectCommentsAll(Integer bid) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		List<CommentVo> commentVoList = CommentService.selectCommentsAll();
		modelAndView.addObject("commentVoList",commentVoList);
		modelAndView.setViewName("commentmanager");
		
		return modelAndView;
	}
	// 通过书籍id查询到该书的所有用户评论
	//@ModelAttribute("commentVoList")
	@RequestMapping("/selectCommentBybid")
	public @ResponseBody List<CommentVo> selectCommentBybid(Integer bid, HttpServletRequest request) throws Exception {
		List<CommentVo> commentList = CommentService.selectCommentsByBid(bid);
		return commentList;
	}

	// 用户对书籍进行评论
	@RequestMapping("/commentWithBook")
	public @ResponseBody String commentWithBook(Comment record) throws Exception {
		String descr=record.getDescr();
		String [] zh={"傻逼","弱智","垃圾","辣鸡","色情"};
		for(String s:zh){
			if(descr.contains(s)){
				descr=descr.replace(s, "**");
			}
		}
		record.setDescr(descr);
		int result = CommentService.commentWithBook(record);
		if (result > 0) {
			return "commentSuccess";
		} else {
			return "commentFailer";
		}
	}

	// 用户对自己的评论进行删除
	@RequestMapping("/deleteComment")
	public @ResponseBody String deleteComment(Integer id) throws Exception {
		int result = CommentService.deleteCommentById(id);
		if (result > 0) {
			return "delCommSuccess";
		} else {
			return "delCommFailer";
		}
	}
	// 用户对自己的评论进行删除
	@RequestMapping("/deleteCommentByManager")
	public String deleteCommentByManager(Integer id) throws Exception {
		CommentService.deleteCommentById(id);
		return "redirect:selectCommentsAll.action";
	}
}
