package com.zzw.trta.image;

import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * @author zhaozhiwei
 * @desc 图像处理测试类
 * @date 2018/9/27 下午9:58
 */
public class ImageTest {

    private final static int DEFAULT_WIDTH = 258;
    private final static int DEFAULT_HEIGHT = 258;
    private final static String DEFAULT_FONT_TYPE = "PingFangSC-Medium";
    private final static String DEFAULT_SUBSTITUTION_IMAGE = "http://image-1257128394.picbj.myqcloud.com/0/substitutionImage.png";
    private final static String DEFAULT_BACKED_IMAGE = "http://image-1257128394.picbj.myqcloud.com/0/backedImage.png";
    private final static String SECKILLING_TAG_IMAGE = "http://image-1257128394.picbj.myqcloud.com/0/seckillingImage.png";
    private final static String SECKILL_TAG_IMAGE = "http://image-1257128394.picbj.myqcloud.com/0/seckillImage.png";

    /**
     * 生成秒杀分享图片
     *
     * @param backFile 底图
     * @param qrCode   二维码
     * @param images   水印图URL，小于等于4
     * @return 加工后的BufferedImage
     * @throws IOException
     */
    public static BufferedImage generateSeckillImage(BufferedImage backFile, String time, BufferedImage qrCode, java.util.List<String> images) throws IOException {
        if (images.size() > 4) {
            throw new IllegalArgumentException("water file count can't greater than 4");
        }
        // 创建Graphics2D对象，用在底图对象上绘图
        Graphics2D g2d = backFile.createGraphics();
        // 消除图片锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (int i = 0; i < images.size(); i++) {
            // 获取层图
            Coordinate coordinate = Coordinate.of(i);
            BufferedImage waterImage = formatImageSize(ImageIO.read(new URL(images.get(i))));
//            BufferedImage waterImage = formatImageSize(ImageIO.read(new File(images.get(i))));
            if (StringUtils.isNotBlank(time)) {
                // 待秒杀分享图
                BufferedImage product = generateSeckillProduct(waterImage, time);
                drawWater(g2d, product, coordinate.getX(), coordinate.getY(), 1F);
            } else {
                // 正在秒杀分享图
                BufferedImage product = generateSeckillingProduct(waterImage);
                drawWater(g2d, product, coordinate.getX(), coordinate.getY(), 1F);
            }
        }
        // 默认图片补位
        if (images.size() < 4) {
            for (int i = images.size(); i < 4; i++) {
                // 获取层图
                Coordinate coordinate = Coordinate.of(i);
                drawWater(g2d, formatImageSize(ImageIO.read(new URL(DEFAULT_SUBSTITUTION_IMAGE))), coordinate.getX(), coordinate.getY(), 1F);
            }
        }
        // 添加二维码到画布上
        drawWater(g2d, formatImageSize(qrCode, 150, 150), 460, 835, 1F);
        // 释放图形上下文使用的系统资源
        g2d.dispose();
        return backFile;
    }

    /**
     * 生成秒杀分享图片（使用默认底图）
     *
     * @param time   秒杀时间，为空表示正在秒杀
     * @param qrCode 小程序码url
     * @param images 产品图url列表
     * @return BufferedImage
     * @throws IOException
     */
    public static BufferedImage generateSeckillImage(String time, BufferedImage qrCode, java.util.List<String> images) throws IOException {
        return generateSeckillImage(ImageIO.read(new URL(DEFAULT_BACKED_IMAGE)), time, qrCode, images);
    }

    /**
     * 合成正在秒杀的产品图
     *
     * @param productImage 产品图
     * @return BufferedImage
     * @throws IOException
     */
    public static BufferedImage generateSeckillingProduct(BufferedImage productImage) throws IOException {
        BufferedImage bufferedImage = setClip(productImage, 25, 1, 1);
        Graphics2D graphics = bufferedImage.createGraphics();
        drawWater(graphics, ImageIO.read(new URL(SECKILLING_TAG_IMAGE)), 0, 0, 1L);
        return bufferedImage;
    }

    /**
     * 合成即将秒杀的产品图
     *
     * @param productImage 产品图
     * @param time         秒杀时间
     * @return BufferedImage
     * @throws IOException
     */
    public static BufferedImage generateSeckillProduct(BufferedImage productImage, String time) throws IOException {
        BufferedImage bufferedImage = setClip(productImage, 25, 1, 1);
        Graphics2D graphics = bufferedImage.createGraphics();
        drawWater(graphics, ImageIO.read(new URL(SECKILL_TAG_IMAGE)), 0, 0, 1L);
        graphics.setFont(new Font(DEFAULT_FONT_TYPE, Font.BOLD, 30));
        graphics.setPaint(Color.WHITE);
        // 消除文字锯齿
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.drawString(time, 50, 33);
        return bufferedImage;
    }

    /**
     * 画水印
     *
     * @param g2d      画板
     * @param waterImg 水印图片
     * @param x        x坐标
     * @param y        y坐标
     * @param alpha    透明度, 选择值从0.0~1.0: 完全透明~完全不透明
     */
    private static void drawWater(Graphics2D g2d, BufferedImage waterImg, int x, int y, float alpha) {
        int waterImgWidth = waterImg.getWidth();
        int waterImgHeight = waterImg.getHeight();
        // 在图形和图像中实现混合和透明效果
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
        g2d.drawImage(waterImg, x, y, waterImgWidth, waterImgHeight, null);
    }

    /**
     * 图片切圆角并描边
     *
     * @param srcImage 目标图像
     * @param radius   圆角半径
     * @return BufferedImage
     */
    public static BufferedImage setClip(BufferedImage srcImage, int radius, int border, int padding) {
        int width = srcImage.getWidth();
        int height = srcImage.getHeight();
        int canvasWidth = width + padding;
        int canvasHeight = height + padding;
        BufferedImage outputImage = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = outputImage.createGraphics();
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(0, 0, canvasWidth, canvasHeight, radius, radius));
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(srcImage, padding, padding, null);
        if (border != 0) {
            g2.setColor(new Color(252, 242, 243));
//            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(border));
            g2.drawRoundRect(0, 0, canvasWidth - padding, canvasHeight - padding, radius, radius);
        }
        g2.dispose();
        return outputImage;
    }

    /**
     * 图片锐化、钝化、边缘检测等
     *
     * @param originalPic 目标图片
     * @return
     */
    public static BufferedImage getSharperPicture(BufferedImage originalPic) {
        int imageWidth = originalPic.getWidth();
        int imageHeight = originalPic.getHeight();
        BufferedImage newPic = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_3BYTE_BGR);
        // 钝化
