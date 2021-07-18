package controllers.login;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import utils.DBUtil;
import utils.EncryptUtil;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    // ログイン画面を表示はGet
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("_token", request.getSession().getId());
        request.setAttribute("hasError", false);
        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login/login.jsp");
        rd.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    // ログイン処理を実行・認証処理はPost
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 認証結果を格納する変数
        Boolean check_result = false;
        //パラメータで入力された社員番号とパスワードを取得
        String code = request.getParameter("code");
        String plain_pass = request.getParameter("password");

        Employee e = null;

        if(code != null && !code.equals("") && plain_pass != null && !plain_pass.equals("")) {
            EntityManager em = DBUtil.createEntityManager();
            /*パスワードはハッシュ化されてデータベースに登録されていますので
             フォームから入力されたパスワードに
             EncryptUtil.getPasswordEncrypt() を使ってペッパー文字列を連結した文字列をハッシュ化し、
             そのデータ（ここではpassword）とデータベース上のデータで照合を行います*/
            String password = EncryptUtil.getPasswordEncrypt(
                    plain_pass,
                    (String)this.getServletContext().getAttribute("pepper")
                    );

            // 社員番号とパスワードが正しいかチェックする
            try {
                e = em.createNamedQuery("checkLoginCodeAndPassword", Employee.class)
                      .setParameter("code", code)
                      .setParameter("pass", password)
                      .getSingleResult();
            } catch(NoResultException ex) {}

            em.close();

            if(e != null) {
                check_result = true;
            }
        }

        if(!check_result) {
            // 認証できなかったらログイン画面に戻る
            request.setAttribute("_token", request.getSession().getId());
            /*「社員番号かパスワードが間違っています。」のエラーメッセージは
             * あらかじめJSPに記述しておき、hasError という変数が
             * リクエストスコープに true でセットされていれば表示する、という形をとっています
             */
            request.setAttribute("hasError", true);
            request.setAttribute("code", code);

            // getRequestDispatcherメソッドで利用するjspファイルを指定する（今回の場合はログインJSP）
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login/login.jsp");
            rd.forward(request, response);
        } else {
            // 認証できたらログイン状態にしてトップページへリダイレクト
            // ここでセッションにlogin_employeeという名前でログイン情報を投入
            request.getSession().setAttribute("login_employee", e);

            request.getSession().setAttribute("flush", "ログインしました。");
            response.sendRedirect(request.getContextPath() + "/");
        }
    }

}