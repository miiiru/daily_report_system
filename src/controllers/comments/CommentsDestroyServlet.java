package controllers.comments;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Comment;
import utils.DBUtil;

/**
 * Servlet implementation class CommentsDestroyServlet
 */
@WebServlet("/comments/destroy")
public class CommentsDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentsDestroyServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // リクエストパラメータに入っているコメントIDを取得する(CSRF対策)
        String _token = request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            Comment c = em.find(Comment.class, (Integer)(request.getSession().getAttribute("comment_id")));

            em.getTransaction().begin();
            em.remove(c);       // データ削除
            em.getTransaction().commit();
            em.close();

            request.getSession().setAttribute("flush", "削除が完了しました。");

            request.getSession().removeAttribute("comment_id");

            response.sendRedirect(request.getContextPath() + "/comments/index");
        }

    }

}
