package domainImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import domain.Cours;
import domain.Etudiant;
import domain.GroupeCours;
import domain.Inscription;
import domain.Session;

public class GroupeCoursImpl implements GroupeCours {

    private Cours cours;
    private int annee;
    private Session session;
    private String enseignant;
    private List<Inscription> inscriptions;

    public GroupeCoursImpl(Cours cours, int annee, Session session, String enseignant, List<Inscription> inscriptions) {
        this.cours = cours;
        this.annee = annee;
        this.session = session;
        this.enseignant = enseignant;
        this.inscriptions = inscriptions;
    }

    @Override
    public Cours getCours() {
        return this.cours;

    }

    @Override
    public int getAnnee() {
        return this.annee;
    }

    @Override
    public Session getSession() {
        return this.session;
    }

    @Override
    public String getEnseignant() {
        return this.enseignant;
    }

    @Override
    public Iterator<Inscription> getInscriptions() {

        return this.inscriptions.iterator();
    }

    @Override
    public Iterator<Etudiant> getEtudiantsInscrits() {

        List<Etudiant> etudiant = new ArrayList<Etudiant>();

        for (int i = 0; i < this.inscriptions.size(); i++) {
            etudiant.add(this.inscriptions.get(i).getEtudiant());
        }
        if (etudiant.isEmpty()) {
            System.out.println("Attention Liste etudiant inscrit est vide!");
        }

        return etudiant.iterator();

    }

    @Override
    public Inscription inscrireEtudiant(Etudiant etud) {

        Inscription i = null;
        boolean trouve = false;

        // vérifie s'il y a des inscriptions
        if (this.inscriptions != null) {
            // vérifie si l'étudiant n'est pas déjà inscrit au groupeCours
            if (estInscrit(etud)) {
                // s'il l'est, retourner son inscription
                int cpt = 0;
                while (cpt < this.inscriptions.size() && trouve == false) {
                    if ((this.inscriptions.get(cpt).getEtudiant() == etud
                            && this.inscriptions.get(cpt).getGroupeCours().getID().equals(this.getID()))) {
                        trouve = true;
                        i = this.inscriptions.get(cpt);
                        System.out.println("l'étudiant est déjà inscrit à ce GC " + this.getID());
                        return i;
                    }
                }
                cpt++;
            }
        }
        // s'il ne l'est pas ou s'il n'a aucune inscription, en créer une et l'ajouter à
        // la liste des inscriptions du groupe cours
        if (this.inscriptions == null || !trouve) {

            // créer une nouvelle inscription pour ce groupeCours
            i = new InscriptionImpl(0, false, this, etud);

            if (this.inscriptions == null)
                this.inscriptions = new ArrayList<>();
            this.inscriptions.add(i);

            // transmission de l'inscription au groupe cours
            etud.inscrireGroupeCours(this);

        } else
            System.out.println("état de l'inscription : " + this.inscriptions);

        return i;

    }

    @Override
    public boolean estInscrit(Etudiant etud) {
        if (this.inscriptions != null) {
            for (Inscription in : this.inscriptions) {
                if (in.getEtudiant().equals(etud)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public Inscription desinscrireEtudiant(Etudiant etud) {
        Inscription ins = null;
        for (int i = 0; i < this.inscriptions.size(); i++) {
            if (this.inscriptions.get(i).getEtudiant().equals(etud)) {
                ins = this.inscriptions.get(i);
                this.inscriptions.remove(i);
                return ins;
            }
        }
        return ins;
    }

    @Override
    public float getNoteEtudiant(Etudiant etud) {

        for (int i = 0; i < this.inscriptions.size(); i++) {
            if (this.inscriptions.get(i).getEtudiant().equals(etud)) {
                return this.inscriptions.get(i).getNoteNumerique();
            }
        }
        return -1;

    }

    @Override
    public void setNoteEtudiant(Etudiant etud, float note) {
        for (int i = 0; i < this.inscriptions.size(); i++) {
            if (this.inscriptions.get(i).getEtudiant().equals(etud)) {
                this.inscriptions.get(i).setNoteNumerique(note);
            }
        }
    }

    /**
     * retourne une chaine de caractères permettant d'identifier uniquement
     * le groupe cours. C'est une concaténation de champs
     * 
     * @return leSigleDuCours-annee-session
     */
    @Override
    public String getID() {
        return this.cours.getSigle() + "-" + this.annee + "-" + this.session;
    }

}
