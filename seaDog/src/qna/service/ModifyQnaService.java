package qna.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import qna.QnaDAO;
import qna.model.QnaDTO;

public class ModifyQnaService {

	private QnaDAO qnaDao = new QnaDAO();

	public void modify(ModifyQnaRequest modReq) throws QnaNotFoundException {
		Connection conn = null;
		
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			QnaDTO qnaDto = qnaDao.selectById(conn, modReq.getQnaNumber());
			if(qnaDto==null) {
				throw new QnaNotFoundException();
			}
			
			qnaDao.update2(conn, modReq.getQnaNumber(), modReq.getTitle(), modReq.getContent());
			conn.commit();
		}catch(SQLException e) {
			JdbcUtil.close(conn);
			throw new RuntimeException(e);
		}finally {
			JdbcUtil.close(conn);
		}
	}
}
