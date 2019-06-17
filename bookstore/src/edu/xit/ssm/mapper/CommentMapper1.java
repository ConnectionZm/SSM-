package edu.xit.ssm.mapper;

import java.util.List;

import edu.xit.ssm.po.Comment;

public interface CommentMapper1 {
	//查找所有用户评论
	public List<Comment>selectCommentsAll()throws Exception;
	// 根据书籍id查询该书籍下的所有评论
	public List<Comment> selectCommentsByBid(Integer bid) throws Exception;

	// 根据评论id删除自己的评论
	public int deleteCommentById(Integer id) throws Exception;
}
