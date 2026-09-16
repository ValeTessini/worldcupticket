package com.worldcupticket.ms_catalog.seeder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.worldcupticket.ms_catalog.domain.Match;
import com.worldcupticket.ms_catalog.domain.MatchSector;
import com.worldcupticket.ms_catalog.domain.Stadium;
import com.worldcupticket.ms_catalog.domain.StadiumSector;
import com.worldcupticket.ms_catalog.domain.Team;
import com.worldcupticket.ms_catalog.enums.MatchStage;
import com.worldcupticket.ms_catalog.enums.SectorType;
import com.worldcupticket.ms_catalog.enums.StadiumLayout;
import com.worldcupticket.ms_catalog.repository.MatchRepository;
import com.worldcupticket.ms_catalog.repository.StadiumRepository;
import com.worldcupticket.ms_catalog.repository.TeamRepository;

import lombok.AllArgsConstructor;

/**
 * Carga la fase de grupos del Mundial 2026 (USA / Mexico / Canada).
 * Datos reales: sorteo del 5 de diciembre de 2025 y fixture oficial.
 * Los horarios estan expresados en hora del este (EDT).
 *
 * El campo "flag" de Team guarda el codigo ISO 3166-1 alpha-2 en minuscula
 * (ar, br, mx...). El front arma la URL de la imagen a partir de ese codigo.
 * Escocia e Inglaterra no son paises ISO: usan los codigos de subdivision
 * del Reino Unido (gb-sct, gb-eng).
 */
