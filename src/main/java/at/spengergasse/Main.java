package at.spengergasse;

import at.spengergasse.entities.Answer;
import at.spengergasse.entities.Question;

import javax.persistence.*;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EntityManager em = Persistence.createEntityManagerFactory("demo").createEntityManager();
        Scanner scanner = new Scanner(System.in);

        // Fragen aus der Datenbank abrufen
        TypedQuery<Question> queryQue = em.createQuery("SELECT q from Question q", Question.class);
        List<Question> questions = queryQue.getResultList();

        if (questions.isEmpty()) {
            System.out.println("Keine Fragen in der Datenbank gefunden!");
            return;
        }

        char[] answerChar = {'a', 'b', 'c', 'd'};
        System.out.println("___________ Quiz ___________");

        int totalQuestions = questions.size();
        int correctAnswers = 0;

        // Durchlaufen aller Fragen
        for (Question q : questions) {
            List<Answer> answers = q.getAnswers();
            if (answers.isEmpty()) {
                System.out.println("No answer for question " + q.getText());
                continue;
            }

            String richtigeAntwort = "";
            System.out.println("\n" + q.getText());

            // Antwortmöglichkeiten anzeigen
            for (int i = 0; i < answers.size(); i++) {
                System.out.println(answerChar[i] + ") " + answers.get(i).getText());
                if (answers.get(i).isCorrect()) {
                    richtigeAntwort = String.valueOf(answerChar[i]); // Speichert den richtigen Buchstaben
                }
            }

            // Eingabe des Nutzers
            String eingabe = "";
            boolean korrekteEingabe = false;
            while (!korrekteEingabe) {
                System.out.print("Your Answer: ");
                eingabe = scanner.next().toLowerCase();
                if (!eingabe.matches("[a-d]")) {
                    System.out.println(" Wrong Entry! please just enter a,b,c or d");
                } else {
                    korrekteEingabe = true;
                }
            }

            // Überprüfung der Antwort
            if (eingabe.equals(richtigeAntwort)) {
                correctAnswers++;
                System.out.println("True!");
            } else {
                System.out.println(" False! The right answer was: " + richtigeAntwort);
            }
        }

        // Quiz-Ergebnis berechnen & anzeigen
        double percentage = ((double) correctAnswers / totalQuestions) * 100;
        System.out.println("\n========== Quiz beendet ==========");
        System.out.println("You have  " + correctAnswers + " from " + totalQuestions + " true answered.");
        System.out.printf("your Score: %.2f%%\n", percentage);

        em.close();
    }
}