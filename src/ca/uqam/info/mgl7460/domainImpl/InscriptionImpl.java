package domainImpl;
import domain.Etudiant;
import domain.GroupeCours;
import domain.Inscription;

public class InscriptionImpl implements Inscription {

        private Etudiant etudiant;
        private float noteNumerique;
        private boolean reussi;
        private GroupeCours groupeCours;

        public InscriptionImpl (float noteNumerique, boolean reussi, GroupeCours groupeCours,
        Etudiant etudiant){
            this.noteNumerique = noteNumerique;
            this.reussi = reussi;
            this.etudiant = etudiant;
            this.groupeCours = groupeCours;
        }

    @Override
    public float getNoteNumerique(){
        return this.noteNumerique;
    }

    @Override
    public boolean isReussi(){
        return this.reussi;
    }

    @Override
    public Etudiant getEtudiant(){
        return this.etudiant;
    }

    @Override
    public GroupeCours getGroupeCours(){
        return this.groupeCours;
    }
    @Override
    public void setNoteNumerique(float note){
        this.noteNumerique = note;
    }

}
