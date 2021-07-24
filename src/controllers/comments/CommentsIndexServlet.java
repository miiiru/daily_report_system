package controllers.comments;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Comment;
import utils.DBUtil;

/**
 * Servlet implementation class CommentsIndexServlet
 */
@WebServlet("/comments/index")
public class CommentsIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentsIndexServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // EntityManagerのオブジェクトを生成
        EntityManager em = DBUtil.createEntityManager();

        // レポート詳細ページにて格納されていたreport_idを取得する
        Object r = request.getSession().getAttribute("report");

        // 指定したレポートIDのコメントを全て取得する
        List<Comment> comments = em.createNamedQuery("getReportIdComments",Comment.class)
                                    .setParameter("report", r)
                                    .getResultList();


        em.close();

        HttpSession session = request.getSession();
        // 下記の"comments"は_formCommentIndex.jspのitems="${comments}と対応
        session.setAttribute("comments", comments);


        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/comments/index.jsp");
        rd.forward(request, response);


    }

}
