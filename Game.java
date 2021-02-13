package Example;

import processing.core.PApplet;
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.RIGHT;

/**
 *
 *
 * @author YUSUFNAS
 */
public class Game extends PApplet {

    public static void main(String[] args) {
        PApplet.main(new String[]{"Example.Game"});
    }

    @Override
    public void settings() {
        size(1000, 600);

    }

    int size = 20;
    int colume = 50;
    int row = 30;
    int x, y;
    int wayi, wayj;
    int ax, ay;
    int rndi, rndj;
    int[][] map = new int[colume][row];
    int[][] agent = new int[height][width];
    int[][] enemy = new int[height][width];
    int[][] wall = new int[height][width];
    boolean tik = true;

    @Override
    public void setup() {
        frameRate(10);
        for (int i = 0; i < colume; i++) {
            for (int j = 0; j < row; j++) {

                fill(0);
                rect(i * size, j * size, size, size);
                stroke(100);

            }
        }

    }

    @Override
    public void draw() {

        if (keyPressed && key == ' ') {
            temizle();
        } else if (keyPressed && key == 'a') {
            agentgoster();
        } else if (keyPressed && key == 'e') {
            enemygoster();
        } else if (keyPressed && key == 'w') {
            wallgoster();
        } else if (keyPressed && key == 'm') {
            mapgoster();
        }

        yakala();//arama algoritması
        enemyhareket();
        drawmap();
    }

    void temizle() {

        for (int i = 0; i < colume; i++) {
            for (int j = 0; j < row; j++) {
                fill(0);
                rect(i, j, size, size);
                stroke(80);
                agent[i][j] = 0;
                enemy[i][j] = 0;
                wall[i][j] = 0;
                map[i][j] = 0;
            }
        }

    }//end temizle

    private void drawmap() {
        for (int i = 0; i < colume; i++) {
            for (int j = 0; j < row; j++) {
                x = i * size;
                y = j * size;
                if (mouseButton == LEFT && mouseX > x && mouseX < (x + size) && mouseY > y && mouseY < (y + size)) {
                    fill(50, 205, 50);//yesil renk
                    rect(x, y, size, size);
                    agent[i][j] = 1;
                    map[i][j] = 1;
                    ax = i;
                    ay = j;
                } else if (mouseButton == RIGHT && mouseX > x && mouseX < (x + size) && mouseY > y && mouseY < (y + size)) {
                    fill(255, 0, 0); //kırmızı renk
                    rect(x, y, size, size);
                    enemy[i][j] = 2;
                    map[i][j] = 2;

                } else if (mouseButton == CENTER && mouseX > x && mouseX < (x + size) && mouseY > y && mouseY < (y + size)) {
                    fill(0, 0, 255); //mavi renk
                    rect(x, y, size, size);
                    wall[i][j] = 3;
                    map[i][j] = 3;
                }
            }
        }
    }//end drawmap

    private void agentgoster() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < colume; j++) {
                System.out.print(agent[j][i] + " ");

            }
            System.out.println("");
        }
        System.out.println("");
    }//end agentgoster

    private void enemygoster() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < colume; j++) {
                System.out.print(enemy[i][j] + " ");

            }
            System.out.println("");
        }
        System.out.println("");
    }//end enemygoster

    private void wallgoster() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < colume; j++) {
                System.out.print(wall[j][i] + " ");

            }
            System.out.println("");
        }
        System.out.println("");
    }//end wallgoster

    private void mapgoster() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < colume; j++) {
                System.out.print(map[j][i] + " ");

            }
            System.out.println("");
        }
        System.out.println("");

    }//end mapgoster

    private void yakala() {
        if (tik) {    //baslangıc konumu
            map[0][2] = 2;
            tik = false;
        }
        map[ax][ay] = 0;

        for (int i = 0; i < colume; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 2) {
                    if (colume > sqrt((ax - i) * (ax - i) + (ay - j) * (ay - j))) {
                        wayi = i;
                        wayj = j;
                    }
                }
            }
        }

        if (wayi > ax) {
            ax += 1;
            if (map[ax][ay] == 3) {
                ax -= 1;
            }
        } else if (wayi < ax) {
            ax -= 1;
            if (map[ax][ay] == 3) {
                ax += 1;
            }
        }
        if (wayj > ay) {
            ay += 1;
            if (map[ax][ay] == 3) {
                ay -= 1;
            }
        } else if (wayj < ay) {
            ay -= 1;
            if (map[ax][ay] == 3) {
                ay += 1;
            }
        }

        map[ax][ay] = 1;
        for (int i = 0; i < colume; i++) {
            for (int j = 0; j < row; j++) {
                x = i * size;
                y = j * size;
//                stroke(250);
                if (map[i][j] == 1) {
                    fill(0, 255, 50);
                } else if (map[i][j] == 2) {

                    fill(255, 0, 0);
                } else if (map[i][j] == 3) {
                    fill(0, 0, 255);
                } else {
                    fill(0);
                }

                rect(x, y, size, size);
            }
        }

    }//end yakala

    void enemyhareket() {

        for (int i = 0; i < colume; i++) {
            for (int j = 0; j < row; j++) {
                map[i][row - 1] = 3; //Bottom
                wall[i][row - 1] = 3;
                map[i][0] = 3; //Top boundary
                wall[i][0] = 3;
                map[0][j] = 3; //Left 
                wall[0][j] = 3;
                map[colume - 1][j] = 3; //Right 
                wall[colume - 1][j] = 3;
                if (map[i][j] == 2) {
                    if (i == 0 || j == 0 || i == colume - 1 || j == row - 1) {
                        if (i == 0) {
                            rndi = (int) random(0, 2);
                        }
                        if (j == 0) {
                            rndj = (int) random(0, 2);
                        }
                        if (i == colume - 1) {
                            rndi = (int) random(-1, 1);
                        }
                        if (j == row - 1) {
                            rndj = (int) random(-1, 1);
                        }
//                        }
                    } else {
                        rndi = (int) random(-2, 2);
                        rndj = (int) random(-2, 2);
                    }
                    if (map[i + rndi][j + rndj] != 3 && map[i + rndi][j + rndj] != 2) {
                        map[i][j] = 0;
                        map[i + rndi][j + rndj] = 2;
                    }
                }
            }
        }

    }//end enemyhareket

}//end class
