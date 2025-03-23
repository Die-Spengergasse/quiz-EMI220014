package at.spengergasse.entities;

import javax.persistence.*;

@Entity
@Table(name = "a_answers")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "a_id")
    private int id;

    @Column(name = "a_correct")
    private boolean correct;

    @Column(name = "a_text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "fk_q_id", nullable = false)
    private Question question;

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", correct=" + correct +
                ", text='" + text + '\'' +
                '}';
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}