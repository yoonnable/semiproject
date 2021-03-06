package com.eol.notice.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eol.notice.model.vo.Notice;
import com.eol.notice.service.NoticeService;

/**
 * Servlet implementation class NoticeViewServlet
 */
@WebServlet("/notice/noticeView")
public class NoticeViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeViewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int no=Integer.parseInt(request.getParameter("no"));
		Notice n= new NoticeService().selectNoticeOne(no);
		
		String msg="";
		String loc="";
		String path="";
		if(n==null) {
			//선택한 공지사항이 삭제됨
			msg="선택한 공지사항이 존재하지않습니다.";
			loc="/notice/noticeList";
			path="/views/common/msg.jsp";
//			요청을 저장함 request에 저장
			request.setAttribute("msg", msg);
			request.setAttribute("loc",loc);
		}else {
			request.setAttribute("notice", n);
			path="views/notice/noticeView.jsp";
		}
		request.getRequestDispatcher(path).forward(request, response);
		
		
		
	
	
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
