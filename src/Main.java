import javax.xml.transform.TransformerException;

public class Main {
    public static void main(String[] args) throws TransformerException {
        XMLUtil xmlUtil = new XMLUtil();
        PlayerScore p1 = new PlayerScore("Bob", 3000);
        xmlUtil.write(p1);
        PlayerScore p2 = new PlayerScore("Tom", 9000);
        xmlUtil.write(p2);
        PlayerScore p3 = new PlayerScore("Win", 1000);
        xmlUtil.write(p3);
        PlayerScore.listPlayers = xmlUtil.read();
        for (PlayerScore p : PlayerScore.listPlayers){
            System.out.println(p.getName());
            System.out.println(p.getScore());
        }
    }
}
