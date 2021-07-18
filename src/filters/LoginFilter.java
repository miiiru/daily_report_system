package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Employee;

/**
 * Servlet Filter implementation class LoginFilter
 */

// @WebFilter("/*")を選択することで、そのアプリケーションの全てのサーブレットでフィルターを適用するという指定になる
@WebFilter("/*")
public class LoginFilter implements Filter {

    /**
     * Default constructor.
     */
    // コンストラクタ（フィルターのインスタンスが生成されるときに実行される）
    public LoginFilter() {
    }

    /**
     * @see Filter#destroy()
     */
    // destroy()には「（フィルタの処理が不要になったため）フィルタを破棄する」というときの処理を定義
    public void destroy() {
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */

    // doFilter()はフィルタとしての実行内容を定義するメソッド
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String context_path = ((HttpServletRequest)request).getContextPath();
        String servlet_path = ((HttpServletRequest)request).getServletPath();

        // @WebFilter("/*")としているため、Webページを表示する上で読み込むすべてのファイルでログイン状態を調べてしまうが以下の通り
        if(!servlet_path.matches("/css.*")) {       // CSSフォルダ内は認証処理から除外する
            HttpSession session = ((HttpServletRequest)request).getSession();

            // セッションスコープに保存された従業員（ログインユーザ）情報を取得し、eに格納
            Employee e = (Employee)session.getAttribute("login_employee");

            if(!servlet_path.equals("/login")) {        // ログイン画面以外について
                // ログアウトしている状態であれば（eがnull、つまりセッションスコープに従業員情報が格納されていない場合）
                // ログイン画面に強制的にリダイレクト
                if(e == null) {
                    ((HttpServletResponse)response).sendRedirect(context_path + "/login");
                    return;
                }

                // 従業員管理（/employees）の機能は管理者(admin_flagが1)のみが閲覧できるようにする
                // admin_flagが0ならトップページにリダイレクトさせる
                if(servlet_path.matches("/employees.*") && e.getAdmin_flag() == 0) {
                    ((HttpServletResponse)response).sendRedirect(context_path + "/");
                    return;
                }
            } else {                                    // ログイン画面について
                // ログインしているのにログイン画面を表示させようとした場合は
                // システムのトップページにリダイレクト
                if(e != null) {
                    ((HttpServletResponse)response).sendRedirect(context_path + "/");
                    return;
                }
            }
        }

        // これが重要！以下の文より前に書いた処理はサーブレットが処理を実行する前にフィルタの処理が実行される
        // 逆に以下の文より後に書いた処理はサーブレットが処理を実行した後にフィルタの処理が実行される
        chain.doFilter(request, response);
    }

    /**
     * @see Filter#init(FilterConfig)
     */

    // init()にはフィルタの処理がはじめて実行されるときの処理を定義する
    public void init(FilterConfig fConfig) throws ServletException {
    }

}