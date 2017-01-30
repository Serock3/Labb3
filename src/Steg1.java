import modell.CrystalModel;

public class Steg1 {
    public static void main(String[] args) {
        CrystalModel crystalModel = new CrystalModel(10);
        for (int i =0;i<10;i++)crystalModel.crystallizeOneIon();
        //while (crystalModel.crystallizeOneIon());
        System.out.println(crystalModel);
    }
}
