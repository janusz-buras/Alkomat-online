package com.example.alkomatonline

class Party {
    var partyID:Int?=null
    var weight:Double?=null
    var gender:Double?=null
    var partyName:String?=null
    var startDate:String?=null
    var pureAlcohol:Double?=null
    var promiles:Double?=null
    var finishDate:String?=null
    var sobrierty:String?=null
    var isFinish:String?=null

    constructor(partyID:Int, weight:Double, gender:Double, partyName:String, startDate:String, pureAlcohol:Double, promiles:Double, finishDate:String, sobriety:String, isFinish: String){
        this.partyID=partyID
        this.weight=weight
        this.gender=gender
        this.partyName=partyName
        this.startDate=startDate
        this.pureAlcohol=pureAlcohol
        this.promiles=promiles
        this.finishDate=finishDate
        this.sobrierty =sobriety
        this.isFinish=isFinish

    }
}