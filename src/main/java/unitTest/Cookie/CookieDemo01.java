package unitTest.Cookie;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "CookieDemo01", value = "/CookieDemo01")
public class CookieDemo01 extends HttpServlet implements Servlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");

        Cookie[] cookies=request.getCookies();
        boolean flag=false;

        if(cookies!=null||cookies.length>0){
            for(Cookie cookie:cookies){

                String name=cookie.getName();

                if("lastTime".equals(name)){
                    flag=true;

                    Date date=new Date();
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss");
                    String str_date=sdf.format(date);
                    str_date= URLEncoder.encode(str_date,"utf-8");
                    cookie.setValue(str_date);
                    cookie.setMaxAge(60*60*24*30);

                    response.addCookie(cookie);

                    String value=cookie.getValue();
                    value = URLDecoder.decode(value,"utf-8");
                    response.getWriter().write("<h1>欢迎回来，你上次访问的时间为:"+value+"</h1>");

                    break;
                }
            }
        }

        if(cookies.length==0 || !flag){
            Date date=new Date();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss");
            String str_date=sdf.format(date);
            str_date= URLEncoder.encode(str_date,"utf-8");
            Cookie cookie=new Cookie("lastTime",str_date);
            cookie.setMaxAge(60*60*24*30);

            response.addCookie(cookie);

            response.getWriter().write("<h1>您好，欢迎您首次访问</h1>");
        }
    }
}
