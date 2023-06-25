package com.example.plantsapp.custom

object Const {
    @JvmField
    var FieldNameAdd = "Name"
    var FieldFamilyAdd = "Family"
    var FieldDesAdd = "Description"
    var FieldKingdomAdd = "Kingdom"
    var FieldHagtagAdd = "Hashtag1"
    var FieldHagtag2Add = "Hashtag2"
    @JvmField
    var FieldImageAdd = "Image"
    @JvmField
    var refPlant = "Plants"

    //  Article
    var refArticle = "Articles"
    var FieldNameTusArticle = "Title"
    var FieldHagtagArticle = "Hashtag1"
    var FieldNamePersonArticle = "Writer"
    var FieldDateArticle = "Date"
    var FieldAvtArticle = "Avatar"
    var FieldDesArticle = "Content"
    var FieldAvtTusArticle = "Image"
    var FieldHagtag2Article = "Hashtag2"
    var article1 = "Article 1"
    var article2 = "Article 2"
    @JvmField
    var Writer1 = "Shally Monic"
    @JvmField
    var Writer2 = "Shivani Vora"
    @JvmField
    var Userid = ""
    @JvmField
    var User = "Users"
    @JvmField
    var likedplants = "Liked Plants"
    @JvmField
    var likedarticle = "Liked Articles"
    @JvmField
    var stringList: MutableList<String> = ArrayList()
    @JvmField
    var stringList2: MutableList<String> = ArrayList()
    @JvmStatic
    fun addStringToList(string: String) {
        if (!stringList.contains(string)) {
            stringList.add(string)
        }
    }

    fun removeStringFromList(string: String) {
        stringList.remove(string)
    }

    @JvmStatic
    fun addStringToList2(string: String) {
        if (!stringList2.contains(string)) {
            stringList2.add(string)
        }
    }

    fun removeStringFromList2(string: String) {
        stringList2.remove(string)
    }
}