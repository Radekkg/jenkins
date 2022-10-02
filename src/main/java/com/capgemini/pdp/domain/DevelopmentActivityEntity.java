package com.capgemini.pdp.domain;

import com.capgemini.pdp.domain.enumerated.ActivityStatus;

import javax.persistence.*;

@Entity
@Table(name = "development_activity")
public class DevelopmentActivityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "development_action")
    private String developmentAction;

    @Column(nullable = false, name = "activity_status")
    @Enumerated(value = EnumType.STRING)
    private ActivityStatus activityStatus;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    private TypeEntity type;

    @ManyToOne
    private CompetencyEntity competency;

    @ManyToOne
    private PlanEntity plan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDevelopmentAction() {
        return developmentAction;
    }

    public void setDevelopmentAction(String developmentAction) {
        this.developmentAction = developmentAction;
    }

    public ActivityStatus getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(ActivityStatus activityStatus) {
        this.activityStatus = activityStatus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public TypeEntity getType() {
        return type;
    }

    public void setType(TypeEntity type) {
        this.type = type;
    }

    public CompetencyEntity getCompetency() {
        return competency;
    }

    public void setCompetency(CompetencyEntity competency) {
        this.competency = competency;
    }

    public PlanEntity getPlan() {
        return plan;
    }

    public void setPlan(PlanEntity plan) {
        this.plan = plan;
    }
}
