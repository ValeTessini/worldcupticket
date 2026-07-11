package com.worldcupticket.ms_catalogo.seeder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.worldcupticket.ms_catalogo.domain.Match;
import com.worldcupticket.ms_catalogo.domain.MatchSector;
import com.worldcupticket.ms_catalogo.domain.Stadium;
import com.worldcupticket.ms_catalogo.domain.StadiumSector;
import com.worldcupticket.ms_catalogo.domain.Team;
import com.worldcupticket.ms_catalogo.enums.MatchStage;
import com.worldcupticket.ms_catalogo.enums.SectorType;
import com.worldcupticket.ms_catalogo.repository.MatchRepository;
import com.worldcupticket.ms_catalogo.repository.StadiumRepository;
import com.worldcupticket.ms_catalogo.repository.TeamRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final TeamRepository teamRepository;
    private final StadiumRepository stadiumRepository;
    private final MatchRepository matchRepository;

    @Override
    public void run(String... args) throws Exception {

        if (teamRepository.count() > 0) {
            return;
        }

        // --- Equipos: Grupo J (Argentina, Argelia, Austria, Jordania) ---
                Team argentina = new Team();
                argentina.setCountry("Argentina");
                argentina.setFlag("🇦🇷");
                argentina.setGroup("Grupo J");
                argentina = teamRepository.save(argentina);

                Team argelia = new Team();
                argelia.setCountry("Argelia");
                argelia.setFlag("🇩🇿");
                argelia.setGroup("Grupo J");
                argelia = teamRepository.save(argelia);

                Team austria = new Team();
                austria.setCountry("Austria");
                austria.setFlag("🇦🇹");
                austria.setGroup("Grupo J");
                austria = teamRepository.save(austria);

                Team jordania = new Team();
                jordania.setCountry("Jordania");
                jordania.setFlag("🇯🇴");
                jordania.setGroup("Grupo J");
                jordania = teamRepository.save(jordania);

        // --- Estadio: AT&T Stadium (Arlington, sede real del Mundial 2026) ---
                StadiumSector plateaEste = new StadiumSector();
                plateaEste.setId("platea-este");
                plateaEste.setName("Platea Este");
                plateaEste.setCapacity(4200);
                plateaEste.setType(SectorType.GENERAL);

                StadiumSector vipNorte = new StadiumSector();
                vipNorte.setId("vip-norte");
                vipNorte.setName("Palco VIP Norte");
                vipNorte.setCapacity(520);
                vipNorte.setType(SectorType.VIP);

                Stadium attStadium = new Stadium();
                attStadium.setName("AT&T Stadium");
                attStadium.setCity("Arlington");
                attStadium.setCountry("Estados Unidos");
                attStadium.setSectors(List.of(plateaEste, vipNorte));
                attStadium = stadiumRepository.save(attStadium);

        // --- Partidos reales del Grupo J, todos en el mismo estadio ---
                Match argVsArgelia = new Match();
                argVsArgelia.setStadiumId(attStadium.getId());
                argVsArgelia.setLocalTeamId(argentina.getId());
                argVsArgelia.setAwayTeamId(argelia.getId());
                argVsArgelia.setDate(LocalDateTime.of(2026, 6, 16, 21, 0));
                argVsArgelia.setStage(MatchStage.GROUP_STAGE);
                argVsArgelia.setSectorsPrice(List.of(
                    new MatchSector("platea-este", new BigDecimal("560")),
                    new MatchSector("vip-norte", new BigDecimal("3200"))
                ));

                Match argVsAustria = new Match();
                argVsAustria.setStadiumId(attStadium.getId());
                argVsAustria.setLocalTeamId(argentina.getId());
                argVsAustria.setAwayTeamId(austria.getId());
                argVsAustria.setDate(LocalDateTime.of(2026, 6, 22, 21, 0));
                argVsAustria.setStage(MatchStage.GROUP_STAGE);
                argVsAustria.setSectorsPrice(List.of(
                    new MatchSector("platea-este", new BigDecimal("620")),
                    new MatchSector("vip-norte", new BigDecimal("3400"))
                ));

                Match argVsJordania = new Match();
                argVsJordania.setStadiumId(attStadium.getId());
                argVsJordania.setLocalTeamId(argentina.getId());
                argVsJordania.setAwayTeamId(jordania.getId());
                argVsJordania.setDate(LocalDateTime.of(2026, 6, 27, 21, 0));
                argVsJordania.setStage(MatchStage.GROUP_STAGE);
                argVsJordania.setSectorsPrice(List.of(
                    new MatchSector("platea-este", new BigDecimal("580")),
                    new MatchSector("vip-norte", new BigDecimal("3300"))
                ));

                matchRepository.saveAll(List.of(argVsArgelia, argVsAustria, argVsJordania));

        System.out.println("Seed cargado: " + teamRepository.count() + " equipos, " + stadiumRepository.count() + " estadio, " + matchRepository.count() + " partidos.");

    }
}
