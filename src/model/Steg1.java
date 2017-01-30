package model;

public class Steg1 {
    public static void main(String[] args) {
        CrystalModel crystalModel = new CrystalModel(100);
        //System.out.println(crystalModel.crystallizeOneIon());
        //for (int i =0;i<10;i++)crystalModel.crystallizeOneIon();
        makeCrystal(crystalModel);
        System.out.println(crystalModel);
    }

    public static void makeCrystal(CrystalModel crystalModel){
        while (crystalModel.crystallizeOneIon());
    }
}
