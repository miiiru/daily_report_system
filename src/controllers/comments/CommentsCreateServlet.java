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
import models.Employee;
import models.Report;
import models.validators.CommentValidator;
import utils.DBUtil;

/**
 * Servlet implementation class CommentsCreateServlet
 */
@WebServlet("/comments/create")
public class CommentsCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentsCreateServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // CSRF対策
        String _token = request.getParameter("_token");
        if (_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            // Comment.javaをインスタンス化(更新したいから）
            Comment c = new Comment();

            // セッションスコープの準備
            HttpSession session = request.getSession();
            // レポート内容、社員番号、コメント内容をcに格納
            c.setReport((Report) session.getAttribute("report"));
            c.setEmployee((Employee) request.getSession().getAttribute("login_employee"));
            c.setContent(request.getParameter("content"));

            // バリデーションを実行してエラーがあったら編集画面のフォームに戻る
            List<String> errors = CommentValidator.validate(c);
            if (errors.size() > 0) {
                em.close();
                // フォームに初期値を設定、さらにエラーメッセージを送る
                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("comment", c);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/comments/new.jsp");
                rd.forward(request, response);
            } else {
                // データベースを更新
                em.getTransaction().begin();
                em.persist(c);
                em.getTransaction().commit();
                em.close();
                request.getSession().setAttribute("flush", "登録が完了しました。");

                response.sendRedirect(request.getContextPath() + "/comments/index");
            }
        }
    }
}
