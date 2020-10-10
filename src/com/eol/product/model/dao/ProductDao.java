package com.eol.product.model.dao;

import static com.eol.common.JDBCTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.eol.product.model.vo.Product;

public class ProductDao {
	private Properties prop = new Properties();
	
	public ProductDao() {
		try {
			String path = ProductDao.class.getResource("/product/product.properties").getPath();
			prop.load(new FileReader(path));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public List<Product> productList(Connection conn, int cPage, int numPerPage) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<Product> list=new ArrayList();
		
		try {
			pstmt=conn.prepareStatement(prop.getProperty("productList"));
			pstmt.setInt(1, (cPage-1)*numPerPage+1);
			pstmt.setInt(2, cPage*numPerPage);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				Product p=new Product();
				p.setpNo(rs.getInt("p_no"));
				p.setpName(rs.getNString("p_name"));
				p.setpPrice(rs.getInt("p_price"));
				p.setpDiscount(rs.getInt("p_Discount"));
				p.setpStock(rs.getInt("p_Stock"));
				p.setpServing(rs.getInt("p_Serving"));
				p.setpCooktime(rs.getInt("p_Cooktime"));
				p.setpReadytime(rs.getInt("p_Readytime"));
				p.setpTaste(rs.getInt("p_Taste"));
				
				p.setpImage1(rs.getNString("p_Image1"));
				p.setpImage2(rs.getNString("p_Image2"));
				p.setpImage3(rs.getNString("p_Image3"));
				p.setpVideo(rs.getNString("p_Video"));
				
				p.setpDetailImage1(rs.getNString("p_DetailImage1"));
				p.setpDetailImage2(rs.getNString("p_DetailImage2"));
				p.setpDetailImage3(rs.getNString("p_DetailImage3"));
				p.setpDetailTitle1(rs.getNString("p_DetailTitle1"));
				p.setpDetailTitle2(rs.getNString("p_DetailTitle2"));
				p.setpDetailMain(rs.getNString("p_DetailMain"));
				
				p.setpDetailContent1(rs.getNString("p_DetailContent1"));
				p.setpDetailContent2(rs.getNString("p_DetailContent2"));
				
				p.setpRecipeImage1(rs.getNString("p_RecipeImage1"));
				p.setpRecipeImage2(rs.getNString("p_RecipeImage2"));
				p.setpRecipeImage3(rs.getNString("p_RecipeImage3"));
				
				p.setpRecipeImage4(rs.getNString("p_RecipeImage4"));
				
				
				p.setpRecipeContent1(rs.getNString("p_RecipeContent1"));
				p.setpRecipeContent2(rs.getNString("p_RecipeContent2"));
				p.setpRecipeContent3(rs.getNString("p_RecipeContent3"));
				p.setpRecipeContent4(rs.getNString("p_RecipeContent4"));
				p.setpRdate(rs.getDate("p_Rdate"));
				p.setpHit(rs.getInt("p_Hit"));
				p.setpSell(rs.getInt("p_Sell"));
				//p를담아준다
				list.add(p);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		System.out.println("db갓다와서 출력햇음");
		return list;
		
	}
	
	public int productCount(Connection conn) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int result=0;
		try {
			pstmt=conn.prepareStatement(prop.getProperty("productCount"));
			rs=pstmt.executeQuery();
			if(rs.next()) {
				result=rs.getInt("cnt");
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}
	public int insertProduct(Connection conn, Product p) {
		PreparedStatement pstmt=null;
		int result=0;
		try {
			pstmt=conn.prepareStatement(prop.getProperty("insertProduct"));
			pstmt.setNString(1, p.getpName());
			pstmt.setInt(2, p.getpPrice());
			pstmt.setInt(3, p.getpDiscount());
			pstmt.setInt(4, p.getpStock());
			pstmt.setInt(5, p.getpServing());
			pstmt.setInt(6, p.getpCooktime());
			pstmt.setInt(7, p.getpReadytime());
			pstmt.setInt(8, p.getpTaste());
			
			pstmt.setNString(9, p.getpImage1());
			pstmt.setNString(10, p.getpImage2());
			pstmt.setNString(11, p.getpImage3());
			pstmt.setNString(12, p.getpVideo());
			
			pstmt.setNString(13, p.getpDetailImage1());
			pstmt.setNString(14, p.getpDetailImage2());
			pstmt.setNString(15, p.getpDetailImage3());
			
			pstmt.setNString(16, p.getpDetailTitle1());
			pstmt.setNString(17, p.getpDetailTitle2());
			pstmt.setNString(18, p.getpDetailMain());
			
			pstmt.setNString(19, p.getpDetailContent1());
			pstmt.setNString(20, p.getpDetailContent2());
			
			pstmt.setNString(21, p.getpRecipeImage1());
			pstmt.setNString(22, p.getpRecipeImage2());
			pstmt.setNString(23, p.getpRecipeImage3());
			pstmt.setNString(24, p.getpRecipeImage4());
			
			pstmt.setNString(25, p.getpRecipeContent1());
			pstmt.setNString(26, p.getpRecipeContent2());
			pstmt.setNString(27, p.getpRecipeContent3());
			pstmt.setNString(28, p.getpRecipeContent4());

			result=pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}return result;
		
	}


	public Product selectOneProduct(Connection conn, int pNo) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Product p=null;
		try {
			pstmt=conn.prepareStatement(prop.getProperty("selectOneProduct"));
			pstmt.setInt(1, pNo);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				p=new Product();
				p.setpNo(rs.getInt("pNo"));
				p.setpName(rs.getNString("pName"));
				p.setpPrice(rs.getInt("pPrice"));
				p.setpDiscount(rs.getInt("pDiscount"));
				p.setpStock(rs.getInt("pStock"));
				p.setpServing(rs.getInt("pServing"));
				p.setpCooktime(rs.getInt("pCooktime"));
				p.setpReadytime(rs.getInt("pReadytime"));
				p.setpTaste(rs.getInt("pTaste"));
				
				p.setpImage1(rs.getNString("pImage1"));
				p.setpImage2(rs.getNString("pImage2"));
				p.setpImage3(rs.getNString("pImage3"));
				p.setpVideo(rs.getNString("pVideo"));
				
				p.setpDetailImage1(rs.getNString("pDetailImage1"));
				p.setpDetailImage2(rs.getNString("pDetailImage2"));
				p.setpDetailImage3(rs.getNString("pDetailImage3"));
				p.setpDetailTitle1(rs.getNString("pDetailTitle1"));
				p.setpDetailTitle2(rs.getNString("pDetailTitle2"));
				p.setpDetailMain(rs.getNString("pDetailMain"));
				
				p.setpDetailContent1(rs.getNString("pDetailContent1"));
				p.setpDetailContent2(rs.getNString("pDetailContent2"));
				
				p.setpRecipeImage1(rs.getNString("pRecipeImage1"));
				p.setpRecipeImage2(rs.getNString("pRecipeImage2"));
				p.setpRecipeImage3(rs.getNString("pRecipeImage3"));
				p.setpRecipeImage4(rs.getNString("pRecipeImage4"));
				
				p.setpRecipeContent1(rs.getNString("pRecipeContent1"));
				p.setpRecipeContent2(rs.getNString("pRecipeContent2"));
				p.setpRecipeContent3(rs.getNString("pRecipeContent3"));
				p.setpRecipeContent4(rs.getNString("pRecipeContent4"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return p;
	}

	public int updateProduct(Connection conn, Product p) {
		PreparedStatement pstmt=null;
		int result=0;
		try {
			pstmt=conn.prepareStatement(prop.getProperty("updateProduct"));
			pstmt.setString(1,p.getpName());
			pstmt.setInt(2,p.getpPrice());
			pstmt.setInt(3,p.getpDiscount());
			pstmt.setInt(4,p.getpStock());
			pstmt.setInt(5,p.getpServing());
			pstmt.setInt(6,p.getpCooktime());
			pstmt.setInt(7,p.getpReadytime());
			pstmt.setInt(8,p.getpTaste());
			
			pstmt.setString(9,p.getpDetailImage1());
			pstmt.setString(10,p.getpDetailImage2());
			pstmt.setString(11,p.getpDetailImage3());
			
			pstmt.setString(12,p.getpVideo());
			
			pstmt.setString(13,p.getpDetailImage1());
			pstmt.setString(14,p.getpDetailImage2());
			pstmt.setString(15,p.getpDetailImage3());
			pstmt.setString(16,p.getpDetailTitle1());
			pstmt.setString(17,p.getpDetailTitle2());
			pstmt.setString(18,p.getpDetailMain());
			pstmt.setString(19,p.getpDetailContent1());
			pstmt.setString(20,p.getpDetailContent2());
			
			pstmt.setString(21,p.getpRecipeImage1());
			pstmt.setString(22,p.getpRecipeImage2());
			pstmt.setString(23,p.getpRecipeImage3());
			pstmt.setString(24,p.getpRecipeImage4());
			
			pstmt.setString(25,p.getpRecipeContent1());
			pstmt.setString(26,p.getpRecipeContent2());
			pstmt.setString(27,p.getpRecipeContent3());
			pstmt.setString(28,p.getpRecipeContent4());
		
			result=pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("업데이트오류");
		}finally {
			close(pstmt);
		}
		return result;
	}

	public String deleteProduct(Connection conn, String pName) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String p=null;
		try {
			pstmt=conn.prepareStatement(prop.getProperty("deleteProduct"));
			pstmt.setString(1, pName);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				p=rs.getString(1);
			}
			
			}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("delete삭제 오류");
		}finally {
			close(rs);
			close(pstmt);
		}
			
		
		return p;
	}
}