package domainImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import domain.Cours;
import domain.Programme;

public class ProgrammeImpl implements Programme {

    private String codeProgramme;
    private String titre;
    private int nombreCredits;
    private List<Cours> cours;

    public ProgrammeImpl(String codeProgramme, String titre, int nombreCredits, List<Cours> cours) {
        this.codeProgramme = codeProgramme;
        this.titre = titre;
        this.nombreCredits = nombreCredits;
        this.cours = cours;
    }

    /**
     * retourne le code du programme
     */
    @Override
    public String getCodeProgramme() {
        return this.codeProgramme;
    }

    /**
     * retourne le titre du programme
     */
    @Override
    public String getTitre() {
        return this.titre;
    }

    /**
     * retourne le nombre de crédits du programme
     */
    @Override
    public int getNombreCredits() {
        return this.nombreCredits;
    }

    /**
     * Ajouter un cours à la liste de cours du programme
     * 
     * @param unCours cours à ajouter
     */
    @Override
    public Cours ajouterCours(Cours unCours) {
        if (this.cours == null)
            this.cours = new ArrayList<>();

        if (cours.add(unCours))
            return unCours;
        else
            return null;
    }

    /**
     * retirer un cours de la liste de cours du programme
     * 
     * @param unCours cours à retirer
     */
    @Override
    public Cours enleverCours(Cours unCours) {
        Cours c = null;
        if (this.cours != null) {
            if (cours.remove(unCours))
                c = unCours;
        }
        return c;
    }

    /**
     * retourner la liste des cours du programme.
     */
    @Override
    public Iterator<Cours> getCours() {
        return this.cours.iterator();
    }

}
