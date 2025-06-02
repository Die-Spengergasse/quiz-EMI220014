package at.spengergasse;

import at.spengergasse.entities.Answer;
import at.spengergasse.entities.Question;
import at.spengergasse.entities.Result;

import javax.persistence.*;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // EntityManager erzeugen: Verbindet uns mit der Datenbank über die Persistence Unit "demo"
        EntityManager em = Persistence.createEntityManagerFactory("demo").createEntityManager();

        // Scanner zum Einlesen von Benutzereingaben aus der Konsole
        Scanner scanner = new Scanner(System.in);

        // Benutzer nach seinem Namen fragen
        System.out.print("Gib deinen Namen ein: ");
        String name = scanner.nextLine();

        // Alle Fragen aus der Datenbank abfragen
        TypedQuery<Question> queryQue = em.createQuery("SELECT q from Question q", Question.class);
        List<Question> questions = queryQue.getResultList();

        // Prüfen, ob Fragen in der DB vorhanden sind
        if (questions.isEmpty()) {
            System.out.println("Keine Fragen in der Datenbank gefunden!");
            return;  // Programm beenden, falls keine Fragen da sind
        }

        // Buchstaben für die Antwortoptionen, um sie dem Nutzer anzuzeigen (a,b,c,d)
        char[] answerChar = {'a', 'b', 'c', 'd'};
        System.out.println("___________ Quiz ___________");

        int totalQuestions = questions.size();  // Gesamtanzahl der Fragen
        int correctAnswers = 0;                  // Zähler für korrekte Antworten

        // Quiz: jede Frage einzeln durchlaufen
        for (Question q : questions) {
            List<Answer> answers = q.getAnswers();
            if (answers.isEmpty()) {
                // Falls eine Frage keine Antwortmöglichkeiten hat, überspringen
                System.out.println("No answer for question " + q.getText());
                continue;
            }

            // Variable um den Buchstaben der richtigen Antwort zu speichern
            String richtigeAntwort = "";
            System.out.println("\n" + q.getText());  // Frage anzeigen

            // Alle Antwortmöglichkeiten anzeigen (a), b), c), d))
            for (int i = 0; i < answers.size(); i++) {
                System.out.println(answerChar[i] + ") " + answers.get(i).getText());

                // Wenn die Antwort richtig ist, merken wir uns den Buchstaben
                if (answers.get(i).isCorrect()) {
                    richtigeAntwort = String.valueOf(answerChar[i]);
                }
            }

            // Benutzerantwort einlesen und validieren (nur a,b,c oder d erlaubt)
            String eingabe = "";
            boolean korrekteEingabe = false;
            while (!korrekteEingabe) {
                System.out.print("Your Answer: ");
                eingabe = scanner.next().toLowerCase();

                // Prüfen, ob Eingabe gültig ist
                if (!eingabe.matches("[a-d]")) {
                    System.out.println(" Wrong Entry! please just enter a,b,c or d");
                } else {
                    korrekteEingabe = true;
                }
            }

            // Überprüfen, ob die Antwort richtig ist
            if (eingabe.equals(richtigeAntwort)) {
                correctAnswers++;  // Richtige Antwort -> Zähler erhöhen
                System.out.println("True!");
            } else {
                System.out.println(" False! The right answer was: " + richtigeAntwort);
            }
        }

        // Quiz beendet, Ergebnis berechnen (Prozent richtig beantwortet)
        double percentage = ((double) correctAnswers / totalQuestions) * 100;

        System.out.println("\n========== Quiz beendet ==========");
        System.out.println("You have  " + correctAnswers + " from " + totalQuestions + " true answered.");
        System.out.printf("your Score: %.2f%%\n", percentage);

        // Ergebnis in der Datenbank speichern
        em.getTransaction().begin();          // Transaktion starten
        Result result = new Result();          // Neues Ergebnisobjekt
        result.setPlayerName(name);            // Spielername setzen
        result.setScore(percentage);           // Punktestand setzen
        em.persist(result);                    // Ergebnis speichern
        em.getTransaction().commit();          // Transaktion abschließen

        // Bestenliste aus der DB abfragen (Top 5 Spieler nach Punktzahl sortiert)
        System.out.println("\n--- Bestenliste ---");
        TypedQuery<Result> bestQuery = em.createQuery(
                "SELECT r FROM Result r ORDER BY r.score DESC", Result.class
        );
        List<Result> topResults = bestQuery.setMaxResults(5).getResultList();

        // Bestenliste ausgeben
        for (Result r : topResults) {
            System.out.printf("%s: %.2f%%\n", r.getPlayerName(), r.getScore());
        }

        // EntityManager schließen, Verbindung zur DB trennen
        em.close();
    }
}