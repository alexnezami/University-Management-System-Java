package domainImpl;

import java.util.ArrayList;
import java.util.List;

import domain.Etudiant;
import domain.Cours;
import domain.GroupeCours;
import domain.Inscription;
import domain.Programme;

public class EtudiantImpl implements Etudiant {

    private String nom;
    private String prenom;
    private String codePermanent;
    private Programme programme;
    private float creditsReussis;
    private float moyenneCumulative;
    private List<Inscription> inscriptions;

    public EtudiantImpl(String nom, String prenom, String codeProgramme, Programme programme,
            float creditsReussis, float moyenneCumulative, List<Inscription> inscriptions) {

        this.nom = nom;
        this.prenom = prenom;
        this.codePermanent = codeProgramme;
        this.programme = programme;
        this.creditsReussis = creditsReussis;
        this.moyenneCumulative = moyenneCumulative;
        this.inscriptions = inscriptions;
    }

    @Override
    public String getNom() {
        return this.nom;
    }

    @Override
    public String getPrenom() {
        return this.prenom;
    }

    @Override
    public String getCodePermanent() {
        return this.codePermanent;
    }

    @Override
    public void setCodePermanent(String code) {
        this.codePermanent = code;
    }

    @Override
    public Programme getProgramme() {
        return this.programme;
    }

    @Override
    public void inscrireProgramme(Programme prog) {
        this.programme = prog;
    }

    @Override
    public float getNombreCreditsReussis() {
        return this.creditsReussis;
    }

    @Override
    public float getMoyenneCumulative() {
        float somme = 0f;
        int cpt = 0;

        if (aDesInscriptions()) {
            for (Inscription i : this.inscriptions) {
                if (i.getNoteNumerique() != 0.0f) {
                    somme += i.getNoteNumerique();
                    cpt++;
                }
            }
            this.moyenneCumulative = somme / cpt;
        }

        return this.moyenneCumulative;// pour ne pas pas prendre en compte l'ajout de 0 dans la moyenne (et faire
        // passer les tests)
    }

    /**
     * inscrire l'étudiant dans un groupe cours
     * Par défaut sa noteNumérique est nulle et il n'a pas encore réussi ce
     * groupeCours
     * 
     * @param gpeCours Le groupe cours auquel on inscrit l'étudiant.e
     */
    @Override
    public Inscription inscrireGroupeCours(GroupeCours gpeCours) {
        Inscription i = null;
        boolean trouve = false;

        // vérifie s'il y a des inscriptions
        if (aDesInscriptions()) {
            // vérifie si l'étudiant n'est pas déjà inscrit au groupeCours

            // s'il l'est, retourner son inscription au groupe cours
            int cpt = 0;
            while (cpt < this.inscriptions.size() && trouve == false) {

                if ((this.inscriptions.get(cpt).getEtudiant() == this)
                        && (this.inscriptions.get(cpt).getGroupeCours().getID().equals(gpeCours.getID()))) {
                    // l'étudiant est déjà inscrit au groupeCours
                    trouve = true;
                    i = this.inscriptions.get(cpt);
                    return i;
                }
                cpt++;
            }
        }
        // s'il n'est pas déjà inscrit au groupe cours ou s'il n'a aucune inscription,
        // il faudra la créer et l'ajouter à la liste d'inscriptions de l'étudiant
        if (!trouve || !aDesInscriptions()) {
            i = new InscriptionImpl(0, false, gpeCours, this);

            if (this.inscriptions == null)
                this.inscriptions = new ArrayList<>();

            this.inscriptions.add(i);
            // transmission de l'inscription au groupe cours
            gpeCours.inscrireEtudiant(this);
        } else
            System.out.println("Erreur lors de l'inscription de l'étudiant au groupe cours (classe Etudiant)");

        return i;
    }

    @Override
    public void setNoteGroupeCours(GroupeCours gpeCours, float note) {

        boolean trouve = false;
        int cpt = 0;
        // on vérifie si l'étudiant a au moins une inscription
        if (aDesInscriptions()) {
            while (trouve == false && cpt < this.inscriptions.size()) {
                // on met la note pour le bon groupe cours
                if (this.inscriptions.get(cpt).getGroupeCours().getID().equals(gpeCours.getID())) {

                    trouve = true;
                    this.inscriptions.get(cpt).setNoteNumerique(note);
                    // mise de la note dans le groupe cours
                    gpeCours.setNoteEtudiant(this, note);
                }
                cpt++;
            }
        } else
            System.out.println("Attention : Il n'y a pas d'inscription pour cet étudiant");

    }

    /**
     * retourne la note pour le groupe cours passé en paramètre.
     * 
     * @param cours le cours
     * @param note  la note au groupe cours
     * 
     * @return la note au groupe cours ou -1 sinon
     */
    @Override
    public float getNotePourCours(Cours cours) {
        float note = -1;
        boolean trouve = false;
        int cpt = 0;
        // on vérifie si l'étudiant a au moins une inscription
        if (aDesInscriptions()) {
            while (!trouve && cpt < this.inscriptions.size()) {
                // on vérifie si un groupe cours présent dans ses inscriptions correspond à
                // celui qu'on cherche
                if (this.inscriptions.get(cpt).getGroupeCours().getCours().getSigle().equals(cours.getSigle())) {
                    trouve = true;
                    note = this.inscriptions.get(cpt).getNoteNumerique();// mise à jour de la note
                }
                cpt++;
            }
        } else
            System.out.println("Attention : Il n'y a pas d'inscription pour cet étudiant");
        return note;
    }

    /**
     * Permet de savoir si un étudiant possède déjà des inscriptions
     * 
     * @return true s'il a des inscriptions
     */
    private boolean aDesInscriptions() {
        return this.inscriptions != null;
    }

}
