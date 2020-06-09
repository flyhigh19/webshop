package com.bianyiit.servlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@WebServlet("/checkCodeServlet")
public class ValidateCodeServlet extends HttpServlet {

    public static final int WIDTH = 120;//定义图片的长
    public static final int HEIGHT = 30;//定义图片的高

    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //首先先生成一张图片
        BufferedImage bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        //获取画笔
        Graphics graphics = bi.getGraphics();
        //给图片设置背景  写一个设置背景的方法
        setBackground(graphics);
        //设置边框  在下面定义一个设置边框的方法
        setBorder(graphics);
        //像图片中设置干扰线  在下面定义一个干扰线
        setLine(graphics);
        //设置干扰点
        setPoint(graphics);
        //创建一个随机数


        String baseString = "1234567890abcdefghigklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ";
        String code = createRandomCode(baseString, (Graphics2D) graphics);//调用下面写入随机数的方法 将随机生成的字符写入到图片中
        req.getSession().setAttribute("code", code);
        //向页面中输出
        resp.setContentType("image/jpeg");//设置类型
        ImageIO.write(bi, "jpeg", resp.getOutputStream());


    }

    /**
     * 设置背景
     *
     * @param g
     */
    public void setBackground(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);
    }

    /**
     * 设置边框
     *
     * @param g
     */
    public void setBorder(Graphics g) {
        g.setColor(Color.GRAY);
        g.drawRect(1, 1, WIDTH - 2, HEIGHT - 2);
    }

    /**
     * 设置干扰线
     *
     * @param g
     */
    public void setLine(Graphics g) {

        //设置彩色的干扰线
        g.setColor(new Color(new Random().nextInt(200), new Random().nextInt(90), new
                Random().nextInt(255)));

        for (int i = 0; i < 5; i++) {
            int x1 = new Random().nextInt(WIDTH);
            int x2 = new Random().nextInt(WIDTH);
            int y1 = new Random().nextInt(HEIGHT);
            int y2 = new Random().nextInt(HEIGHT);
            g.drawLine(x1, y1, x2, y2);
        }
    }

    /**
     * 设置干扰点
     *
     * @param g
     */
    public void setPoint(Graphics g) {
        //设置彩色的干扰线
        g.setColor(new Color(new Random().nextInt(200), new Random().nextInt(90), new Random().nextInt(255)));
        for (int i = 0; i < 100; i++) {
            int x = new Random().nextInt(WIDTH);
            int y = new Random().nextInt(HEIGHT);
            g.drawOval(x, y, 1, 1);
        }
    }

    /**
     * 产生随机数
     *
     * @param baseString
     * @param g
     * @return
     */
    public String createRandomCode(String baseString, Graphics2D g) {
        StringBuffer sb = new StringBuffer();
        //定义X
        int x = 5;
        String ch = "";
        //产生随机数
        for (int i = 0; i < 5; i++) {
            //产生一个随机索引
            int index = new Random().nextInt(baseString.length());
            //产生一个随机数
            ch = baseString.charAt(index) + "";
            //把产生的随机数添加到stringbuffer中
            sb.append(ch);
            //为每个字体设置颜色
            g.setColor(new Color(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255)));
            //设置字体
            g.setFont(new Font("微软雅黑", Font.BOLD, 20));
            //把随机数写入图片
            g.drawString(ch, x, 20);
            //设置一个旋转的角度
            int degree = new Random().nextInt(5) % 30;
            //实现旋转
            g.rotate(degree * Math.PI / 180, x, 20);

            x += 20;
        }
        return sb.toString();
    }
}