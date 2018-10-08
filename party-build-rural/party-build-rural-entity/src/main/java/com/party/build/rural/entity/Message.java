package com.party.build.rural.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name= "ws_message")
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 接收标识 0：未接收  1：已接收
     */
    @Column(name = "received_flag")
    private Integer receivedFlag = 1;
    //标记信息的作用 0：发送/接收  1:上线   2:下线
    private Integer flag;
    // 发送者
    @Column(name = "from_id")
    private Integer from;
    // 发送者名称
    @Column(name = "from_name")
    private String fromName;
    // 接收者
    @Column(name = "to_id")
    private Integer to;

    private String photo;
    // 发送的文本
    @Column(name = "send_text")
    private String text;
    // 发送日期
    @Column(name = "send_date")
    private Date date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReceivedFlag() {
        return receivedFlag;
    }

    public void setReceivedFlag(Integer receivedFlag) {
        this.receivedFlag = receivedFlag;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
