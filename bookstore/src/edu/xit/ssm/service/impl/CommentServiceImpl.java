package edu.xit.ssm.service.impl;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import edu.xit.ssm.mapper.CommentMapper;
import edu.xit.ssm.mapper.CommentMapper1;
import edu.xit.ssm.mapper.UsersMapper;
import edu.xit.ssm.po.Comment;
import edu.xit.ssm.po.CommentVo;
import edu.xit.ssm.po.Users;
import edu.xit.ssm.service.CommentService;

public class CommentServiceImpl implements CommentService{
	@Autowired
	private CommentMapper1 CommentMapper1;
	@Autowired
	private CommentMapper CommentMapper;
	@Autowired
	private UsersMapper usersMapper;
	
	@Override
	public List<CommentVo> selectCommentsAll() throws Exception {
		List<Comment>commentList=CommentMapper1.selectCommentsAll();
		List<CommentVo>commentVoList=new ArrayList<CommentVo>();
		for(Comment cm:commentList){
			Users user=usersMapper.selectByPrimaryKey(cm.getUid());
			if(user==null) {
				//用户已经被注销
			}else {
				CommentVo cmVo=new CommentVo();
				cmVo.setId(cm.getId());
				cmVo.setDescr(cm.getDescr());
				cmVo.setBid(cm.getBid());
				cmVo.setUid(cm.getUid());
				cmVo.setEmail(user.getEmail());
				cmVo.setUsername(user.getUsername());
				cmVo.setTx(user.getTx());
				commentVoList.add(cmVo);
			}
		}
		return commentVoList;
	}
	@Override
	public List<CommentVo> selectCommentsByBid(Integer bid) throws Exception {
		List<Comment> commentList=CommentMapper1.selectCommentsByBid(bid);
		List<CommentVo>commentVoList=new ArrayList<CommentVo>();
		for(Comment cm:commentList) {
			Users user=usersMapper.selectByPrimaryKey(cm.getUid());
			if(user==null) {
				//用户已经被注销
			}else {
				CommentVo cmVo=new CommentVo();
				cmVo.setId(cm.getId());
				cmVo.setDescr(cm.getDescr());
				cmVo.setBid(cm.getBid());
				cmVo.setUid(cm.getUid());
				cmVo.setUsername(user.getUsername());
				commentVoList.add(cmVo);
			}
		}
		return commentVoList;
	}
	@Override
	public int commentWithBook(Comment record) throws Exception {
		int result=CommentMapper.insertSelective(record);
		return result;
	}
	@Override
	public int deleteCommentById(Integer id) throws Exception {
		int result=CommentMapper1.deleteCommentById(id);
		return result;
	}
	

}
