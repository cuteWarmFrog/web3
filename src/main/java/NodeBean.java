import com.example.lab3.Models.Node;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean()
@Named("nodeBean")
@SessionScoped
public class NodeBean implements Serializable {


//    @ManagedProperty(value = "#{nodeJPA")
    private NodeJPA nodeJPA;
    private List<Node> nodes;
    private Node node;

    public NodeBean() {
        this.nodes = new ArrayList<>();
        this.node = new Node();
    }

    public List<Node> getSavedNodes() {
        //nodes = nodeJPA.getPoints();
        return nodes;
    }

    public void addNode() {
        node.checkResult();
        nodes.add(node);
        //nodeJPA.addPoint();
        node = new Node();
    }

    public NodeJPA getNodeJPA() {
        return nodeJPA;
    }

    public void setNodeJPA(NodeJPA nodeJPA) {
        this.nodeJPA = nodeJPA;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}
