/**
 * Ivaylo Ivanov
 * IVAI08039506
 */
public class batnav extends Pep8{

    static final char debut = 'A'-2;
    static char[][] tableau = new char[10][('R'-'A')+4];
    static char choix = ' ';

    //Compte le nomber des cases avec bateaux dans le jeu
    static int bateau = 0;

    static final String MSG_ERR = "Erreur d'entree!";
    static final String MSG_FIN = "Aurevoir!";
    static final String MSG_RE = "Voulez-vous recommencer ? (Entree pour rejouer)";
    static final String MSG_SOL = "Entrer la description et la position des bateaux\n" +
                                  "selon le format suivant, separes par des espaces:\n" +
                                  "taille[p/m/g] orientation[h/v] colonne[A-R] rangée[1-9] :  ";

    public static void main(String[] args) {

        char grandeur;
        char orientation;
        char colonne;
        int rangee;



        do {
            //Creer tableau
            for (int i = 0; i < tableau.length; i++) {
                for (int k = 0; k < tableau[i].length; k++) {
                    if (i == 0) {
                        if (k <= 1 || k > 19) {
                            tableau[i][k] = ' ';
                        } else {
                            tableau[i][k] = (char) (('A' + k) - 2);
                        }
                    } else {
                        if (k == 0) {
                            tableau[i][0] = (char) ('0' + i);
                        } else if (k == 1 || k == 20) {
                            tableau[i][k] = '|';
                        } else {
                            tableau[i][k] = '~';
                        }
                    }
                }
            }

            //Imprimer tableau
            imprimer();

            //Boucle pour ajouter des bateaux
            Pep8.stro(MSG_SOL);
            do {
                grandeur = Pep8.chari();
                if (grandeur != 'p' && grandeur != 'm' && grandeur != 'g') {
                    break;
                }

                orientation = Pep8.chari();
                if (orientation != 'h' && orientation != 'v') {
                    break;
                }

                colonne = Pep8.chari();
                if (colonne < 'A' || colonne > 'R') {
                    break;
                }

                rangee = Pep8.deci();
                if (rangee < 1 || rangee > 9) {
                    break;
                }

                ajoutBateau(grandeur, orientation, colonne, rangee);

                choix = Pep8.chari();
            } while (choix != '\n');

            //Imprimer tableau
            imprimer();
            viderTampon();

            //Boucle attaque bateux
            while (bateau>0) {
                for (int i = 0; i < tableau.length; i++) {
                    for (int k = 0; k < tableau[i].length; k++) {
                        if (tableau[i][k] == 'v' || tableau[i][k] == '>') {
                            stro("Feu à volonte");
                            do {
                                colonne = Pep8.chari();
                                if (colonne < 'A' || colonne > 'R') {
                                    break;
                                }

                                rangee = Pep8.deci();
                                if (rangee < 1 || rangee > 9) {
                                    break;
                                }

                                combat(colonne, rangee);

                                choix = Pep8.chari();
                            } while (choix != '\n');
                            imprimer();
                            viderTampon();
                        }
                    }
                }
            }

            viderTampon();
            stro(MSG_RE);
            choix = chari();
        }while(choix == '\n');
        stro(MSG_FIN);
    }

    /**
     * Ajouter bateau sur tableau
     * @param grandeur taille du bateau
     * @param orientation orientation du bateau
     * @param colonne coordonne - y
     * @param rangee coordonne - x
     */
    static void ajoutBateau(char grandeur, char orientation, char colonne, int rangee){
        int longueur;
        if(grandeur == 'p'){
            longueur = 1;
        }else if(grandeur == 'm'){
            longueur = 3;
        }else{
            longueur = 5;
        }
        for(int i=0;i<longueur;i++){
            if(orientation =='h'){
                if(tableau[rangee][colonne-debut+i] == 'v' || tableau[rangee][colonne-debut+i] == '>'
                        ||colonne-debut+i>tableau[rangee].length-2){
                    stro(MSG_ERR);
                    stop();
                }else{
                    tableau[rangee][colonne-debut+i] = '>';
                    bateau++;
                }

            }else{
                if(tableau[rangee][colonne-debut+i] == 'v' || tableau[rangee][colonne-debut+i] == '>'
                        ||rangee+i>tableau.length-1){
                    stro(MSG_ERR);
                    stop();
                }else{
                    tableau[rangee+i][colonne-debut] = 'v';
                    bateau++;
                }
            }
        }
    }

    /**
     * Vider le tampon et mettre choix =''
     */
    static void viderTampon(){
        while(choix!='\n'){
            choix = chari();
        }
    }

    /**
     * Imprimer tableau
     */
    static void imprimer(){
        for (int i = 0; i < tableau.length; i++) {
            for (int k = 0; k < tableau[i].length; k++) {
                System.out.print(tableau[i][k]);
            }
            System.out.println();
        }
    }

    /**
     * Verifier si coordonne saisi est un bateau
     * et si oui le detruire. Fonction recursive
     * pour verifier a x-1 x+1 et y-1 y+1 pour les coordonnes
     * ou se trouve un bateau
     * @param colonne coordonne - y
     * @param rangee coordonne - x
     */
    static void combat(char colonne, int rangee) {
        if (colonne >= 'A' && colonne <= 'R' && rangee >= 1 && rangee <= 9) {
            if (tableau[rangee][colonne - debut] == 'v' || tableau[rangee][colonne - debut] == '>') {
                    tableau[rangee][colonne - debut] = '*';
                    bateau--;
                    //Recursion
                    combat((char)(colonne-1),rangee);
                    combat((char)(colonne+1),rangee);
                    combat((colonne),rangee+1);
                    combat((colonne),rangee-1);
            } else{
                //Cas Base
                if(tableau[rangee][colonne - debut] !='*'){
                    tableau[rangee][colonne - debut] ='o';
                }
            }
        }
    }
}
