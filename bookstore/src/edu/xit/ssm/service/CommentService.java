package edu.xit.ssm.service;

import java.util.List;

import edu.xit.ssm.po.Comment;
import edu.xit.ssm.po.CommentVo;

public interface CommentService {
	//查看所有用户评论
	public List<CommentVo> selectCommentsAll()throws Exception;
	// 根据书籍id查询该书籍下的所有评论
	public List<CommentVo> selectCommentsByBid(Integer bid) throws Exception;

	// 用户对书籍进行评论
	public int commentWithBook(Comment record) throws Exception;

	// 用户对自己的评论进行删除
	public int deleteCommentById(Integer id) throws Exception;
}
