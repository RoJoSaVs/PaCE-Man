import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import static java.lang.Thread.sleep;

/**
 * Clase PAcBoard
 * Se encarga de todas las funcionalidades del juegos
 */
public class PacBoard extends Client  {


    Timer redrawTimer;
    ActionListener redrawAL;

    int[][] map;
    Image[] mapSegments;

    Image foodImage;
    Image[] pfoodImage;

    Image goImage;
    Image vicImage;

    Pacman pacman;

    ArrayList<Food> foods;
    ArrayList<PowerUpFood> pufoods;
    ArrayList<Ghost> ghosts;
    ArrayList<TeleportTunnel> teleports;

    boolean isCustom = false;
    boolean isGameOver = false;
    boolean isWin = false;
    boolean drawScore = false;
    boolean clearScore = false;
    int scoreToAdd = 0;

    boolean drawVida = false;
    boolean clearVida = false;
    int vidaToAdd = 3;

    int score;
    JLabel scoreboard;

    int vidas;
    JLabel vidasboard;

    LoopPlayer siren;
    boolean mustReactivateSiren = false;
    LoopPlayer pac6;

    public Point ghostBase;
    int vids = 3;
    public int m_x;
    public int m_y;

    boolean colisionfantasma = false;
    int colisionfantasm = 1;
    boolean colisionfruta = false;
    int colisionfrut = 2;
    int n ;
    int m ;

    boolean powerup = false;
    int powerupp = 3;
    Timer Mover;

    MapData md_backup;
    PacWindow windowParent;


