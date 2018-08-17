package de.heilsen.ganzhornfest.app.data

import de.heilsen.ganzhornfest.domain.entity.*
import de.heilsen.ganzhornfest.domain.repository.Repository
import io.objectbox.Box
import io.objectbox.BoxStore

//TODO: make ObjectBoxCLubRepo a Singleton
class ObjectBoxClubRepository(boxStore: BoxStore, private val foodRepo: ObjectBoxFoodRepository, private val drinkRepo: ObjectBoxDrinkRepository, private val actionActionOfferRepo: ObjectBoxActionOfferRepository) : Repository<Club> {
    private var box: Box<ClubEntity> = boxStore.boxFor(ClubEntity::class.java)

    var asb = ClubEntity("Arbeiter-Samariter-Bund", "")
    var bsv = ClubEntity("Bowlingsportverein", "")
    var circoloItaliano = ClubEntity("Circolo Italiano")
    var dlrg = ClubEntity("DLRG")
    var downtownboys = ClubEntity("Downtownboys")
    var drkOrtsverein = ClubEntity("DRK Ortsverein")
    var fsv = ClubEntity("Fischerei- und Sportanglerverein", "")
    var freierKindergartenNeckarsulm = ClubEntity("Freier Kindergarten Neckarsulm")
    var georgspfadfinder = ClubEntity("Georgspfadfinder")
    var gesangsvereinConcordia = ClubEntity("Gesangsverein Concordia")
    var gesangsvereinLassallia = ClubEntity("Gesangsverein Lassallia")
    var griechischeGemeinde = ClubEntity("Griechische Gemeinde")
    var jugendfarm = ClubEntity("Jugendfarm")
    var jungeUnionStadtverband = ClubEntity("Junge Union Stadtverband")
    var jusos = ClubEntity("Jusos")
    var kathKirchenchorStDionysius = ClubEntity("Kath. Kirchenchor St. Dionysius")
    var kiwanisClub = ClubEntity("Kiwanis Club")
    var kolpingBlasorchester = ClubEntity("Kolping Blasorchester")
    var kolpingsfamilie = ClubEntity("Kolpingsfamilie")
    var kreatief = ClubEntity("Kreatief - Kultur im Unterland")
    var metropolitianJazzCommunity = ClubEntity("Metropolitian Jazz Community")
    var neckarValleyDancers = ClubEntity("Neckar Valley Dancers")
    var nsuFussballAktive = ClubEntity("NSU - Fußball Aktive")
    var nsuHandball = ClubEntity("NSU - Handball")
    var nsuKanu = ClubEntity("NSU - Kanu")
    var nsuKarate = ClubEntity("NSU - Karate")
    var nsuLeichtathletik = ClubEntity("NSU - Leichtathletik")
    var nsuRugby = ClubEntity("NSU - Rugby")
    var nsuTT1 = ClubEntity("NSU - Tischtennis 1")
    var nsuTT2 = ClubEntity("NSU - Tischtennis 2")
    var saengerbund = ClubEntity("Sängerbund")
    var scAmorbach = ClubEntity("SC Amorbach")
    var schuetzengilde = ClubEntity("Schützengilde")
    var spdOrtsverein = ClubEntity("SPD Ortsverein")
    var stoneHeads = ClubEntity("Stone Heads")
    var stPaulusClub = ClubEntity("St. Paulus Club")
    var sulmanafetza = ClubEntity("Sulmanafetza")
    var tauchclubWalhai = ClubEntity("Tauchclub Walhai")
    var tcSulmtal = ClubEntity("TC Sulmtal")
    var tierschutzvereinPfoetchenhilfeNeckarsulm = ClubEntity("Tierschutzverein Pfötchenhilfe Neckarsulm")
    var tuerksporNeckarsulm = ClubEntity("Türkspor Neckarsulm")
    var tuerksporNeckarsulmJugend = ClubEntity("Türkspor Neckarsulm - Jugendabteilung")
    var ufc = ClubEntity("UFC")
    var weinbauverein = ClubEntity("Weinbauverein")

    init {
        prefill()
    }

    override fun getAll(): List<Club> {
        return box.all.map { ClubConverter.from(it) }
    }


    override fun get(i: Int): Club {
        return ClubConverter.from(box.get(i.toLong()))
    }

    override fun get(name: String): Club {
        val query = box.query().equal(ClubEntity_.name, name).build()
        return Club("club", "club") //TODO: ClubConverter.from(query.findUnique()!!)
    }


