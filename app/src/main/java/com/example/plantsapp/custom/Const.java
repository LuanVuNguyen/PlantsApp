package com.example.plantsapp.custom;

import java.util.ArrayList;
import java.util.List;

public class Const {


    public static String FieldNameAdd = "Name";
    public static String FieldFamilyAdd = "Family";
    public static String FieldDesAdd = "Description";
    public static String FieldKingdomAdd = "Kingdom";
    public static String FieldHagtagAdd = "Hashtag1";
    public static String FieldHagtag2Add = "Hashtag2";
    public static String FieldImageAdd = "Image";
    public static String refPlant = "Plants";
//  Article
public static String refArticle = "Articles";
    public static String FieldNameTusArticle = "Title";
    public static String FieldHagtagArticle = "Hashtag1";
    public static String FieldNamePersonArticle = "Writer";
    public static String FieldDateArticle = "Date";
    public static String FieldAvtArticle = "Avatar";
    public static String FieldDesArticle = "Content";
    public static String FieldAvtTusArticle = "Image";
    public static String FieldHagtag2Article = "Hashtag2";
    public static String article1 = "Article 1";
    public static String article2 = "Article 2";

    public static List<String> stringList = new ArrayList<>();

    public static void addStringToList(String string) {
        if (!stringList.contains(string))
        {
            stringList.add(string);
        }
    }

    public static void removeStringFromList(String string) {
        stringList.remove(string);
    }

}
