package de.heilsen.ganzhornfest.app.data

import de.heilsen.ganzhornfest.domain.entity.Drink
import de.heilsen.ganzhornfest.domain.repository.Repository
import io.objectbox.Box
import io.objectbox.BoxStore

class ObjectBoxDrinkRepository(boxStore: BoxStore) : Repository<Drink> {

    private var box: Box<DrinkEntity> = boxStore.boxFor(DrinkEntity::class.java)

    internal val cola = DrinkEntity("Cola")
    internal val fanta = DrinkEntity("Fanta")
    internal val mineralwasser = DrinkEntity("Mineralwasser")
    internal val kaffee = DrinkEntity("Kaffee")
    internal val kellerpils = DrinkEntity("Bier (Kellerpils)")
    internal val hefeweizen = DrinkEntity("Bier (Hefeweizen)")
    internal val alkoholfreiesHefeweizen = DrinkEntity("Bier (alkoholfreies Hefeweizen)")
    internal val radler = DrinkEntity("Radler")
    internal val erfrischungsgetraenke = DrinkEntity("Erfrischungsgetränke")
    internal val italienischerWein = DrinkEntity("Wein (Italienisch)")
    internal val aperolSpriz = DrinkEntity("Aperol Spriz")
    internal val campariOrange = DrinkEntity("Campari Orange")
    internal val softdrinks = DrinkEntity("Softdrinks")
    internal val wasser = DrinkEntity("Wasser")
    internal val sekt = DrinkEntity("Sekt")
    internal val cocktails = DrinkEntity("Cocktails")
    internal val mixgetraenke = DrinkEntity("Mixgetränke")
    internal val hugo = DrinkEntity("Hugo")
    internal val lilletBarry = DrinkEntity("LilletBarry")
    internal val strawberryMagarita = DrinkEntity("Strawberry Magarita")
    internal val pils = DrinkEntity("Bier (Pils)")
    internal val longdrinks = DrinkEntity("Longdrinks")
    internal val slush = DrinkEntity("Slush")
    internal val wein = DrinkEntity("Wein")
    internal val orangensaft = DrinkEntity("Orangensaft")
    internal val sprudelSuess = DrinkEntity("Sprudel (Süß)")
    internal val sprudelSauer = DrinkEntity("Sprudel (Sauer)")
    internal val traubenSecco = DrinkEntity("Trauben Secco alkohlfrei")
    internal val apfelschorle = DrinkEntity("Apfelschorle")
    internal val sinalco = DrinkEntity("Sinalco")
    internal val bitterOrange = DrinkEntity("Bitter Orange")
    internal val bitterLemon = DrinkEntity("Bitter Lemon")
    internal val bionade = DrinkEntity("Bionade")
    internal val altenmuenster = DrinkEntity("Bier (Altenmünster)")
    internal val weinschorle = DrinkEntity("Weinschorle")
    internal val traubensaftschorle = DrinkEntity("Traubensaftschorle")
    internal val neuerWein = DrinkEntity("Neuer Wein")
    internal val alkoholfreieGetraenke = DrinkEntity("alkoholfreie Getränke")
    internal val grichischerWein = DrinkEntity("Wein (Griechisch)")
    internal val schnaps = DrinkEntity("Schnaps")
    internal val ouzo = DrinkEntity("Ouzo")
    internal val metaxa = DrinkEntity("Metaxa")
    internal val kraeuterschnaps = DrinkEntity("Kräuterschnaps")
    internal val schnapsmeter = DrinkEntity("Schnapsmeter")
    internal val longdrinkPitcher = DrinkEntity("Longdrink-Pitcher")
    internal val saft = DrinkEntity("Saft")
    internal val alkohlfreiesBier = DrinkEntity("Bier (alkoholfrei)")
    internal val alokohlfreieCocktails = DrinkEntity("Cocktails (alkoholfrei)")
    internal val gutmann = DrinkEntity("Bier (Gutmann Hefeweizen)")
    internal val wulle = DrinkEntity("Bier (Wulle Vollbier Hell)")
    internal val tequilla = DrinkEntity("Tequilla")
    internal val pfefferminzlikoer = DrinkEntity("Pfefferminzlikör")
    internal val joster = DrinkEntity("Joster")
    internal val koelsch = DrinkEntity("Bier (Kölsch)")
    internal val verschiedeneBiere = DrinkEntity("verschiedene Biere")
    internal val helga = DrinkEntity("Helga")
    internal val sommerschorle = DrinkEntity("Sommerschorle")

    init {
        prefill()
    }

    override fun getAll(): List<Drink> {
        return box.all.map { DrinkEntityDelegate(it) }
    }


    override fun get(i: Int): Drink {
        return DrinkEntityDelegate(box.get(i.toLong()))
    }

    override fun get(name: String): Drink {
        return Drink("asdf") // DrinkEntityDelegate(box.query().equal(DrinkEntity_.name, name).build().findUnique()!!)
    }

    fun asdf() {
        //TODO continue with Kolpingsfamilile
    }


    fun prefill() {
        box.removeAll()
        box.put(
                cola,
                fanta,
                mineralwasser,
                kaffee,
                kellerpils,
                hefeweizen,
                alkoholfreiesHefeweizen,
                radler,
                erfrischungsgetraenke,
                italienischerWein,
                aperolSpriz,
                campariOrange,
                softdrinks,
                wasser,
                sekt,
                cocktails,
                mixgetraenke,
                hugo,
                lilletBarry,
                strawberryMagarita,
                pils,
                longdrinks,
                slush,
                wein,
                orangensaft,
                sprudelSuess,
                sprudelSauer,
                traubenSecco,
                apfelschorle,
                sinalco,
                bitterOrange,
                bitterLemon,
                bionade,
                altenmuenster,
                weinschorle,
                traubensaftschorle,
                neuerWein,
                alkoholfreieGetraenke,
                grichischerWein,
                schnaps,
                ouzo,
                metaxa,
                kraeuterschnaps,
                schnapsmeter,
                longdrinkPitcher,
                saft,
                alkohlfreiesBier,
                alokohlfreieCocktails,
                gutmann,
                wulle,
                tequilla,
                pfefferminzlikoer,
                joster,
                koelsch,
                verschiedeneBiere,
                helga,
                sommerschorle

        )
    }

}
