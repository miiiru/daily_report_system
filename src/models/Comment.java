package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "comments")
@NamedQueries({
    @NamedQuery(
        name = "getAllComments",
        query = "SELECT c FROM Comment AS c ORDER BY c.id DESC"
    ),
    @NamedQuery(
        name = "getCommnetsCount",
        query = "SELECT COUNT(c) FROM Comment AS c"
    ),
    @NamedQuery(
        name = "getMyAllComments",
        query = "SELECT c FROM Report AS c WHERE c.employee = :employee ORDER BY c.id DESC"
    ),
    @NamedQuery(
        name = "getMyCommentsCount",
        query = "SELECT COUNT(c) FROM Comment AS c WHERE c.employee = :employee"
    )
})
@Entity
public class Comment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "report_id", nullable = false)
    private Report report;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
