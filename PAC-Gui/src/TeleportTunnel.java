import java.awt.*;

/**
 * Clase TeleporTunnel
 * Esta calse permite que cuando un fanyasma muera llegue al punto central del mapa para inicializarse nuevamente
 * durante el proceso solo se veran los ojos del fantasma y su inica funcion es llegar al centro
 */
public class TeleportTunnel {

    private Point from;
    private Point to;
    private moveType reqMove;

    public Point getTo() {
        return to;
    }

    public void setTo(Point to) {
        this.to = to;
    }

    public Point getFrom() {
        return from;
    }

    public void setFrom(Point from) {
        this.from = from;
    }

    public moveType getReqMove() {
        return reqMove;
    }

    public void setReqMove(moveType reqMove) {
        this.reqMove = reqMove;
    }

    public TeleportTunnel(int x1,int y1,int x2,int y2,moveType reqMove){
        from = new Point(x1,y1);
        to = new Point(x2,y2);
        this.reqMove = reqMove;
    }
}
