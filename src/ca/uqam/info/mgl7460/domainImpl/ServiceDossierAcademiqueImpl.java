package domainImpl;

import domain.Etudiant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import domain.Cours;
import domain.GroupeCours;
import domain.Inscription;
import domain.Programme;
import domain.ServiceDossierAcademique;
import domain.Session;

public class ServiceDossierAcademiqueImpl implements ServiceDossierAcademique {

    HashMap<String, Etudiant> listeEtudiants = new HashMap<>();
    HashMap<String, Cours> listeCours = new HashMap<>();
    HashMap<String, GroupeCours> listeGroupeCours = new HashMap<>();
    HashMap<String, Programme> listeProgrammes = new HashMap<>();
    List<Inscription> listeInscriptions = new ArrayList<>();

    @Override
    public Etudiant creerEtudiant(String prenom, String nom, String codePermanent) {
        Etudiant e = new EtudiantImpl(nom, prenom, codePermanent, null, 0,
                0, null);
        listeEtudiants.put(codePermanent, e);
        return e;
    }

    @Override
    public Programme creerProgramme(String codeProg, String titre, int nombreCredits) {
        Programme p = new ProgrammeImpl(codeProg, titre, nombreCredits, null);
        listeProgrammes.put(codeProg, p);
        return p;
    }

    @Override
    public Cours creerCours(String sigle, String titre, String description, int nombreCredits,
            Cours... prerequis) {
        Cours c = new CoursImpl(sigle, titre, description, nombreCredits, Arrays.asList(prerequis));
        listeCours.put(sigle, c);
        return c;
    }

    @Override
    public GroupeCours creerGroupeCours(Cours crs, int annee, Session session, String enseignant) {
        GroupeCours g = new GroupeCoursImpl(crs, annee, session, enseignant, null);
        listeGroupeCours.put(g.getID(), g);
        return g;
    }

    @Override
    public Inscription inscrireEtudiantCours(Etudiant et, GroupeCours gpeCours) {
        Inscription i = new InscriptionImpl(0, false, gpeCours, et);
        listeInscriptions.add(i);
        et.inscrireGroupeCours(gpeCours);
        return i;
    }

    @Override
    public Etudiant inscrireEtudiantProgramme(Etudiant etud, Programme prog) {
        etud.inscrireProgramme(prog);
        return etud;
    }

    @Override
    public void saisirNote(Etudiant etud, GroupeCours gpeCours, float note) {
        for (Inscription i : listeInscriptions) {
            if (i.getEtudiant() == etud && i.getGroupeCours() == gpeCours)
                etud.setNoteGroupeCours(gpeCours, note);
        }
    }

    @Override
    public float getMoyenne(Etudiant etud) {
        return etud.getMoyenneCumulative();
    }

    @Override
    public float getNombreCreditsCompletes(Etudiant etud) {
        return etud.getNombreCreditsReussis();
    }

    @Override
    public void chargerDonnees() {
        // méthode non implémentée car non utile d'après les consignes
    }

    @Override
    public Etudiant getEtudiantAvecCodePermanent(String code) {
        Etudiant etudiantTrouve = null;
        for (Etudiant e : listeEtudiants.values()) {
            if (e.getCodePermanent() == code)
                etudiantTrouve = e;
        }
        return etudiantTrouve;
    }

    @Override
    public Cours getCoursAvecSigle(String sigle) {
        Cours coursTrouve = null;
        for (Cours c : listeCours.values()) {
            if (c.getSigle() == sigle)
                coursTrouve = c;
        }
        return coursTrouve;

    }

    @Override
    public Programme getProgrammeAvecCode(String code) {
        Programme programmeTrouve = null;
        for (Programme p : listeProgrammes.values()) {
            if (p.getCodeProgramme() == code)
                programmeTrouve = p;
        }
        return programmeTrouve;
    }

    @Override
    public GroupeCours getGroupeCoursAvecCode(String code) {
        GroupeCours groupeCoursTrouve = null;
        boolean trouve = false;
        for (GroupeCours g : listeGroupeCours.values()) {
            if (!trouve) {
                if (g.getID().equals(code)) {
                    groupeCoursTrouve = g;
                    trouve = true;
                    return groupeCoursTrouve;
                }
            }
        }
        return groupeCoursTrouve;
    }

}
