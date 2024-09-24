package domainImpl;

import java.util.Iterator;
import java.util.List;

import domain.Cours;
// import ca.uqam.info.mgl7460.domain.Cours;

public class CoursImpl implements Cours {

    private String sigle;
    private String titre;
    private String description;
    private int nombreCredits;
    private List<Cours> prerequis;

    public CoursImpl(String sigle, String titre, String description, int nombreCredits, List<Cours> prerequis) {
        this.sigle = sigle;
        this.titre = titre;
        this.description = description;
        this.nombreCredits = nombreCredits;
        this.prerequis = prerequis;
    }

    @Override
    public String getSigle() {
        return this.sigle;
    }

    @Override
    public String getTitre() {
        return this.titre;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public int getNombreCredits() {
        return this.nombreCredits;
    }

    @Override
    public Iterator<Cours> getPrerequis() {
        return this.prerequis.iterator();
    }

    @Override
    public Cours ajouterPrerequis(Cours prerequis) {
        if (this.prerequis.add(prerequis))
            return prerequis;
        else
            return null;
    }

    @Override
    public Cours retirerPrerequis(Cours prerequis) {
        if (this.prerequis.remove(prerequis))
            return prerequis;
        else
            return null;
    }

}
