package com.bianyiit.web.controller;

import com.bianyiit.domain.*;
import com.bianyiit.services.UserService;
import com.bianyiit.services.Impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

//用户模块
public class UserController {
    public void regist(HttpServletRequest request, HttpServletResponse response)throws IOException {
        /*设置响应体的格式为json*/
        response.setContentType("application/json;charset=utf-8");
        /*检验注册时输入的验证码是否正确*/
        String check = request.getParameter("checkCode");
        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        request.getSession().removeAttribute("CHECKCODE_SERVER");
        ResultInfo resultInfo = new ResultInfo();
        ObjectMapper objectMapper = new ObjectMapper();
        if(checkcode_server==null||!check.equalsIgnoreCase(checkcode_server)){
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("图形验证码输入错误，请重新输入");
            objectMapper.writeValue(response.getWriter(),resultInfo);
            return;
        }
        /*将用户手机号和密码封装进Member对象中*/
        Map<String, String[]> parameterMap = request.getParameterMap();
        Member member=new Member();
        try {
            BeanUtils.populate(member,parameterMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        UserService userService = new UserServiceImpl();
        int update = 0;
        try {
            update = userService.regist(member);
        } catch (Exception e) {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("注册失败");
            objectMapper.writeValue(response.getWriter(),resultInfo);
            return;
        }
        if(update>0){
            resultInfo.setFlag(true);
            resultInfo.setErrorMsg("恭喜您，注册成功！！");
            objectMapper.writeValue(response.getWriter(),resultInfo);
        }
    }
    public void isContainsSamePhone(HttpServletRequest request, HttpServletResponse response)throws IOException {
        /*设置响应体的格式为json*/
        response.setContentType("application/json;charset=utf-8");
        ResultInfo resultInfo = new ResultInfo();
        ObjectMapper objectMapper = new ObjectMapper();
        String memberPhone = request.getParameter("MemberPhone");
        UserService userService = new UserServiceImpl();
        boolean istrue = userService.includeSamePhone(memberPhone);
        if(istrue){
            resultInfo.setFlag(true);
            objectMapper.writeValue(response.getWriter(),resultInfo);
        }else{
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("此手机号已注册，请更换其它手机号，或使用该手机号登录");
            objectMapper.writeValue(response.getWriter(),resultInfo);
        }
    }
    /*企业用户名校验*/
    public void isExistUserName(HttpServletRequest request, HttpServletResponse response)throws IOException {
        /*设置响应体的格式为json*/
        response.setContentType("application/json;charset=utf-8");
        ResultInfo resultInfo = new ResultInfo();
        ObjectMapper objectMapper = new ObjectMapper();
        String employeeuser = request.getParameter("employeeUser");
        UserService userService = new UserServiceImpl();
        boolean istrue = userService.isExistEmployeeUser(employeeuser);
        if(istrue){
            resultInfo.setFlag(true);
            objectMapper.writeValue(response.getWriter(),resultInfo);
        }else{
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("该用户名已被使用，请更换其它用户名，或者使用该用户名登录");
            objectMapper.writeValue(response.getWriter(),resultInfo);
        }
    }
    /*企业邮箱校验*/
    public void isExistEmail(HttpServletRequest request, HttpServletResponse response)throws IOException {
        /*设置响应体的格式为json*/
        response.setContentType("application/json;charset=utf-8");
        ResultInfo resultInfo = new ResultInfo();
        ObjectMapper objectMapper = new ObjectMapper();
        String businessEmail = request.getParameter("businessEmail");
        UserService userService = new UserServiceImpl();
        boolean istrue = userService.isExistEmail(businessEmail);
        if(istrue){
            resultInfo.setFlag(true);
            objectMapper.writeValue(response.getWriter(),resultInfo);
        }else{
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("该邮箱已被注册，请更换其它邮箱，或使用该邮箱登录！！");
            objectMapper.writeValue(response.getWriter(),resultInfo);
        }
    }
    /*法人手机号码验证*/
    public void isExistPhone(HttpServletRequest request, HttpServletResponse response)throws IOException {
        /*设置响应体的格式为json*/
        response.setContentType("application/json;charset=utf-8");
        ResultInfo resultInfo = new ResultInfo();
        ObjectMapper objectMapper = new ObjectMapper();
        String legalPhone = request.getParameter("legalPhone");
        UserService userService = new UserServiceImpl();
        boolean istrue = userService.isExistPhone(legalPhone);
        if(istrue){
            resultInfo.setFlag(true);
            objectMapper.writeValue(response.getWriter(),resultInfo);
        }else{
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("此手机号已注册，请更换其它手机号，或使用该手机号登录");
            objectMapper.writeValue(response.getWriter(),resultInfo);
        }
    }
    /*将商家入驻的相关信息添加至数据库中*/
    public void business_regist(HttpServletRequest request, HttpServletResponse response)throws IOException {
        /*设置响应体的格式为json*/
        response.setContentType("application/json;charset=utf-8");
        /*检验注册时输入的验证码是否正确*/
        String check = request.getParameter("checkCode");
        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        request.getSession().removeAttribute("CHECKCODE_SERVER");
        ResultInfo resultInfo = new ResultInfo();
        ObjectMapper objectMapper = new ObjectMapper();
        if(checkcode_server==null||!check.equalsIgnoreCase(checkcode_server)){
            resultInfo.setFlag(false);
            resultInfo.setIdentify("CheckCodeError");
            resultInfo.setErrorMsg("图形验证码输入错误，请重新输入");
            objectMapper.writeValue(response.getWriter(),resultInfo);
            return;
        }
        /*将用户手机号和密码封装进business和Employee对象中*/
        Map<String, String[]> parameterMap = request.getParameterMap();
        Business business = new Business();
        Employee employee = new Employee();
        try {
            BeanUtils.populate(business,parameterMap);
            /*将用户选择的下拉列表的值封装进地址中*/
            String prov = request.getParameter("prov");
            String city = request.getParameter("city");
            String dist = request.getParameter("dist");
            String businessAddress = request.getParameter("businessAddress");
            businessAddress=prov+"省"+city+"市"+dist+businessAddress;
            business.setBusinessAddress(businessAddress);

            BeanUtils.populate(employee,parameterMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        UserService userService = new UserServiceImpl();
        int update=0;
        try {
            update = userService.business_reigst(business, employee);
        } catch (Exception e) {
            resultInfo.setFlag(false);
            resultInfo.setIdentify("RegistError");
            resultInfo.setErrorMsg("注册失败");
            objectMapper.writeValue(response.getWriter(),resultInfo);
            return;
        }
        if(update>0){
            resultInfo.setFlag(true);
            resultInfo.setErrorMsg("恭喜您，注册成功！！");
            objectMapper.writeValue(response.getWriter(),resultInfo);
        }
    }

    public void login(HttpServletRequest request, HttpServletResponse response)throws IOException {
        /*设置响应体的格式为json*/
        response.setContentType("application/json;charset=utf-8");
        /*检验注册时输入的验证码是否正确*/
        String check = request.getParameter("checkCode");
        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        request.getSession().removeAttribute("CHECKCODE_SERVER");
        ResultInfo resultInfo = new ResultInfo();
        ObjectMapper objectMapper = new ObjectMapper();
        if(checkcode_server==null||!check.equalsIgnoreCase(checkcode_server)){
            resultInfo.setFlag(false);
            resultInfo.setIdentify("CheckCodeError");
            resultInfo.setErrorMsg("图形验证码输入错误，请重新输入");
            objectMapper.writeValue(response.getWriter(),resultInfo);
            return;
        }
        /*将用户手机号和密码封装进Member对象中*/
        Map<String, String[]> parameterMap = request.getParameterMap();
        Member member=new Member();
        try {
            BeanUtils.populate(member,parameterMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        UserService userService = new UserServiceImpl();
        Member login_user = userService.login(member);
        login_user.setReceiveMsg(null);
        List<ReceiveMsg> member_ReceiveMsg = userService.getReceiveMsg(login_user.getMemberID());
        login_user.setReceiveMsg(member_ReceiveMsg);
        if(login_user!=null){
            try {
                String sevenday_login = request.getParameter("sevenday_login");
                if(sevenday_login.equals("on")){
                    //把用户名，密码保存到cookie中
                    Cookie cookie=new Cookie("autologin",member.getMemberPhone()+"="+member.getMemberPassword());
                    cookie.setPath("/");
                    cookie.setMaxAge(7*24*60*60);
                    response.addCookie(cookie);
                }
            } catch (Exception e) {
                //e.printStackTrace();
                Cookie cookie=new Cookie("autologin",null);
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
            request.getSession().setAttribute("loginUser",login_user);
            resultInfo.setFlag(true);
            resultInfo.setErrorMsg("恭喜您，登录成功！！");
            objectMapper.writeValue(response.getWriter(),resultInfo);
        }else {
            resultInfo.setFlag(false);
            resultInfo.setIdentify("PasswordError");
            resultInfo.setErrorMsg("密码输入错误，请核对后重新输入");
            objectMapper.writeValue(response.getWriter(),resultInfo);
            return;
        }
    }
    public void loginPhone(HttpServletRequest request, HttpServletResponse response)throws IOException {
        /*设置响应体的格式为json*/
        response.setContentType("application/json;charset=utf-8");
        ResultInfo resultInfo = new ResultInfo();
        ObjectMapper objectMapper = new ObjectMapper();
        String memberPhone = request.getParameter("MemberPhone");
        UserService userService = new UserServiceImpl();
        boolean istrue = userService.includeSamePhone(memberPhone);
        if(istrue){
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("此手机号尚未完成注册，请核对后重新输入");
            objectMapper.writeValue(response.getWriter(),resultInfo);
        }else {
            resultInfo.setFlag(true);
            objectMapper.writeValue(response.getWriter(),resultInfo);
        }
    }
    public void FindLoginUser(HttpServletRequest request, HttpServletResponse response)throws IOException {
        response.setContentType("application/json;charset=utf-8");
        Member loginUser = (Member) request.getSession().getAttribute("loginUser");
        ObjectMapper objectMapper=new ObjectMapper();
        Member login_user=new Member();
        UserService userService=new UserServiceImpl();
        if(loginUser==null){
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("autologin")){
                    String value = cookie.getValue();
                    String[] split = value.split("=");
                    String memberPhone = split[0];
                    String memberPassword = split[1];
                    login_user.setMemberPhone(memberPhone);
                    login_user.setMemberPassword(memberPassword);
                    String memberID = userService.getMemberID(memberPhone);
                    login_user.setMemberID(memberID);
                    List<ReceiveMsg> member_ReceiveMsg = userService.getReceiveMsg(login_user.getMemberID());
                    login_user.setReceiveMsg(member_ReceiveMsg);
                    String memberNickname = userService.getmemberNickname(login_user.getMemberID());
                    if(memberNickname!=null){
                        login_user.setMemberNickname(memberNickname);
                    }
                    request.getSession().setAttribute("loginUser",login_user);
                }
            }
        }else{
            List<ReceiveMsg> member_ReceiveMsg = userService.getReceiveMsg(loginUser.getMemberID());
            loginUser.setReceiveMsg(member_ReceiveMsg);
            String memberNickname = userService.getmemberNickname(loginUser.getMemberID());
            if(memberNickname!=null){
                loginUser.setMemberNickname(memberNickname);
            }
            request.getSession().setAttribute("loginUser",loginUser);
        }
        objectMapper.writeValue(response.getWriter(),loginUser);
    }
    public void Exit(HttpServletRequest request, HttpServletResponse response)throws IOException {
        response.setContentType("application/json;charset=utf-8");
        request.getSession().invalidate();
        Cookie cookie=new Cookie("autologin",null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        response.sendRedirect("/index.html");
    }
    /*验证码图片生成*/
    public void CheckCode(HttpServletRequest request, HttpServletResponse response)throws IOException {
        //服务器通知浏览器不要缓存
        response.setHeader("pragma","no-cache");
        response.setHeader("cache-control","no-cache");
        response.setHeader("expires","0");

        //在内存中创建一个长80，宽30的图片，默认黑色背景
        //参数一：长
        //参数二：宽
        //参数三：颜色
        int width = 80;
        int height = 30;
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

        //获取画笔
        Graphics g = image.getGraphics();
        //设置画笔颜色为灰色
        g.setColor(Color.GRAY);
        //填充图片
        g.fillRect(0,0, width,height);

        //产生4个随机验证码，12Ey
        String checkCode = getCheckCode();
        //将验证码放入HttpSession中
        request.getSession().setAttribute("CHECKCODE_SERVER",checkCode);

        //设置画笔颜色为黄色
        g.setColor(Color.YELLOW);
        //设置字体的小大
        g.setFont(new Font("黑体",Font.BOLD,24));
        //向图片上写入验证码
        g.drawString(checkCode,15,25);

        //将内存中的图片输出到浏览器
        //参数一：图片对象
        //参数二：图片的格式，如PNG,JPG,GIF
        //参数三：图片输出到哪里去
        ImageIO.write(image,"PNG",response.getOutputStream());
    }
    /**
     * 产生4位随机字符串
     */
    private String getCheckCode() {
        String base = "0123456789ABCDEFGabcdefg";
        int size = base.length();
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        for(int i=1;i<=4;i++){
            //产生0到size-1的随机值
            int index = r.nextInt(size);
            //在base字符串中获取下标为index的字符
            char c = base.charAt(index);
            //将c放入到StringBuffer中去
            sb.append(c);
        }
        return sb.toString();
    }
    /*public void Active(HttpServletRequest request, HttpServletResponse response)throws IOException {
        String activeCode = request.getParameter("activeCode");
        System.out.println(activeCode);
        UserService userService=new UserServiceImpl();
        boolean isactive = userService.active(activeCode);
        if(isactive){
            //激活成功，跳转至主页
            response.sendRedirect(request.getContextPath()+"/active_ok.html");
        }else {
            response.sendRedirect(request.getContextPath()+"/active_fail.html");
        }
    }*/
}