    public PacBoard(JLabel scoreboard, JLabel vidasboard,MapData md,PacWindow pw){
        this.scoreboard = scoreboard;
        this.vidasboard = vidasboard;
        this.setDoubleBuffered(true);
        md_backup = md;
        windowParent = pw;

        m_x = md.getX();
        m_y = md.getY();
        this.map = md.getMap();


        //Fantasmas
        this.isCustom = md.isCustom();
        this.ghostBase = md.getGhostBasePosition();

        //loadMap();

        pacman = new Pacman(md.getPacmanPosition().x,md.getPacmanPosition().y,this);


        foods = new ArrayList<>();
        pufoods = new ArrayList<>();
        ghosts = new ArrayList<>();
        teleports = new ArrayList<>();

        //TODO : read food from mapData (Map 1)

        if(!isCustom) {
            for (int i = 0; i < m_x; i++) {
                for (int j = 0; j < m_y; j++) {
                    if (map[i][j] == 0)
                        foods.add(new Food(i, j));
                }
            }
        }else{
            foods = md.getFoodPositions();
        }



        pufoods = md.getPufoodPositions();


        teleports = md.getTeleports();

        setLayout(null);
        setSize(20*m_x,20*m_y);
        setBackground(Color.black);

        mapSegments = new Image[28];
        mapSegments[0] = null;
        for(int ms=1;ms<28;ms++){
            try {
                mapSegments[ms] = ImageIO.read(this.getClass().getResource("resources/images/map segments/"+ms+".png"));
            }catch(Exception e){}
        }

        pfoodImage = new Image[5];

        try{
            foodImage = ImageIO.read(this.getClass().getResource("resources/images/food.png"));
            goImage = ImageIO.read(this.getClass().getResource("resources/images/gameover.png"));
            vicImage = ImageIO.read(this.getClass().getResource("resources/images/victory.png"));
            //pfoodImage = ImageIO.read(this.getClass().getResource("/images/pfood.png"));
        }catch(Exception e){}


        redrawAL = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //Dibuja
                repaint();
            }
        };
        redrawTimer = new Timer(16,redrawAL);
        redrawTimer .start();

        //Sonidos
        siren = new LoopPlayer("siren.wav");
        pac6 = new LoopPlayer("pac6.wav");
        siren.start();
    }

    /**
     * metodo CrearFantasma
     * @return El fantasma que se seleciona para crear
     * @param n
     * @param m
     */
    public void CrearFantasma( int n, int m){

        //Fantasmas
        for(GhostData gd : md_backup.getGhostsData()){
            if (n == 1){
                switch(gd.getType()) {
                    case RED:
                        ghosts.add(new RedGhost(gd.getX(), gd.getY(), this, m));

                        break;}

                if (n == 2){
                    switch(gd.getType()) {
                        case PINK:
                            ghosts.add(new PinkGhost(gd.getX(), gd.getY(), this, m));
                            break;}}

                if(n == 3){
                    switch(gd.getType()) {
                        case CYAN:
                            ghosts.add(new CyanGhost(gd.getX(), gd.getY(), this, m));
                            break;}}
                if(n == 4){
                    switch(gd.getType()) {
                        case CYAN:
                            ghosts.add(new Orangeghost(gd.getX(), gd.getY(), this, m));
                            break;}}

            }
        } n = 0;

    }

    /**
     * metodo CrearPowerUP
     * @return genera los powerups cuando es llamado
     *
     */
    public void CrearPowerUp(){
        for(int ms=0 ;ms<2;ms++){
            try {
                pfoodImage[ms] = ImageIO.read(this.getClass().getResource("resources/images/food/0.png"));
            }catch(Exception e){}
        }
    }

    /**
     * metodo CrearFruit
     * @return genera las frutas cuando es llamado
     *
     */
    public void CrearFruit(){
        for(int ms=1 ;ms<5;ms++){
            try {
                pfoodImage[ms] = ImageIO.read(this.getClass().getResource("resources/images/food/"+ms+".png"));
            }catch(Exception e){}
        }
    }

    /**
     * metodo collisionTest
     * @return al llamarlo verifica si exite una collion y si se puede comer al fantasma dependiendo del modo en que se encuentre
     * @return verifica si el jugador le quedan vidas depues de la colision
     */
    private void collisionTest(){
        //pacman = new Pacman(md.getPacmanPosition().x,md.getPacmanPosition().y,this);
        Rectangle pr = new Rectangle(pacman.pixelPosition.x+13,pacman.pixelPosition.y+13,2,2);
        Ghost ghostToRemove = null;
        for(Ghost g : ghosts){
            Rectangle gr = new Rectangle(g.pixelPosition.x,g.pixelPosition.y,28,28);

            if(pr.intersects(gr)){
                if(!g.isDead()) {
                    if (!g.isWeak()) {
                        if (vids !=0) {

                            pacman.logicalPosition = new Point(1, 3);
                            pacman.pixelPosition = new Point( 28, 84);


                            drawVida = true;
                            vidaToAdd--;
                            vidas--;
                            vids--;
                            vidasboard.setText(" perdiste una vida te quedan:" +vids);

                        }
                        else{
                       // PErdiste
                        siren.stop();
                        SoundPlayer.play("pacman_lose.wav");
                        pacman.moveTimer.stop();
                        pacman.animTimer.stop();
                        g.moveTimer.stop();
                        isGameOver = true;
                        scoreboard.setText("    Press R to try again !");
                        scoreboard.setForeground(Color.red);
                        break;}


                    } else {
                        //Come un fantasma
                        SoundPlayer.play("pacman_eatghost.wav");
                        //getGraphics().setFont(new Font("Arial",Font.BOLD,20));
                        drawScore = true;
                        scoreToAdd++;
                        colisionfantasma = true;
                        if(ghostBase!=null)
                            g.die();

                        else
                            ghostToRemove = g;

                    }
                }
            }
        }
        colisionfantasma = false;

    }

    /**
     * metodo update
     * @return un puntaje al verificar si el jugador se comio un puntol o una fruta
     * @return si el jugador se come un Powerup le brinda la funcion de comerse a los fantasmas por un cierto tiempo
     */
    private void update(){

        Food foodToEat = null;
        //Check food eat
        for(Food f : foods){
            if(pacman.logicalPosition.x == f.position.x && pacman.logicalPosition.y == f.position.y)
                foodToEat = f;
        }
        if(foodToEat!=null) {
            SoundPlayer.play("pacman_eat.wav");
            foods.remove(foodToEat);
            score ++;
            scoreboard.setText("    Score : "+score);
            colisionfruta = true;


            //aqui suma el socre

            if(foods.size() == 0){
                siren.stop();
                pac6.stop();
                SoundPlayer.play("pacman_intermission.wav");
                isWin = true;
                pacman.moveTimer.stop();
                for(Ghost g : ghosts){
                    g.moveTimer.stop();
                }
            }
        }

        PowerUpFood puFoodToEat = null;
        //Check pu food eat
        for(PowerUpFood puf : pufoods){
            if(pacman.logicalPosition.x == puf.position.x && pacman.logicalPosition.y == puf.position.y)
                puFoodToEat = puf;
        }
        if(puFoodToEat!=null) {
            //Sonido
            switch(puFoodToEat.type) {
                case 0:
                    //PACMAN 6
                    pufoods.remove(puFoodToEat);
                    siren.stop();
                    mustReactivateSiren = true;
                    pac6.start();
                    for (Ghost g : ghosts) {
                        g.weaken();
                    }
                    scoreToAdd = 0;
                    break;
                default:
                    SoundPlayer.play("pacman_eatfruit.wav");
                    pufoods.remove(puFoodToEat);
                    scoreToAdd = 1;
                    drawScore = true;
            }

        }

        //Fantasma muere
        for(Ghost g:ghosts){
            if(g.isDead() && g.logicalPosition.x == ghostBase.x && g.logicalPosition.y == ghostBase.y){
                g.undie();
            }
        }

        //revisa
        for(TeleportTunnel tp : teleports) {
            if (pacman.logicalPosition.x == tp.getFrom().x && pacman.logicalPosition.y == tp.getFrom().y && pacman.activeMove == tp.getReqMove()) {
                //System.out.println("TELE");
                pacman.logicalPosition = tp.getTo();
                pacman.pixelPosition.x = pacman.logicalPosition.x * 28;
                pacman.pixelPosition.y = pacman.logicalPosition.y * 28;
            }
        }

        //Check isSiren
        boolean isSiren = true;
        for(Ghost g:ghosts){
            if(g.isWeak()){
                isSiren = false;
            }
        }
        if(isSiren){
            pac6.stop();
            if(mustReactivateSiren){
                mustReactivateSiren = false;
                siren.start();
            }

        }



    }


    /**
     * metodo PaintComponent
     * @return se encarga de graficar todos los componentes
     * @param g
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        // dibuja Vidas
        if(drawVida) {
            g.setFont(new Font("Arial",Font.LAYOUT_RIGHT_TO_LEFT,15));
            g.setColor(Color.red);
            Integer s = vidaToAdd-1;
            g.drawString(s.toString(), 50, 50);
            vidasboard.setText("    vidas : "+vidaToAdd);
            clearVida = true;

        }
        if(clearVida){
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            drawVida = false;
            clearVida =false;
        }
        //Draw Walls
        g.setColor(Color.blue);
        for(int i=0;i<m_x;i++){
            for(int j=0;j<m_y;j++){
                if(map[i][j]>0){
                    //g.drawImage(10+i*28,10+j*28,28,28);
                    g.drawImage(mapSegments[map[i][j]],10+i*28,10+j*28,null);
                }
            }
        }

        //Draw Food
        g.setColor(new Color(204, 122, 122));
        for(Food f : foods){
            //g.fillOval(f.position.x*28+22,f.position.y*28+22,4,4);
            g.drawImage(foodImage,10+f.position.x*28,10+f.position.y*28,null);
        }

        //Draw PowerUpFoods
        g.setColor(new Color(204, 174, 168));
        for(PowerUpFood f : pufoods){
            //g.fillOval(f.position.x*28+20,f.position.y*28+20,8,8);
            g.drawImage(pfoodImage[f.type],10+f.position.x*28,10+f.position.y*28,null);
        }

        //Draw Pacman
        switch(pacman.activeMove){
            case NONE:
            case RIGHT:
                g.drawImage(pacman.getPacmanImage(),10+pacman.pixelPosition.x,10+pacman.pixelPosition.y,null);
                break;
            case LEFT:
                g.drawImage(ImageHelper.flipHor(pacman.getPacmanImage()),10+pacman.pixelPosition.x,10+pacman.pixelPosition.y,null);
                break;
            case DOWN:
                g.drawImage(ImageHelper.rotate90(pacman.getPacmanImage()),10+pacman.pixelPosition.x,10+pacman.pixelPosition.y,null);
                break;
            case UP:
                g.drawImage(ImageHelper.flipVer(ImageHelper.rotate90(pacman.getPacmanImage())),10+pacman.pixelPosition.x,10+pacman.pixelPosition.y,null);
                break;
        }

        //Draw Ghosts
        for(Ghost gh : ghosts){
            g.drawImage(gh.getGhostImage(),10+gh.pixelPosition.x,10+gh.pixelPosition.y,null);
        }

        if(clearScore){
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            drawScore = false;
            clearScore =false;
        }

        if(drawScore) {
            //System.out.println("must draw score !");
            g.setFont(new Font("Arial",Font.BOLD,15));
            g.setColor(Color.yellow);
            Integer s = scoreToAdd*100;
            g.drawString(s.toString(), pacman.pixelPosition.x + 13, pacman.pixelPosition.y + 50);
            //drawScore = false;
            score += s;
            scoreboard.setText("    Score : "+score);
            clearScore = true;

        }



        if(isGameOver){
            g.drawImage(goImage,this.getSize().width/2-315,this.getSize().height/2-75,null);
        }

        if(isWin){
            g.drawImage(vicImage,this.getSize().width/2-315,this.getSize().height/2-75,null);
        }


    }

    /**
     * metodo ProcessEvent
     * @return se encagar de verifiar que sucede en cada envento
     * @param ae
     */
    @Override
    public void processEvent(AWTEvent ae){

        if(ae.getID()==Messeges.UPDATE) {
            update();

            String str1 = Integer.toString(score);
            Client client=new Client();
            String Servermessage = "1234567890123456789";
            try {
                Servermessage = client.send_to_server("str1");
            } catch (Exception e) {
                e.printStackTrace();
            }
            // client=null;

            System.out.println("MensajedelServer:"+Servermessage);
            String Valores_Server = Servermessage.substring(0,5);
            System.out.println("Valores_Server:" + Valores_Server +"\n");

            String Fantasma = Valores_Server.substring(0,1);
            System.out.println("Fantasma:" + Fantasma +"\n");

            String Velocidad = Valores_Server.substring(2,4);
            System.out.println("Velocidad:" + Velocidad +"\n");
            int velo = Integer.parseInt(Velocidad);

            if(!Servermessage.equals("1234567890123456789")){
                if(Fantasma.equals("B")){
                    CrearFantasma(1,velo);
                }else if(Fantasma.equals("P")){
                    CrearFantasma(2,velo);
                }else if(Fantasma.equals("C")){
                    CrearFantasma(3,velo);
                }else if(Fantasma.equals("I")){
                    CrearFantasma(4,velo);
                }
                else if(Fantasma.equals("P")){
                    CrearPowerUp();
                }
                else if(Fantasma.equals("F")){
                    CrearFruit();
                }
            }

        } else if(ae.getID()==Messeges.COLTEST) {
            if (!isGameOver) {
                collisionTest();


            }
        }else if(ae.getID()==Messeges.RESET){
            if(isGameOver)
                restart();
        }else {
            super.processEvent(ae);

        }
    }
    
    public void restart(){

        siren.stop();

        new PacWindow();
        windowParent.dispose();


    }




}
