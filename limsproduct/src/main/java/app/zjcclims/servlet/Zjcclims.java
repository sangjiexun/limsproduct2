package app.zjcclims.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.zjcclims.Course;
import app.zjcclims.Response;

import com.google.gson.GsonBuilder;
import com.mysql.jdbc.Connection;

/**
 * Servlet implementation class zjcclims
 */
@WebServlet("/servlet/zjcclims")
public class Zjcclims extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Zjcclims() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//连接数据库
        response.setContentType("text/html；charset=utf-8");
        response.setCharacterEncoding("utf-8");       
        PrintWriter out = response.getWriter();

        //用于连接数据库
        Connection ct= null;
        //用于存放sql语句
        PreparedStatement ps =null;
        //用于存放从数据库获得的记录
        ResultSet rs = null;
        
        //用于存放从数据库获得的数据
        List<Course> list = new ArrayList<Course>();

        
        try {
            //1.加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            //2.得到连接
            ct = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/zjcclims", "root", "gengshang");
            //3.创建PreparedStatment 用于传送sql查询语句
            ps=(PreparedStatement) ct.prepareStatement("select * from school_course_info");
            //4.执行操作
            rs= ps.executeQuery();
            //5.根据结果做处理
            while(rs.next())
            {
            	//获取从数据库得到的每一条记录，并加到list中
            	Course course = new Course(rs.getString("course_number"),rs.getString("course_name"));
                list.add(course);

            }
  
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }finally
        {
            //关闭资源
            if(rs!=null)
            {
                try {
                    rs.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                rs=null;
            }
            if(ps!=null)
            {
                try {
                    ps.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                ps=null;
            }
            if(ct!=null)
            {
                try {
                    ct.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                ct=null;
            }
        }
        //打包成JSON格式
        Response result=new Response(1,list);
        out.println(new GsonBuilder().create().toJson(result));
   
		out.flush();
		out.close();

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
