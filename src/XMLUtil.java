import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class XMLUtil {
    private File file = new File("src/score.xml");
    private DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
    private DocumentBuilder builder;
    private Document doc;

    public XMLUtil(){
        try {
            builder = builderFactory.newDocumentBuilder();
            doc = builder.parse(file);
            doc.getDocumentElement().normalize();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void reset(){
        try {
            builder = builderFactory.newDocumentBuilder();
            doc = builder.parse(file);
            doc.getDocumentElement().normalize();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<PlayerScore> read(){
        ArrayList<PlayerScore> resList = new ArrayList<>();
        NodeList nodeList = doc.getElementsByTagName("player");
        for (int i = 0 ; i < nodeList.getLength() ; i++){
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                PlayerScore newPlayer = new PlayerScore(element.getElementsByTagName("name").item(0).getTextContent(),
                        Integer.parseInt(element.getElementsByTagName("score").item(0).getTextContent()));
                resList.add(newPlayer);
            }
        }
        return resList;
    }

    public void write(PlayerScore player) throws TransformerException {

        reset();
        //ArrayList<PlayerScore> playersList = read();
        //Or
        PlayerScore.listPlayers = read();

        if (PlayerScore.listPlayers.size() != 0){
            if (PlayerScore.CheckAPlayerIsInList(player)){
                for (int i = 0; i < PlayerScore.listPlayers.size() ; i++){
                    if (PlayerScore.listPlayers.get(i).getName().equalsIgnoreCase(player.getName())){
                        int oldScore = PlayerScore.listPlayers.get(i).getScore();
                        PlayerScore.listPlayers.get(i).setScore( oldScore + player.getScore());
                        break;
                    }
                }
            }
            else{
                PlayerScore.listPlayers.add(player);
            }
        }
        //else list.size() == 0

        Document document = builder.newDocument();

        Element root = document.createElement("gomoku");
        document.appendChild(root);

        for (int i = 0 ; i < PlayerScore.listPlayers.size() ; i++){
            Element childRoot = document.createElement("player");
            root.appendChild(childRoot);

            Element username = document.createElement("name");
            username.setTextContent(PlayerScore.listPlayers.get(i).getName());
            childRoot.appendChild(username);

            Element password = document.createElement("score");
            password.setTextContent(String.valueOf(PlayerScore.listPlayers.get(i).getScore()));
            childRoot.appendChild(password);
        }

        Transformer tr = TransformerFactory.newInstance().newTransformer();
        tr.setOutputProperty(OutputKeys.INDENT, "yes");
        tr.transform(new DOMSource(document), new StreamResult(file));
    }
}
