package de.heilsen.ganzhornfest.data.objectbox

import de.heilsen.ganzhornfest.domain.entity.Drink
import de.heilsen.ganzhornfest.domain.repository.EntityNotFoundException
import de.heilsen.ganzhornfest.domain.repository.Repository
import io.objectbox.Box
import io.objectbox.BoxStore

class ObjectBoxDrinkRepository(boxStore: BoxStore) : Repository<Drink> {
    internal var box: Box<DrinkEntity> = boxStore.boxFor(DrinkEntity::class.java)

    internal val alkohlfreiesBier = DrinkEntity("Bier (alkoholfrei)")
    internal val alkoholfreieGetraenke = DrinkEntity("alkoholfreie Getränke")
    internal val alkoholfreiesHefeweizen = DrinkEntity("Bier (alkoholfreies Hefeweizen)")
    internal val alokohlfreieCocktails = DrinkEntity("Cocktails (alkoholfrei)")
    internal val altenmuenster = DrinkEntity("Bier (Altenmünster)")
    internal val aperolSpriz = DrinkEntity("Aperol Spriz")
    internal val apfelschorle = DrinkEntity("Apfelschorle")
    internal val sprite = DrinkEntity("Sprite")
    internal val ayran = DrinkEntity("Ayran", "türkisches Joghurt-Getränk")
    internal val becks = DrinkEntity("Beck's Biere")
    internal val becksGold = DrinkEntity("Bier (Beck's Gold)")
    internal val bionade = DrinkEntity("Bionade")
    internal val chillyWilli = DrinkEntity("Chilly Willi")
    internal val cocktails = DrinkEntity("Cocktails")
    internal val cola = DrinkEntity("Cola")
    internal val colaLight = DrinkEntity("Cola Light")
    internal val cubaLibre = DrinkEntity("Cuba Libre")
    internal val erfrischungsgetraenke = DrinkEntity("Erfrischungsgetränke")
    internal val export = DrinkEntity("Bier (Export)")
    internal val landbier = DrinkEntity("Bier (Landbier)")
    internal val fanta = DrinkEntity("Fanta")
    internal val gazoz = DrinkEntity("Gazos", "türkische Limonade")
    internal val ginTonic = DrinkEntity("Gin Tonic")
    internal val grichischerWein = DrinkEntity("Wein (Griechisch)")
    internal val gutmann = DrinkEntity("Bier (Gutmann Hefeweizen)")
    internal val hefeweizen = DrinkEntity("Bier (Hefeweizen)")
    internal val helga = DrinkEntity("Helga")
    internal val hugo = DrinkEntity("Hugo")
    internal val jackieCola = DrinkEntity("Jackie-Cola")
    internal val joster = DrinkEntity("Joster")
    internal val kaffee = DrinkEntity("Kaffee")
    internal val kellerpils = DrinkEntity("Bier (Kellerpils)")
    internal val koelsch = DrinkEntity("Bier (Kölsch)")
    internal val kraeuterschnaps = DrinkEntity("Kräuterschnaps")
    internal val lilletBarry = DrinkEntity("LilletBarry")
    internal val lockstedter = DrinkEntity("Lockstedter")
    internal val longdrinkPitcher = DrinkEntity("Longdrink-Pitcher")
    internal val longdrinks = DrinkEntity("Longdrinks")
    internal val metaxa = DrinkEntity("Metaxa")
    internal val mineralwasser = DrinkEntity("Mineralwasser")
    internal val mixgetraenke = DrinkEntity("Mixgetränke")
    internal val neuerWein = DrinkEntity("Neuer Wein")
    internal val orangensaft = DrinkEntity("Orangensaft")
    internal val ouzo = DrinkEntity("Ouzo")
    internal val pfefferminzlikoer = DrinkEntity("Pfefferminzlikör")
    internal val pils = DrinkEntity("Bier (Pils)")
    internal val prinzAlteHimbeere = DrinkEntity("Prinz Alte Himbeere", "österreichische Traditionsbrennerei")
    internal val prinzAlteMarielle = DrinkEntity("Prinz Alte Marielle", "österreichische Traditionsbrennerei")
    internal val radler = DrinkEntity("Radler")
    internal val raki = DrinkEntity("Raki")
    internal val schnaps = DrinkEntity("Schnaps")
    internal val schnapsmeter = DrinkEntity("Schnapsmeter")
    internal val secco = DrinkEntity("Secco")
    internal val sekt = DrinkEntity("Sekt")
    internal val sinalco = DrinkEntity("Sinalco")
    internal val slush = DrinkEntity("Slush")
    internal val softdrinks = DrinkEntity("Softdrinks")
    internal val sommerschorle = DrinkEntity("Sommerschorle")
    internal val sprudelSauer = DrinkEntity("Sprudel (Sauer)")
    internal val sprudelSuess = DrinkEntity("Sprudel (Süß)")
    internal val strawberryMagarita = DrinkEntity("Strawberry Magarita")
    internal val tee = DrinkEntity("Tee")
    internal val tequilla = DrinkEntity("Tequilla")
    internal val traubensaft = DrinkEntity("Traubensaft")
    internal val traubensaftschorle = DrinkEntity("Traubensaftschorle")
    internal val traubenSeccoAlkohlfrei = DrinkEntity("Trauben Secco alkohlfrei")
    internal val tuerkischerTee = DrinkEntity("Tee (türkisch)")
    internal val verschiedeneBiere = DrinkEntity("verschiedene Biere")
    internal val wasser = DrinkEntity("Wasser")
    internal val wein = DrinkEntity("Wein")
    internal val weinbergpfirsichlikoer = DrinkEntity("Weinbergpfirsichlikör")
    internal val weinschorle = DrinkEntity("Weinschorle")
    internal val wodkaAhoi = DrinkEntity("Wodka-Ahoi")
    internal val wulle = DrinkEntity("Bier (Wulle Vollbier Hell)")