//        float[] data = {0.0625f, 0.125f, 0.0625f, 0.125f, 0.125f, 0.125f, 0.0625f, 0.125f, 0.0625f};
        // 锐化
        float[] data = {-0.05f, -0.05f, -0.05f, -0.05f, 1.2f, 0.0f, 0.1f, -0.05f, -0.05f};
        // 边缘检测
//        float[] data = {0.0f, -1.0f, 0.0f, -1.0f, 4.0f, -1.0f, 0.0f, -1.0f, 0.0f};
        Kernel kernel = new Kernel(3, 3, data);
        ConvolveOp co = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        co.filter(originalPic, newPic);
        return newPic;
    }

    /**
     * 调整图片宽高
     *
     * @param originalImage 图片文件
     * @param width         期望的宽
     * @param height        期望的高
     * @return BufferedImage
     * @throws IOException
     */
    public static BufferedImage formatImageSize(BufferedImage originalImage, int width, int height) throws IOException {
        int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }

    /**
     * 调整图片宽高（使用默认大小）
     *
     * @param originalImage 文件
     * @return BufferedImage
     * @throws IOException
     */
    public static BufferedImage formatImageSize(BufferedImage originalImage) throws IOException {
        return formatImageSize(originalImage, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }


    /**
     * 打印图片大小
     *
     * @param images 图片文件
     * @throws IOException
     */
    public static void printSize(File... images) throws IOException {
        for (File image : images) {
            BufferedImage read = ImageIO.read(image);
            System.out.println("image name: " + image.getName() + ", width: " + read.getWidth() + ", height: " + read.getHeight());
        }
    }

    /**
     * 坐标枚举
     */
    public enum Coordinate {
        // 左上角位置
        ONE(0, 93, 300),
        // 右上角位置
        TWO(1, 363, 300),
        // 左下角位置
        THREE(2, 93, 570),
        // 右下角位置
        FOUR(3, 363, 570),
        // 默认
        DEFAULT(4, 0, 0);

        int position;
        int x;
        int y;

        Coordinate(int position, int x, int y) {
            this.position = position;
            this.x = x;
            this.y = y;
        }

        public int getPosition() {
            return position;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        /**
         * 根据序号返回位置
         *
         * @param position 位置序号
         * @return
         */
        public static Coordinate of(int position) {
            for (Coordinate coordinate : Coordinate.values()) {
                if (position == coordinate.getPosition()) {
                    return coordinate;
                }
            }
            return DEFAULT;
        }
    }

    public static void main(String[] args) throws IOException {
        String product = "C:\\Users\\Xigua\\Pictures\\每日一淘\\productImage.png";
        String productPreview = "C:\\Users\\Xigua\\Pictures\\每日一淘\\productPreview1.png";
        String fishUrl = "http://image-1257128394.picbj.myqcloud.com/0/20180911153132905.jpg";
        // 生成秒杀分享图片
//        BufferedImage backed = ImageIO.read(new URL(DEFAULT_BACKED_IMAGE));
        java.util.List<String> images = new ArrayList<>();
        images.add(productPreview);
        images.add(productPreview);
        BufferedImage shareImage = generateSeckillImage("9:30",
                ImageIO.read(new File("C:\\Users\\Xigua\\Pictures\\每日一淘\\qrCode.png")), images);
//        ImageIO.write(formatImageSize(shareImage, 375, 553), "png", new File("C:\\Users\\Xigua\\Pictures\\每日一淘\\sharedPreview.png"));
        ImageIO.write(shareImage, "png", new File("C:\\Users\\Xigua\\Pictures\\每日一淘\\sharedPreview.png"));
        // 生成产品图
//        BufferedImage bufferedImage = setClip(ImageIO.read(new File(product)), 25, 1, 1);
//        Graphics2D graphics = bufferedImage.createGraphics();
//        drawWater(graphics, ImageIO.read(new URL(SECKILLING_TAG_IMAGE)), 0, 0, 1L);
//        ImageIO.write(bufferedImage, "png", new File("C:\\Users\\Xigua\\Pictures\\每日一淘\\productPreview1.png"));
    }
}
