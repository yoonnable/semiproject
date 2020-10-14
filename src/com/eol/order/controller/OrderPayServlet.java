package com.eol.order.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eol.cart.model.service.CartService;
import com.eol.cart.model.vo.Cart;
import com.eol.member.model.vo.Member;
import com.eol.order.model.service.OrderService;
import com.eol.order.model.vo.Orders;
import com.eol.product.model.vo.Product;

/**
 * Servlet implementation class OrderPayServlet
 */
@WebServlet("/orderpay.do")
public class OrderPayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderPayServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//회원이 결제가 성공하면,
		Member m = (Member)request.getSession().getAttribute("loginMember");
		//Member m = new Member(1,"qwer","qwer","이요셉","93/03/09","laksmi0628@gmail.com","123123","경기도","M",1,0,1000,null,1000);
		Orders o = new Orders();
		int result = 0;
		int odresult = 0;
		
		if(m!=null) {		//회원이 주문결제 했을 경우	
			o.setmNo(m.getmNo());
			o.setoName(m.getmName());
			o.setoPhone(m.getmPhone());
		}else {//비회원이 주문했을경우
			o.setoPw(request.getParameter("oPw"));
			o.setoName(request.getParameter("oName"));
			o.setoPhone(request.getParameter("oPhone"));
		}
		o.setoStatus("결제완료");
		o.setoAddress(request.getParameter("address1")+request.getParameter("address2"));
		o.setoToName(request.getParameter("oToName"));
		o.setoToPhone(request.getParameter("oToPhone"));
		o.setoAmount(Integer.parseInt(request.getParameter("oAmount")));
		o.setoPayment(Integer.parseInt(request.getParameter("oPayment")));
		o.setoPayWays(request.getParameter("oPayway"));
		String date = request.getParameter("oDeliveryEDate");
		
		System.out.println(date);

		result = new OrderService().orderinsert(m, o);//회원이 주문을 했을경우 Member m에 회원정보가 들어간 상태로 가고, 비회원이 주문을 했을경우 Member m이 null이다. 이것을 가지고 dao에서 if문으로 실행될 쿼리문을 결정함!!
		
		if(result>0) {//orders에 인서트를 성공하면 그 후엔 orderDetail에 인서트 하기!!!!
			
			if(m!=null) {//회원일때 			
				o.setoNo(new OrderService().selectoNo(o.getmNo()));//회원일때 지금 바로 주문완료한 주문번호 들고오기	
				List<Cart> list = new CartService().cartintopay(m.getmNo());
				for(Cart c : list) {
					odresult = new OrderService().odinsert(c, o.getoNo());
					
				}
				request.setAttribute("order",o);
			}else {//비회원일 때 
				o.setoNo(new OrderService().noMemberselectoNo(o.getoPw(), o.getoName(), o.getoPhone()));//비회원일 때 지금 바로 주문 완료한 주문번호 들고오기
				List<Product> list = (List)request.getSession().getAttribute("nonCartList");
				for(Product p : list) {
					System.out.println("이거 세션에 담은 상품번호"+ p.getpNo());
					System.out.println("이건 주문번호"+o.getoNo());
					
					odresult = new OrderService().noMemberodinsert(p, o.getoNo());
			
					}
				request.setAttribute("order", o);
				}
			
			if(odresult > 0) {
				request.getRequestDispatcher("/views/order/orderpayEnd.jsp").forward(request, response);
			}else {
				request.setAttribute("msg", "주문에 실패하였습니다");
				request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
			}
			
		}else {
			request.setAttribute("msg", "주문에 실패하였습니다");
			request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
		}
		
		
		
		
		
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}