package com.example.lab3.Models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public class Node implements Serializable {

    private int id;
    private Double x;
    private Double y;
    private Double r;
    private boolean result;
    private LocalDateTime createTime;

    public Node() {
        this.id = ThreadLocalRandom.current().nextInt(1, 10000);
        this.createTime = LocalDateTime.now();
    }

    public Node(Double x, Double y, Double r) {
        this();
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public void checkResult() {

         this.result = x <= 0 && x >= -r && y <= 0 && y >= -r ||
                x >= 0 && y <= 0 && x*x + y*y <= r*r/4 ||
                x <= 0 && y >= 0 && y <= r/2 + x/2 && x >= -r && y <= r/2;             // y = 0.5х + 0.5r// ; //
    }

    public String getCreateTimeFormatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy 'at' HH:mm:ss a");
        return createTime.format(formatter);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getR() {
        return r;
    }

    public void setR(Double r) {
        this.r = r;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime localDateTime) {
        this.createTime = localDateTime;
    }
}
