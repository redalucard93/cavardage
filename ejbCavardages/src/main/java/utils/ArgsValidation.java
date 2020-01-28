package utils;

import exceptions.DonneesInvalidesException;
import exceptions.VoyageNonTermineException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alphalion27 on 04/11/17.
 */
public class ArgsValidation {

    public static void verifierChaine(String s) throws DonneesInvalidesException {
        if(s == null || s.equals("")){
            throw new DonneesInvalidesException();
        }
    }

    public static void verifierEntier(int n) throws DonneesInvalidesException {
        if(n < 0){
            throw new DonneesInvalidesException();
        }
    }

    public static void verifierFloat(float f) throws DonneesInvalidesException {
        if(f < 0){
            throw new DonneesInvalidesException();
        }
    }

    public static void verifierListFloat(List<Float> liste) throws DonneesInvalidesException {
        for(float f : liste){
           verifierFloat(f);
        }
    }

    public static void verifierDate(LocalDate d) throws DonneesInvalidesException{
        if(d == null){
            throw new DonneesInvalidesException();
        }
    }

    public static void verifierHeure(LocalDateTime d) throws DonneesInvalidesException{
        if(d == null){
            throw new DonneesInvalidesException();
        }
    }

    public static void verifierDateDeNaissance(LocalDate d) throws DonneesInvalidesException {
        verifierDate(d);
        if(d.compareTo(LocalDate.now()) >= 0){
            throw new DonneesInvalidesException();
        }
    }

    public static void verifierHeureDepart(LocalDateTime d) throws DonneesInvalidesException {
        verifierHeure(d);
        if(d.compareTo(LocalDateTime.now()) < 0){
            throw new DonneesInvalidesException();
        }
    }

    public static void verifierHeureArrivee(LocalDateTime d) throws DonneesInvalidesException, VoyageNonTermineException {
        verifierHeure(d);
        if(d.compareTo(LocalDateTime.now()) < 0){
            throw new VoyageNonTermineException();
        }
    }

    public static void verifierListHeures(List<LocalDateTime> l) throws DonneesInvalidesException{
        for (LocalDateTime i:l) {
            verifierHeure(i);
        }
    }

    public static void verifierListEntiers(List<Integer> l) throws DonneesInvalidesException{
        for (int i:l) {
            verifierEntier(i);
        }
    }

    public static void verifierDoublonsEntiers(List<Integer> liste) throws DonneesInvalidesException {
        for (Integer n : liste) {
            List<Integer> l = new ArrayList<>(liste);
            l.remove(n);

            if(l.contains(n)){
                throw new DonneesInvalidesException();
            }
        }
    }

    public static void verifierDoublonsLocalDateTime(List<LocalDateTime> liste) throws DonneesInvalidesException {
        for (LocalDateTime d : liste) {
            List<LocalDateTime> l = new ArrayList<>(liste);
            l.remove(d);

            if(l.contains(d)){
                throw new DonneesInvalidesException();
            }
        }
    }
}
