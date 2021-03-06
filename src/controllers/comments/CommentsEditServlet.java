package controllers.comments;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Comment;
import models.Employee;
import utils.DBUtil;

/**
 * Servlet implementation class CommentsEditServlet
 */
@WebServlet("/comments/edit")
public class CommentsEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentsEditServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // DBUtilを立ち上げ
        EntityManager em = DBUtil.createEntityManager();

        // JSPからidを受け取り、cに格納する
        Comment c = em.find(Comment.class, Integer.parseInt(request.getParameter("id")));

        em.close();

        // login_employeeをセッションスコープから取得し、login_employee変数に格納 nullではなく且つイコールなら実行
        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");
        if(c != null && login_employee.getId() == c.getEmployee().getId()) {
            request.setAttribute("comment", c);
            request.setAttribute("_token", request.getSession().getId());
            request.getSession().setAttribute("comment_id", c.getId());
        }


        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/comments/edit.jsp");
        rd.forward(request, response);
    }


}