@Component
@AllArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final TeamRepository teamRepository;
    private final StadiumRepository stadiumRepository;
    private final MatchRepository matchRepository;

    // Layout generico: lo usan los estadios que todavia no tienen un SVG
    // propio en el front (mapa simple de 4 sectores).
    private static final String GENERAL_NORTE = "general-norte";
    private static final String GENERAL_SUR = "general-sur";
    private static final String PLATEA_CENTRAL = "platea-central";
    private static final String PALCO_VIP = "palco-vip";

    // Layout del Estadio Azteca: coincide con los ids del SVG real que arma
    // el front para ese estadio puntual (4 lados x 3 niveles).
    private static final String ESPECIAL_BAJO_NORTE = "especial-bajo-norte";
    private static final String PALCOS_NORTE = "palcos-norte";
    private static final String SUPERIOR_NORTE = "superior-norte";
    private static final String ESPECIAL_BAJO_SUR = "especial-bajo-sur";
    private static final String PALCOS_SUR = "palcos-sur";
    private static final String SUPERIOR_SUR = "superior-sur";
    private static final String ESPECIAL_BAJO_OESTE = "especial-bajo-oeste";
    private static final String PALCOS_OESTE = "palcos-oeste";
    private static final String SUPERIOR_OESTE = "superior-oeste";
    private static final String ESPECIAL_BAJO_ESTE = "especial-bajo-este";
    private static final String PALCOS_ESTE = "palcos-este";
    private static final String SUPERIOR_ESTE = "superior-este";

    @Override
    public void run(String... args) throws Exception {

        if (teamRepository.count() > 0) {
            return;
        }

        Map<String, Team> teams = seedTeams();
        Map<String, Stadium> stadiums = seedStadiums();
        seedMatches(teams, stadiums);

        System.out.println("Seed cargado: " + teamRepository.count() + " equipos, "
                + stadiumRepository.count() + " estadios, " + matchRepository.count() + " partidos.");
    }

    // ---------- EQUIPOS (48) ----------

    private Map<String, Team> seedTeams() {
        Map<String, Team> teams = new HashMap<>();

        team(teams, "Mexico", "mx", "A");
        team(teams, "Corea del Sur", "kr", "A");
        team(teams, "Sudafrica", "za", "A");
        team(teams, "Chequia", "cz", "A");

        team(teams, "Canada", "ca", "B");
        team(teams, "Suiza", "ch", "B");
        team(teams, "Qatar", "qa", "B");
        team(teams, "Bosnia y Herzegovina", "ba", "B");

        team(teams, "Brasil", "br", "C");
        team(teams, "Marruecos", "ma", "C");
        team(teams, "Escocia", "gb-sct", "C");
        team(teams, "Haiti", "ht", "C");

        team(teams, "Estados Unidos", "us", "D");
        team(teams, "Paraguay", "py", "D");
        team(teams, "Australia", "au", "D");
        team(teams, "Turquia", "tr", "D");

        team(teams, "Alemania", "de", "E");
        team(teams, "Ecuador", "ec", "E");
        team(teams, "Costa de Marfil", "ci", "E");
        team(teams, "Curazao", "cw", "E");

        team(teams, "Paises Bajos", "nl", "F");
        team(teams, "Japon", "jp", "F");
        team(teams, "Tunez", "tn", "F");
        team(teams, "Suecia", "se", "F");

        team(teams, "Belgica", "be", "G");
        team(teams, "Iran", "ir", "G");
        team(teams, "Egipto", "eg", "G");
        team(teams, "Nueva Zelanda", "nz", "G");

        team(teams, "España", "es", "H");
        team(teams, "Uruguay", "uy", "H");
        team(teams, "Arabia Saudita", "sa", "H");
        team(teams, "Cabo Verde", "cv", "H");

        team(teams, "Francia", "fr", "I");
        team(teams, "Senegal", "sn", "I");
        team(teams, "Noruega", "no", "I");
        team(teams, "Irak", "iq", "I");

        team(teams, "Argentina", "ar", "J");
        team(teams, "Austria", "at", "J");
        team(teams, "Argelia", "dz", "J");
        team(teams, "Jordania", "jo", "J");

        team(teams, "Portugal", "pt", "K");
        team(teams, "Colombia", "co", "K");
        team(teams, "Uzbekistan", "uz", "K");
        team(teams, "Republica Democratica del Congo", "cd", "K");

        team(teams, "Inglaterra", "gb-eng", "L");
        team(teams, "Croacia", "hr", "L");
        team(teams, "Panama", "pa", "L");
        team(teams, "Ghana", "gh", "L");

        return teams;
    }

    private void team(Map<String, Team> teams, String country, String flag, String group) {
        Team team = new Team();
        team.setCountry(country);
        team.setFlag(flag);
        team.setGroup(group);
        teams.put(country, teamRepository.save(team));
    }

    // ---------- ESTADIOS (16) ----------

    private Map<String, Stadium> seedStadiums() {
        Map<String, Stadium> stadiums = new HashMap<>();

        // Unico estadio con layout propio por ahora. El resto usa el generico
        // hasta que tengan su SVG real (ver comentario en seedStadiums()).
        aztecaStadium(stadiums, "azteca", "Estadio Azteca", "Ciudad de Mexico", "Mexico", 80824);

        genericStadium(stadiums, "metlife", "MetLife Stadium", "East Rutherford", "Estados Unidos", 80663);
        genericStadium(stadiums, "att", "AT&T Stadium", "Arlington", "Estados Unidos", 70649);
        genericStadium(stadiums, "sofi", "SoFi Stadium", "Inglewood", "Estados Unidos", 70492);
        genericStadium(stadiums, "arrowhead", "Arrowhead Stadium", "Kansas City", "Estados Unidos", 69045);
        genericStadium(stadiums, "levis", "Levi's Stadium", "Santa Clara", "Estados Unidos", 68827);
        genericStadium(stadiums, "nrg", "NRG Stadium", "Houston", "Estados Unidos", 68777);
        genericStadium(stadiums, "lincoln", "Lincoln Financial Field", "Filadelfia", "Estados Unidos", 68324);
        genericStadium(stadiums, "mercedes", "Mercedes-Benz Stadium", "Atlanta", "Estados Unidos", 68239);
        genericStadium(stadiums, "lumen", "Lumen Field", "Seattle", "Estados Unidos", 66925);
        genericStadium(stadiums, "hardrock", "Hard Rock Stadium", "Miami Gardens", "Estados Unidos", 64478);
        genericStadium(stadiums, "gillette", "Gillette Stadium", "Foxborough", "Estados Unidos", 64146);
        genericStadium(stadiums, "bcplace", "BC Place", "Vancouver", "Canada", 52497);
        genericStadium(stadiums, "bbva", "Estadio BBVA", "Monterrey", "Mexico", 51243);
        genericStadium(stadiums, "akron", "Estadio Akron", "Guadalajara", "Mexico", 45664);
        genericStadium(stadiums, "bmo", "BMO Field", "Toronto", "Canada", 43036);

        return stadiums;
    }

    /**
     * Layout simple de 4 sectores para cualquier estadio que todavia no tenga
     * su propio SVG en el front. El ultimo sector se queda con lo que sobra
     * (no calcula su propio porcentaje), para que la suma de exacto la
     * capacidad real en vez de perder butacas por redondeo.
     */
    private void genericStadium(Map<String, Stadium> stadiums, String key, String name, String city,
            String country, int capacity) {

        int generalNorte = (int) (capacity * 0.32);
        int generalSur = (int) (capacity * 0.32);
        int plateaCentral = (int) (capacity * 0.28);
        int palcoVip = capacity - generalNorte - generalSur - plateaCentral;

        List<StadiumSector> sectors = List.of(
                sector(GENERAL_NORTE, "General Norte", generalNorte, SectorType.GENERAL),
                sector(GENERAL_SUR, "General Sur", generalSur, SectorType.GENERAL),
                sector(PLATEA_CENTRAL, "Platea Central", plateaCentral, SectorType.GENERAL),
                sector(PALCO_VIP, "Palco VIP", palcoVip, SectorType.VIP));

        saveStadium(stadiums, key, name, city, country, sectors, StadiumLayout.GENERIC);
    }

    /**
     * Layout real del Estadio Azteca, pensado 1 a 1 con el SVG del front:
     * 4 lados (Norte/Sur son las cabeceras, mas chicas; Oeste/Este son las
     * plateas laterales, mas grandes) x 3 niveles cada uno (Especial Bajo,
     * cerca de la cancha; Palcos, VIP; Superior, el mas barato). Mismo truco
     * de redondeo que el layout generico: el ultimo sector absorbe el resto.
     */
    private void aztecaStadium(Map<String, Stadium> stadiums, String key, String name, String city,
            String country, int capacity) {

        int especialBajoNorte = (int) (capacity * 0.03);
        int palcosNorte = (int) (capacity * 0.02);
        int superiorNorte = (int) (capacity * 0.06);

        int especialBajoSur = (int) (capacity * 0.03);
        int palcosSur = (int) (capacity * 0.02);
        int superiorSur = (int) (capacity * 0.06);

        int especialBajoOeste = (int) (capacity * 0.10);
        int palcosOeste = (int) (capacity * 0.06);
        int superiorOeste = (int) (capacity * 0.23);

        int especialBajoEste = (int) (capacity * 0.10);
        int palcosEste = (int) (capacity * 0.06);
        int superiorEste = capacity
                - especialBajoNorte - palcosNorte - superiorNorte
                - especialBajoSur - palcosSur - superiorSur
                - especialBajoOeste - palcosOeste - superiorOeste
                - especialBajoEste - palcosEste;

        List<StadiumSector> sectors = List.of(
                sector(ESPECIAL_BAJO_NORTE, "Especial Bajo Norte", especialBajoNorte, SectorType.GENERAL),
                sector(PALCOS_NORTE, "Palcos Norte", palcosNorte, SectorType.VIP),
                sector(SUPERIOR_NORTE, "Superior Norte", superiorNorte, SectorType.GENERAL),
                sector(ESPECIAL_BAJO_SUR, "Especial Bajo Sur", especialBajoSur, SectorType.GENERAL),
                sector(PALCOS_SUR, "Palcos Sur", palcosSur, SectorType.VIP),
                sector(SUPERIOR_SUR, "Superior Sur", superiorSur, SectorType.GENERAL),
                sector(ESPECIAL_BAJO_OESTE, "Especial Bajo Oeste", especialBajoOeste, SectorType.GENERAL),
                sector(PALCOS_OESTE, "Palcos Oeste", palcosOeste, SectorType.VIP),
                sector(SUPERIOR_OESTE, "Superior Oeste", superiorOeste, SectorType.GENERAL),
                sector(ESPECIAL_BAJO_ESTE, "Especial Bajo Este", especialBajoEste, SectorType.GENERAL),
                sector(PALCOS_ESTE, "Palcos Este", palcosEste, SectorType.VIP),
                sector(SUPERIOR_ESTE, "Superior Este", superiorEste, SectorType.GENERAL));

        saveStadium(stadiums, key, name, city, country, sectors, StadiumLayout.AZTECA);
    }

    private void saveStadium(Map<String, Stadium> stadiums, String key, String name, String city,
            String country, List<StadiumSector> sectors, StadiumLayout layout) {

        Stadium stadium = new Stadium();
        stadium.setName(name);
        stadium.setCity(city);
        stadium.setCountry(country);
        stadium.setSectors(sectors);
        stadium.setLayout(layout);

        stadiums.put(key, stadiumRepository.save(stadium));
    }

    private StadiumSector sector(String id, String name, int capacity, SectorType type) {
        StadiumSector sector = new StadiumSector();
        sector.setId(id);
        sector.setName(name);
        sector.setCapacity(capacity);
        sector.setType(type);
        return sector;
    }

    // ---------- PARTIDOS (72) ----------

    private void seedMatches(Map<String, Team> teams, Map<String, Stadium> stadiums) {
        List<Match> matches = new ArrayList<>();

        // Fecha 1
        add(matches, teams, stadiums, "Mexico", "Sudafrica", "azteca", 2026, 6, 11, 15, 0);
        add(matches, teams, stadiums, "Corea del Sur", "Chequia", "akron", 2026, 6, 11, 21, 0);
        add(matches, teams, stadiums, "Canada", "Bosnia y Herzegovina", "bmo", 2026, 6, 12, 15, 0);
        add(matches, teams, stadiums, "Estados Unidos", "Paraguay", "sofi", 2026, 6, 12, 21, 0);
        add(matches, teams, stadiums, "Australia", "Turquia", "bcplace", 2026, 6, 13, 0, 0);
        add(matches, teams, stadiums, "Qatar", "Suiza", "levis", 2026, 6, 13, 15, 0);
        add(matches, teams, stadiums, "Brasil", "Marruecos", "metlife", 2026, 6, 13, 18, 0);
        add(matches, teams, stadiums, "Haiti", "Escocia", "gillette", 2026, 6, 13, 21, 0);
        add(matches, teams, stadiums, "Alemania", "Curazao", "nrg", 2026, 6, 14, 13, 0);
        add(matches, teams, stadiums, "Paises Bajos", "Japon", "att", 2026, 6, 14, 16, 0);
        add(matches, teams, stadiums, "Costa de Marfil", "Ecuador", "lincoln", 2026, 6, 14, 19, 0);
        add(matches, teams, stadiums, "Suecia", "Tunez", "bbva", 2026, 6, 14, 21, 0);
        add(matches, teams, stadiums, "España", "Cabo Verde", "mercedes", 2026, 6, 15, 12, 0);
        add(matches, teams, stadiums, "Belgica", "Egipto", "lumen", 2026, 6, 15, 15, 0);
        add(matches, teams, stadiums, "Arabia Saudita", "Uruguay", "hardrock", 2026, 6, 15, 18, 0);
        add(matches, teams, stadiums, "Iran", "Nueva Zelanda", "sofi", 2026, 6, 15, 21, 0);
        add(matches, teams, stadiums, "Francia", "Senegal", "metlife", 2026, 6, 16, 15, 0);
        add(matches, teams, stadiums, "Irak", "Noruega", "gillette", 2026, 6, 16, 18, 0);
        add(matches, teams, stadiums, "Argentina", "Argelia", "arrowhead", 2026, 6, 16, 21, 0);
        add(matches, teams, stadiums, "Austria", "Jordania", "levis", 2026, 6, 17, 0, 0);
        add(matches, teams, stadiums, "Portugal", "Republica Democratica del Congo", "nrg", 2026, 6, 17, 13, 0);
        add(matches, teams, stadiums, "Inglaterra", "Croacia", "att", 2026, 6, 17, 16, 0);
        add(matches, teams, stadiums, "Ghana", "Panama", "bmo", 2026, 6, 17, 19, 0);
        add(matches, teams, stadiums, "Uzbekistan", "Colombia", "azteca", 2026, 6, 17, 22, 0);

        // Fecha 2
        add(matches, teams, stadiums, "Chequia", "Sudafrica", "mercedes", 2026, 6, 18, 12, 0);
        add(matches, teams, stadiums, "Suiza", "Bosnia y Herzegovina", "sofi", 2026, 6, 18, 15, 0);
        add(matches, teams, stadiums, "Mexico", "Corea del Sur", "akron", 2026, 6, 18, 21, 0);
        add(matches, teams, stadiums, "Canada", "Qatar", "bcplace", 2026, 6, 18, 21, 0);
        add(matches, teams, stadiums, "Estados Unidos", "Australia", "lumen", 2026, 6, 19, 15, 0);
        add(matches, teams, stadiums, "Escocia", "Marruecos", "gillette", 2026, 6, 19, 18, 0);
        add(matches, teams, stadiums, "Brasil", "Haiti", "lincoln", 2026, 6, 19, 21, 0);
        add(matches, teams, stadiums, "Turquia", "Paraguay", "levis", 2026, 6, 19, 21, 0);
        add(matches, teams, stadiums, "Paises Bajos", "Suecia", "nrg", 2026, 6, 20, 13, 0);
        add(matches, teams, stadiums, "Alemania", "Costa de Marfil", "bmo", 2026, 6, 20, 16, 0);
        add(matches, teams, stadiums, "Ecuador", "Curazao", "arrowhead", 2026, 6, 20, 20, 0);
        add(matches, teams, stadiums, "Tunez", "Japon", "bbva", 2026, 6, 21, 0, 0);
        add(matches, teams, stadiums, "España", "Arabia Saudita", "mercedes", 2026, 6, 21, 12, 0);
        add(matches, teams, stadiums, "Belgica", "Iran", "sofi", 2026, 6, 21, 15, 0);
        add(matches, teams, stadiums, "Uruguay", "Cabo Verde", "hardrock", 2026, 6, 21, 18, 0);
        add(matches, teams, stadiums, "Nueva Zelanda", "Egipto", "bcplace", 2026, 6, 21, 21, 0);
        add(matches, teams, stadiums, "Argentina", "Austria", "att", 2026, 6, 22, 13, 0);
        add(matches, teams, stadiums, "Francia", "Irak", "lincoln", 2026, 6, 22, 17, 0);
        add(matches, teams, stadiums, "Noruega", "Senegal", "metlife", 2026, 6, 22, 20, 0);
        add(matches, teams, stadiums, "Jordania", "Argelia", "levis", 2026, 6, 22, 23, 0);
        add(matches, teams, stadiums, "Portugal", "Uzbekistan", "nrg", 2026, 6, 23, 13, 0);
        add(matches, teams, stadiums, "Inglaterra", "Ghana", "gillette", 2026, 6, 23, 16, 0);
        add(matches, teams, stadiums, "Panama", "Croacia", "bmo", 2026, 6, 23, 19, 0);
        add(matches, teams, stadiums, "Colombia", "Republica Democratica del Congo", "akron", 2026, 6, 23, 22, 0);

        // Fecha 3 (partidos simultaneos por grupo)
        add(matches, teams, stadiums, "Bosnia y Herzegovina", "Qatar", "lumen", 2026, 6, 24, 15, 0);
        add(matches, teams, stadiums, "Escocia", "Brasil", "hardrock", 2026, 6, 24, 18, 0);
        add(matches, teams, stadiums, "Marruecos", "Haiti", "mercedes", 2026, 6, 24, 18, 0);
        add(matches, teams, stadiums, "Chequia", "Mexico", "azteca", 2026, 6, 24, 21, 0);
        add(matches, teams, stadiums, "Sudafrica", "Corea del Sur", "bbva", 2026, 6, 24, 21, 0);
        add(matches, teams, stadiums, "Suiza", "Canada", "bcplace", 2026, 6, 24, 21, 0);
        add(matches, teams, stadiums, "Ecuador", "Alemania", "metlife", 2026, 6, 25, 16, 0);
        add(matches, teams, stadiums, "Curazao", "Costa de Marfil", "lincoln", 2026, 6, 25, 16, 0);
        add(matches, teams, stadiums, "Japon", "Suecia", "att", 2026, 6, 25, 19, 0);
        add(matches, teams, stadiums, "Tunez", "Paises Bajos", "arrowhead", 2026, 6, 25, 19, 0);
        add(matches, teams, stadiums, "Turquia", "Estados Unidos", "sofi", 2026, 6, 25, 22, 0);
        add(matches, teams, stadiums, "Paraguay", "Australia", "levis", 2026, 6, 25, 22, 0);
        add(matches, teams, stadiums, "Noruega", "Francia", "gillette", 2026, 6, 26, 15, 0);
        add(matches, teams, stadiums, "Senegal", "Irak", "bmo", 2026, 6, 26, 15, 0);
        add(matches, teams, stadiums, "Cabo Verde", "Arabia Saudita", "nrg", 2026, 6, 26, 20, 0);
        add(matches, teams, stadiums, "Uruguay", "España", "akron", 2026, 6, 26, 20, 0);
        add(matches, teams, stadiums, "Egipto", "Iran", "lumen", 2026, 6, 26, 23, 0);
        add(matches, teams, stadiums, "Nueva Zelanda", "Belgica", "bcplace", 2026, 6, 26, 23, 0);
        add(matches, teams, stadiums, "Panama", "Inglaterra", "metlife", 2026, 6, 27, 17, 0);
        add(matches, teams, stadiums, "Croacia", "Ghana", "lincoln", 2026, 6, 27, 17, 0);
        add(matches, teams, stadiums, "Colombia", "Portugal", "hardrock", 2026, 6, 27, 19, 0);
        add(matches, teams, stadiums, "Republica Democratica del Congo", "Uzbekistan", "mercedes", 2026, 6, 27, 19, 0);
        add(matches, teams, stadiums, "Argelia", "Austria", "arrowhead", 2026, 6, 27, 22, 0);
        add(matches, teams, stadiums, "Jordania", "Argentina", "att", 2026, 6, 27, 22, 0);

        matchRepository.saveAll(matches);
    }

    private void add(List<Match> matches, Map<String, Team> teams, Map<String, Stadium> stadiums,
            String local, String away, String stadiumKey,
            int year, int month, int day, int hour, int minute) {

        Stadium stadium = stadiums.get(stadiumKey);

        Match match = new Match();
        match.setStadiumId(stadium.getId());
        match.setLocalTeamId(teams.get(local).getId());
        match.setAwayTeamId(teams.get(away).getId());
        match.setDate(LocalDateTime.of(year, month, day, hour, minute));
        match.setStage(MatchStage.GROUP_STAGE);
        match.setSectorsPrice(buildPrices(stadium));

        matches.add(match);
    }

    /**
     * El precio base se deriva de la capacidad del estadio (las sedes mas
     * grandes reciben los partidos de mayor demanda). Recorre los sectores
     * que tenga ese estadio en particular -sin asumir cuantos son ni que
     * layout usa- y les aplica el multiplicador segun su nivel, para que
     * sirva igual con el layout generico (4 sectores) que con el del Azteca
     * (12) o cualquier layout custom que se agregue despues.
     */
    private List<MatchSector> buildPrices(Stadium stadium) {
        int capacity = stadium.getSectors().stream().mapToInt(StadiumSector::getCapacity).sum();
        BigDecimal base = BigDecimal.valueOf(120 + (capacity / 1000));

        return stadium.getSectors().stream()
                .map(sector -> new MatchSector(sector.getId(), base.multiply(priceMultiplier(sector))))
                .toList();
    }

    /**
     * VIP (palcos) siempre x6. Entre los sectores GENERAL, los que estan mas
     * cerca de la cancha ("especial bajo" en el Azteca, "platea central" en
     * el generico) valen x2; el resto (el nivel mas alto/barato) paga la base.
     */
    private BigDecimal priceMultiplier(StadiumSector sector) {
        if (sector.getType() == SectorType.VIP) {
            return BigDecimal.valueOf(6);
        }
        if (sector.getId().startsWith("especial-bajo") || sector.getId().equals(PLATEA_CENTRAL)) {
            return BigDecimal.valueOf(2);
        }
        return BigDecimal.ONE;
    }

}
