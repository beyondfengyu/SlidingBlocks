package cn.fy.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * @author 锋宇
 * @contact QQ群399643539
 * @website http://www.wolfbe.com
 * @copyright 版权归朗度云所有
 */
public class Window extends JFrame implements ActionListener, KeyListener {
    /**
     * 所有形态的所有方块都可以看做是由4*4=16个方格的区域块组成，称为方块区。
     * 按顺序给这16个方格做标识，0~15,用长度0为16的一维数组来存储方块区，
     * 数组中为1的元素表示方块区的这个方格有颜色覆盖，为0则表示空白方格
     */
    private final int block[][][] = new int[][][]{
            // I
            {{0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0},
                    {0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0}},
            // S
            {{0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                    {0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0}},
            // Z
            {{1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                    {1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0}},
            // J
            {{0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0},
                    {1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                    {1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}},
            // O
            {{1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}},
            // L
            {{1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0},
                    {1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                    {0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}},
            // T
            {{0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                    {1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0}}};
    private final int[][] map = new int[13][23]; //记录地图所有方格的类型信息
    private final int[][] cmap = new int[13][23];//记录地图所有方格的颜色信息
    private final int SPEED = 800;  //方块下落的速度
    private final int SIDE = 15;    //每一格的像素
    private final int MWIDTH = 180; //地图宽的像素
    private final int MHEIGHT = 330;//地图高的像素
    private final Color CTYPE[] = {Color.pink, Color.orange, Color.green, Color.gray, new Color(10, 14, 41)};
    private Timer timer;
    /**
     * 地图面板的方块
     */
    private int bType; //标识方块的形状，共有7种
    private int bState;//标识方块的形态，每种形状的方块有4种形态
    private int bColor;//标识方块的颜色，可选的颜色有3种
    private int curX;  //当前方块横坐标，以深蓝色区域为标准
    private int curY;  //当前方块纵坐标，以深蓝色区域为标准
    /**
     * 提示面板的方块
     */
    private int nbType; //标识方块的形状，共有7种
    private int nbState;//标识方块的形态，每种形状的方块有4种形态
    private int nbColor;//标识方块的颜色，可选的颜色有3种

    private int score;//分数
    private int level;//等级
    private boolean isPlay = false;

    private Board board;
    private NextBoard nextb;
    private JLabel lscore;
    private JLabel llevel;
    private JButton bstart;
    private JButton bstop;

    public Window() {
        board = new Board();
        nextb = new NextBoard();
        bstart = new JButton("开始");
        bstop = new JButton("停止ֹ");
        lscore = new JLabel("分数：" + score);
        llevel = new JLabel("等级：" + level);
        this.addKeyListener(this);
        timer = new Timer(SPEED, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isCan(curX, curY + 1)) {
                    //下移一格
                    curY++;
                } else {
                    saveBlockToStore(curX, curY);
                    delline();
                    createBlock();
                }
                //重绘地图面板
                board.repaint();
            }
        });
    }

    public void paintComponent(Graphics g) {
        super.paintComponents(g);
    }

    /**
     * 捕获按钮的事件
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton o = (JButton) e.getSource();
        if (o == bstart) {
            if (isPlay) {
                if (o.getText().equals("开始")) {
                    o.setText("暂停");
                    timer.start();
                } else {
                    o.setText("开始");
                    timer.stop();
                }
            } else {
                //产生下一个方块的信息
                nbType = (int) ((Math.random() * 1000) % 7);
                nbState = (int) ((Math.random() * 1000) % 4);
                nbColor = (int) ((Math.random() * 1000) % 3);
                isPlay = true;
                score = 0;
                level = 0;
                o.setText("暂停");
                initMapData();
                createBlock();
                timer.start();
                this.repaint();
            }
        }
        if (o == bstop) {
            isPlay = false;
            timer.stop();
            bstart.setText("开始");
        }
        bstart.setFocusable(false);//使开始按钮失去焦点
        bstop.setFocusable(false); //使停止按钮失去焦点
        board.setFocusable(true);  //使地图面板获得焦点
    }

    /**
     * 捕获方向键事件
     *
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (bstart.getText().equals("暂停")) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_DOWN: {
                    if (isCan(curX, curY + 1)) {
                        curY++;
                    } else {
                        saveBlockToStore(curX, curY);
                        delline();
                        createBlock();
                    }
                    break;
                }
                case KeyEvent.VK_RIGHT: {
                    if (isCan(curX + 1, curY)) {
                        curX++;
                    }
                    break;
                }
                case KeyEvent.VK_LEFT: {
                    if (isCan(curX - 1, curY)) {
                        curX--;
                    }
                    break;
                }
                //顺时针变换方块的形态
                case KeyEvent.VK_UP: {
                    int state = bState;
                    bState = (bState + 1) % 4;
                    if (!isCan(curX, curY)) {
                        bState = state;
                    }
                    break;
                }
            }
        }
        board.repaint();
    }

    public void initView() {
        this.setLayout(null);
        //地图面板组件
        board.setPreferredSize(new Dimension(MWIDTH, MHEIGHT));
        board.setBounds(0, 0, MWIDTH, MHEIGHT);
        //提示面板组件
        nextb.setPreferredSize(new Dimension(60, 60));
        nextb.setBounds(200, 20, 60, 60);
        //按钮组件
        bstart.setBounds(200, MHEIGHT - 60, 80, 20);
        bstop.setBounds(200, MHEIGHT - 30, 80, 20);
        //文本组件
        lscore.setBounds(200, MHEIGHT - 200, 80, 20);
        llevel.setBounds(200, MHEIGHT - 165, 80, 20);
        //添加监听器
        bstart.addActionListener(this);
        bstop.addActionListener(this);
        board.addKeyListener(this);
        board.setFocusable(true);
        this.add(nextb);
        this.add(bstart);
        this.add(bstop);
        this.add(lscore);
        this.add(llevel);
        this.add(board);
        //初始化地图数据
        initMapData();
    }

    /**
     * 游戏开始前初始化地图的数据，
     * 地图的各种方格用数组来表示
     */
    private void initMapData() {
        int i, j;
        //地图所有方格类型为0
        //渲染地图所有方格成深蓝色,4
        for (i = 0; i < 12; i++) {
            for (j = 0; j < 22; j++) {
                map[i][j] = 0;
                cmap[i][j] = 4;
            }
        }
        //地图下边框方格类型为2
        //渲染地图上下边框方格成灰色,3
        for (i = 0; i < 12; i++) {
            map[i][21] = 2;
            cmap[i][0] = 3;
            cmap[i][21] = 3;
        }
        //地图左右边框方格类型为2
        //渲染地图左右边框方格成灰色,3
        for (i = 0; i < 22; i++) {
            map[0][i] = 2;
            map[11][i] = 2;
            cmap[0][i] = 4;
            cmap[11][i] = 4;
        }
    }

    /**
     * 产生下一个方块
     */
    private void createBlock() {
        if (!isOver(4, 0)) {
            bType = nbType;
            bState = nbState;
            bColor = nbColor;
            //产生下一方块的信息
            nbType = (int) ((Math.random() * 1000) % 7);
            nbState = (int) ((Math.random() * 1000) % 4);
            nbColor = (int) ((Math.random() * 1000) % 3);
            curX = 4;//初始化X坐标
            curY = 0;//初始化Y坐标
            nextb.repaint();
        } else {
            timer.stop();
            isPlay = false;
            JOptionPane.showMessageDialog(null, "GAME OVER");
        }
    }

    /**
     * 判断方块区是否可以移动，
     * 若覆盖堆积区的方块，则不可以移动，
     * 若覆盖边框区的方块，则不可以移动，
     *
     * @param x 移动后X坐标
     * @param y 移动后Y坐标
     * @return
     */
    private boolean isCan(int x, int y) {
        for (int a = 0; a < 4; a++) {
            for (int b = 0; b < 4; b++) {
                if ((block[bType][bState][a * 4 + b] == 1) && (map[x + b + 1][y + a] == 1
                        || map[x + b + 1][y + a] == 2)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 保存方块区到堆积区，
     * 需要判断
     *
     * @param x 方块当前X坐标
     * @param y 方块当前Y坐标
     */
    private void saveBlockToStore(int x, int y) {
        int j = 0;
        for (int a = 0; a < 4; a++) {
            for (int b = 0; b < 4; b++) {
                if (map[x + b + 1][y + a] == 0) {
                    map[x + b + 1][y + a] = block[bType][bState][j];
                    cmap[x + b + 1][y + a] = bColor;
                }
                j++;
            }
        }
    }

    /**
     * 消行，增加分数，升级
     */
    private void delline() {
        int c = 0;
        for (int b = 0; b < 22; b++) {
            for (int a = 0; a < 12; a++) {
                if (map[a][b] == 1) {
                    c = c + 1;
                }
            }
            //一行堆满10个方格可以得分
            if (c == 10) {
                score += 10;
                level = score / 100 + 1;
                timer.setDelay(SPEED - level * 20);
                lscore.setText("分数：" + score);
                llevel.setText("等级：" + level);
                //执行消行，更新地图数据
                for (int d = b; d > 0; d--) {
                    for (int e = 1; e < 11; e++) {
                        map[e][d] = map[e][d - 1];
                        cmap[e][d] = cmap[e][d - 1];
                    }
                }
            }
            c = 0;
        }
    }

    /**
     * 判断游戏是否结束，判断刚产生的方块是否可以移动，
     * 不能移动，游戏应该结束；
     * 若能移动，游戏没有结束；
     * @param x
     * @param y
     * @return
     */
    private boolean isOver(int x, int y) {
        if (isCan(x, y)) {
            return false;
        }
        return true;
    }

    class Board extends JPanel {

        public void paint(Graphics g) {
            //绘制当前地图的元素
            for (int i = 0; i < 12; i++) {
                for (int j = 0; j < 22; j++) {
                    switch(map[i][j]){
                        case 0: //活动区域，深蓝色
                            g.setColor(CTYPE[4]);
                            g.fill3DRect(i * SIDE, j * SIDE, SIDE, SIDE, true);
                            break;
                        case 1: //堆积区，方块颜色
                            g.setColor(CTYPE[cmap[i][j]]);
                            g.fill3DRect(i * SIDE, j * SIDE, SIDE, SIDE, true);
                            g.setColor(CTYPE[4]);
                            break;
                        case 2: //边框区，灰色
                            g.setColor(CTYPE[3]);
                            g.fill3DRect(i * SIDE, j * SIDE, SIDE, SIDE, true);
                            break;
                    }
                }
            }
            if (isPlay) {
                //绘制当前活动的方块
                g.setColor(CTYPE[bColor]);
                for (int i = 0; i < 16; i++)
                    if (block[bType][bState][i] == 1)
                        g.fill3DRect((i % 4 + curX + 1) * SIDE, (curY + i / 4) * SIDE, SIDE, SIDE, true);
            }
            //边框区的顶部区域
            g.setColor(CTYPE[3]);
            for (int i = 1; i < 12; i++){
                g.fill3DRect(i * SIDE, 0, SIDE, SIDE, true);
            }
        }

    }

    class NextBoard extends JPanel {
        @Override
        public void paint(Graphics g) {
            for (int i = 0; i < 16; i++) {
                if (block[nbType][nbState][i] == 1 && isPlay) {
                    //渲染提示面板下一方块的方格
                    g.setColor(CTYPE[nbColor]);
                    g.fill3DRect((i % 4) * SIDE, (i / 4) * SIDE, SIDE, SIDE, true);
                } else {
                    //渲染提示面板背景方格
                    g.setColor(CTYPE[3]);
                    g.fill3DRect((i % 4) * SIDE, (i / 4) * SIDE, SIDE, SIDE, true);
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }
}