    init {
        prefill()
    }

    override fun getAll(): List<Drink> {
        return box.all.map { DrinkConverter.from(it) }
    }


    override fun get(i: Int): Drink {
        return DrinkConverter.from(box.get(i.toLong()))
    }

    override fun get(name: String): Drink {
        val entity = box.query().equal(DrinkEntity_.name, name).build().findUnique() ?: throw EntityNotFoundException()
        return DrinkConverter.from(entity)
    }


    fun prefill() {
        box.removeAll()
        box.put(
                alkohlfreiesBier,
                alkoholfreieGetraenke,
                alkoholfreiesHefeweizen,
                alokohlfreieCocktails,
                altenmuenster,
                aperolSpriz,
                apfelschorle,
                ayran,
                becks,
                becksGold,
                bionade,
                chillyWilli,
                cocktails,
                cola,
                colaLight,
                cubaLibre,
                erfrischungsgetraenke,
                export,
                fanta,
                gazoz,
                ginTonic,
                grichischerWein,
                gutmann,
                hefeweizen,
                helga,
                hugo,
                jackieCola,
                joster,
                kaffee,
                kellerpils,
                koelsch,
                kraeuterschnaps,
                lilletBarry,
                lockstedter,
                longdrinkPitcher,
                longdrinks,
                metaxa,
                mineralwasser,
                mixgetraenke,
                neuerWein,
                orangensaft,
                ouzo,
                pfefferminzlikoer,
                pils,
                prinzAlteHimbeere,
                prinzAlteMarielle,
                radler,
                raki,
                schnaps,
                schnapsmeter,
                secco,
                sekt,
                sinalco,
                slush,
                softdrinks,
                sommerschorle,
                sprudelSauer,
                sprudelSuess,
                strawberryMagarita,
                tee,
                tequilla,
                traubensaft,
                traubensaftschorle,
                traubenSeccoAlkohlfrei,
                tuerkischerTee,
                verschiedeneBiere,
                wasser,
                wein,
                weinbergpfirsichlikoer,
                weinschorle,
                wodkaAhoi,
                wulle
        )
    }

}
