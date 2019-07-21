package net.zjcclims.domain;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;


/**
 */

@Entity
@NamedQueries({
        @NamedQuery(name = "findAllUsers2", query = "select u from User2 u"),
        @NamedQuery(name = "findUser2ByUsername", query = "select u from User2 u where u.username = ?1")
        })
@Table(name = "user2")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "User2")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class User2 implements Serializable{
    @Column(name = "username", length = 40, nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @Id
    @XmlElement
    String username;
    /**
     * ����
     *
     */

    @Column(name = "cardno")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    String cardno;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * ����
     *
     */

    @Column(name = "cname", length = 100, nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    String cname;
    /**
     * ����
     *
     */

    @Column(name = "password", length = 120, nullable = false)
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    String password;
    /**
     * �Ա�
     *
     */
}
