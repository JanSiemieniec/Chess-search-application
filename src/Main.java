import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.swing.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) throws Exception {

        StandardServiceRegistry registry = null;
        SessionFactory sessionFactory = null;
        try {
            registry = new StandardServiceRegistryBuilder()
                    .configure()
                    .build();
            sessionFactory = new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            Player player1 = Player.addNew("Magnus", "Carlsen", GenderEnum.MAN, 3333);
            Player player2 = Player.addNew("Hikaru", "Nakamura", Optional.of("Christopher"), GenderEnum.MAN, 3123);

            Judge judge1 = Judge.addNew("Alexandra", "Botex", GenderEnum.WOMAN, 2017);
            Judge judge2 = Judge.addNew("Judit", "Polgar", Optional.of("Adam"), GenderEnum.MAN, 2004);

            Tournament tournament = Tournament.addTournament("FIDE Championship", LocalDate.of(2017, 4, 14),
                    LocalDate.of(2017, 5, 4), 50000,
                    Adress.addAdress("Poland", "Szczecin", "Strumykowa", "33a"));
            Tournament tournament2 = Tournament.addTournament("Ciechanow Cap", LocalDate.of(2022, 7, 11),
                    LocalDate.of(2017, 5, 4), 50000,
                    Adress.addAdress("Poland", "Ciechanow", "Wojskowa", "1b", "4"));

            try {
                PointsForPlace.createPoints(tournament, "WorldClass");
                PointsForPlace.createPoints(tournament2, "Continental");
            } catch (Exception e) {
                System.out.println('\n' + e.getMessage());
            }


            ChessCategory cat = new ChessCategory(ChessCategoryEnum.IM);
            ChessCategory cat2 = new ChessCategory(ChessCategoryEnum.CM);
            ChessCategory cat3 = new ChessCategory(ChessCategoryEnum.GM);

            PlayerCategory pc = new PlayerCategory(player1, cat, LocalDate.of(2020, 5, 23));
            PlayerCategory pc2 = new PlayerCategory(player1, cat2, LocalDate.of(2017, 2, 28));
            PlayerCategory pc3 = new PlayerCategory(cat3, LocalDate.of(2016, 5, 27));

            player1.addPlayerCategory(pc);
            player1.addPlayerCategory(pc2);
            pc2.setPlayer(player2);
            pc3.setPlayer(player2);

            PlayerJudge playerJudge = new PlayerJudge("Nguyen", "Son", Optional.of("Ngoc"), GenderEnum.MAN, 2017, 2645);
            playerJudge.addPlayerCategory(pc3);

            tournament.setJudgeToTournament(playerJudge);
            judge1.addTournamentQualif(tournament2);
            tournament.addPlayer(player1);
            tournament2.addPlayer(player1);
            player2.addTournament(tournament);
            tournament.addPlayer(player2);
            playerJudge.addTournamentQualif(tournament2);
            player2.addTournament(tournament);

            session.save(player1);
            session.save(player2);
            session.save(playerJudge);
            session.save(judge1);
            session.save(judge2);
            session.save(pc);
            session.save(pc2);
            session.save(pc3);
            session.save(cat);
            session.save(cat2);
            session.save(cat3);
            session.save(tournament);
            session.save(tournament2);

            Win win = new Win(session);
            while (isFrameShowing(win)) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            session.getTransaction().commit();
            session.close();

        } catch (Exception e) {
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy(registry);
        } finally {
            if (sessionFactory != null) {
                sessionFactory.close();
            }
        }
/*

//file extension
        final String extentDebutFile = "extentDebutFile.bin";

        try {
            var in = new ObjectInputStream(new FileInputStream(extentDebutFile));
            Debut.readExtent(in);
            in.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        Debut debut = new Debut("Obrona Polska", new StringBuilder("Rozwinieciu hetmanskiego gonca czarnych na pole b7, skad, wzdluz przekatnej a8-h1 oddzialuje on na skrzydlo krolewskie bialych,\n" +
                "    a przede wszystkim kontroluje wazny punkt e4. Dzieki obecnosci piona na polu b5 czarne moga walczyc o przewage przestrzeni na skrzydle hetmanskim. \n" +
                "    Obrana przez czarne strategia nie narzuca zwykle forsownych rozwiazan w poczatkowej fazie gry, ewentualna erudycja przeciwnika traci na znaczeniu."));
        Debut debut1 = new Debut("Gambit krolowej", new StringBuilder("Jest jednym z najczesciej wybieranych debiutow w grupie debiutow zamknietych. Biale poswiecaja piona w zamian za opanowanie centrum szachownicy. \n" +
                "    Czarne moga ofiare przyjac badz odrzucic.Rozrozniamy dwa glowne warianty gambitu hetmanskiego: gambit nieprzyjety d4 d5, c4 e6; gambit przyjety d4 d5 c4 d:c4"));
        Debut debut2 = new Debut("Gambit Sandomierski", new StringBuilder("Otwarcie polegajace na szybkim pozbyciu sie dwoch lekkich figur przeciwnika oraz wiezy. Dokonujemy to za pomoca skoczka. Debiut ten ostatnimi czasu stal sie bardzo popularny.\n" +
                "    Pierwszy raz zostal zagrany przez Ojca Mateusza. Sklada sie z nastepujacych posuniec 1. Nc3 Nf6 2. Nb1 Nh5 3. Nc3 Ng3 4. Nb1 Nxh1 5. Nc3 Ng3 6. Nb1 Nxf1 7. Nc3 Ng3 8. Nb1 Nh5 9. Nc3 Nf4 10. Nb1 Nh3 11. Nc3 Nxg1 12. Nb1 Nh3 13. Nc3 Nf4 14. Nb1 Nh5 15. Nc3 Nf6 16. Nb1 Ng8"));

        try {
            var out = new ObjectOutputStream(new FileOutputStream(extentDebutFile));
            Debut.writeExtent(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Debut.showAllDebuts();

//overlaping
        Rule ruleBlitz = new Rule(LocalTime.of(0, 15, 0), EnumSet.of(RuleType.Blitz));
        Rule ruleBulletBlitz = new Rule(LocalTime.of(0, 15, 0), EnumSet.of(RuleType.Blitz, RuleType.Bullet));
        Rule ruleBulletBlitzClassic = new Rule(LocalTime.of(0, 15, 0), EnumSet.of(RuleType.Blitz, RuleType.Bullet, RuleType.Classic));

        System.out.println(ruleBlitz);
        System.out.println(ruleBulletBlitz);
        System.out.println(ruleBulletBlitzClassic);

*/
    }

    private static boolean isFrameShowing(Win win) {
        if (win.isShowing()) {
            return true;
        }
        return false;
    }
}