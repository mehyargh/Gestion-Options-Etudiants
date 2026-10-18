package utilities;

import metiers.EtudiantBusiness;
import metiers.OptionBusiness;

public final class Services {
    public static final OptionBusiness OPTIONS;
    public static final EtudiantBusiness ETUDIANTS;

    static {
        OPTIONS = new OptionBusiness();
        ETUDIANTS = new EtudiantBusiness();
    }

    private Services() {
    }
}
