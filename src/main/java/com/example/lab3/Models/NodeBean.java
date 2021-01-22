package com.example.lab3.Models;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.ManagedProperty;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@Named("nodeBean")
@SessionScoped
public class NodeBean implements Serializable {

    @ManagedProperty(value = "#{nodeDao}")
    private NodeDAO nodeDAO;

    private List<Node> nodes;
    private Node node;

    public NodeBean() {
        this.nodeDAO = new NodeDAO();
        this.nodes = new ArrayList<>();
        this.node = new Node();
    }

    public List<Node> getSavedNodes() {
        nodes = nodeDAO.getNodes();
        return nodes;
    }

    public void addNode() {
        node.checkResult();
        nodeDAO.addNode(node);
        node = new Node();
    }

    public NodeDAO getNodeJPA() {
        return nodeDAO;
    }

    public void setNodeJPA(NodeDAO nodeDAO) {
        this.nodeDAO = nodeDAO;
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
