package net.zjcclims.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ValidateCodeServle")
public class ValidateCodeServle extends HttpServlet {
    private static Random r = new Random();
    //字符
    private static char[] chs = "0123456789".toCharArray();
    //字符个数
    private static final int NUMBER_OF_CHS = 4;
    //图像大小
    private static final int IMG_WIDTH = 80;
    private static final int IMG_HEIGHT = 40;

    private static final long serialVersionUID = 1L;

    public ValidateCodeServle() {
        super();
    }

    // 生成随机的颜色
    private Color randomColor() {
        int red = r.nextInt(200);
        int green = r.nextInt(200);
        int blue = r.nextInt(200);
        return new Color(red, green, blue);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        BufferedImage image = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_RGB);    // 实例化BufferedImage
        Graphics g = image.getGraphics();
        Color c = new Color(200, 200, 255);                                             // 验证码图片的背景颜色
        g.setColor(c);
        g.fillRect(0, 0, IMG_WIDTH, IMG_HEIGHT);                                        // 图片的边框

        StringBuffer sb = new StringBuffer();                                           // 用于保存验证码字符串
        int index;                                                                      // 数组的下标
        for (int i = 0; i < NUMBER_OF_CHS; i++) {
            index = r.nextInt(chs.length);                                              // 随机一个下标
            g.setColor(randomColor());       // 随机一个颜色
            g.setFont( new Font("宋体", 4, 28));
            g.drawString(chs[index] + "", 15 * i + 10, 30);                              // 画出字符
            sb.append(chs[index]);                                                      // 验证码字符串
        }

        request.getSession().setAttribute("validateCode", sb.toString());                    // 将验证码字符串保存到session中
        ImageIO.write(image, "jpg", response.getOutputStream());                        // 向页面输出图像
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}