    private fun prefill() {
        box.removeAll()

        asb.foods.add(foodRepo.linsenSpaetzleSaitenwuerste)
        asb.foods.add(foodRepo.apfelkuechle)
        bsv.foods.add(foodRepo.steakMitZwiebeln)
        bsv.foods.add(foodRepo.currywurst)
        bsv.foods.add(foodRepo.currywurstSpezial)
        bsv.foods.add(foodRepo.roteWurst)
        bsv.foods.add(foodRepo.pommes)
        circoloItaliano.foods.add(foodRepo.arancinoSiciliano)
        circoloItaliano.foods.add(foodRepo.arancinoMitNutella)
        circoloItaliano.foods.add(foodRepo.pasta)
        circoloItaliano.foods.add(foodRepo.paniniMitSalsiccia)
        circoloItaliano.foods.add(foodRepo.piadinaRomana)
        circoloItaliano.foods.add(foodRepo.pizza)
        downtownboys.foods.add(foodRepo.bratwurstInDerSeele)
        drkOrtsverein.foods.add(foodRepo.crepesPikant)
        drkOrtsverein.foods.add(foodRepo.crepesSuess)
        fsv.foods.add(foodRepo.geraeucherteForellenMitKartoffelsalat)
        fsv.foods.add(foodRepo.lachsUndAalbroetchen)
        fsv.foods.add(foodRepo.shrimpsMitSosse)
        freierKindergartenNeckarsulm.foods.add(foodRepo.crepesSuess)
        georgspfadfinder.foods.add(foodRepo.pizza)
        georgspfadfinder.foods.add(foodRepo.neckarsulmerRaedle)
        gesangsvereinConcordia.foods.add(foodRepo.schnitzelMitKartoffelsalatOderBrot)
        gesangsvereinConcordia.foods.add(foodRepo.wurstVomSud)
        gesangsvereinConcordia.foods.add(foodRepo.rauchfleischbrot)
        gesangsvereinConcordia.foods.add(foodRepo.kraeuterkaesbrot)
        gesangsvereinConcordia.foods.add(foodRepo.wurstsalat)
        gesangsvereinConcordia.foods.add(foodRepo.gefuellterHals)
        gesangsvereinConcordia.foods.add(foodRepo.schaelrippchenMitBrot)
        gesangsvereinLassallia.foods.add(foodRepo.hacksteaks)
        gesangsvereinLassallia.foods.add(foodRepo.steaks)
        gesangsvereinLassallia.foods.add(foodRepo.kartoffelsalat)
        gesangsvereinLassallia.foods.add(foodRepo.zwiebelkuchen)
        gesangsvereinLassallia.foods.add(foodRepo.kuchen)
        griechischeGemeinde.foods.add(foodRepo.souvlaki)
        jugendfarm.foods.add(foodRepo.langos)
        kathKirchenchorStDionysius.foods.add(foodRepo.crepesSuess)
        kiwanisClub.foods.add(foodRepo.flammkuchen)
        kolpingBlasorchester.foods.add(foodRepo.baguettes)
        kolpingsfamilie.foods.add(foodRepo.schupfnudeln)
        kolpingsfamilie.foods.add(foodRepo.sauerkraut)
        kolpingsfamilie.foods.add(foodRepo.leberUndBlutwurst)
        kolpingsfamilie.foods.add(foodRepo.besentoast)
        kolpingsfamilie.foods.add(foodRepo.kaeseUndRauchfleischbrot)
        kolpingsfamilie.foods.add(foodRepo.kaesewuerfel)
        kolpingsfamilie.foods.add(foodRepo.kuchen)
        kolpingsfamilie.foods.add(foodRepo.waffeln)
        kolpingsfamilie.foods.add(foodRepo.zuckerwatte)
        kolpingsfamilie.foods.add(foodRepo.bratwuersteMitKartoffelsalat)
        kreatief.foods.add(foodRepo.suesskartoffelchips)
        kreatief.foods.add(foodRepo.veganeCurrywurst)
        kreatief.foods.add(foodRepo.veganerHotDog)
        kreatief.foods.add(foodRepo.weizenwrapMitGegrilltemGemuese)
        metropolitianJazzCommunity.foods.add(foodRepo.metBurger)
        metropolitianJazzCommunity.foods.add(foodRepo.tapasTeller)
        metropolitianJazzCommunity.foods.add(foodRepo.wildbratwurst)
        metropolitianJazzCommunity.foods.add(foodRepo.kaesewuerfel)
        metropolitianJazzCommunity.foods.add(foodRepo.baguetteMitLachs)
        metropolitianJazzCommunity.foods.add(foodRepo.oliven)
        metropolitianJazzCommunity.foods.add(foodRepo.weisswurstfruehstueck)
        metropolitianJazzCommunity.foods.add(foodRepo.kuchen)
        metropolitianJazzCommunity.foods.add(foodRepo.zopf)
        neckarValleyDancers.foods.add(foodRepo.waffeln)
        neckarValleyDancers.foods.add(foodRepo.kartoffelpuffer)
        neckarValleyDancers.foods.add(foodRepo.maiskolben)
        nsuHandball.foods.add(foodRepo.grillwurst)
        nsuHandball.foods.add(foodRepo.currywurst)
        nsuHandball.foods.add(foodRepo.countryPotatoes)
        nsuHandball.foods.add(foodRepo.pommes)
        nsuKanu.foods.add(foodRepo.raclette)
        nsuTT2.foods.add(foodRepo.gebrannteMandeln)
        nsuTT2.foods.add(foodRepo.magenbrot)
        nsuTT2.foods.add(foodRepo.popcorn)
        nsuTT2.foods.add(foodRepo.sonstigeSuessigkeiten)
        saengerbund.foods.add(foodRepo.kuchen)
        scAmorbach.foods.add(foodRepo.haehnchen)
        scAmorbach.foods.add(foodRepo.wurst)
        scAmorbach.foods.add(foodRepo.pommes)
        schuetzengilde.foods.add(foodRepo.calamaris)
        schuetzengilde.foods.add(foodRepo.currywurst)
        schuetzengilde.foods.add(foodRepo.bogenwurst)
        schuetzengilde.foods.add(foodRepo.grillwurst)
        schuetzengilde.foods.add(foodRepo.steaks)
        schuetzengilde.foods.add(foodRepo.pommes)
        spdOrtsverein.foods.add(foodRepo.snacks)
        stPaulusClub.foods.add(foodRepo.spitzdappen)
        stPaulusClub.foods.add(foodRepo.germknoedel)
        stPaulusClub.foods.add(foodRepo.kaesspaetzle)
        stPaulusClub.foods.add(foodRepo.kuchen)
        sulmanafetza.foods.add(foodRepo.hamburger)
        tcSulmtal.foods.add(foodRepo.grillwurst)
        tcSulmtal.foods.add(foodRepo.currywurst)
        tierschutzvereinPfoetchenhilfeNeckarsulm.foods.add(foodRepo.nachosMitKaeseUeberbacken)
        tierschutzvereinPfoetchenhilfeNeckarsulm.foods.add(foodRepo.thaisuppe)
        tierschutzvereinPfoetchenhilfeNeckarsulm.foods.add(foodRepo.cheeseWaffle)
        tuerksporNeckarsulm.foods.add(foodRepo.doenerKebap)
        tuerksporNeckarsulm.foods.add(foodRepo.pommes)
        tuerksporNeckarsulm.foods.add(foodRepo.pfannkuchen)
        tuerksporNeckarsulm.foods.add(foodRepo.koefte)
        tuerksporNeckarsulm.foods.add(foodRepo.tuerkischeFleischGrillspezialitaeten)
        tuerksporNeckarsulm.foods.add(foodRepo.boerek)
        tuerksporNeckarsulm.foods.add(foodRepo.kuchen)
        tuerksporNeckarsulm.foods.add(foodRepo.tuerkischeSuessspeisen)
        tuerksporNeckarsulmJugend.foods.add(foodRepo.boerek)
        tuerksporNeckarsulmJugend.foods.add(foodRepo.sueBoerek)
        tuerksporNeckarsulmJugend.foods.add(foodRepo.sarma)
        tuerksporNeckarsulmJugend.foods.add(foodRepo.salate)
        tuerksporNeckarsulmJugend.foods.add(foodRepo.kuchen)
        tuerksporNeckarsulmJugend.foods.add(foodRepo.muffins)
        tuerksporNeckarsulmJugend.foods.add(foodRepo.putenwiener)
        tuerksporNeckarsulmJugend.foods.add(foodRepo.waffeln)
        //TODO: add foods after here

        box.put(
                asb,
                bsv,
                circoloItaliano,
                dlrg,
                downtownboys,
                drkOrtsverein,
                fsv,
                freierKindergartenNeckarsulm,
                georgspfadfinder,
                gesangsvereinConcordia,
                gesangsvereinLassallia,
                griechischeGemeinde,
                jugendfarm,
                jungeUnionStadtverband,
                jusos,
                kathKirchenchorStDionysius,
                kiwanisClub,
                kolpingBlasorchester,
                kolpingsfamilie,
                kreatief,
                metropolitianJazzCommunity,
                neckarValleyDancers,
                nsuFussballAktive,
                nsuHandball,
                nsuKanu,
                nsuKarate,
                nsuLeichtathletik,
                nsuRugby,
                nsuTT1,
                nsuTT2,
                saengerbund,
                scAmorbach,
                schuetzengilde,
                spdOrtsverein,
                stoneHeads,
                stPaulusClub,
                sulmanafetza,
                tauchclubWalhai,
                tcSulmtal,
                tierschutzvereinPfoetchenhilfeNeckarsulm,
                tuerksporNeckarsulm,
                tuerksporNeckarsulmJugend,
                ufc,
                weinbauverein
        )

    }

}
