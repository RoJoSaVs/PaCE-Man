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

   // public void dato (int m ,int n){
     //   this.m = m;
       // this.n = n;

    //}

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
    /*
      // System.out.println("n1" +n );
        for(GhostData gd : md.getGhostsData()){
            if (n == 1){
            switch(gd.getType()) {
                case RED:
                    ghosts.add(new RedGhost(gd.getX(), gd.getY(), this, 1));
                    break;}

            if (n == 2){
            switch(gd.getType()) {
                case PINK:
                    ghosts.add(new PinkGhost(gd.getX(), gd.getY(), this));
                    break;}}

            if(n == 3){
            switch(gd.getType()) {
                case CYAN:
                    ghosts.add(new CyanGhost(gd.getX(), gd.getY(), this));
                    break;}}

            }
        }
/*
        int mx = input.indexOf('\n');
        int my = StringHelper.countLines(input);
        MapData customMap = new MapData(mx,my);
        customMap.setCustom(true);
        int i=0;
        int j=0;
        for(char c : input.toCharArray()){
            if(c == '1'){
                map[i][j] = 0;
                customMap.getGhostsData().add(new GhostData(i,j,ghostType.RED));
            }
            if(c == '2'){
                map[i][j] = 0;
                customMap.getGhostsData().add(new GhostData(i,j,ghostType.PINK));
            }
            if(c == '3'){
                map[i][j] = 0;
                customMap.getGhostsData().add(new GhostData(i,j,ghostType.CYAN));
            }}*/

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
        for(int ms=1 ;ms<5;ms++){
            try {
                pfoodImage[ms] = ImageIO.read(this.getClass().getResource("resources/images/food/"+ms+".png"));
            }catch(Exception e){}
        }
        try{
            foodImage = ImageIO.read(this.getClass().getResource("resources/images/food.png"));
            goImage = ImageIO.read(this.getClass().getResource("resources/images/gameover.png"));
            vicImage = ImageIO.read(this.getClass().getResource("resources/images/victory.png"));
            //pfoodImage = ImageIO.read(this.getClass().getResource("/images/pfood.png"));
        }catch(Exception e){}


        redrawAL = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //Draw Board
                repaint();
            }
        };
        redrawTimer = new Timer(16,redrawAL);
        redrawTimer .start();

        //SoundPlayer.play("pacman_start.wav");
        siren = new LoopPlayer("siren.wav");
        pac6 = new LoopPlayer("pac6.wav");
        siren.start();
    }

    public void CrearFantasma( int n, int m){
       // public void CrearFantasma( ){
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
                            ghosts.add(new PinkGhost(gd.getX(), gd.getY(), this));
                            break;}}

                if(n == 3){
                    switch(gd.getType()) {
                        case CYAN:
                            ghosts.add(new CyanGhost(gd.getX(), gd.getY(), this));
                            break;}}

            }
        } n = 0;

    }

    public void CrearPowerUp(){
        for(int ms=0 ;ms<2;ms++){
            try {
                pfoodImage[ms] = ImageIO.read(this.getClass().getResource("resources/images/food/0.png"));
            }catch(Exception e){}
        }
    }



    private void collisionTest(){
        //pacman = new Pacman(md.getPacmanPosition().x,md.getPacmanPosition().y,this);
        Rectangle pr = new Rectangle(pacman.pixelPosition.x+13,pacman.pixelPosition.y+13,2,2);
        Ghost ghostToRemove = null;
        for(Ghost g : ghosts){
            Rectangle gr = new Rectangle(g.pixelPosition.x,g.pixelPosition.y,28,28);
            //pacman.logicalPosition = new Point(3,1);
            /* Vidas*/
            if(pr.intersects(gr)){
                if(!g.isDead()) {
                    if (!g.isWeak()) {
                        if (vids !=0) {
                            // pacman = new Pacman(1,3,this);
                            // pacman = new Pacman(md_backup.getPacmanPosition().x =1,md_backup.getPacmanPosition().y= 3,this);
                            // pacman.logicalPosition.x =7;
                            //pacman.logicalPosition.y = 3;

                            pacman.logicalPosition = new Point(1, 3);
                            pacman.pixelPosition = new Point( 28, 84);



                            //pacman = (md_backup.getPacmanPosition().y = 25; md_backup.getPacmanPosition().x = 4);
                            //pacman = new Pacman(md.getPacmanPosition().x,md.getPacmanPosition().y,this);
                            //pacman = new Pacman(pacman.pixelPosition.x = 1,3,this);
                            //new Pacman(md_backup.getPacmanPosition().x = 1,md_backup.getPacmanPosition().y = 1, this);
                            //addKeyListener(pacman);
                          //  System.out.println("1.PAcboard");
                            //this.md_backup.getPacmanPosition().y = 1;
                            //this.md_backup.getPacmanPosition().x = 3;

                            drawVida = true;
                            vidaToAdd--;
                            vidas--;
                            vids--;
                            vidasboard.setText(" perdiste una vida te quedan:" +vids);
                        //scoreboard.setText("    Vidas : "+vids);
                            //  pacman.logicalPosition = new Point(3,1);
                        }
                        else{
                       // Game Over
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
                        //Eat Ghost
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

       // if(ghostToRemove!= null){
         //   ghosts.remove(ghostToRemove);
        //}
    }


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


            //////////////////////////////////////////////////////// aqui suma el socre

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
        } //colisionfruta = false;

        PowerUpFood puFoodToEat = null;
        //Check pu food eat
        for(PowerUpFood puf : pufoods){
            if(pacman.logicalPosition.x == puf.position.x && pacman.logicalPosition.y == puf.position.y)
                puFoodToEat = puf;
        }
        if(puFoodToEat!=null) {
            //SoundPlayer.play("pacman_eat.wav");
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
            //score ++;
            //scoreboard.setText("    Score : "+score);
        }

        //Check Ghost Undie
        for(Ghost g:ghosts){
            if(g.isDead() && g.logicalPosition.x == ghostBase.x && g.logicalPosition.y == ghostBase.y){
                g.undie();
            }
        }

        //Check Teleport
        for(TeleportTunnel tp : teleports) {
            if (pacman.logicalPosition.x == tp.getFrom().x && pacman.logicalPosition.y == tp.getFrom().y && pacman.activeMove == tp.getReqMove()) {
                //System.out.println("TELE !");
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
/*
    private void mensajes(){
        switch(pacman.activeMove){
            case NONE:
            case RIGHT:
                if (colisionfruta == true){
                    System.out.println("fruta" +colisionfrut);
                }if(colisionfantasma == true){
                    System.out.println("fantasma" +colisionfantasm);
            }else{
                    System.out.println("{}");
            }

                break;
            case LEFT:

                break;
            case DOWN:

                break;
            case UP:

                break;
        }
    } */

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        // draw Vidas
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


    @Override
    public void processEvent(AWTEvent ae){

        if(ae.getID()==Messeges.UPDATE) {
            update();
            
            Client client=new Client();
            String Servermessage = "1234567890123456789";
            try {
                Servermessage = client.send_to_server("mensaje de prueba para el servidor");
            } catch (Exception e) {
                e.printStackTrace();
            }
            // client=null;

            System.out.println("Pene:"+Servermessage);
            String Valores_Server = Servermessage.substring(0,5);
            System.out.println("Valores_Server:" + Valores_Server +"\n");

            String Fantasma = Valores_Server.substring(0,1);
            System.out.println("Fantasma:" + Fantasma +"\n");

            String Velocidad = Valores_Server.substring(2,4);
            System.out.println("Velocidad:" + Velocidad +"\n");
            int velo = Integer.parseInt(Velocidad);

            if(!Servermessage.equals("1234567890123456789")){
                if(Fantasma.equals("1")){
                    CrearFantasma(1,velo);
                }else if(Fantasma.equals("2")){
                    CrearFantasma(2,velo);
                }else if(Fantasma.equals("3")){
                    CrearFantasma(3,velo);
                }else if(Fantasma.equal("4")){
                    CrearPowerUp();
                }
            }

        } else if(ae.getID()==Messeges.GHOST) {
            /*BufferedReader reader =
                    new BufferedReader(new InputStreamReader(System.in));

            // Reading data using readLine
            String name = null;
            try {
                name = reader.readLine();
                CrearFantasma( Integer.parseInt(name));
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Printing the read line
            System.out.println(name);
            /*if (n != 0) {
                CrearFantasma(1, 1);
            }
            for (int z = 0; z < n; z++) {
                Multithreading object = new Multithreading();
                CrearFantasma(1, 1);
            }
            //object.start();}

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /*int n = 50;
            for (int z = 0; z<n; z++){
                //Multithreading object = new Multithreading();
                CrearFantasma(1, 1);}
            //object.start();}
            /*
            try {
                sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            //  CrearFantasma();}

        }

        else if(ae.getID()==Messeges.COLTEST) {
            if (!isGameOver) {
                collisionTest();

              //  CrearFantasma();
            //   mensajes();
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


        /*
        removeKeyListener(pacman);

        isGameOver = false;

        pacman = new Pacman(md_backup.getPacmanPosition().x,md_backup.getPacmanPosition().y,this);
        addKeyListener(pacman);

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
            foods = md_backup.getFoodPositions();
        }



        pufoods = md_backup.getPufoodPositions();

        ghosts = new ArrayList<>();
        for(GhostData gd : md_backup.getGhostsData()){
            switch(gd.getType()) {
                case RED:
                    ghosts.add(new RedGhost(gd.getX(), gd.getY(), this));
                    break;
                case PINK:
                    ghosts.add(new PinkGhost(gd.getX(), gd.getY(), this));
                    break;
                case CYAN:
                    ghosts.add(new CyanGhost(gd.getX(), gd.getY(), this));
                    break;
            }
        }

        teleports = md_backup.getTeleports();
        */
    }




}
